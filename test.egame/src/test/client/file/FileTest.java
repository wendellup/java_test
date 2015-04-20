package test.client.file;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.EGameClientV2;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.ExceptionCommon;
import cn.egame.interfaces.fl.FileInfo;
import cn.egame.interfaces.fl.FileUsedType;
import cn.egame.interfaces.fl.FileUtils;

public class FileTest {
//	public static void main(String[] args) {
//		Utils.initLog4j();
//		System.out.println(Utils.getFileId("51e54234h1b81b25", 0));
//	}
	
	
	@Test
	public void getFilePath(){
		try {
			Utils.initLog4j();
			System.out.println(FileUtils.getFilePath(FileUsedType.game, 1802073, "aa"));
		} catch (ExceptionCommon e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		Utils.initLog4j();
	}
	
//	@Test
	public void fileUsedTypeTest() throws RemoteException{
		String fileUrl = "";
//            try {
//            	FileUsedType[] types = FileUsedType.values();
            	FileUsedType fut = FileUsedType.game_photo;
//            	for(FileUsedType fut : types){
            		FileInfo fileInfo = EGameClientV2.getInstance().getGameIconByGidAndFileType(0, 0, 1, fut);
//            	}
            	throw new RuntimeException("new RuntimeException...");
            		
//			} catch (RemoteException e) {
////				logger.error(e.getMessage(), e);
//				logger.error(e);
//			}
//            fileUrl = Urls.getFileUrlByFileInfo(request, appId, loginUserId, fileInfo, fileUsedTypeStore, fileUsedTypeShow);
//        return fileUrl;
	}
}
