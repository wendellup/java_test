package cn.egame.common.data.dbpool;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

public class SConnectionManager extends ConnectionManager {
	private static Logger logger = Logger.getLogger(SConnectionManager.class);
	private static byte[] lock = new byte[0];
	private static Map<String, ConnectionManager> pools = new HashMap<String, ConnectionManager>();

	@Deprecated
	static public ConnectionManager getInstance() throws ExceptionCommonBase {
		return getInstance(null);
	}

	static public ConnectionManager getInstance(String dbname)
			throws ExceptionCommonBase {
		ConnectionManager instance = pools.get(dbname);
		if (instance == null) {
			synchronized (lock) {
				instance = pools.get(dbname);
				if (instance == null) {
					Properties properties;
					try {
						properties = Utils.getProperties("db.properties");
						instance = new ConnectionManager(dbname, properties);
						logger.info("dbname=" + dbname + "/file="
								+ Utils.getConfigFile("db.properties"));

						pools.put(dbname, instance);
					} catch (FileNotFoundException e) {
						throw new ExceptionCommonBase(
								ErrorCodeBase.SysConfigError,
								"db.properties is not find!");
					}

				}
			}
		}
		return instance;
	}

}
