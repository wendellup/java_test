package cn.egame.common.logging.server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

import cn.egame.common.logging.ObjectSerializer;

/**
 * Description 实现多线程处理接受到的数据报文，记录到文件中
 */
public class EventManager
{
    
    private static Logger logger = Logger.getLogger(EventManager.class);
    
    private static Map<String, EventManager> managerMap = new HashMap<String, EventManager>();
    
    private String name;
    
    private Executor excutor;
    
    private LinkedBlockingQueue<Runnable> workQueue;
    
    /**
     * 默认构造方法：默认配置如下：name=defaultEventManager;corePoolSize=2*内核数;maximumPoolSize
     * =4*内核数;keepAliveTime=10*60;workQueueMaxSize=50000
     */
    public EventManager()
    {
        this("defaultEventManager", 2 * Runtime.getRuntime().availableProcessors(), 4 * Runtime.getRuntime()
            .availableProcessors(), 10 * 60, 50000);
    }
    
    public EventManager(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, int workQueueMaxSize)
    {
        this.name = name;
        this.workQueue = new LinkedBlockingQueue<Runnable>(workQueueMaxSize);
        this.excutor =
            new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        
        managerMap.put(this.name, this);
        logger.info("[EventManager]Create EventManager successfully!/name=" + name + ";corePoolSize=" + corePoolSize
            + ";maximumPoolSize=" + maximumPoolSize + ";keepAliveTime=" + keepAliveTime + ";workQueueMaxSize="
            + workQueueMaxSize);
    }
    
    public static EventManager getManagerByName(String name)
    {
        return managerMap.get(name);
    }
    
    public static boolean pubEvent(UDPSocketServer server, DatagramPacket dp)
    {
        boolean flag = true;
        
        logger.info("[EventManger]pubEvent:event=" + dp + "/managerMap=" + managerMap);
        synchronized (managerMap)
        {
            for (Entry<String, EventManager> entry : managerMap.entrySet())
            {
                flag = flag & entry.getValue().pub(server, dp);
            }
        }
        return flag;
    }
    
    private boolean pub(final UDPSocketServer server, DatagramPacket dp)
    {
        boolean flag = true;
        try
        {
            if (dp == null)
            {
                throw (new Exception("dp is null"));
            }
            
            excutor.execute(new HandlerRunner(server, dp));
            logger.info("[EventManger]" + name + ".size()=" + workQueue.size());
        }
        catch (Exception e)
        {
            logger.error("[EventManger]pub failed, dp, dp:" + dp, e);
            flag = false;
        }
        
        return flag;
    }
    
    class HandlerRunner implements Runnable
    {
        private DatagramPacket dp;
        
        private UDPSocketServer server;
        
        HandlerRunner(UDPSocketServer server, DatagramPacket dp)
        {
            this.dp = dp;
            this.server = server;
        }
        
        @Override
        public void run()
        {
            byte[] buf = dp.getData();
            InetAddress inetAddress = dp.getAddress();
            logger.info("Connected to client at " + inetAddress);
            // String socketMsg = new String(buf, 0, dp.getLength());
            
            buf[dp.getLength()] = '\0';
            
            LoggingEvent event;
            try
            {
                event = (LoggingEvent)ObjectSerializer.getInstance().deSerialize(buf);
                
                Logger remoteLogger = null;
                
                LoggerRepository h = (LoggerRepository)UDPSocketServer.hierarchyMap.get(inetAddress);
                if (h == null)
                {
                    logger.error("h is null");
                    h = UDPSocketServer.configureHierarchy(inetAddress);
                }
                remoteLogger = h.getLogger(event.getLoggerName());
                
                if (event.getLevel().isGreaterOrEqual(remoteLogger.getEffectiveLevel()))
                {
                    // finally log the event as if was generated locally
                    remoteLogger.callAppenders(event);
                }
            }
            catch (Exception e)
            {
                logger.error("remoteLogger handler faild. err: ", e);
            }
        }
    }
}
