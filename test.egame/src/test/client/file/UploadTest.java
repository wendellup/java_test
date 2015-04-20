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
    		File file = new File("E:\\pic\\512_512.png");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
    		ImageScaleInfo imageScaleInfo = new ImageScaleInfo(FileUsedType.game_view);
    		imageScaleInfo.setImageScaleLevel(7);//1:压缩小图, 3:压缩小图和中图, 4:压缩小图,中图和大图
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_view,
    				0, 0L, file.getName(), true, imageScaleInfo);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Test
	public void newUploadForIcon(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("E:\\pic\\800_800.jpg");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
    		ImageScaleInfo imageScaleInfo = new ImageScaleInfo(FileUsedType.game_photo);
    		imageScaleInfo.setImageScaleLevel(7);//1:压缩小图, 3:压缩小图和中图, 4:压缩小图,中图和大图
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_photo,
    				0, 0L, file.getName(), true, imageScaleInfo);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Test
	public void newUploadForIcon2(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("C:\\Users\\yuchao\\Desktop\\a33fac72h1d890fe.png");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
//    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_photo.value(),
//    				0, 0L, file.getName(), true);
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_view.value(),
    				0, 0L, file.getName());
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		UploadTest uploadDemo = new UploadTest();
		int i=5000;
		while(i>0){
			i--;
			uploadDemo.newUploadForIcon();
			uploadDemo.newUploadForIcon2();
			try {
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("i---------->"+i);
			
		}
		logger.info("cost:" + (System.currentTimeMillis()-begin));
		
	}
}
