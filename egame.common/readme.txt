egame.common 通用包
	日志文件的初始化
	封装了对mysql数据库连接池,redis/memcached 的底层缓存，后期扩充数据库/缓存集群化

	
1，jar引用
	必须
		log4j-1.2.16.jar
		mysql-connector-java-5.1.14-bin.jar
		cache-jedis-2.1.0.jar
		java_memcached-release_2.5.3.jar
		druid-0.2.8.jar
		commons-pool-1.5.4.jar
	可选
		mysql
		jedis
	

2，packet说明
	cn.egame.common.cache
		实现 redis的缓存
	cn.egame.common.data
		实现对数据库的常用方法封装
	cn.egame.common.exception
		实现自定义的异常封装
	cn.egame.common.lang
		实现多语言的配置
	cn.egame.common.nosql
		实现nosal的存储
	cn.egame.common.broadcast
		实现对消息广播的封装（例如通知各个client清理本地缓存）
	cn.egame.common.threadpool
		实现对线程池的封装，单例模式
	cn.egame.common.web
		一些基础的web方法
	cn.egame.common.util
		基本的工具库
3，使用说明
	在程序的入口处，需要调用
		Util.initLog4j();
	若根目录路径计算出错
		请调用路径  Util.setAppRoot
		
		
4， 读取的配置文件(/config)		