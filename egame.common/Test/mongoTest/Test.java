package Test.mongoTest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;

import cn.egame.common.cache.SCacheClient;
import cn.egame.common.dataserver.DataManager;
import cn.egame.common.dataserver.TableManager;
import cn.egame.common.mongo.MongoDBManager;

public class Test {
	public static void main(String[] args) {
		MongoDBManager mongoDBManager;
		try {
			PropertyConfigurator.configure("E:\\svn\\code\\lib.common\\egame.common\\src\\Test\\mongoTest\\log4j.properties");
			//DOMConfigurator.configure("E:\\svn\\code\\lib.common\\egame.common\\src\\Test\\mongoTest\\log4j.properties");//加载.xml文件
			//PropertyConfigurator.configure("E:/study/log4j/log4j.properties");//加载.properties
			TableManager tableManager = new TableManager("E:\\svn\\code\\lib.common\\egame.common\\src\\Test\\mongoTest\\table_config.xml"); 
			mongoDBManager = new MongoDBManager("E:\\svn\\code\\lib.common\\egame.common\\src\\Test\\mongoTest\\db_config.xml",tableManager);
			
			DataManager dm = new DataManager(null,mongoDBManager,null);
			User user = new User();
			user.setUid(100);
			user.setName("hhaha");
			// dm.save(user);
			System.out.println(user.toString());
			user.setAge(999);
			dm.update(user);
			User ddd = (User) dm.getById(user);
			User user2 = new User();
			user2.setAge(999);
			User user3 = (User) dm.getOne(user2);
			System.out.println(user3.toString());
			//dm.delete(user);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
