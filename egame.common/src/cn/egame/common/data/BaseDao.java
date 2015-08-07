package cn.egame.common.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.egame.common.data.dbpool.SConnectionManager;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

import com.mysql.jdbc.StringUtils;

public class BaseDao {
	private static Logger logger = Logger.getLogger(BaseDao.class);

	public static void main(String[] args) {
		try {
			BaseDao dao = new BaseDao("egame");
			dao.getConnection();
			String gameName = dao
					.getString("select game_name from t_game where game_id=1");
			System.out.println(gameName);
		} catch (ExceptionCommonBase e) {

		}
	}

	private String dbname = "";

	public BaseDao(String dbname) throws ExceptionCommonBase {
		if (Utils.stringIsNullOrEmpty(dbname))
			throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError,
					"dbname=null");
		this.dbname = dbname;
	}

	public void close(ResultSet rs) throws ExceptionCommonBase {
		try {
			if (rs != null) {
				close(rs.getStatement());
				rs.close();
			}
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
	}

	public void close(Statement statement) throws ExceptionCommonBase {
		try {
			if (statement != null && !statement.isClosed())
				statement.close();
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
	}

	protected int[] executeBatch(String sql[]) throws ExceptionCommonBase {
		try {
			Connection conn = getConnection();
			int[] codes = new int[sql.length];
			if (null == conn)
				return codes;
			Statement stmt = null;
			conn.setAutoCommit(false);
			try {
				stmt = conn.createStatement();
				for (int i = 0; i < sql.length; i++) {
					stmt.addBatch(sql[i]);
				}
				codes = stmt.executeBatch();
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw ExceptionCommonBase.throwExceptionCommonBase(e);
			} finally {
				close(stmt);
				releaseConnection(conn);
			}
			return codes;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}

	}

	protected ResultSet executeQuery(Connection conn, String sql)
			throws ExceptionCommonBase {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			logger.error(sql);
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			// close(statement);
		}
	}

	protected int executeUpdate(Connection conn, String sql)
			throws ExceptionCommonBase {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(statement);
		}
	}

	protected int executeUpdate(String sql) throws ExceptionCommonBase {
		Connection conn = getConnection();
		try {
			return this.executeUpdate(conn, sql);
		} finally {
			releaseConnection(conn);
		}
	}

	public Connection getConnection() throws ExceptionCommonBase {
		return SConnectionManager.getInstance(dbname).getConnection();
	}

	public Connection getConnection(boolean readOnly)
			throws ExceptionCommonBase {
		if (readOnly) {
			return SConnectionManager.getInstance(dbname + ".read_only")
					.getConnection();
		} else {
			return SConnectionManager.getInstance(dbname).getConnection();
		}

	}

	protected Map<String, Object> getData(String sql)
			throws ExceptionCommonBase {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				metaData = rs.getMetaData();
				int count = metaData.getColumnCount();
				for (int i = 1; i <= count; i++)
					map.put(metaData.getColumnName(i), rs.getObject(i));
			}
			return map;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
	}

	protected int getIdentityId(Connection conn, String sql)
			throws ExceptionCommonBase {
		int id = 0;
		int row = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			row = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(stmt);
		}
	}

	protected long getIdentityIdLong(Connection conn, String sql)
			throws ExceptionCommonBase {
		long id = 0;
		int row = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			row = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
			return id;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(stmt);
		}
	}

	protected long getIdentityIdLong(String sql) throws ExceptionCommonBase {
		Connection conn = getConnection();
		try {
			return getIdentityIdLong(conn, sql);
		} finally {
			releaseConnection(conn);
		}
	}

	protected int getIdentityId(String sql) throws ExceptionCommonBase {
		Connection conn = getConnection();
		try {
			return getIdentityId(conn, sql);
		} finally {
			releaseConnection(conn);
		}
	}

	protected int getInt(String sql) throws ExceptionCommonBase {
		Object obj = getObject(sql);
		if (obj == null) {
			return 0;
		}
		return Utils.toInt(getObject(sql), 0);
	}

	public List<Map<String, Object>> getListData(String sql)
			throws ExceptionCommonBase {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
				if (count == 0) {
					metaData = rs.getMetaData();
					count = metaData.getColumnCount();
				}
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
					list.add(map);
				}
			}
			return list;

		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
	}

	protected List<Integer> getListInteger(String sql)
			throws ExceptionCommonBase {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Integer> list = new ArrayList<Integer>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
			return list;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
	}

	protected List<Map<String, Object>> getListKeyValue(String sql)
			throws ExceptionCommonBase {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(rs.getString(1), rs.getObject(2));
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}

	}

	protected List<Map<String, Object>> getListImport(String sql)
			throws ExceptionCommonBase {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				String gameName = rs.getString(2);
				String gameLabel = rs.getString(3);
				if (StringUtils.isNullOrEmpty(gameName)) {
					gameName = " ";
				}
				if (StringUtils.isNullOrEmpty(gameLabel)) {
					gameLabel = " ";
				}
				String result = gameName + "/" + gameLabel;
				map.put(rs.getString(1), result);
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}

	}

	protected List<Long> getListLong(String sql) throws ExceptionCommonBase {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Long> list = new ArrayList<Long>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
			return list;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
	}

	protected List<String> getListString(String sql) throws ExceptionCommonBase {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<String> list = new ArrayList<String>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
	}

	protected List<String[]> getListStrings(String sql)
			throws ExceptionCommonBase {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String value[] = null;
		try {
			List<String[]> list = new ArrayList<String[]>();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
				if (count == 0) {
					ResultSetMetaData metaData = rs.getMetaData();
					count = metaData.getColumnCount();
				}
				value = new String[count];
				for (int i = 1; i <= count; i++) {
					value[i - 1] = rs.getString(i);
				}
				list.add(value);
				value = null;
			}

			return list;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}

	}

	protected Long getLong(String sql) throws ExceptionCommonBase {
		return Utils.toLong(getObject(sql), 0L);
	}

	protected Map<String, String> getMapKeyValue(String sql)
			throws ExceptionCommonBase {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
		return map;
	}

	protected Object getObject(String sql) throws ExceptionCommonBase {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object obj = null;
		Connection conn = getConnection();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				obj = rs.getObject(1);
			}
			return obj;
		} catch (SQLException e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			close(ps);
			close(rs);
			releaseConnection(conn);
		}
	}

	protected String getString(String sql) throws ExceptionCommonBase {
		Object obj = getObject(sql);
		if (obj != null)
			return String.valueOf(obj);
		return null;
	}

	/**
	 * 释放数据库连接到线程池
	 * 
	 * @throws SQLException
	 * @throws ExceptionCommonBase
	 */
	public void releaseConnection(Connection conn) throws ExceptionCommonBase {
		if (conn != null) {
			SConnectionManager.getInstance(this.dbname).releaseConnection(conn);
		}
	}
}
