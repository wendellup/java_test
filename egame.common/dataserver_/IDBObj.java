package cn.egame.common.dataserver;


/**
 * @author Allen Yang
 *
 * @date 2013-5-10
 * 
 * 数据库对象接口
 * 
 * 设计目的：
 * (1)垂直，水平分库切表
 * (2)分布式缓存
 * (3)
 *
 */
public interface  IDBObj {
	//public abstract int getDdId();         //获取数据库ID
	public  String getTableName(); //获取该javaBean对应的表名
	
	public  String[] getUninId();  //获取主键，可以是联合主键，[columnName,value,columnName,value] 
	public  String getCacheKey();  //获得缓存key,全局唯一不重复
	
	public int getDBIndex();
}
