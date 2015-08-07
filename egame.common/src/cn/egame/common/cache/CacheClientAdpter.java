package cn.egame.common.cache;

import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPubSub;
import cn.egame.common.exception.ExceptionCommonBase;

public class CacheClientAdpter extends CacheClientBase {
	private Logger logger = Logger.getLogger("slowCacheLog");

	private List<CacheClientBase> cacheClients;

	private boolean useCache;

	public CacheClientAdpter(List<CacheClientBase> cacheClients) {
		if (cacheClients == null || cacheClients.size() == 0) {
			return;
		}
		this.cacheClients = cacheClients;
		CacheClientBase cacheClient = cacheClients.get(0);
		this.useCache = cacheClient.cacheSwitch;
		this.cacheNotNullList = cacheClient.cacheNotNullList;
		this.localCacheSwitch = cacheClient.localCacheSwitch;
		this.slowTimeMillion = cacheClient.slowTimeMillion;

	}

	@Override
	public void setCachePool(String serverURL) throws ExceptionCommonBase {
		for(int i = 0;i < cacheClients.size(); i++){
			cacheClients.get(i).setCachePool(serverURL);
		}
	}

	public Object get(String key) {
		long time1 = System.currentTimeMillis();
        Object result = null;
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			if (!this.localCacheSwitch) {
				Object obj = cacheClient.getRemote(key);
				result = obj;
				if (obj instanceof LocalCacheObject) {
					LocalCacheObject l = (LocalCacheObject) obj;
					if (l.isLocal()) {
						// this.setLocalCache(key, obj);
						result = l.getObj();
					}
				}
			} else {
				result = cacheClient.get(key);
			}
		}

		long costTime = System.currentTimeMillis() - time1;
		if (costTime >= slowTimeMillion) {
			logger.info("[get]key:" + key + ",cost time:" + costTime);
		}

		return result;
	}

	public void set(String key, Object obj, int seconds) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
		    CacheClientBase cacheClient = cacheClients.get(no);
			long time1 = System.currentTimeMillis();

			if (!this.localCacheSwitch) {
				setRemote(key, obj, seconds);
			} else {
				cacheClient.set(key, obj, seconds);
			}

			long costTime = System.currentTimeMillis() - time1;

			if (costTime >= slowTimeMillion) {
				logger.info("[get]key:" + key + ",cost time:" + costTime);
			}
		} else {
			return;
		}

	}

	public Object getRemote(String key) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
		    CacheClientBase cacheClient = cacheClients.get(no);
			return cacheClient.getRemote(key);
		} else {
			return null;
		}
	}

	@Override
	public void removeRemote(String key) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			cacheClient.removeRemote(key);
		} else {
			return;
		}
	}

	@Override
	public void setRemote(String key, Object v, int seconds) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
		    CacheClientBase cacheClient = cacheClients.get(no);
			cacheClient.setRemote(key, v, seconds);
		} else {
			return;
		}

	}

	@Override
	public boolean addRemote(String key, Object obj, int seconds) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			return cacheClient.addRemote(key, obj, seconds);
		} else {
			return false;
		}
	}

	@Override
	public void subscribe(JedisPubSub pubsub, String... channel) {
		if (useCache) {
		    CacheClientBase cacheClient = cacheClients.get(0);
			cacheClient.subscribe(pubsub, channel);
		}

	}

	@Override
	public void psubscribe(JedisPubSub pubsub, String... partten) {
		if (useCache) {
		    CacheClientBase cacheClient = cacheClients.get(0);
			cacheClient.psubscribe(pubsub, partten);
		}
	}

	@Override
	public void publishMessage(String channel, String message) {
		if (useCache) {
		    CacheClientBase cacheClient = cacheClients.get(0);
			cacheClient.publishMessage(channel, message);
		}
	}

	@Override
	public void cas(String key, Object v, int seconds) {
		if (useCache) {
		    CacheClientBase cacheClient = cacheClients.get(0);
			cacheClient.cas(key, v, seconds);
		}
	}

	public Long incrBy(String key, int step) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			return cacheClient.incrBy(key, step);
		}
		return null;
	}

	public Long decrBy(String key, int step) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			return cacheClient.decrBy(key, step);
		}
		return null;
	}

	@Override
	public void setStr(String key, String v, int seconds) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			cacheClient.setStr(key, v, seconds);
		}
	}

	@Override
	public String getStr(String key) {
		if (useCache) {
		    int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
			return cacheClient.getStr(key);
		}

		return null;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.egame.common.cache.ICacheClient#getElementFromQueue(java.lang.String,
	 * int)
	 */
	// @Override
	// public <T> T getTFromList(String k, int index) throws ExceptionCommonBase
	// {
	// if (useCache) {
	// return (T) cacheClient.getTFromList(k, index);
	// } else {
	// return null;
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see cn.egame.common.cache.ICacheClient#popFromQueue(java.lang.String,
	// * java.lang.Object, boolean)
	// */
	// @Override
	// public <T> T popTFromList(String k, boolean isPopLeft)
	// throws ExceptionCommonBase {
	// if (useCache) {
	// return (T) cacheClient.popTFromList(k, isPopLeft);
	// } else {
	// return null;
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see cn.egame.common.cache.ICacheClient#pushToQueue(java.lang.String,
	// * java.lang.Object, boolean, int)
	// */
	// @Override
	// public <T> void setT2List(String k, T v, boolean isPushLeft, int seconds)
	// throws ExceptionCommonBase {
	// if (useCache) {
	// cacheClient.setT2List(k, v, isPushLeft, seconds);
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * cn.egame.common.cache.ICacheClient#pushBatchToQueue(java.lang.String,
	// * java.util.List, boolean, int)
	// */
	// @Override
	// public <T> void setBatch2List(String k, List<T> list, boolean isPushLeft,
	// int second) throws ExceptionCommonBase {
	// if (useCache) {
	// cacheClient.setBatch2List(k, list, isPushLeft, second);
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * cn.egame.common.cache.ICacheClient#getLRangeFromQueue(java.lang.String,
	// * int, int)
	// */
	// @Override
	// public <T> List<T> getRangeTFromList(String k, int start, int limit)
	// throws ExceptionCommonBase {
	// if (useCache) {
	// return cacheClient.getRangeTFromList(k, start, limit);
	// } else {
	// return null;
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// cn.egame.common.cache.ICacheClient#removeFromQueue(java.lang.String,
	// * java.lang.Object)
	// */
	// @Override
	// public <T> boolean removeFromList(String k, T v) throws
	// ExceptionCommonBase {
	// if (useCache) {
	// return cacheClient.removeFromList(k, v);
	// } else {
	// return false;
	// }
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// cn.egame.common.cache.ICacheClient#getLenthOfQueue(java.lang.String)
	// */
	// @Override
	// public long getLenthOfList(String k) throws ExceptionCommonBase {
	// if (useCache) {
	// return cacheClient.getLenthOfList(k);
	// }
	// return 0;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see cn.egame.common.cache.ICacheClient#insertValue(java.lang.String,
	// * java.lang.Object, int)
	// */
	// @Override
	// public <T> void insertValue(String k, T v, int index)
	// throws ExceptionCommonBase {
	// if (useCache) {
	// cacheClient.insertValue(k, v, index);
	// }
	// }
	//
	// @Override
	// public <T> void replaceList(String k, List<T> list, boolean isListHead,
	// int second) throws ExceptionCommonBase {
	// if (useCache) {
	// cacheClient.replaceList(k, list, isListHead, second);
	// }
	//
	// }

	
	public Long rpush(String key, List<?> value) {
	    if (useCache) {
	        int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
	        return cacheClient.rpush(key, value);
	    }
	    return 0L;
	}
	
	@Override
	public <T> T rpopT(Class clazz, String key) {
	    if (useCache) {
	        int no = Math.abs(key.hashCode()) % cacheClients.size();
	        CacheClientBase cacheClient = cacheClients.get(no);
            return (T) cacheClient.rpopT(clazz, key);
        }
        return null;
	}

    @Override
    public Long llen(String key) {
        if (useCache) {
            int no = Math.abs(key.hashCode()) % cacheClients.size();
            CacheClientBase cacheClient = cacheClients.get(no);
            return cacheClient.llen(key);
        }
        return 0L;
    }

    @Override
    public <T> T lindexT(Class clazz, String key, int index) {
        if (useCache) {
            int no = Math.abs(key.hashCode()) % cacheClients.size();
            CacheClientBase cacheClient = cacheClients.get(no);
            return cacheClient.lindexT(clazz, key, index);
        }
        return null;
    }
}
