package test.client.file;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

public class TestLog {
	private static Logger logger = Logger.getLogger(FileTest.class);
	
	public static void main(String[] args) {
		try {
			new TestLog().test();
		} catch (RemoteException e) {
//			logger.info("",e);
			logger.info(e);
		}
	}
	
	public void test() throws RemoteException{
		new FileTest().fileUsedTypeTest();
	}
}
