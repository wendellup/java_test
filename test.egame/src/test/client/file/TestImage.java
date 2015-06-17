package test.client.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.EGameClient;
import cn.egame.client.biz.EGameClientBiz;
import cn.egame.client.biz.model.ImageScaleInfo;
import cn.egame.common.efs.IFileSystem;
import cn.egame.common.efs.SFileSystemClient;
import cn.egame.common.util.ImageUtils;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.ConstVar;
import cn.egame.interfaces.ExceptionCommon;
import cn.egame.interfaces.fl.FileInfo;
import cn.egame.interfaces.fl.FileUsedType;
import cn.egame.interfaces.fl.FileUtils;

public class TestImage {
	private static Logger logger = Logger.getLogger(EGameClientBiz.class);
	
	public static void main(String[] args) {
//		String filePath = "E:\\pic\\meidui.jpg";
		String filePathGif = "E:\\pic\\60dddb54jw1ervmz8iqvcg208c058b2c.gif";
//		String filePath = "E:\\pic\\show_advs.png";
		String filePathMp4 = "E:\\pic\\video3.mp4";
		String filePathPng = "E:\\pic\\9.png";
		
		
		
		File file = new File(filePathPng);
		String type = ImageUtils.getFormatInFile(file);
		System.out.println(type);
	}
	
	@Test
	public void testScaleImage() throws ExceptionCommon, NoSuchAlgorithmException, IOException{
		Utils.initLog4j();
//		long efsId = 2553042; //10M图片, cost:7000
//		long efsId = 2553046; //550KB图片, cost:763
//		long efsId = 2553047; //3M图片, cost:1381, innerCost 1300
		long efsId = 2553049; //10M图片, cost:5184 innerCost 5166
		
		FileInfo fileInfo = EGameClientBiz.getInstance().getFileInfo(0, 0, efsId);
		String filePath = FileUtils.getFilePath(fileInfo.getFileUsedType(), efsId, fileInfo.getFileName());
		String readRootPath = ConstVar.UPLOAD_ADDRESS;
		String writeRootPath = ConstVar.UPLOAD_WRITE_ADDRESS;
        String writePath = writeRootPath + filePath;
        String readPath = readRootPath + filePath;
        System.out.println(readPath);
        System.out.println(filePath);
        
        ImageScaleInfo imageScaleInfo = new ImageScaleInfo(FileUsedType.game_view);
		imageScaleInfo.setImageScaleLevel(7);//1:压缩小图, 3:压缩小图和中图, 7:压缩小图,中图和大图
		String fileSystemType = "egame";
//		scaleImage(efsId, fileInfo.getFileName(), writeRootPath);
		long beginMillis = System.currentTimeMillis();
		scaleImage(efsId, fileInfo.getFileName(), writeRootPath, readRootPath, filePath, FileUsedType.GAME_VIEW_MID, imageScaleInfo.getBigWidth(),
				imageScaleInfo.getBigHeight(), 10, fileSystemType);
		System.out.println("scaleImage cost:"+(System.currentTimeMillis()-beginMillis));
	}
	// 图片压缩
    private static void scaleImage(long efsId, String fileName, String writeRootPath, String readRootPath, String filePath, int type,
            int width, int height, int zoomMaxSourcePixels, String fileSystemType) throws NoSuchAlgorithmException, ExceptionCommon,
            IOException {
        String imagePath = FileUtils.getFilePath(FileUsedType.lookup(type), efsId, fileName);
        IFileSystem fileSystem = SFileSystemClient.getInstance(fileSystemType);
        fileSystem.mkdirs(writeRootPath + imagePath);
        long beginMillis = System.currentTimeMillis();
        InputStream is = ImageUtils.scaledToEfs(readRootPath + filePath, width, height, zoomMaxSourcePixels);
        System.out.println("scaleImage inner cost:"+(System.currentTimeMillis()-beginMillis));
        fileSystem.uploadFile(writeRootPath + imagePath, is);
        System.out.println(writeRootPath + imagePath);
        closeInputStream(is);
    }
    private static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                logger.error(EGameClient.class, e);
            }
        }

    }
}
