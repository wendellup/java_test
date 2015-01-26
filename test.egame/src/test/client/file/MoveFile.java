package test.client.file;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.common.efs.IFileSystem;
import cn.egame.common.efs.SFileSystemClient;

public class MoveFile {
	private static Logger logger = Logger.getLogger(UploadTest.class);
	
	@Test
	public void movieFile(){
    	try {
    		
    		/** 文件上传 */
    		String filePath = "F:/data/local/pkg/ph/view/000/001/800/";
			String bakPath = "F:/data/local/bak/pkg/ph/view/000/001/800/";
			
			IFileSystem fileSystem = SFileSystemClient.getInstance("egame");
            fileSystem.mkdirs(bakPath);
            boolean isMoveSuccess = fileSystem.moveFile(filePath, "9176c049h1b7acf3.jpg", bakPath, "9176c049h1b7acf3.jpg");
            if(isMoveSuccess){
            	logger.info("movie success");
            	
            }else{
            	logger.info("movie error");
            }
		} catch (Exception e) {
			logger.error("", e);
		}
//		File file = new File("F:/f/ad/90be655f2hc35667.jpg");
//		System.out.println(file.exists());
	}
}
