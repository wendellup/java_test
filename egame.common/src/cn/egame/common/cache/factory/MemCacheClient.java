package cn.egame.common.cache.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisPubSub;
import cn.egame.common.cache.CacheClientBase;
import cn.egame.common.cache.MemcachePage;
import cn.egame.common.util.Utils;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class MemCacheClient extends CacheClientBase
{
    
    public static void main(String[] args)
    {
        
        try
        {
            
            MemCacheClient c = new MemCacheClient();
            c.setCachePool("192.168.70.12:20002");
            c.setBroadcast("224.10.10.11", 825);
            
            int i = 100000;
            List<Long> l = new ArrayList<Long>();
            // while (i-- > 0) {
            l.add(123123L);
            Thread.sleep(0);
            // }
            
            // c.set("test1", l);
            // Object os = c.get("test1");
            // Object o = c.get("JAVA-EXT-listUserIdByGameId:1000072");
            // o = c.get("JAVA-EXT-listSNSRankScoreUIdByGId:1000072-0-2");
            // boolean ts = c.lock("123", 123000);
            i = 50;
            while (i-- > 0)
            {
                // c.remove("JAVA-EXT-listSNSRankScoreUIdByGId:1000040-0-2");
                c.remove("JAVA-EXT-listSNSRankScoreUIdByGId:1000072-0-2");
                Object o = c.get("JAVA-EXT-listSNSRankScoreUIdByGId:1000072-0-2");
                if (o == null)
                {
                    break;
                }
                // c.remove("JAVA-EXT-listUserIdByGameId:1000072");
                Thread.sleep(1000);
            }
            
            Object o = c.get("JAVA-EXT-listSNSRankScoreUIdByGId:1000072-0-2");
            
            // Object o =
            // c.get("JAVA-EXT-listSNSRankScoreUIdByGId:1000072-0-2");
            // c.set("test", "ok");
            // c.set("test", 5, 10);
            // Thread.sleep(2000);
            
            String key = "JAVA-EXT-listSNSRankScoreUIdByGId:239892-0-2";
            String[] ids =
                new String[] {"234181", "238209", "238589", "238953", "239103", "239104", "239177", "239526", "239756",
                    "239759", "239760", "239876", "239891", "239892", "239900", "239955", "240138", "240226", "240227",
                    "240519", "240564", "240862", "241027", "241074", "241154", "241388", "241468", "241555", "241665",
                    "241755", "241829", "241833", "242255", "242366", "242374", "242380", "242388", "242513", "242533",
                    "242690", "243095", "730020", "730023", "730027", "730028", "730031", "730962", "730967", "730968",
                    "730969", "730970", "730971", "730972", "730973", "730974", "730975", "730976", "730977", "730978",
                    "730979", "730980", "730981", "730982", "730983", "730984", "730985", "730986", "730987", "830100",
                    "830107", "830108", "830109", "830110", "830111", "1000038", "1000040", "1000041", "1000043",
                    "1000044", "1000045", "1000046", "1000048", "1000049", "1000050", "1000051", "1000052", "1000056",
                    "1000057", "1000058", "1000059", "1000061", "1000062", "1000063", "1000064", "1000065", "1000066",
                    "1000067", "1000068", "1000069", "1000070", "1000071", "1000072", "1000073", "1000075", "1000076",
                    "1000077", "1000078", "1000079", "1000080", "1000081", "1000082", "1000083", "1000084", "1000085",
                    "1000086", "1000087", "1000088", "1000089", "1000090", "1000091", "1000092", "1000093", "1000094",
                    "1000095", "1000096", "1000097", "1000098", "1000099", "1000100", "1000101", "1000104", "1000105",
                    "1000106", "1000107"};
            for (String id : ids)
            {
                c.remove("JAVA-EXT-listSNSRankScoreUIdByGId:" + id + "-0-1");
                c.remove("JAVA-EXT-listSNSRankScoreUIdByGId:" + id + "-0-2");
                // c.remove("JAVA-EXT-listUserIdByGameId:" + id);
                System.out.println(id);
            }
            
            int j = 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private MemCachedClient memCached = null;
    
    public MemCacheClient()
    {
        super();
    }
    
    public MemCacheClient(String serverURL, String broadcastAddress, int broadcastPort)
        throws IOException
    {
        this();
        setCachePool(serverURL);
        setBroadcast(broadcastAddress, broadcastPort);
    }
    
    public Object getRemote(String key)
    {
        Object obj = getRemoteInternal(key);
        if (true)
            return obj;
        
        if (obj != null && obj instanceof MemcachePage)
        {
            try
            {
                MemcachePage page = (MemcachePage)obj;
                if (page.getBuffer() != null)
                {
                    return Utils.objectRead(page.getBuffer());
                }
                
                String[] arrays = Utils.toArrayString(page.getKeys());
                Map map = memCached.getMulti(arrays);
                ByteOutputStream stream = null;
                try
                {
                    byte[] bs = null;
                    stream = new ByteOutputStream();
                    int length = 0;
                    for (int i = 0; i < page.getKeys().size(); i++)
                    {
                        MemcachePage item = (MemcachePage)map.get(page.getKeys().get(i));
                        
                        if (item == null || item.getBuffer() == null)
                        {
                            logger.info("setRemote memCached(byte(" + page.getKeys().get(i) + ")==null)=null");
                            return null;
                        }
                        length += item.getBuffer().length;
                        stream.write(item.getBuffer());
                    }
                    bs = stream.getBytes();
                    bs = Arrays.copyOf(bs, length);
                    return Utils.objectRead(bs);
                }
                finally
                {
                    if (stream != null)
                        stream.close();
                }
                
            }
            catch (IOException e)
            {
                logger.info("setRemote memCached(IOException)=null");
            }
            catch (ClassNotFoundException e)
            {
                logger.info("setRemote memCached(ClassNotFoundException)=null");
            }
            return null;
        }
        return obj;
    }
    
    private Object getRemoteInternal(String key)
    {
        if (memCached != null)
            return memCached.get(key);
        else
            logger.info("getRemote memCached=null");
        return null;
    }
    
    public void setRemote(String key, Object obj, int seconds)
    {
        
        if (true)
        {
            setRemoteInternal(key, obj, seconds);
            return;
        }
        
        try
        {
            boolean st = false;
            if (obj instanceof Object)
            {
                if (obj instanceof String && ((String)obj).length() > 1000000)
                    st = true;
                else
                    st = true;
            }
            
            if (st)
            {
                byte[] bs = Utils.objectWrite(obj);
                byte[] buffer = null;
                if (bs.length > 1000000)
                {
                    MemcachePage page = new MemcachePage();
                    int p = Utils.page(bs.length, 1000000);
                    for (int i = 0; i < p; i++)
                    {
                        String page_key = "PAGE(" + i + "):" + key;
                        page.getKeys().add(page_key);
                        if (i == p - 1)
                            buffer = Arrays.copyOfRange(bs, 1000000 * i, bs.length);
                        else
                            buffer = Arrays.copyOfRange(bs, 1000000 * i, 1000000 * (i + 1));
                        
                        setRemoteInternal(page_key, new MemcachePage(buffer), seconds * 2);
                        
                    }
                    
                    setRemoteInternal(key, page, seconds);
                }
                else
                    setRemoteInternal(key, new MemcachePage(bs), seconds);
            }
            else
                setRemoteInternal(key, obj, seconds);
            
        }
        catch (Exception e)
        {
            logger.info("setRemote memCached(IOException)=null");
        }
    }
    
    private void setRemoteInternal(String key, Object obj, int seconds)
    {
        if (memCached != null)
        {
            if (seconds > 0)
                memCached.set(key, obj, new Date(seconds * 1000));
            else
                memCached.set(key, obj);
        }
        else
        {
            logger.info("setRemote memCached=null");
        }
    }
    
    @Override
    public void removeRemote(String key)
    {
        if (memCached != null)
        {
            memCached.delete(key);
            super.sendClearKey(key);
        }
    }
    
    public void setCachePool(String serverURL)
    {
        
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
        
        memCached = new MemCachedClient();
        memCached.setPrimitiveAsString(true);
        memCached.setSanitizeKeys(false);
        
        // memCached.setCompressEnable(true);
        // memCached.setCompressThreshold(64 * 1024);
    }
    
    @Override
    public boolean addRemote(String key, Object v, int mils)
    {
        if (memCached != null)
            return memCached.add(key, v, new Date(mils));
        else
        {
            logger.info("addRemote memCached=null");
        }
        
        return false;
    }
    
    public void cas(String key, Object v, int seconds)
    {
        if (Utils.stringIsNullOrEmpty(key) || v == null)
        {
            return;
        }
        
        if (memCached != null)
        {
            long casUnique = memCached.gets(key) == null ? 1 : memCached.gets(key).getCasUnique();
            if (seconds > 0)
            {
                memCached.cas(key, v, new Date(seconds * 1000), casUnique);
            }
            else
            {
                memCached.cas(key, v, casUnique);
            }
        }
        else
        {
            logger.info("addRemote memCached=null");
        }
    }
    
    @Override
    public void subscribe(JedisPubSub pubsub, String... channel)
    {
        
    }
    
    @Override
    public void psubscribe(JedisPubSub pubsub, String... partten)
    {
        
    }
    
    @Override
    public void publishMessage(String channel, String message)
    {
        
    }
    
    @Deprecated
    @Override
    public Long incrBy(String key, int step)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Deprecated
    @Override
    public Long decrBy(String key, int step)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void setStr(String key, String v, int seconds)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getStr(String key)
    {
        // TODO Auto-generated method stub
        return null;
        
    }
    
    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // *
    // cn.egame.common.cache.ICacheClient#getElementFromQueue(java.lang.String,
    // * int)
    // */
    // @Override
    // public <T> T getTFromList(String k, int index) throws ExceptionCommonBase
    // {
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
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
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
    // }
    //
    // @Override
    // public <T> void replaceList(String k, List<T> list, boolean isListHead,
    // int second) throws ExceptionCommonBase {
    // throw new ExceptionCommonBase(-1, "MemCached不支持此操作");
    //
    // }
    
    public Long rpush(String key, List<?> value) {
        return 0L;
    }
    
    public <T> T rpopT(Class clazz, String key) {
        return null;
    }

    @Override
    public Long llen(String key) {
        return null;
    }

    @Override
    public <T> T lindexT(Class clazz, String key, int index) {
        return null;
    }
}
