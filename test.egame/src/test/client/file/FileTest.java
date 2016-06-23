package test.client.file;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import cn.egame.client.EGameClientV2;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.ConstVar;
import cn.egame.interfaces.ExceptionCommon;
import cn.egame.interfaces.fl.FileInfo;
import cn.egame.interfaces.fl.FileUsedType;
import cn.egame.interfaces.fl.FileUtils;

public class FileTest {
	public static void main(String[] args) {
		Utils.initLog4j();
//		System.out.println(Utils.getFileId("47d5d513h221e8bd", 0));
//		http://cdn.play.cn/f/pkg/gm/000/002/770/84bea3d0h2a4772a/5008336.apk
		System.out.println(Utils.getFileId("91bfa820h2c1f2c6", 0));
		System.out.println(Utils.getFileId("http://cdn.play.cn/f/pkg/gm/000/002/558/36c8d67bh2709e28/2017_RM_2.5.3.2_Channel_2017.apk", 0));
		System.out.println(Utils.getFileId("http://cdn.play.cn/f/pkg/gm/006399c276h1b8eeab/_RM_2.5.3.2_Channel_2017.apk", 0));
		System.out.println(Utils.getFileId("/opt/data/cdn/efs/pkg/gm/000/002/558/006399c276h1b8eeab.apk", 0));
		
	}
	
	
	@Test
	public void getFilePath(){
		try {
			Utils.initLog4j();
			FileInfo fi = EGameClientV2.getInstance().getFileInfo(0, 0, 2942461);
			System.out.println(ConstVar.DOWNLOAD_URL+FileUtils.getFilePath(fi.getFileUsedType(), fi.getEFSId(), fi.getFileName()));
			System.out.println(ConstVar.DOWNLOAD_URL+FileUtils.getFilePath(FileUsedType.game, 2316049, "Boom_Beach-159-23540-kunlun-UC-release_1143871_135516a94b15.apk"));
		} catch (ExceptionCommon e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
//	public static void main(String[] args) {
//		Utils.initLog4j();
//	}
	
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
