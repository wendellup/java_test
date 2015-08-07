package cn.egame.common.mongo;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * @author Allen Yang
 * 
 *         mongodb数据库连接池
 * 
 *         mongodb连接步骤： (1) new MongoDB (2) connect (3) checkAuth
 */
public class MongoDB {
	private static Logger logger = Logger.getLogger(MongoDB.class);

	private int id;
	private MongoClient mongo;
	private DB db;

	private String host;
	private String port;
	private String dbname;
	private String user;
	private String password;
	private String poolsize;
	private boolean isSlave;// 是否为从库

	// 分库字段的上下界，bottomValue <= value < topValue,topValue值如果小于1，表示没有上限值
	private long bottomValue;
	private long topValue;

	public MongoDB(int id, String host, String port, String dbname, String user, String password, String poolsize, boolean isSlave,long bottomValue,long topValue) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.dbname = dbname;
		this.user = user;
		this.password = password;
		this.poolsize = poolsize;
		this.isSlave = isSlave;
		this.bottomValue =  bottomValue;
		if(topValue <= 0){
			topValue = Long.MAX_VALUE;
		}
		this.topValue = topValue;
	}

	public boolean connect() {
		boolean connected = false;
		try {
			// System.setProperty("MONGO.POOLSIZE", this.poolsize);
			this.mongo = new MongoClient(this.host, Integer.valueOf(this.port.trim()));
			this.db = mongo.getDB(this.dbname);
			if (this.isSlave) {
				mongo.slaveOk();
			}
			connected = true;
		} catch (Exception e) {
			logger.error("exception--connect()", e);
		}
		return connected;
	}

	public boolean checkAuth() {
		try {
			boolean auth = db.authenticate(user, password.toCharArray());
			if (!auth) {
				logger.error("mongodb->host=" + this.host + ",port=" + this.port + ",dbname=" + this.dbname + " auth false!");
			}
			return auth;
		} catch (Exception ex) {
			logger.error("auth exception.", ex);
		}
		return false;
	}

	public DB getDb() {
		return db;
	}

	public int getId() {
		return id;
	}
	
	public String getDbname() {
		return dbname;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getPoolsize() {
		return poolsize;
	}

	public boolean isSlave() {
		return isSlave;
	}
	
	public long getBottomValue() {
		return bottomValue;
	}

	public long getTopValue() {
		return topValue;
	}

	@Override
	public String toString() {
		return "MongoDB [id=" + id + ", mongo=" + mongo + ", db=" + db + ", host=" + host + ", port=" + port + ", dbname=" + dbname + ", user=" + user + ", password=" + password
				+ ", poolsize=" + poolsize + ", isSlave=" + isSlave + ", bottomValue=" + bottomValue + ", topValue=" + topValue + "]";
	}

}
