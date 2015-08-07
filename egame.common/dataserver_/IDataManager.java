package cn.egame.common.dataserver;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Allen Yang
 *
 * @date 2013-5-10
 * 
 * 解耦数据层架构与业务
 * 
 * 底层封装垂直,水平分库切表的服务
 * 
 * 分布式缓存策略以及缓存的统一维护
 * 
 * 
 * 提供的服务:
 * (1)主要是基于主键的增删查改
 * (2)条件查询
 * (3)排序分页
 * 
 * (4)boolean处理方案
 */
public interface IDataManager {
	public boolean save(BaseDBObj dbObj); //插入;
	public boolean update(BaseDBObj dbObj);//更新,根据主键更新,先查后更新;
	public boolean delete(BaseDBObj dbObj);//根据主键删除,需要设置dbObj的主键;
	
	public BaseDBObj getById(BaseDBObj cond);//根据主键查询，需要设置dbObj的主键;
	public BaseDBObj getOne(BaseDBObj cond);//设置dbObj的条件，查询一个对象;
	
	public List<BaseDBObj> getList(BaseDBObj where,LinkedHashMap<String, ESortType> orderBy,int start ,int count);//
	
	
	//表连接的排序分页
	//数据列表在数据插入与更新的时候如何维护,数据列表缓存id形式？
	//boolean类型如何处理
}
