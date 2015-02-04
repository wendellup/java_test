package encryption;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: www.jagie.com</p>
 * @author Jaige
 * @version 1.0
 */
public class FileToCRCUtil {

    public static String getFileCRCCode(File file) throws Exception {
        
        FileInputStream fileinputstream = new FileInputStream(file);
        CRC32 crc32 = new CRC32();
        for (CheckedInputStream checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
            checkedinputstream.read() != -1;
            ) {
        }
        return Long.toHexString(crc32.getValue());
        

    }

    public static void main(String[] args) throws Exception {
        
    	File fileDir = new File("F:\\安装包");
    	File[] files = fileDir.listFiles();
    	for(File file : files){
    		if(file.isFile()){
    			long beginMillis = System.currentTimeMillis();
    			String encryCode = getFileCRCCode(file);
    			long endMillis = System.currentTimeMillis();
    			System.out.println(encryCode+",fileSize:"
    					+file.length()/1024/1024+"MB,cost:"+(endMillis-beginMillis)/1000
    					+"seconds."+",fileName:"+file.getName());
    			
    		}
    	}
      }

}