package test.client.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;


public class UploadFileUseHttp {
    public static void main(String args[]) {
        String targetURL = null; // -- 指定URL
        File targetFile = null; // -- 指定上传文件
        targetFile = new File("C:\\Users\\yuchao\\Desktop\\imsi.txt");
        //targetURL = "http://localhost:9090/egame.open.front/api/file_upload/uploadCoreFile?file_type=1002"; // servleturl
//        targetURL = "http://192.168.251.52:8102/sns-clientForTV/tv/basic/uploadFile.json"; // servleturl
//        targetURL = "http://192.168.251.52:8102/api/v2/egame/log_error.json?type=1"; // servleturl
        
        targetURL = "http://218.94.99.204:18102/api/v2/egame/log_error.json?type=1"; // servleturl
        
        
        PostMethod filePost = new PostMethod(targetURL);
        try {
            
            
            Part[] parts = { new FilePart(targetFile.getName(), targetFile)
            
            };
            filePost.setRequestEntity(new MultipartRequestEntity(parts,
                    filePost.getParams()));
            
            HttpClient client = new HttpClient();
            
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                FileInputStream in = null;
                FileOutputStream out = null;
                byte[] responseBody = filePost.getResponseBody();

                //System.out.println("="+new String(responseBody));

                System.out.println(filePost.getResponseBodyAsString());
//                createFile("D:/dd.txt", responseBody);
                // responseBody.
                // 上传成功
            } else {
                System.out.println("上传失败");
                // 上传失败
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            filePost.releaseConnection();
        }
    }

    public static void createFile(String path, byte[] content)
            throws IOException {

        FileOutputStream fos = new FileOutputStream(path);
        System.out.println(content.toString());

        fos.write(content);
        fos.close();
    }
    
    
    public static String encryptForMD5(String souce) {
        if (souce == null || souce.length() == 0) {
            System.err.println("警告 ： 空字符串不可以作MD5加密 !");
            return null;
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(souce.getBytes());
            byte hash[] = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0"
                            + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("当请求特定的加密算法<MD5>而它在该环境中不可用时抛出此异常");
        }
        return null;
    }
}
