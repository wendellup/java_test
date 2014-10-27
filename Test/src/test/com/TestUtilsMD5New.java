package test.com;

import java.io.File;

import cn.egame.common.util.Utils;

public class TestUtilsMD5New {
	public static void main(String[] args) {
			System.out.println("-------------begin-------------");
			File fileDir = new File("F:/data/local/pkg/gm/000/001/354/78d30eadh14a9483/");
			for(File file : fileDir.listFiles()){
				try {
					if(file.isDirectory()){
						continue;
					}
					long begin1 = System.currentTimeMillis();
					System.out.println("file size is "+file.length()/1024L/1024L + "MB");
					System.out.println(Utils.encryptFileMD5(file.getAbsolutePath()));
					long begin2 = System.currentTimeMillis();
					System.out.println("filePath: " + file.getAbsolutePath()
							+", cost = " + (begin2 - begin1) + "millis seconds");
					System.out.println("-------------------------------");
				} catch (Throwable e) {
					e.printStackTrace();
					System.out.println("xxxxxxxxxxxx");
				}
			}
			System.out.println("-------------end-------------");
	}
}	
