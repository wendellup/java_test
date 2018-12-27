package cache.redis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.egame.common.util.Utils;


public class JedisUtils {
	private static Logger logger = Logger.getLogger(JedisUtils.class);
	
	private static final String REDIS_SERVER = "192.168.251.56";
	private static final int REDIS_PORT = 6380;
	private static final int MaxActive = 500;
	private static final int MaxIdle = 10;
	private static final long MaxWait = 5000l;
//	private Jedis jedis;// 非切片额客户端连接
	private JedisPool jedisPool;// 非切片连接池
	private static JedisUtils instance;
	private JedisUtils() {
		initialPool();
	}
	private void initialPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(MaxActive);
		config.setMaxIdle(MaxIdle);
		config.setMaxWait(MaxWait);
		//config.setWhenExhaustedAction(whenExhaustedAction)
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, REDIS_SERVER, REDIS_PORT);
	}
	public synchronized static JedisUtils getInstance() {
		if (instance == null) {
			instance = new JedisUtils();
		}
		return instance;
	}
//	public void doOperation(JedisOperation operation) {
//		jedis = jedisPool.getResource();
//		operation.doProcee(jedis);
//		jedisPool.returnResource(jedis);
//		jedis = null;
//	}

	public static abstract class JedisOperation {
		public static final String PREFIX = "realtime";
		public static final String DETELITER = ",";
		public static final String REPLACE = "&;&";
		public abstract void doProcee(Jedis jedis);
		public abstract String buildPrefix();
		protected String buildRedisKey(String key) {
			if (key == null || "".equals(key)) {
				return PREFIX + DETELITER + buildPrefix();
			}
			return PREFIX + DETELITER + buildPrefix() + DETELITER + key;
		}
	}
	
	public byte[] toByteArray (Object obj) {     
        byte[] bytes = null;     
        ByteArrayOutputStream bos = new ByteArrayOutputStream();     
        try {       
            ObjectOutputStream oos = new ObjectOutputStream(bos);        
            oos.writeObject(obj);       
            oos.flush();        
            bytes = bos.toByteArray ();     
            oos.close();        
            bos.close();       
        } catch (IOException ex) {       
            ex.printStackTrace();  
        }     
        return bytes;   
    }  
	
	public String set(String key, Object value){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				return jedis.set(key.getBytes(), toByteArray(value));
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		
		return null;
	}
	
	public String get(String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				return jedis.get(key);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		
		return null;
	}
	
	public String setString(String key, String val){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				return jedis.set(key, val);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		
		return null;
	}
	
	public Set<String> keys(String patternStr) {
		Jedis jedis = null;
		Set<String> matchKeys = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				matchKeys = jedis.keys(patternStr);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		return matchKeys;
	}
	
	public int getInt(String key, int defaultValue) {
		Jedis jedis = null;
		Object obj = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				obj = jedis.get(key);
				if (obj == null) {
					return defaultValue;
				}
				return Integer.valueOf(String.valueOf(obj)).intValue();
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
				jedis = null;
			}
		}
		return defaultValue;
	}
	
	public List<Integer> mGetInteger(String... keys) {
		Jedis jedis = null;
		List<String> list;
		List<Integer> intList = new ArrayList<Integer>();
		if(keys==null){
			return intList;
		}
			
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				list = jedis.mget(keys);
				if(list!=null){
					for(String str : list){
						intList.add(Utils.toInt(str, 0));
					}
				}
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
				jedis = null;
			}
		}
		return intList;
	}
	
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> list = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				list = jedis.hmget(key, fields);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
				jedis = null;
			}
		}
		return list;
	}
	
	public List<String> getMapValue(String key) {
		Jedis jedis = null;
        List<String> list = null;
            
        try {
            jedis = jedisPool.getResource();
            if(jedis!=null){
                list = jedis.hvals(key);
            }
        } catch (Exception ex) {
            logger.error("", ex);
        } finally{
            if(jedis!=null){
                jedisPool.returnResource(jedis);
                jedis = null;
            }
        }
        return list;
    }
	
	public static void main(String[] args) throws ParseException {
		Utils.initLog4j();
		
		EGamePushCacheKey value = new EGamePushCacheKey();
		String ret = JedisUtils.getInstance().set("test", value);
		System.out.println(ret);
		
//		Object commonBlackCitys = JedisUtils.getInstance().get(EGamePushCacheKey.listPushCommonBlackCitys());
		Object commonBlackCitys = JedisUtils.getInstance().get("test");
		
//		Object commonBlackCitys2 = JedisUtils.getInstance().get(EGamePushCacheKey.listPushCommonBlackCitys());
		Object commonBlackCitys2 = JedisUtils.getInstance().get("test");
		
		System.out.println("commonBlackCitys:"+commonBlackCitys.hashCode());
		System.out.println("commonBlackCitys2:"+commonBlackCitys2.hashCode());
		
		
    }
		
}
