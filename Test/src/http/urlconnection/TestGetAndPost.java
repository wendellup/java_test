package http.urlconnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonProcessingException;
import org.junit.Test;

import parse.json.JsonParseTest;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.web.WebUtils;


public class TestGetAndPost {
	
	private static String APPID = "wx1141594d3ec678a7";
	
	private static String APPSECRET = "09d861ca7bed9151422b37adb0898761";
	
	public static String getAccessToken() throws ExceptionCommonBase{
		String str = null;
		try {
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
			str = WebUtils.http(tokenUrl, "GET", "UTF-8", null, null, null);
			str = JsonParseTest.getAccessToken(str);
		} catch (Exception e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
		return str;
	}
	
	@Test
	public void testGetStream() throws ExceptionCommonBase{
		String accessToken = getAccessToken();
		String getUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id=C9GbRzMHnbCMSp7TqQYqBvBSYCeWowL9vulTx8JvL9D3L78M9_yRj7sEN3COqLW5";
		
		HashMap<String, String> response = new HashMap<String, String>();
		
		FileOutputStream fos = null;
		String fileName = "output"+System.currentTimeMillis()+".jpg";
		try {
			ByteArrayOutputStream baos = WebUtils.httpStreamNew(getUrl, "GET", "utf-8", null, null, response);
			String val = response.get("Content-disposition");
			if(val!=null
					&& val.indexOf("filename=\"")!=-1){
				//获取文件的名称
				int beginIndex = val.indexOf("filename=\"")+"filename=\"".length();
				int endIndex = val.indexOf("\"", beginIndex);
				fileName = val.substring(beginIndex, endIndex);
			}
			fos = new FileOutputStream(new File(fileName));
			baos.writeTo(fos);
		} catch (IOException ioe) {
			throw ExceptionCommonBase.throwExceptionCommonBase(ioe);
		} finally {
			if(fos!=null){
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	@Test
	public void testPostStream() throws JsonProcessingException, IOException{
		String accessToken = getAccessToken();
		String postUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image";
		
		File file = new File("C9GbRzMHnbCMSp7TqQYqBvBSYCeWowL9vulTx8JvL9D3L78M9_yRj7sEN3COqLW5.jpg");
		io.image.ImageUtil.changeImge(file);
		
		String retStr = WebUtils.uploadFile(postUrl, file, "media");
		System.out.println(JsonParseTest.getMediaId(retStr));
		
		
	}
	
}
