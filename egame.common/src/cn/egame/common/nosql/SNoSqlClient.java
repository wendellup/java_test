package cn.egame.common.nosql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.cache.factory.MemCacheClient;
import cn.egame.common.cache.factory.RedisCacheClient;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.nosql.factory.MongoDBClient;
import cn.egame.common.util.Utils;

public class SNoSqlClient {
	private static byte[] lock = new byte[0];
	private static Logger logger = Logger.getLogger(SCacheClient.class
			.getSimpleName());

	private static Map<String, INoSQLClient> hash = new HashMap<String, INoSQLClient>();

	public static INoSQLClient getInstance() {
		return getInstance(null);
	}

	public static INoSQLClient getInstance(String server) {
		INoSQLClient instance = hash.get(server);
		if (instance == null)
			synchronized (lock) {
				instance = hash.get(server);
				if (instance == null) {
					instance = init(server);
				}
			}
		return instance;
	}

	private static INoSQLClient init(String server) {
		try {
			StringBuffer logBuffer = new StringBuffer();
			
			String temp = Utils.stringIsNullOrEmpty(server) ? "" : "."+server ;
			Properties properties = Utils.getProperties("server.properties");
			String nosql = properties.getProperty("nosql" + temp, "mongo");
			logger.info("nosql" + temp + "=" + nosql);

			if (Utils.stringCompare("mongo", nosql)) {
				String host = properties.getProperty("nosql" + temp
						+ ".mongo.host");
				if (Utils.stringIsNullOrEmpty(host))
					throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError,
							"nosql" + temp + ".mongo.host=null");
				int port = Utils.toInt(properties.getProperty("nosql" + temp
						+ ".mongo.port"), 27017);
				String dbName  = properties.getProperty("nosql" + temp
						+ ".mongo.dbname");
				int poolSize = Utils.toInt(properties.getProperty("nosql" + temp
						+ ".mongo.poolsize"), 10);
				String userName = properties.getProperty("nosql" + temp
						+ ".mongo.user");
				String password =  properties.getProperty("nosql" + temp
						+ ".mongo.password");
				boolean isSlave = Utils.stringCompare("true", properties.getProperty("nosql" + temp
						+ ".mongo.slave"));
				
				logBuffer.append("");
				
				MongoDBClient client = new MongoDBClient(dbName);
				client.setNoSqlHost(host);
				client.setNoSqlPort(port);
				client.setUserName(userName);
				client.setUserPassword(password);
				client.setPoolSize(poolSize);
				client.setSlave(isSlave);
				
				hash.put(server, client);
				return client;
			}else
				throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError,
						"nosql" + temp + "[mongo]="+ nosql);
		} catch (FileNotFoundException e) {
			logger.error(null, e);
		} catch (NumberFormatException e) {
			logger.error(null, e);
		} catch (IOException e) {
			logger.error(null, e);
		}

		return null;
	}
}
