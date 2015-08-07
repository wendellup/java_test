package cn.egame.common.dataserver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Allen Yang
 *
 * @date 2013-5-13
 */
public class Table {
	private String tableName;
	private Class<?> clz;
	private int dbId;
	private String dbName;
	private Map<Integer, Boolean> indexList = new HashMap<Integer, Boolean>();//dbId->true|false
	
	public boolean isHasIndexes(int dbId) {
		return indexList.containsKey(dbId);
	}

	public void setHasIndexes(int dbId) {
		indexList.put(dbId, true);
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Class<?> getClz() {
		return clz;
	}
	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
	
}
