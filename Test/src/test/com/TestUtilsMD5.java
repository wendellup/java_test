package test.com;

import java.io.File;

import cn.egame.common.util.Utils;

public class TestUtilsMD5 {
	public static void main(String[] args) {
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
}	
