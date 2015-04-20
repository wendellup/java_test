package cache.memcached;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.util.Utils;

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
    
    
}
