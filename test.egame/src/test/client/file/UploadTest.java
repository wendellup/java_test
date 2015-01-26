package test.client.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.biz.EGameClientBiz;
import cn.egame.client.biz.model.ImageScaleInfo;
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
	
	@Test
	public void uploadView(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("E:\\pic\\jgl2.jpg");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.GAME_VIEW, 0, 0, file.getName(), true);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
//		File file = new File("F:/f/ad/90be655f2hc35667.jpg");
//		System.out.println(file.exists());
	}
	
	@Test
	public void newUploadForView(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("E:\\pic\\jgl2.jpg");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
//    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_view,
//    				0, 0L, file.getName(), true, new ImageScaleInfo(FileUsedType.game_view, 100, 100, 200, 200));
//    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
//		File file = new File("F:/f/ad/90be655f2hc35667.jpg");
//		System.out.println(file.exists());
	}
	
	@Test
	public void newUploadForIcon(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("E:\\pic\\jgl2.jpg");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
//    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_view,
//    				0, 0L, file.getName(), true, new ImageScaleInfo(FileUsedType.game_photo, 100, 100, 200, 200));
//    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
//		File file = new File("F:/f/ad/90be655f2hc35667.jpg");
//		System.out.println(file.exists());
	}
}
