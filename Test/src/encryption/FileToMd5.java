package encryption;

import java.io.File;

import cn.egame.common.util.Utils;

public class FileToMd5 {
	
	
	
	public static void main(String[] args) {
		File fileDir = new File("F:\\安装包");
    	File[] files = fileDir.listFiles();
    	for(File file : files){
    		if(file.isFile()){
    			long beginMillis = System.currentTimeMillis();
    			String encryCode = Utils.encryptFileMD5(file.getAbsolutePath());
    			long endMillis = System.currentTimeMillis();
    			System.out.println(encryCode+",fileSize:"
    					+file.length()/1024/1024+"MB,cost:"+(endMillis-beginMillis)
    					+"millisSeconds."+",fileName:"+file.getName());
    			
    		}
    	}
		
	}
}
