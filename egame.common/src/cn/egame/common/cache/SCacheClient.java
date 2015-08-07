package cn.egame.common.cache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.cache.factory.MemCacheClient;
import cn.egame.common.cache.factory.RedisCacheClient;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.nosql.factory.MongoDBClient;
import cn.egame.common.util.Utils;

public class SCacheClient implements Runnable {
    private static byte[] lock = new byte[0];
    private static Logger logger = Logger.getLogger(SCacheClient.class.getSimpleName());

    private static Map<String, ICacheClient> hash = new HashMap<String, ICacheClient>();

    public static ICacheClient getInstance() {
        return getInstance(null);
    }

    // private static ICacheClient getInstance(String server) {
    // ICacheClient instance = hash.get(server);
    // if (instance == null)
    // synchronized (lock) {
    // instance = hash.get(server);
    // if (instance == null) {
    // instance = init(server);
    // }
    // }
    // return instance;
    // }

    public static ICacheClient getInstance(String server) {

        ICacheClient instance = hash.get(server);
        if (instance == null)
            synchronized (lock) {
                instance = hash.get(server);
                if (instance == null) {
                    List<CacheClientBase> list = init(server);
                    instance = new CacheClientAdpter(list);
                    
                    hash.put(server, instance);
                }
            }
        return instance;
    }

    private static List<CacheClientBase> init(String server) {
        try {
        	
        	List list = new ArrayList<ICacheClient>();
            String temp = Utils.stringIsNullOrEmpty(server) ? "" : "." + server;
            Properties properties = Utils.getProperties("server.properties");
            String cache = properties.getProperty("cache" + temp, "redis");
            logger.info("cache" + temp + "=" + cache);

            if (Utils.stringCompare("redis", cache)) {
            	 String urls = properties.getProperty("cache" + temp + ".redis.server_url");
            	 if(Utils.stringIsNullOrEmpty(urls)){
            	     logger.warn("server_url is null");
            	     return null;
            	 }
            	 String[] urlArr = urls.split(",");
            	 for (int i = 0; i <urlArr.length ; i++){
            		String url = urlArr[i];
            		int slowTimeMillion = Utils.toInt(properties.getProperty("cache" + temp + ".redis.slowTimeMillion"), 50);
                 	boolean cacheSwitch = Utils.toBoolean(properties.getProperty("cache" + temp + ".redis.switch"), true);
                     boolean cacheNotNullList = Utils.toBoolean(properties.getProperty("cache" + temp + ".redis.not_null_list"), false);

                     boolean localCacheSwitch = Utils.toBoolean(properties.getProperty("cache" + temp + ".redis.local.switch"), true);

                    

                     if (!cacheSwitch) {// 如果关闭，不去连接
                         logger.warn("redis is turn off ,return null,url = " + url);
                         return null;
                     }

                     if (Utils.stringIsNullOrEmpty(url))
                         throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError, "cache" + temp + ".redis.server_url=null");

                     RedisCacheClient client = new RedisCacheClient();
                     client.setCachePool(url);
                     client.setBroadcast(properties.getProperty("cache" + temp + ".redis.broadcast_address"),
                             Integer.parseInt(properties.getProperty("cache" + temp + ".redis.broadcast_port")));
                     hash.put(server, client);
                     client.slowTimeMillion = slowTimeMillion;
                     client.cacheSwitch = cacheSwitch;
                     client.cacheNotNullList = cacheNotNullList;
                     client.localCacheSwitch = localCacheSwitch;
                     list.add(client); 
            	   }
                    return list;
                     
            	
            } else if (Utils.stringCompare("memcached", cache)) {
                String url = properties.getProperty("cache" + temp + ".memcached.server_url");
                boolean cacheSwitch = Utils.toBoolean(properties.getProperty("cache" + temp + ".memcached.switch"), true);
                boolean cacheNotNullList = Utils.toBoolean(properties.getProperty("cache" + temp + ".memcached.not_null_list"), false);

                boolean localCacheSwitch = Utils.toBoolean(properties.getProperty("cache" + temp + ".memcached.local.switch"), true);

                if (!cacheSwitch) {// 如果关闭，不去连接
                    logger.warn("memcache is turn off ,return null,url = " + url);
                    return null;
                }

                if (Utils.stringIsNullOrEmpty(url))
                    throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError, "cache" + temp + ".memcached.server_url=null");
                MemCacheClient client = new MemCacheClient();
                client.setCachePool(url);
                client.setBroadcast(properties.getProperty("cache" + temp + ".memcached.broadcast_address"),
                        Integer.parseInt(properties.getProperty("cache" + temp + ".memcached.broadcast_port")));
                hash.put(server, client);
                client.cacheSwitch = cacheSwitch;
                client.cacheNotNullList = cacheNotNullList;
                client.localCacheSwitch = localCacheSwitch;
                list.add(client);
                return list;
            } else if (Utils.stringCompare("mongo", cache)) {
                String url = properties.getProperty("cache" + temp + ".mongo.server_url");
                if (Utils.stringIsNullOrEmpty(url))
                    throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError, "cache" + temp + ".mongo.server_url=null");
                MongoDBClient client = new MongoDBClient(server);
                hash.put(server, client);
                list.add(client);
                return list;
            } else
                throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError, "cache" + temp + "[redis|memcached|mongo]==" + cache);
        } catch (FileNotFoundException e) {
            logger.error(null, e);
        } catch (NumberFormatException e) {
            logger.error(null, e);
        } catch (IOException e) {
            logger.error(null, e);
        }

        return null;
    }

    public static void main(String[] args) {

        try {
            // Assert.
            Utils.initLog4j();
            SCacheClient c = new SCacheClient();
            SCacheClient.getInstance().set("test", "v");
            for (int i = 0; i < 500; i++) {
                Thread t = new Thread(c);
                t.start();
            }

        } catch (Exception ex) {
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int i = 0;
        long t = Utils.getCurrentTime();
        try {
            while (i++ < 1000) {
                // SCacheClient.getInstance().get("test");
                // System.out.println(SCacheClient.getInstance().get("test"));
                Thread.sleep(10);
            }
        } catch (Exception ex) {
            logger.error(null, ex);
        }
        logger.info("run:" + (Utils.getCurrentTime() - t));
    }
}
