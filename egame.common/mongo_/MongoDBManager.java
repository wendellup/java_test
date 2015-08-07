package cn.egame.common.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import cn.egame.common.dataserver.BaseDBObj;
import cn.egame.common.dataserver.ESortType;
import cn.egame.common.dataserver.Table;
import cn.egame.common.dataserver.TableManager;
import cn.egame.common.util.CommonUtil;
import cn.egame.common.util.DomUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@SuppressWarnings("unchecked")
public class MongoDBManager implements IMongoDBManager {
	private static int DB_NOT_FIND = 0;

	private static Logger logger = Logger.getLogger(MongoDBManager.class);

	private Map<Integer, MongoDB> dbPools = new HashMap<Integer, MongoDB>();// mong
																			// id索引字典,dbId->DB
	private Map<String, List<MongoDB>> dbGroups = new HashMap<String, List<MongoDB>>(); // mongo
																						// name索引字典,dbName->DB
	private TableManager tableManager;

	public MongoDBManager(String xmlPath, TableManager tableManager)
			throws Exception {
		try {
			Document doc = DomUtil.read(xmlPath);
			Element root = DomUtil.getRootElement(doc);
			List elements = root.elements();
			for (int i = 0; i < elements.size(); i++) {
				Element el = (Element) elements.get(i);
				String idStr = el.attributeValue("id").trim();
				int id = Integer.valueOf(idStr);
				String host = el.attributeValue("host").trim();
				String port = el.attributeValue("port").trim();
				String dbname = el.attributeValue("dbname").trim();
				String user = el.attributeValue("user").trim();
				String password = el.attributeValue("password").trim();
				String poolsize = el.attributeValue("poolsize").trim();
				String slaveFlag = el.attributeValue("isSlave").trim();
				int bottomValue = CommonUtil.parseInt(el
						.attributeValue("bottomValue"));
				int topValue = CommonUtil.parseInt(el
						.attributeValue("topValue"));

				boolean isSlave = false;
				if (slaveFlag.equals("true")) {
					isSlave = true;
				}

				MongoDB dbp = new MongoDB(id, host, port, dbname, user,
						password, poolsize, isSlave, bottomValue, topValue);
				// 连接
				while (!dbp.connect()) {
					logger.error("try to connect mongodb。" + dbp.toString());
					Thread.sleep(2000);
				}
				// 验证
				while (!dbp.checkAuth()) {
					logger.error("try to check mongodb。 " + dbp.toString());
					Thread.sleep(2000);
				}
				dbPools.put(id, dbp);

				// 建名字索引
				List<MongoDB> groupList = dbGroups.get(dbname);
				if (groupList == null) {
					groupList = new ArrayList<MongoDB>();
					dbGroups.put(dbname, groupList);
				}
				groupList.add(dbp);

			}
			this.tableManager = tableManager;
			logger.info("init mongodb successfully ." + dbPools.toString());
			logger.info("init mongodb successfully ." + dbGroups.toString());
		} catch (Exception ex) {
			logger.error("mongo异常", ex);
			throw ex;
		}
	}

	private int getDbId(String dbName, long shardValue) {
		if (shardValue == 0)
			return DB_NOT_FIND;

		List<MongoDB> list = dbGroups.get(dbName);

		for (MongoDB db : list) {
			long bottomValue = db.getBottomValue();
			long topValue = db.getTopValue();
			if (bottomValue <= shardValue && shardValue < topValue) {
				return db.getId();
			}
		}
		return DB_NOT_FIND;
	}

	private int getDbId(BaseDBObj dbObj) {
		String tableName = dbObj.getTableName();
		Table table = tableManager.getTable(tableName);
		int dbId = table.getDbId();
		if (dbId != DB_NOT_FIND) {
			return dbId;
		} else {
			String dbName = table.getDbName();
			long shardValue = dbObj.getDBIndex();
			dbId = this.getDbId(dbName, shardValue);
		}
		return dbId;
	}

	/**
	 * 保存对象
	 * 
	 * @param igo
	 * @return
	 */
	public boolean save(BaseDBObj dbObj) {
		if (dbObj == null) {
			return false;
		}
		try {
			int dbId = getDbId(dbObj);
			DBCollection table = getCollection(dbId, dbObj.getTableName());
			dbObj.set_id(dbObj.getUninKey());
			table.insert(MongoUtil.toBasicDBObject(dbObj));
			return true;
		} catch (Exception ex) {
			logger.error("mongo异常 save", ex);
		}
		return false;
	}

	/**
	 * 更新对象
	 * 
	 * @param igo
	 * @return
	 */
	public boolean update(BasicDBObject obj) {
		if (dbObj == null || dbObj.get_id() == null) {
			return false;
		}
		try {
			int dbId = getDbId(dbObj);
			DBCollection table = getCollection(dbId, dbObj.getTableName());

			BasicDBObject query = new BasicDBObject();
			query.put("_id", dbObj.get_id());

			table.update(query, MongoUtil.toBasicDBObject(dbObj));
			return true;
		} catch (Exception ex) {
			logger.error("mongo异常 update", ex);
		}
		return false;
	}

	/**
	 * 删除对象
	 * 
	 * @param igo
	 * @return
	 */
	public boolean delete(BaseDBObj dbObj) {
		if (dbObj == null) {
			return false;
		}
		try {
			int dbId = getDbId(dbObj);
			DBCollection table = getCollection(dbId, dbObj.getTableName());

			BasicDBObject query = new BasicDBObject();
			String _id = dbObj.get_id();
			if (_id == null) {
				dbObj.set_id(dbObj.getUninKey());
			}
			query.put("_id", MongoUtil.parse_id(dbObj));

			table.remove(query);// 只传_id,减少网络传输字节
			return true;
		} catch (Exception ex) {
			logger.error("mongo异常 delete", ex);
		}
		return false;
	}

	/**
	 * 获取数据列表
	 * 
	 * @param <T>
	 * @param dbId
	 * @param tableObj
	 * @param where
	 * @param skipBy
	 * @param limitBy
	 * @return
	 */
	public <T> List<T> getListByCondition(BaseDBObj dbObj, BasicDBObject where,
			BasicDBObject orderBy, int skipBy, int limitBy) {
		List<T> list = new ArrayList<T>();
		try {
			String tableName = dbObj.getTableName();
			int dbId = getDbId(dbObj);
			DBCollection table = getCollection(dbId, tableName);
			DBCursor cur = table.find(parseCondition(tableName, where))
					.sort(parseCondition(tableName, orderBy)).skip(skipBy)
					.limit(limitBy);
			while (cur.hasNext()) {
				BasicDBObject baseObj = (BasicDBObject) cur.next();
				BaseDBObj igo = (BaseDBObj) MongoUtil.getBean(dbObj.getClass(),
						baseObj);
				list.add((T) igo);
			}
		} catch (Exception ex) {
			logger.error("mongo异常", ex);
		}
		return list;
	}

	/**
	 * 获取Mongodb的某个表
	 * 
	 * @param db
	 * @param tableName
	 * @return
	 */
	private DBCollection getCollection(int dbId, String tableName)
			throws Exception {
		MongoDB mongo = this.dbPools.get(dbId);

		DBCollection dbCollection = mongo.getDb().getCollection(tableName);

		Table table = tableManager.getTable(tableName);
		if (table != null) {
			if (!table.isHasIndexes(dbId)) {
				List<DBObject> indexes = dbCollection.getIndexInfo();
				BaseDBObj bean = (BaseDBObj) table.getClz().newInstance();
				if (bean != null) {
					String[] uninId = bean.getUninId();

					if (uninId.length == 2) {
						// 单键(uid)
					} else if (uninId.length == 4) {
						// 双键(uid+复合)
						if (!isIndexExist(indexes, uninId[0])) {
							DBObject index = new BasicDBObject();
							index.put(uninId[0], 1);
							dbCollection.ensureIndex(index, uninId[0]);
						}
					} else if (uninId.length == 6) {
						// 三键(uid+复合)
						if (!isIndexExist(indexes, uninId[0])) {
							DBObject index = new BasicDBObject();
							index.put(uninId[0], 1);
							dbCollection.ensureIndex(index, uninId[0]);
						}
					}
					table.setHasIndexes(dbId);
				}

			}
		}
		return dbCollection;
	}

	/**
	 * 判断某个索引是否存在
	 * 
	 * @param indexes
	 * @param indexName
	 * @return
	 */
	private boolean isIndexExist(List<DBObject> indexes, String indexName) {
		for (DBObject index : indexes) {
			String name = (String) index.get("name");
			if (name != null && name.equalsIgnoreCase(indexName)) {
				return true;
			}// end if
		}// end for
		return false;
	}

	/**
	 * 获取数据库的where
	 * 
	 * @param tableName
	 * @param where
	 * @return
	 */
	private BasicDBObject parseCondition(String tableName, BasicDBObject where) {
		return where;
	}

	@Override
	public BaseDBObj getById(BaseDBObj dbObj) {
		try {
			String tableName = dbObj.getTableName();
			int dbId = getDbId(dbObj);

			DBCollection table = getCollection(dbId, tableName);

			BasicDBObject where = new BasicDBObject();
			where.put("_id", MongoUtil.parse_id(dbObj));
			BasicDBObject baseObj = (BasicDBObject) table.findOne(where);

			BaseDBObj obj = MongoUtil.getBean(dbObj.getClass(), baseObj);
			return obj;
		} catch (Exception ex) {
			logger.error("mongo异常", ex);
		}
		return null;
	}

	@Override
	public BaseDBObj getOne(BaseDBObj dbObj) {
		BasicDBObject where = MongoUtil.getCondition(dbObj);
		BaseDBObj obj = null;
		try {
			String tableName = dbObj.getTableName();
			Table table = tableManager.getTable(tableName);
			int dbId = getDbId(dbObj);
			if (dbId == DB_NOT_FIND) {
				List<MongoDB> dbs = dbGroups.get(table.getDbName());
				for (int i = 0; i < dbs.size(); i++) {
					dbId = dbs.get(i).getId();
					DBCollection collection = getCollection(dbId, tableName);
					BasicDBObject baseObj = (BasicDBObject) collection
							.findOne(where);
					obj = MongoUtil.getBean(dbObj.getClass(), baseObj);
					if (obj != null) {
						return obj;
					}
				}
			} else {
				DBCollection collection = getCollection(dbId, tableName);
				BasicDBObject baseObj = (BasicDBObject) collection
						.findOne(where);
				obj = MongoUtil.getBean(dbObj.getClass(), baseObj);
			}
		} catch (Exception ex) {
			logger.error("mongo异常", ex);
		}
		return obj;
	}

	@Override
	public List<BaseDBObj> getList(BaseDBObj where,
			LinkedHashMap<String, ESortType> orderBy, int start, int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
