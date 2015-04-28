package test.com;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import cn.egame.common.util.Utils;

public class TestUtilsMD5 {
	public static void main2(String[] args) {
			File fileDir = new File("F:\\安装包");
			for(File file : fileDir.listFiles()){
				try {
					if(file.isDirectory()){
						continue;
					}
					long begin1 = System.currentTimeMillis();
					System.out.println("file size is "+file.length()/1024L/1024L + "MB");
					System.out.println(Utils.getFileMD5String(file.getAbsolutePath()));
					long begin2 = System.currentTimeMillis();
					System.out.println("filePath: " + file.getAbsolutePath()
							+", cost = " + (begin2 - begin1) + "millis seconds");
					System.out.println("-------------------------------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
	}
	
	public static void main(String[] args) {
		testMd5();
	}
	
	@Test
	public static void testMd5(){
//		String str = "xbgpyjwacUNZ5NxMiBav";
		String str = 6636+"xbgpyjwacUNZ5NxMiBav";
		String encodeStr = "";
		try {
			encodeStr = Utils.encryptMD5(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(encodeStr);
	}
}	
