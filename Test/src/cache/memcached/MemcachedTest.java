package cache.memcached;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.util.Utils;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedTest {
	static {
		
        Utils.initLog4j();
    }
    static Logger logger = Logger.getLogger("OpenHandler");
    static byte[] SyncRoot = new byte[0];

    public static ICacheClient getCache() {
        return SCacheClient.getInstance("open");
    }
    
    private static String key = "";
    
    private List<String> keyList = new ArrayList<String>();
    
    
    @Test
    public void initKeyList(){
    	Random random = new Random();
    	for(int i=0; i<1000; i++){
    		String key = "";
    		for(int j=0; j<250; j++){
    			char c = (char) (random.nextInt(26)+65);
    			key = key + c;
    		}
    		keyList.add(key);
    	}
    }
    
    @Test
    public void getKeyList(){
    	initKeyList();
    	for(int i=0; i<keyList.size(); i++){
    		System.out.println(keyList.get(i));
    	}
    }
    
    
    @Test
    public void setMemcachedKey(){
    	initKeyList();
    	for(int i=0; i<keyList.size(); i++){
    		String key = keyList.get(i);
    		getCache().set(key, key);
    		System.out.println("第"+i+"个key:" + key);
    		System.out.println("对应的value为 :" + getCache().get(key));
    	}
    }
    
    @Test
    public void testMemcachedKeyLength(){
    	for(int i=0; i<1000; i++){
    		long beginMillis = System.currentTimeMillis();
    		System.out.println(getCache().get("mem"));
    		System.out.println("第"+i+"次取长度为:"+key.length()+"的key耗时:"+(System.currentTimeMillis()- beginMillis));
    		
    	}
    }
    
    @Test
    public void testMemcachedSetGet(){
    	String serverURL = "192.168.251.53:20003";
    	
            logger.info("setMemcachedPool:" + serverURL);
            
            if (serverURL == null || serverURL.length() < 1)
                return;
            
            SockIOPool pool = SockIOPool.getInstance();
            // set the servers and the weights
            pool.setServers(new String[] {serverURL});
            // pool.setWeights( weights );
            
            // set some basic pool settings
            // 5 initial, 5 min, and 250 max conns
            // and set the max idle time for a conn
            // to 30
            pool.setInitConn(5);
            pool.setMinConn(5);
            pool.setMaxConn(500);
            pool.setMaxIdle(1000 * 60 * 30);
            
            // set the sleep for the maint thread
            // it will wake up every x seconds and
            // maintain the pool size
            pool.setMaintSleep(30);
            
            // set some TCP settings
            // disable nagle
            // set the read timeout to 3 secs
            // and don't set a connect timeout
            pool.setNagle(false);
            pool.setSocketTO(3000);
            pool.setSocketConnectTO(0);
            
            // initialize the connection pool
            pool.initialize();
             
            MemCachedClient memCached = new MemCachedClient();
            memCached.setPrimitiveAsString(true);
            memCached.setSanitizeKeys(false);
            
            memCached.set("", "abc1");
            System.out.println("----------->");
            System.out.println(memCached.get(""));
            
            // memCached.setCompressEnable(true);
            // memCached.setCompressThreshold(64 * 1024);
        }
    	
//    		boolean isNullInstanceOfObj = null instanceof Object;
//    		System.out.println(isNullInstanceOfObj);
    
}
