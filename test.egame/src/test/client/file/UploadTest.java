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
    		
    		InputStream inputStream = new FileInputStream(new File("E:\\pic\\6.png"));
    		System.out.println(inputStream.available());
    		//a.上传mate_file普通文件类型
//    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.MATE_FILE, 0, 0, "6.png", false);
    		//b.上传mate_photo图片文件类型
    		ImageScaleInfo imageScaleInfo = new ImageScaleInfo(FileUsedType.mate_photo);
    		imageScaleInfo.setImageScaleLevel(3);//1:压缩小图, 3:压缩小图和中图
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.mate_photo, 0, 0, "6.png", true, imageScaleInfo);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	//上传截图
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
		long beginMillis = System.currentTimeMillis();
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
//    		File file = new File("E:\\pic\\mao.jpg"); //size:550KB cost:753mllis
//    		File file = new File("E:\\pic\\mao_550_2.jpg"); //size:3MB cost:1558mllis
//    		File file = new File("C:\\Users\\yuchao\\Desktop\\222222.jpg");size:10MB cost:7000millis
    		File file = new File("E:\\pic\\mao_550_3.jpg"); //size:10MB cost:5149
    		
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
    		ImageScaleInfo imageScaleInfo = new ImageScaleInfo(FileUsedType.game_view);
    		imageScaleInfo.setImageScaleLevel(1);//1:压缩小图, 3:压缩小图和中图, 7:压缩小图,中图和大图
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_view,
    				0, 0L, file.getName(), true, imageScaleInfo);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
    	System.out.println("cost:"+(System.currentTimeMillis()-beginMillis));
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
	
	@Test
	public void uploadCmp(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("C:\\Users\\yuchao\\Desktop\\EPSH_103.zip");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
//    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game_photo.value(),
//    				0, 0L, file.getName(), true);
    		long efsId = EGameClientBiz.getInstance().writeToFile(inputStream, FileUsedType.game.value(),
    				0, 0L, file.getName(), true);
    		logger.info("efsId:"+efsId);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Test
	public void uploadPushImsiFile(){
    	try {
    		/** 文件上传 */
    		Utils.initLog4j();
    		File file = new File("C:\\Users\\yuchao\\Desktop\\EPSH_120.zip");
    		InputStream inputStream = new FileInputStream(file);
    		System.out.println(inputStream.available());
//    		String filePath = EGameClientBiz.getInstance().writeToPushFile(inputStream, FileUsedType.PUSH_ISMI_FILE, 0, 0L, file.getName());
//    		String filePath = EGameClientBiz.getInstance().writeToPushFile(inputStream, FileUsedType.PUSH_HALL_FILE, 0, 0L, file.getName());
//    		logger.info("filePath:"+filePath);
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
