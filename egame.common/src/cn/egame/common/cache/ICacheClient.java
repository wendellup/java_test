package cn.egame.common.cache;

import java.util.List;

public interface ICacheClient
{
    public int getInt(String key, int defaultValue);
    
    public List<Integer> getListInt(String key);
    
    public List<Long> getListLong(String key);
    
    public List<String> getListString(String key);
    
    public long getLong(String key, long defaultValue);
    
    public String getString(String key);
    
    public <T> T getT(Class clazz, String key);
    
    public <T> List<T> getT(Class clazz, List<String> keys);
    
    public <T1, T2> List<T1> getT(Class clazz, List<String> keys, List<T2> losts);
    
    public <T> List<T> getListT(Class clazz, String key);
    
    public void remove(String key);
    
    public void remove(String... args);
    
    public void removeLocal(String key);
    
    public void removeRemote(String key);
    
    public Object get(String key);
    
    public void set(String key, Object v);
    
    public void set(String key, Object v, int seconds);
    
    /**
     * <b> 请不要使用 </b>
     * 
     * @deprecated
     * @param key
     * @param v
     * @param seconds
     */
    public void setStr(String key, String v, int seconds);
    
    /**
     * <b> 请不要使用 </b>
     * 
     * @deprecated
     * @param key
     */
    public String getStr(String key);
    
    /**
     * <b>把key对应的值设置为v</b> <br />
     * 只对memcached有效
     * 
     * @param key
     * @param v
     * @param seconds
     * @author WangHuan
     */
    public void cas(String key, Object v, int seconds);
    
    /**
     * <b>key对应的String 加上step</b> <br />
     * 只对redis 有效
     * 
     * @author WangHuan
     * @param key
     * @param step
     * @return
     */
    public Long incrBy(String key, int step);
    
    /**
     * <b>key对应的String 减去step</b> <br />
     * 只对redis 有效
     * 
     * @author WangHuan
     * @param key
     * @param step
     * @return
     */
    public Long decrBy(String key, int step);
    // public <T> T getTFromList(String k, int index) throws
    // ExceptionCommonBase;
    //
    // public <T> T popTFromList(String k, boolean isListHead)
    // throws ExceptionCommonBase;
    //
    // public <T> void setT2List(String k, T v, boolean isListHead, int seconds)
    // throws ExceptionCommonBase;
    //
    // public <T> void setBatch2List(String k, List<T> list, boolean isListHead,
    // int second) throws ExceptionCommonBase;
    //
    // public <T> void replaceList(String k, List<T> list, boolean isListHead,
    // int second) throws ExceptionCommonBase;
    //
    // public <T> List<T> getRangeTFromList(String k, int start, int limit)
    // throws ExceptionCommonBase;
    //
    // public <T> boolean removeFromList(String k, T v) throws
    // ExceptionCommonBase;
    //
    // public long getLenthOfList(String k) throws ExceptionCommonBase;
    //
    // public <T> void insertValue(String k, T v, int index)
    // throws ExceptionCommonBase;
    
    public Long rpush(String key, List<?> value) ;
    
    public <T> T rpopT(Class clazz, String key);
    
    public Long llen(String key);
    
    public <T> T lindexT(Class clazz, String key, int index);
}
