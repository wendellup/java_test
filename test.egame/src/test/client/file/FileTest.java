package test.client.file;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import cn.egame.common.util.Utils;
import cn.egame.interfaces.ExceptionCommon;
import cn.egame.interfaces.fl.FileUsedType;
import cn.egame.interfaces.fl.FileUtils;

public class FileTest {
	public static void main(String[] args) {
		Utils.initLog4j();
		System.out.println(Utils.getFileId("51e54234h1b81b25", 0));
	}
	
	
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
}
