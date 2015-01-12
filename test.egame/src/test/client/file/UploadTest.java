package test.client.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.biz.EGameClientBiz;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.fl.FileUsedType;

public class UploadTest {
	private static Logger logger = Logger.getLogger(UploadTest.class);
	
	
	@Test
	public void uploadTest(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		
    		InputStream inputStream = new FileInputStream(new File("F:\\test1\\250378_0.apk"));
    		System.out.println(inputStream.available());
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.GAME, 0, 0, "250378_0.apk", false);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
//		File file = new File("F:/f/ad/90be655f2hc35667.jpg");
//		System.out.println(file.exists());
	}
}
