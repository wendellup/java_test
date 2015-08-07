package cn.egame.common.dataserver;

import java.util.LinkedHashMap;
import java.util.List;

import cn.egame.common.mongo.IMongoDBManager;

public class DataManager implements IDataManager {
	private IMongoDBManager mongoDBManager;
	private ICacheManager cacheManager;
	private IMySqlManager mySqlManager;

	public DataManager(ICacheManager cacheManager,IMongoDBManager mongoDBManager,IMySqlManager mySqlManager) {
		this.cacheManager = cacheManager;
		this.mongoDBManager = mongoDBManager;
		this.mySqlManager = mySqlManager;
	}

	@Override
	public boolean save(BaseDBObj dbObj) {

		if (mongoDBManager != null)
			mongoDBManager.save(dbObj);
		if (cacheManager != null)
			cacheManager.save(dbObj);
		return true;
	}

	@Override
	public boolean update(BaseDBObj dbObj) {
		if (mongoDBManager != null)
			mongoDBManager.update(dbObj);
		if (cacheManager != null)
			cacheManager.update(dbObj);
		return true;
	}

	@Override
	public boolean delete(BaseDBObj dbObj) {
		if (mongoDBManager != null)
			mongoDBManager.delete(dbObj);
		if (cacheManager != null)
			cacheManager.delete(dbObj);
		return true;
	}

	@Override
	public BaseDBObj getById(BaseDBObj cond) {
		BaseDBObj obj = null;
		if (cacheManager != null)
			obj = cacheManager.getById(cond);

		if (obj != null) {
			return obj;
		} else {
			if(mongoDBManager != null)
				obj = mongoDBManager.getById(cond);
			
			if (obj != null && cacheManager != null)
				cacheManager.save(obj);
		}
		return obj;
	}

	@Override
	public BaseDBObj getOne(BaseDBObj cond) {
		BaseDBObj obj = null;
		if(cacheManager != null)
			obj = cacheManager.getOne(cond);
		
		if(obj != null){
			return obj;
		}else{	
			if(mongoDBManager != null)
				obj = mongoDBManager.getOne(cond);
			
			if (obj != null && cacheManager != null){
				cacheManager.save(obj);
				cacheManager.set(CacheKeyUtil.getConditionKey(obj), CacheKeyUtil.getUninIdKey(obj));
			}
		}
		return obj;
	}

	@Override
	public List<BaseDBObj> getList(BaseDBObj where, LinkedHashMap<String, ESortType> orderBy, int start, int count) {
		return mongoDBManager.getList(where, orderBy, start, count);
	}

}
