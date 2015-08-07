package cn.egame.common.nosql.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisPubSub;
import cn.egame.common.cache.CacheClientBase;
import cn.egame.common.cache.ICacheClient;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.nosql.DBElementMap;
import cn.egame.common.nosql.INoSQLClient;
import cn.egame.common.util.Utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoDBClient extends CacheClientBase implements ICacheClient, INoSQLClient
{
    
    protected static DBObject toDbObject(Object obj)
        throws ExceptionCommonBase
    {
        if (obj == null)
            return null;
        try
        {
            DBObject dbobject = new BasicDBObject();
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            String name = null;
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }
                DBElementMap an = field.getAnnotation(DBElementMap.class);
                if (an != null && an.needSet())
                {
                    Object value = field.get(obj);
                    if (value != null)
                    {
                        name = an.name();
                        if (Utils.stringIsNullOrEmpty(name))
                            throw new ExceptionCommonBase(ErrorCodeBase.NotImplementCode);
                        dbobject.put(name, value);
                    }
                }
            }
            return dbobject;
        }
        catch (Exception ex)
        {
            throw ExceptionCommonBase.throwExceptionCommonBase(ex);
        }
    }
    
    private static Object get_Id(Object obj)
        throws ExceptionCommonBase
    {
        DBElementMap an = obj.getClass().getAnnotation(DBElementMap.class);
        String[] primrayKey = an.primaryKey();
        if (primrayKey.length == 0)
        {
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "db obj primary key is null ,class = "
                + obj.getClass().getSimpleName());
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < primrayKey.length; i++)
        {
            try
            {
                Field field = obj.getClass().getDeclaredField(primrayKey[i]);
                field.setAccessible(true);
                Object value = field.get(obj);
                // if ((value instanceof Integer) || (value instanceof Long)){
                // value = (Long) field.get(obj);
                // } else{
                // value = String.valueOf(field.get(obj));
                // }
                
                if (primrayKey.length > 1)
                {
                    if (buf.length() > 0)
                    {
                        buf.append("_");
                    }
                    buf.append(value);
                }
                else
                {
                    return value;
                }
                
            }
            catch (Exception e)
            {
                throw ExceptionCommonBase.throwExceptionCommonBase(e);
            }
        }
        return buf.toString();
    }
    
    protected static BasicDBObject toQueryDbObject(Object dbObj)
        throws ExceptionCommonBase
    {
        try
        {
            BasicDBObject where = new BasicDBObject();
            
            Field[] fields = dbObj.getClass().getDeclaredFields();
            
            BasicDBObject baseObj = new BasicDBObject();
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }
                DBElementMap ann = field.getAnnotation(DBElementMap.class);
                
                if (ann != null && !ann.needSet())
                {
                    continue;
                }
                // String name = field.getName();
                Object value = field.get(dbObj);
                String name = field.getName();
                
                String dbField = ann.name();
                
                if (dbField == null)
                {
                    throw new RuntimeException("db field name is null");
                }
                
                if (value != null)
                {
                    if (value instanceof Integer)
                    {
                        if ((Integer)value != 0)
                            where.append(dbField, value);
                    }
                    else if (value instanceof Long)
                    {
                        if ((Long)value != 0)
                            where.append(dbField, value);
                    }
                    else if (value instanceof Float)
                    {
                        if ((Float)value != 0)
                            where.append(dbField, value);
                    }
                    else if (value instanceof Double)
                    {
                        if ((Double)value != 0)
                            where.append(dbField, value);
                    }
                    else if (value instanceof Boolean)
                    {
                        // 暂时不处理
                        
                    }
                    else if (value instanceof String)
                    {
                        if ((String)value != null)
                            where.append(dbField, value);
                    }
                    else if (value instanceof byte[])
                    {
                        // 不作为查询条件
                        
                    }
                    else
                    {
                        // Object 不作为查询条件
                        
                    }
                }
                else
                {
                    // 字符串类型赋值--字符串类型不是原始类型
                    if ((String)value != null)
                        where.append(dbField, value);
                }
            }
            
            return where;
        }
        catch (Exception e)
        {
            throw ExceptionCommonBase.throwExceptionCommonBase(e);
        }
    }
    
    private static String getTableName(Object obj)
    {
        if (obj != null)
        {
            DBElementMap an = obj.getClass().getAnnotation(DBElementMap.class);
            return an.name();
        }
        return null;
    }
    
    public static <T> T toObjectT(T obj, DBObject val)
        throws ExceptionCommonBase
    {
        if (obj == null || val == null)
            return null;
        try
        {
            Class clazz = obj.getClass();
            Object result = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            String name = null;
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                field.setAccessible(true);
                DBElementMap an = field.getAnnotation(DBElementMap.class);
                if (Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }
                if (an != null && an.needSet())
                {
                    name = an.name();
                    Object value = val.get(name);
                    if (Utils.stringIsNullOrEmpty(name))
                        throw new ExceptionCommonBase(ErrorCodeBase.NotImplementCode, "db name is null ,propertyName="
                            + field.getName());
                    if (value != null)
                        field.set(result, value);
                }
            }
            
            return (T)result;
        }
        catch (Exception ex)
        {
            throw ExceptionCommonBase.throwExceptionCommonBase(ex);
        }
    }
    
    private String dbname = "";
    
    private String tableName = "def";
    
    private String nosqlHost;
    
    private int nosqlPort;
    
    private boolean slave = false;
    
    private String userName = null;
    
    private String userPassword = null;
    
    private int poolSize = 0;
    
    private MongoClient mongo = null;
    
    private DB db = null;
    
    public MongoDBClient(String dbname)
        throws ExceptionCommonBase
    {
        if (Utils.stringIsNullOrEmpty(dbname))
            throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError, "dbname=null");
        this.dbname = dbname;
    }
    
    protected boolean delete(String table, DBObject query)
        throws ExceptionCommonBase
    {
        if (query == null)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "query is null");
        
        DBCollection dbcollection = getCollection(table);
        WriteResult wr = dbcollection.remove(query);
        return false;
    }
    
    @Override
    public Object getRemote(String key)
    {
        try
        {
            BasicDBObject obj = new BasicDBObject();
            obj.put("_key", key);
            return getOne(this.tableName, obj);
        }
        catch (Exception ex)
        {
            logger.error(key, ex);
        }
        return null;
    }
    
    protected DBCollection getCollection(String table)
        throws ExceptionCommonBase
    {
        try
        {
            return getDB().getCollection(table);
        }
        catch (Exception ex)
        {
            throw ExceptionCommonBase.throwExceptionCommonBase(ex);
        }
    }
    
    public synchronized DB getDB()
        throws ExceptionCommonBase
    {
        if (db == null)
        {
            Mongo m = getMongo();
            db = m.getDB(this.dbname);
            if (!Utils.stringIsNullOrEmpty(this.getUserName()) && !Utils.stringIsNullOrEmpty(this.getUserPassword()))
                db.authenticate(this.getUserName(), this.getUserPassword().toCharArray());
        }
        return db;
    }
    
    public synchronized MongoClient getMongo()
        throws ExceptionCommonBase
    {
        
        if (mongo == null)
        {
            try
            {
                if (this.getPoolSize() > 0)
                    System.setProperty("MONGO.POOLSIZE", String.valueOf(this.getPoolSize()));
                
                mongo = new MongoClient(nosqlHost, nosqlPort);
                if (this.isSlave())
                    mongo.slaveOk();
                
            }
            catch (Exception ex)
            {
                throw ExceptionCommonBase.throwExceptionCommonBase(ex);
            }
        }
        
        return mongo;
    }
    
    public String getNosqlHost()
    {
        return nosqlHost;
    }
    
    public int getNosqlPort()
    {
        return nosqlPort;
    }
    
    protected DBObject getOne(String table, DBObject query)
        throws ExceptionCommonBase
    {
        if (query == null)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "query is null");
        
        DBCollection dbcollection = getCollection(table);
        if (dbcollection == null)
        {
            return null;
        }
        return dbcollection.findOne(query);
    }
    
    public int getPoolSize()
    {
        return poolSize;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public String getUserPassword()
    {
        return userPassword;
    }
    
    public boolean isSlave()
    {
        return slave;
    }
    
    @Override
    public void removeRemote(String key)
    {
        try
        {
            BasicDBObject obj = new BasicDBObject();
            obj.put("_key", key);
            this.delete(this.tableName, obj);
        }
        catch (Exception ex)
        {
            logger.error(key, ex);
        }
    }
    
    private boolean save(String table, DBObject obj)
        throws ExceptionCommonBase
    {
        return this.saveOrUpdate(table, null, obj);
    }
    
    private boolean saveOrUpdate(String table, DBObject query, DBObject obj)
        throws ExceptionCommonBase
    {
        
        DBObject result = null;
        if (query != null)
            result = getOne(table, query);
        
        DBCollection dbcollection = getCollection(table);
        if (result == null)
        {
            dbcollection.insert(obj);
        }
        else
        {
            dbcollection.update(result, obj);
        }
        return true;
    }
    
    @Override
    public void set(String key, Object v)
    {
        try
        {
            BasicDBObject id = new BasicDBObject();
            id.put("_key", key);
            
            BasicDBObject obj = new BasicDBObject();
            obj.put("_key", key);
            obj.put("_obj", v);
            this.update(this.tableName, id, obj);
        }
        catch (Exception ex)
        {
            logger.error(key, ex);
        }
    }
    
    @Override
    public void setCachePool(String serverURL)
        throws ExceptionCommonBase
    {
        
    }
    
    public void setNoSqlHost(String nosqlHost)
    {
        this.nosqlHost = nosqlHost;
    }
    
    public void setNoSqlPort(int nosqlPort)
    {
        this.nosqlPort = nosqlPort;
    }
    
    public void setPoolSize(int poolSize)
    {
        this.poolSize = poolSize;
    }
    
    public void setSlave(boolean slave)
    {
        this.slave = slave;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }
    
    private boolean update(String table, DBObject id, DBObject obj)
        throws ExceptionCommonBase
    {
        return this.saveOrUpdate(table, id, obj);
    }
    
    @Override
    public boolean save(Object val)
        throws ExceptionCommonBase
    {
        DBCollection collection = getCollection(getTableName(val));
        DBObject dbObject = toDbObject(val);
        dbObject.put("_id", get_Id(val));
        collection.insert(dbObject);
        return true;
    }
    
    @Override
    public boolean update(Object val)
        throws ExceptionCommonBase
    {
        DBCollection collection = getCollection(getTableName(val));
        DBObject dbObject = toDbObject(val);
        DBObject query = new BasicDBObject();
        query.put("_id", get_Id(val));
        collection.update(query, dbObject);
        return false;
    }
    
    @Override
    public boolean delete(Object val)
        throws ExceptionCommonBase
    {
        if (val == null)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "query is null");
        DBObject query = new BasicDBObject();
        query.put("_id", get_Id(val));
        return this.delete(getTableName(val), query);
    }
    
    @Override
    public boolean deleteOne(Object val)
        throws ExceptionCommonBase
    {
        if (val == null)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "query is null");
        
        DBObject query = toQueryDbObject(val);
        if (query.toString().equalsIgnoreCase("{ }"))
        {
            System.out.println("欲删除所有数据，拒绝!");
            logger.info("欲删除所有数据，拒绝!");
            return false;
        }
        return this.delete(getTableName(val), query);
    }
    
    @Override
    public <T> T getById(T query)
        throws ExceptionCommonBase
    {
        DBCollection collection = getCollection(getTableName(query));
        DBObject b = new BasicDBObject();
        b.put("_id", get_Id(query));
        DBObject result = this.getOne(getTableName(query), b);
        return toObjectT(query, result);
    }
    
    @Override
    public <T extends Object> T getOne(T query)
        throws ExceptionCommonBase
    {
        if (query == null)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "query is null");
        
        DBObject q = toQueryDbObject(query);
        DBObject result = this.getOne(getTableName(query), q);
        return toObjectT(query, result);
    }
    
    @Override
    public boolean saveOrUpdate(Object val)
        throws ExceptionCommonBase
    {
        if (val == null)
            throw new ExceptionCommonBase(ErrorCodeBase.ParameterError, "query is null");
        Object _id = get_Id(val);
        DBObject query = new BasicDBObject();
        query.put("_id", _id);
        DBObject obj = toDbObject(val);
        obj.put("_id", _id);
        return this.saveOrUpdate(getTableName(val), query, obj);
    }
    
    @Override
    public void setRemote(String key, Object v, int seconds)
    {
        
    }
    
    @Override
    public boolean addRemote(String key, Object obj, int mils)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void subscribe(JedisPubSub pubsub, String... channel)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void psubscribe(JedisPubSub pubsub, String... partten)
    {
        
    }
    
    @Override
    public void publishMessage(String channel, String message)
    {
        
    }
    
    protected static BasicDBObject toQueryDbObject(Map<String, Boolean> orderBy)
        throws ExceptionCommonBase
    {
        BasicDBObject boj = new BasicDBObject();
        if (orderBy != null & orderBy.size() > 0)
        {
            Iterator<String> it = orderBy.keySet().iterator();
            while (it.hasNext())
            {
                String columnName = it.next();
                Boolean val = orderBy.get(columnName);
                boj.put(columnName, val ? 1 : -1);
            }
        }
        return boj;
    }
    
    @Override
    public <T> List<T> getList(T query, LinkedHashMap<String, Boolean> orderBy, int skip, int count)
        throws ExceptionCommonBase
    {
        List<T> list = new ArrayList<T>();
        DBCollection table = getCollection(getTableName(query));
        DBCursor cur = table.find(toQueryDbObject(query)).sort(toQueryDbObject(orderBy)).skip(skip).limit(count);
        while (cur.hasNext())
        {
            BasicDBObject baseObj = (BasicDBObject)cur.next();
            T t = toObjectT(query, baseObj);
            list.add(t);
            
        }
        return list;
    }
    
    @Override
    public <T> List<T> getList(T query)
        throws ExceptionCommonBase
    {
        return getList(query, null, 0, 0);
    }
    
    @Override
    public <T1, T2> List<T1> getT(Class clazz, List<String> keys, List<T2> losts)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public <T> T getOneAndDel(T query)
        throws ExceptionCommonBase
    {
        DBCollection table = getCollection(getTableName(query));
        DBObject obj = table.findAndRemove(toQueryDbObject(query));
        T t = toObjectT(query, obj);
        return t;
    }
    
    @Deprecated
    @Override
    public void cas(String key, Object v, int seconds)
    {
        // TODO Auto-generated method stub
        
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
