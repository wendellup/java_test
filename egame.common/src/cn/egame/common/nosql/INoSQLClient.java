package cn.egame.common.nosql;

import java.util.LinkedHashMap;
import java.util.List;

import cn.egame.common.exception.ExceptionCommonBase;

public interface INoSQLClient {

	public boolean save(Object val) throws ExceptionCommonBase;

	public boolean update(Object val) throws ExceptionCommonBase;

	public boolean delete(Object val) throws ExceptionCommonBase;

	public boolean deleteOne(Object val) throws ExceptionCommonBase;

	public <T> T getById(T query) throws ExceptionCommonBase;

	public <T> T getOne(T query) throws ExceptionCommonBase;

	public <T> T getOneAndDel(T query) throws ExceptionCommonBase;

	public boolean saveOrUpdate(Object val) throws ExceptionCommonBase;

	/**
	 * @Description: TODO
	 * @param <T>
	 * @param query
	 *            condition
	 * @param orderBy
	 *            String:columnName Boolean: true asc false desc
	 * @param start
	 *            : default is 0
	 * @param count
	 *            : 0 means no limit,find all.
	 * @return
	 * @throws ExceptionCommonBase
	 * @return List<T>
	 * @throws
	 * @Author Allen Yang
	 * @Create Date 2014-6-25
	 * @Modified by none
	 * @Modified Date
	 */
	public <T> List<T> getList(T query, LinkedHashMap<String, Boolean> orderBy,
			int start, int count) throws ExceptionCommonBase;

	public <T> List<T> getList(T query) throws ExceptionCommonBase;
}
