package cn.egame.common.dataserver;

public interface ICacheManager extends IDataManager{
	public void set(String key, Object obj);
	public Object get(String key);
	public boolean remove(String key);
}
