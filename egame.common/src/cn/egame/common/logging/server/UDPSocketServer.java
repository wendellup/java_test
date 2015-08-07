package cn.egame.common.logging.server;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Hashtable;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;

/**
 * log4j1.x 功能加强，UDP记录日志的服务端
 * 
 * @author WangHuan
 * @Date 2015-01-19
 */
public class UDPSocketServer
{
    private static Logger logger = Logger.getLogger(UDPSocketServer.class);
    
    static String GENERIC = "generic";
    
    static String CONFIG_FILE_EXT = ".lcf";
    
    static UDPSocketServer server;
    
    static int port;
    
    static boolean isNotInit = true;
    
    // key=inetAddress, value=hierarchy
    static Hashtable hierarchyMap;
    
    static LoggerRepository genericHierarchy;
    
    static File dir;
    
    public static void main(String argv[])
    {
        
        // init("8899", "D:\\workspace\\log4j-test-udp\\src\\log4j.properties",
        // "E:\\Project\\log4j\\127.0.0.1");
        if (argv.length == 3)
        {
            init(argv[0], argv[1], argv[2]);
        }
        else
        {
            usage("Wrong number of arguments.");
        }
        
        DatagramSocket ds = null;
        
        try
        {
            logger.info("Listening on port " + port);
            // ServerSocket serverSocket = new ServerSocket(port);
            
            DatagramPacket dp = null;
            // byte[] buf = null;
            
            ds = new DatagramSocket(port);
            int maxLen = 10240;
            while (true)
            {
                try
                {
                    // logger.info("Waiting to accept a new client.");
                    byte[] buf = new byte[maxLen];
                    dp = new DatagramPacket(buf, maxLen);
                    
                    ds.receive(dp);
                    if (isNotInit)
                    {
                        EventManager eventManager = new EventManager();
                        isNotInit = false;
                    }
                    
                    EventManager.pubEvent(server, dp);
                }
                catch (Exception e)
                {
                    logger.error("UDPSocketServer 接收数据出错...", e);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ds.close();
        }
    }
    
    static void usage(String msg)
    {
        System.err.println(msg);
        System.err.println("Usage: java " + UDPSocketServer.class.getName() + " port configFile directory");
        System.exit(1);
    }
    
    static void init(String portStr, String configFile, String dirStr)
    {
        try
        {
            port = Integer.parseInt(portStr);
        }
        catch (java.lang.NumberFormatException e)
        {
            e.printStackTrace();
            usage("Could not interpret port number [" + portStr + "].");
        }
        
        PropertyConfigurator.configure(configFile);
        
        File dir = new File(dirStr);
        if (!dir.isDirectory())
        {
            usage("[" + dirStr + "] is not a directory.");
        }
        server = new UDPSocketServer(dir);
    }
    
    public UDPSocketServer(File directory)
    {
        this.dir = directory;
        hierarchyMap = new Hashtable(16);
    }
    
    // This method assumes that there is no hiearchy for inetAddress
    // yet. It will configure one and return it.
    static LoggerRepository configureHierarchy(InetAddress inetAddress)
    {
        logger.info("Locating configuration file for " + inetAddress);
        // We assume that the toSting method of InetAddress returns is in
        // the format hostname/d1.d2.d3.d4 e.g. torino/192.168.1.1
        String s = inetAddress.toString();
        int i = s.indexOf("/");
        if (i == -1)
        {
            logger.warn("Could not parse the inetAddress [" + inetAddress + "]. Using default hierarchy.");
            return genericHierarchy();
        }
        else
        {
            String key = s.substring(i + 1);
            
            File configFile = new File(dir, key + CONFIG_FILE_EXT);
            if (configFile.exists())
            {
                Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
                hierarchyMap.put(inetAddress, h);
                new PropertyConfigurator().doConfigure(configFile.getAbsolutePath(), h);
                return h;
            }
            else
            {
                logger.warn("Could not find config file [" + configFile + "].");
                return genericHierarchy();
            }
        }
    }
    
    static LoggerRepository genericHierarchy()
    {
        if (genericHierarchy == null)
        {
            File f = new File(dir, GENERIC + CONFIG_FILE_EXT);
            if (f.exists())
            {
                genericHierarchy = new Hierarchy(new RootLogger(Level.DEBUG));
                new PropertyConfigurator().doConfigure(f.getAbsolutePath(), genericHierarchy);
            }
            else
            {
                logger.error("Could not find config file [" + f + "]. Will use the default hierarchy.");
                // genericHierarchy = LogManager.getLoggerRepository();
                genericHierarchy = null;
            }
        }
        return genericHierarchy;
    }
}
