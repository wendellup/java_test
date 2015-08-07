package cn.egame.common.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.egame.common.broadcast.EGameBroadcast;
import cn.egame.common.broadcast.IRecvDatagramPacket;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.threadpool.SEGameExecutorService;
import cn.egame.common.util.Utils;

public abstract class CacheClientBase implements ICacheClient, IGlobalLock,
		IRecvDatagramPacket, ICacheMessagePubSub, Runnable {
	protected long slowTimeMillion = 50; // slow time ,default is 50 ms;

	protected EGameBroadcast broadcast = null;

	protected int cacheExpireTime = 3600 * 1000;

	protected Map<String, LocalCacheObject> localCached = null;

	protected int maxLocalCachedSize = 10000;

	protected int localCacheGcThreadTime = 180 * 1000;

	protected Logger logger = null;

	protected boolean cacheSwitch = true;

	protected boolean cacheNotNullList = false;

	protected boolean localCacheSwitch = true;

	protected static Object SyncRoot = new Object();

	final List<Integer> list_integer = new ArrayList<Integer>();

	final List<Long> list_long = new ArrayList<Long>();

	final List<String> list_string = new ArrayList<String>();

	public CacheClientBase() {
		localCached = Collections
				.synchronizedMap(new HashMap<String, LocalCacheObject>(
						maxLocalCachedSize));
		logger = Logger.getLogger(CacheClientBase.class);
		SEGameExecutorService.getInstance().execute(this);
	}

	public abstract boolean addRemote(String key, Object obj, int seconds);

	public abstract void setRemote(String key, Object obj, int seconds);

	public abstract Object getRemote(String key);

	public abstract void removeRemote(String key);

	public Object get(String key) {
		Object obj = null;
		try {
			obj = getLocalCached(key);
			if (obj == null) {
				obj = getRemote(key);
				if (obj instanceof LocalCacheObject) {
					LocalCacheObject l = (LocalCacheObject) obj;
					if (l.isLocal()) {
						this.setLocalCache(key, obj);
						return l.getObj();
					}
				}

				if (obj != null)
					this.setLocalCache(key, obj);
			}

		} catch (Exception ex) {
			logger.error("get", ex);
		}
		return obj;
	}

	@Override
	public void set(String key, Object obj, int seconds) {
		try {

			if (Utils.stringIsNullOrEmpty(key) || obj == null)
				return;

			LocalCacheObject l = new LocalCacheObject(key, obj,
					Utils.getCurrentTime(), this.localCacheGcThreadTime,
					seconds);
			localCached.put(key, l);
			if (l.isLocal())
				setRemote(key, l, seconds);
			else
				setRemote(key, obj, 0);

			sendClearKey(key);

		} catch (Exception ex) {
			logger.error("set memcached:", ex);
		}

	}

	public void set(String key, Object obj) {
		this.set(key, obj, 0);
	}

	public int getCacheExpireTime() {
		return cacheExpireTime;
	}

	// get local cache
	public Object getLocalCached(String key) {
		Object temp = localCached.get(key);
		if (temp != null) {
			if (temp instanceof LocalCacheObject) {
				LocalCacheObject l = (LocalCacheObject) temp;
				long date = Utils.getCurrentTime();
				if (l.isExpired(date, localCacheGcThreadTime)) {
					l.setStamp(date, localCacheGcThreadTime);
					return l.getObj();
				} else
					localCached.remove(key);
			}
		}
		return null;
	}

	@Override
	public int getInt(String key, int defaultValue) {
		Object obj = null;
		try {
			obj = get(key);
			if (obj == null)
				return defaultValue;
			else
				return Integer.valueOf(String.valueOf(obj));
		} catch (NumberFormatException ex) {
			logger.info("format error!!!toInt("
					+ (obj != null ? obj.toString() : "null") + ") key=" + key);
			return defaultValue;
		}

		// return Utils.toInt(get(key), defaultValue);
	}

	@Override
	public List<Integer> getListInt(String key) {
		List<Integer> list = null;
		try {
			Object obj = get(key);
			if (obj != null)
				list = (List<Integer>) obj;
		} catch (Exception ex) {
			logger.info("key=" + key, ex);
		}
		if (cacheNotNullList && list == null)
			list = list_integer;
		return list;
	}

	@Override
	public List<Long> getListLong(String key) {
		List<Long> list = null;
		try {
			Object obj = get(key);
			if (obj != null)
				list = (List<Long>) obj;
		} catch (Exception ex) {
			logger.info("key=" + key, ex);
		}

		if (cacheNotNullList && list == null)
			list = list_long;
		return list;
	}

	@Override
	public List<String> getListString(String key) {
		List<String> list = null;
		try {
			Object obj = get(key);
			if (obj != null)
				list = (List<String>) obj;
		} catch (Exception ex) {
			logger.info("key=" + key, ex);
		}

		if (cacheNotNullList && list == null)
			list = list_string;
		return list;
	}

	@Override
	public long getLong(String key, long defaultValue) {
		Object obj = null;
		try {
			obj = get(key);
			if (obj == null)
				return defaultValue;
			else
				return Long.valueOf(String.valueOf(obj));
		} catch (NumberFormatException ex) {
			logger.info("format error!!!toLong("
					+ (obj != null ? obj.toString() : "null") + ") key=" + key);
			return defaultValue;
		}

		// return Utils.toLong(get(key), defaultValue);
	}

	@Override
	public String getString(String key) {
		return getT(String.class, key);
	}

	@Override
	public <T> T getT(Class clazz, String key) {
		try {
			Object obj = get(key);
			if (obj != null && clazz.isInstance(obj))
				return (T) obj;
			else
				return null;
		} catch (Exception ex) {
			logger.info("key=" + key + " is not object("
					+ clazz.getSimpleName() + ")");
		}
		return null;
	}

	@Override
	public <T> List<T> getT(Class clazz, List<String> keys) {
		return this.getT(clazz, keys, null);
	}

	@Override
	public <T1, T2> List<T1> getT(Class clazz, List<String> keys, List<T2> losts) {
		List<T1> list = new ArrayList<T1>(keys.size());
		for (int i = keys.size() - 1; i >= 0; i--) {
			T1 item = (T1) this.getT(clazz, keys.get(i));
			list.add(0, item);
			if (item != null && losts != null)
				losts.remove(i);
		}
		return list;
	}

	@Override
	public <T> List<T> getListT(Class clazz, String key) {
		List<T> list = null;
		try {
			Object obj = get(key);
			if (obj != null)
				list = (List<T>) obj;
		} catch (Exception ex) {
			logger.info("key=" + key + " is not object("
					+ clazz.getSimpleName() + ")");
		}
		if (cacheNotNullList && list == null)
			list = new ArrayList<T>();
		return list;
	}

	@Override
	public void recvDatagramPacket(String senderIp, String data) {
		logger.debug("Clear Local key:" + data);
		if (Utils.stringCompare("ALL", data))
			this.localCached.clear();
		else
			this.removeLocal(data);
	}

	@Override
	public void remove(String key) {
		if (key == null)
			return;

		logger.debug("Send Remove Key:" + key);
		try {
			removeLocal(key);
		} catch (Exception ex) {
			logger.error("remove", ex);
		}
		try {
			removeRemote(key);
		} catch (Exception ex) {
			logger.error("remove", ex);
		}
	}

	@Override
	public void remove(String... args) {
		for (String key : args) {
			if (key == null)
				continue;

			try {
				removeLocal(key);
			} catch (Exception ex) {
				logger.error("remove", ex);
			}
			try {
				removeRemote(key);
			} catch (Exception ex) {
				logger.error("remove", ex);
			}
		}
	}

	@Override
	public void removeLocal(String key) {
		localCached.remove(key);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(localCacheGcThreadTime);
				if (localCached.size() > maxLocalCachedSize - 1000) { // gc
																		// before
																		// reach
																		// ceil
																		// maxLocalCachedSize
					long now = Utils.getCurrentTime();
					long expire_now = now - localCacheGcThreadTime;
					Set<String> keys = localCached.keySet();
					Object[] objs = keys.toArray();
					for (Object obj : objs) {
						LocalCacheObject item = localCached.get(obj);
						if (item == null || expire_now > item.getStamp()) {
							localCached.remove(obj);
						}
					}
				}
			} catch (Exception ex) {
				try {
					logger.error("run", ex);
				} catch (Exception e) {
					System.out.println(ex);
				}
			}
		}
	}

	public void setBroadcast(String address, int port) throws IOException {
		if (address != null && address.length() > 0 && port > 0) {
			logger.info("setBroadcast:" + address + ":" + port);
			broadcast = new EGameBroadcast(this);
			broadcast.connect(address, port);
		}
	}

	public void setCacheExpireTime(int cacheExpireTime) {
		if (cacheExpireTime > 10000)
			this.cacheExpireTime = cacheExpireTime;
	}

	public abstract void setCachePool(String serverURL)
			throws ExceptionCommonBase;

	public boolean isCacheSwitch() {
		return cacheSwitch;
	}

	protected void setLocalCache(String key, Object obj) {
		if (obj != null) {
			LocalCacheObject l = null;
			if (obj instanceof LocalCacheObject) {
				LocalCacheObject temp = (LocalCacheObject) obj;
				if (temp.isLocal()) {
					Long date = Utils.getCurrentTime();
					temp.setStamp(date, localCacheGcThreadTime);
					l = temp;
					if (!temp.isExpired(date, localCacheGcThreadTime)) {
						temp.setObj(null);
						if (temp.getExpireTime() < date) {
							// clear remote cache
							logger.info("clear remote cache(remote is expired):"
									+ key);
							this.removeRemote(key);
						}
						return;
					}
				}
			}

			if (l == null)
				l = new LocalCacheObject(key, obj, Utils.getCurrentTime());
			localCached.put(key, l);
		}
	}

	public void sendClearKey(String key) {
		if (broadcast != null) {
			try {
				logger.debug("send clear key : " + key);
				broadcast.send(key);
			} catch (IOException ex) {
				logger.error("removeCached", ex);
			}
		}
	}

	public Map<String, LocalCacheObject> getLocalCache() {
		return localCached;
	}

	public boolean lock(String key, int seconds) {
		try {
			if (getRemote("lock-" + key) != null)
				return false;

			return this.addRemote("lock-" + key, Utils.getCurrentTime()
					+ seconds, seconds);
		} catch (Exception ex) {
		}
		return false;
	}

	public boolean unlock(String key) {
		try {
			this.removeRemote("lock-" + key);
		} catch (Exception ex) {
		}
		return true;
	}

	public void cas(String key, Object v, int seconds) {

	}

	public abstract Long incrBy(String key, int step);

	public abstract Long decrBy(String key, int step);
	
    public abstract Long rpush(String key, List<?> object) ;
    
    public abstract <T> T rpopT(Class clazz, String key);
    
    public abstract Long llen(String key);
    
    public abstract <T> T lindexT(Class clazz, String key, int index);
}
