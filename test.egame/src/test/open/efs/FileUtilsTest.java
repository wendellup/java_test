package test.open.efs;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.biz.EGameClientBiz;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.fl.FileInfo;
import cn.egame.interfaces.fl.FileUtils;

public class FileUtilsTest {
	private static Logger logger = Logger.getLogger(FileUtilsTest.class);
	
	
	@Test
	public void test(){
		long id1 = Utils.getFileId("000/001/656/00a29e81h1b91043", 0);
		System.out.println(id1);
	}
	
	@Test
	public void testEfsId() throws Exception{
//		Utils.initLog4j();
		long efsId = 1798177;
		FileInfo fileInfo = EGameClientBiz.getInstance().getFileInfo(0, 0, efsId);
		String ftpFilePath = FileUtils.getFilePath(
				fileInfo.getFileUsedType(), efsId, fileInfo.getFileName());
		System.out.println(ftpFilePath);
	}
	
	@Test
	public void testEfsPath() throws Exception{
//		Utils.initLog4j();
		long efsId = 1812984;
		String remotePath = EGameClientBiz.getInstance().getEfsPath(0, 0, efsId);
		System.out.println("remotePath----->"+remotePath);
	}
	

}
