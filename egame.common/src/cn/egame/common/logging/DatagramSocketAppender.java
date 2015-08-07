package cn.egame.common.logging;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * log4j1.x 新增UDP记录日志的方法 <br />
 * 在UDP发送数据之前，数据记入 eventList 中，若是数据大于队列大小，数据直接丢失。
 * 
 * @author WangHuan
 * @Date 2015-01-19
 */
public class DatagramSocketAppender extends AppenderSkeleton
{
    private static Logger logger = Logger.getLogger(DatagramSocketAppender.class.getName());
    
    private String remoteHost;
    
    private int port;
    
    private int packetMaxLength;
    
    private DatagramSocket ds;
    
    private int eventListLength = 10000;
    
    /**
     * 不是多线程安全的，后期使用多线程的话，需要换线程安全的list
     */
    ArrayBlockingQueue<LoggingEvent> queue;
    
    private Handler handler = new Handler();
    
    private boolean handlerStatus = false;
    
    public DatagramSocketAppender()
    {
    }
    
    public DatagramSocketAppender(String host, int port, int packetMaxLength, int eventListLength)
    {
        this.port = port;
        this.remoteHost = host;
        this.packetMaxLength = packetMaxLength;
        this.eventListLength = eventListLength;
    }
    
    public void activateOptions()
    {
        if (this.ds == null)
        {
            connect(this.remoteHost, this.port);
        }
        
        // if (this.eventList == null) {
        // // this.eventList = new ArrayList<LoggingEvent>(eventListLength);
        // this.eventList = Collections.synchronizedList(new
        // ArrayList<LoggingEvent>(eventListLength));
        // }
        
        if (queue == null)
        {
            this.queue = new ArrayBlockingQueue<LoggingEvent>(eventListLength);
        }
    }
    
    static InetAddress getAddressByName(String host)
    {
        try
        {
            return InetAddress.getByName(host);
        }
        catch (Exception e)
        {
            LogLog.error("Could not find address of [" + host + "].", e);
        }
        return null;
    }
    
    public void close()
    {
        if (this.closed)
            return;
        this.closed = true;
        cleanUp();
    }
    
    public void cleanUp()
    {
        if ((this.ds != null) && (this.ds.isConnected()))
            this.ds.close();
    }
    
    void connect(String host, int port)
    {
        if (this.remoteHost == null)
            return;
        try
        {
            cleanUp();
            this.ds = new DatagramSocket();
            this.ds.connect(getAddressByName(this.remoteHost), port);
        }
        catch (IOException e)
        {
            String msg = "Could not connect to remote log4j server at [" + this.remoteHost + "].";
            LogLog.error(msg);
        }
    }
    
    public void append(LoggingEvent event)
    {
        if (event == null)
        {
            return;
        }
        
        if (this.remoteHost == null)
        {
            this.errorHandler.error("No remote host is set");
            return;
        }
        
        if (this.ds == null)
        {
            return;
        }
        
        if (queue == null)
        {
            this.errorHandler.error("DatagramSocketAppender queue is null");
            return;
        }
        
        // if (queue.size() >= eventListLength) {
        // this.errorHandler.error("DatagramSocketAppender queue is full, size is "
        // + eventListLength);
        // return;
        // }
        
        // add event
        queue.offer(event);
        
        // start handler process
        if (!handlerStatus)
        {
            Thread ht = new Thread(handler);
            ht.start();
            handlerStatus = true;
        }
    }
    
    public boolean requiresLayout()
    {
        return false;
    }
    
    public void setRemoteHost(String host)
    {
        this.remoteHost = host;
    }
    
    public String getRemoteHost()
    {
        return this.remoteHost;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public int getPort()
    {
        return this.port;
    }
    
    public int getPacketMaxLength()
    {
        return this.packetMaxLength;
    }
    
    public void setPacketMaxLength(int packetMaxLength)
    {
        this.packetMaxLength = packetMaxLength;
    }
    
    public int getEventListLength()
    {
        return eventListLength;
    }
    
    public void setEventListLength(int eventListLength)
    {
        this.eventListLength = eventListLength;
    }
    
    private class Handler implements Runnable
    {
        
        @Override
        public void run()
        {
            
            while (true)
            {
                try
                {
                    int count = 1;
                    if (DatagramSocketAppender.this.queue.isEmpty())
                    {
                        try
                        {
                            Thread.sleep(10);
                        }
                        catch (InterruptedException e)
                        {
                            logger.error("", e);
                        }
                        continue;
                    }
                    
                    try
                    {
                        // List<LoggingEvent> eventList = new ArrayList<LoggingEvent>();
                        // int listLength = DatagramSocketAppender.this.queue.drainTo(eventList);
                        // if (listLength == 0) {
                        // continue;
                        // }
                        //
                        // for (LoggingEvent event : eventList) {
                        // byte[] b =
                        // ObjectSerializer.getInstance().serialize(DatagramSocketAppender.this.queue.take());
                        // // DatagramPacket dp = new DatagramPacket(b, (b.length >
                        // // packetMaxLength) ? packetMaxLength : b.length);
                        // DatagramPacket dp = new DatagramPacket(b, b.length);
                        // DatagramSocketAppender.this.ds.send(dp);
                        // count++;
                        // }
                        
                        byte[] b = ObjectSerializer.getInstance().serialize(DatagramSocketAppender.this.queue.take());
                        // DatagramPacket dp = new DatagramPacket(b, (b.length >
                        // packetMaxLength) ? packetMaxLength : b.length);
                        DatagramPacket dp = new DatagramPacket(b, b.length);
                        DatagramSocketAppender.this.ds.send(dp);
                        count++;
                    }
                    catch (IOException e)
                    {
                        if (ds == null || ds.isClosed())
                        {
                            logger.warn("Detected problem with connection: ds is null.", e);
                            DatagramSocketAppender.this.connect(remoteHost, port);
                        }
                        else
                        {
                            try
                            {
                                Thread.sleep(500);
                            }
                            catch (InterruptedException e1)
                            {
                            }
                        }
                        LogLog.warn("Detected problem with connection: " + e);
                    }
                    
                    if (count % 10000 == 0)
                    {
                        logger.info("发送了10000条数据...任务还在继续...");
                        count = 1;
                    }
                }
                catch (Exception e)
                {
                    if (ds == null || ds.isClosed())
                    {
                        logger.error("ds is null. ", e);
                        DatagramSocketAppender.this.connect(remoteHost, port);
                    }
                    else
                    {
                        try
                        {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException e1)
                        {
                        }
                    }
                    logger.error("", e);
                }
            }
        }
    }
}
