package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.egame.common.util.Utils;

public class ApacheMD5Util {
    private final static Log log = LogFactory.getLog(ApacheMD5Util.class);
    static MessageDigest md = null;
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
    	System.out.println(System.currentTimeMillis());
    	String terminalIdStr = "100";
    	String gameIdStr = "251141";
    	String loginUserIdStr = "1028129";
    	String validTimeStr = "1436250506612";
    	String GAME_DOWNLOAD_MD5_KEY = "egameDownload";
    	
    	String baseStr = terminalIdStr + gameIdStr + loginUserIdStr + validTimeStr + GAME_DOWNLOAD_MD5_KEY;
    	System.out.println(Utils.encryptMD5(baseStr));
    	System.out.println(md5(baseStr));;
    	
	}
    
    public static void main2(String[] args) {
    	File fileDir = new File("F:\\安装包");
    	File[] files = fileDir.listFiles();
    	for(File file : files){
    		if(file.isFile()){
    			long beginMillis = System.currentTimeMillis();
//    			InputStream is = null;
//    			try {
//    				is = new FileInputStream(file);
//    				String encryCode = md5File(is);
    			String encryCode = md5(file);
    				long endMillis = System.currentTimeMillis();
    				System.out.println(encryCode+",fileSize:"
    						+file.length()/1024/1024+"MB,cost:"+(endMillis-beginMillis)
    						+"millisSeconds."+",fileName:"+file.getName());
					
//				} catch (Exception e) {
//					log.error(e);
//				} finally{
//					try {
//						is.close();
//					} catch (IOException e) {
//						log.error(e);
//					}
//				}
    			
    		}
    	}
	}
    
 
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            log.error("NoSuchAlgorithmException: md5", ne);
        }
    }
 
    /**
     * 对一个文件求他的md5值
     * @param f 要求md5值的文件
     * @return md5串
     */
    public static String md5(File f) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
 
            return new String(Hex.encodeHex(md.digest()));
        } catch (FileNotFoundException e) {
            log.error("md5 file " + f.getAbsolutePath() + " failed:" + e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("md5 file " + f.getAbsolutePath() + " failed:" + e.getMessage());
            return null;
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 求一个字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String md5(String target) {
        return DigestUtils.md5Hex(target);
    }
 
    public static String md5File(InputStream is) throws IOException {
        return DigestUtils.md5Hex(is);
    }
    
    
}