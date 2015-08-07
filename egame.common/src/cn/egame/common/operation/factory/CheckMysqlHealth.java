package cn.egame.common.operation.factory;

import cn.egame.common.data.BaseDao;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.operation.ResultHealth;

public class CheckMysqlHealth extends BaseDao {

	String dbName;

	public CheckMysqlHealth(String dbName) throws ExceptionCommonBase {
		super(dbName);
		this.dbName = dbName;
	}

	public ResultHealth checkHealth() {
		ResultHealth result = new ResultHealth();
		result.setCode(1);
		result.setName("mysql_" + dbName);
		try {
			result.setCode(this.getInt("select 1 from dual"));
		} catch (Exception e) {
			result.setCode(0);
		}

		return result;
	}

}
