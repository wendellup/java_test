package cn.egame.common.data.dbpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

import com.alibaba.druid.pool.DruidDataSource;

public class ConnectionManager {
	private static Logger logger = Logger.getLogger(ConnectionManager.class);

	private static final long serialVersionUID = 1L;
	private DruidDataSource pool = null;

	public ConnectionManager() {
		// init();
	}

	public ConnectionManager(String dbname, Properties props)
			throws ExceptionCommonBase {
		this();
		init(dbname, props);
	}

	public Connection getConnection() throws ExceptionCommonBase {
		try {
			Connection conn = pool.getConnection();
			if (null != conn && !conn.getAutoCommit()) {
				conn.setAutoCommit(true);
			}
			return conn;

		} catch (SQLException e) {
			logger.error("pool dump : " + pool.dump());
			logger.error("activeStackTrace : "
					+ pool.getActiveConnectionStackTrace());

			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
	}

	private String dbname = null;

	private void init(String dbname, Properties props)
			throws ExceptionCommonBase {
		this.dbname = dbname;
		DruidDataSource temp = new DruidDataSource();
		String url = props.getProperty(dbname + ".url");
		if (Utils.stringIsNullOrEmpty(url))
			throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError, dbname
					+ ".url==null");
		temp.setUrl(url);
		temp.setDriverClassName(props
				.getProperty(dbname + ".driver_class_name"));
		// 基本属性 url、user、password
		temp.setUsername(props.getProperty(dbname + ".username"));
		temp.setPassword(props.getProperty(dbname + ".password"));
		// 配置初始化大小、最小、最大
		temp.setInitialSize(Integer.parseInt(props.getProperty(dbname
				+ ".initial_size")));
		temp.setMinIdle(Integer.parseInt(props
				.getProperty(dbname + ".min_idle")));
		temp.setMaxActive(Integer.parseInt(props.getProperty(dbname
				+ ".max_active")));
		// 配置获取连接等待超时的时间
		temp.setMaxWait(Integer.parseInt(props
				.getProperty(dbname + ".max_wait")));
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		temp.setTimeBetweenEvictionRunsMillis(Integer.parseInt(props
				.getProperty(dbname + ".time_between_eviction_runs_millis")));
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		temp.setMinEvictableIdleTimeMillis(Integer.parseInt(props
				.getProperty(dbname + ".min_evictable_idle_time_millis")));
		// 配置空闲时保持连接查询语句
		String validationQuery = props.getProperty(dbname + ".validationQuery");
		if (null != validationQuery && !"".equals(validationQuery)) {
			temp.setValidationQuery(validationQuery);
		}
		// 让连接池在空闲时保持连接
		String testWhileIdle = props.getProperty(dbname + ".testWhileIdle");
		if (null != testWhileIdle && !"".equals(testWhileIdle)) {
			temp.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
		}
		
		temp.setRemoveAbandoned(Utils.toBoolean(props.getProperty(dbname+".remove_abandoned"), false));
		
		// temp.setRemoveAbandonedTimeout(3);
		 temp.setLogAbandoned(true);
		pool = temp;
	}

	public void releaseConnection(Connection conn) throws ExceptionCommonBase {
		try {
			if (null != conn && !conn.getAutoCommit()) {
				conn.setAutoCommit(true);
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
	}
	
	public boolean switchLogAbandoned(){
	    boolean f = pool.isLogAbandoned() ? false : true;
	    pool.setLogAbandoned(f);
	    return f;
	}
	
	public String pumpPool(){
	    return pool.dump();
	}
	
	public String activeStackTrace(){
	    return pool.getActiveConnectionStackTrace().toString();
	}
}
