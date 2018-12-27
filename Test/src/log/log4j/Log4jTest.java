package log.log4j;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
	
	private static Logger LOGGER = Logger.getLogger(Log4jTest.class);
	
//	@Test
//	public void testInitLog4j(){
////		Utils.initLog4j();
//		LOGGER.info("testInitLog4j...");
//	}
	
	public static void main(String[] args) {
		String regex = "\\s*(create|drop|alter)\\s+table\\s+[\\s\\S]*";
		String executeSql = "CREATE TABLE `t_test` (\n"
  +"`id` int(11) NOT NULL AUTO_INCREMENT,\n"
  +"`reward_time` varchar(64) NOT NULL,\n"
 + "`user_id` varchar(64) NOT NULL,\n"
 + "`phone` varchar(11) NOT NULL,\n"
 + "`award_type` varchar(2) NOT NULL,\n"
 + "`status` varchar(20) DEFAULT NULL,\n"
 + "PRIMARY KEY (`id`)\n"
+") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		
		executeSql = executeSql.toLowerCase();
    System.out.println(executeSql.matches(regex));
    
//    System.out.println(String.format("%1$-10s","a"));
    System.out.println(String.format("%-16s","上线业务名称"));
	}
	
}
