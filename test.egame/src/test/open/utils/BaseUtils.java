package test.open.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.egame.common.web.ExceptionWeb;

public class BaseUtils {
	
    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
            try {           
                    KeyGenerator kgen = KeyGenerator.getInstance("AES");
                    kgen.init(128, new SecureRandom(password.getBytes()));
                    SecretKey secretKey = kgen.generateKey();
                    byte[] enCodeFormat = secretKey.getEncoded();
                    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                    Cipher cipher = Cipher.getInstance("AES");// 创建密码器
                    byte[] byteContent = content.getBytes("utf-8");
                    cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
                    byte[] result = cipher.doFinal(byteContent);
                    return result; // 加密
            } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
            } catch (InvalidKeyException e) {
                    e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
            } catch (BadPaddingException e) {
                    e.printStackTrace();
            }
            return null;
    }
	
	private static final Logger LOG = Logger.getLogger(BaseUtils.class);
	
    public static long getInputStreamLengthFromUrl(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = httpClient.execute(httpGet, localContext);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return response.getEntity().getContentLength();
        }
        return 0;
    }

    public static InputStream getInputStreamFromUrl(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = httpClient.execute(httpGet, localContext);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new ExceptionWeb(url + "对应的文件不存在");
        }
        return response.getEntity().getContent();
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
    
    public static String httpGet(String url) {
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		String returnStr = null;
		try {
		    httpclient = HttpClients.createDefault();
		    httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			returnStr = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception ex) {
			LOG.error("", ex);
		} finally{
		    if(httpGet!=null){
		        httpGet.releaseConnection();
		    }
		    if(httpclient!=null){
		        try {
                    httpclient.close();
                } catch (IOException e) {
                    LOG.error("", e);
                }
		    }
		}
		return returnStr;
	}
}
