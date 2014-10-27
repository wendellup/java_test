package test.com;

import org.apache.log4j.Logger;


public class TestThrow {
	private static final Logger logger = Logger.getLogger(TestThrow.class);
	
	
	public static void _main(String[] args) {
		try {
			throw new RuntimeException("this is a runtime exception...");
		} catch (Throwable e) {
//			logger = null;
			logger.error(e);
		}
	}
	
	public static void main(String[] args) {
		try {
			throw new RuntimeException("this is a runtime exception...");
		} catch (Error e) {
//			logger = null;
			logger.error("error block begin...");
			logger.error(e);
			logger.error("error block end...");
		}
	}
}	
