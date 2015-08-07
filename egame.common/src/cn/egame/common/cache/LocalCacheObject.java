package cn.egame.common.cache;

import java.io.Serializable;

import cn.egame.common.util.Utils;

public class LocalCacheObject implements Serializable {
	private String key;

	private long stamp;

	private long expireTime = 0;

	public long getExpireTime() {
		return expireTime;
	}

	private Object obj;

	public boolean isLocal() {
		return expireTime > 0;
	}

	public LocalCacheObject() {
	}

	public LocalCacheObject(String key, Object obj, long stamp,
			int localCacheGcThreadTime, int expireSeconds) {
		this.key = key;
		this.obj = obj;
		if (expireSeconds > 0) {
			this.expireTime = stamp + expireSeconds * 1000L;
		}
		this.setStamp(stamp, localCacheGcThreadTime);
	}

	public LocalCacheObject(String key, Object obj) {
		this.key = key;
		this.obj = obj;
		this.stamp = Utils.getCurrentTime();
	}

	public LocalCacheObject(String key, Object obj, long stamp) {
		this.key = key;
		this.obj = obj;
		this.stamp = stamp;
	}

	public String getKey() {
		return key;
	}

	public Object getObj() {
		return obj;
	}

	public long getStamp() {
		return stamp;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public void setStamp(long now, int localCacheGcThreadTime) {
		if (this.expireTime == 0
				|| this.expireTime - localCacheGcThreadTime > now)
			this.stamp = now;
		else
			this.stamp = this.expireTime - localCacheGcThreadTime;
	}

	public boolean isExpired(long now, int localCacheGcThreadTime) {
		return now - this.stamp < localCacheGcThreadTime;
	}
}
