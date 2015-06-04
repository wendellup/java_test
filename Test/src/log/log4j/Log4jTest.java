package log.log4j;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
	
	private static Logger LOGGER = Logger.getLogger(Log4jTest.class);
	
	@Test
	public void testInitLog4j(){
//		Utils.initLog4j();
		LOGGER.info("testInitLog4j...");
	}
	
	
}
