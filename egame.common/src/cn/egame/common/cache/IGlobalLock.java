package cn.egame.common.cache;

public interface IGlobalLock {
	public boolean lock(String key, int seconds);

	public boolean unlock(String key);
}
