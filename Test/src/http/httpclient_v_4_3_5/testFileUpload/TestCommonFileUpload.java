package http.httpclient_v_4_3_5.testFileUpload;

import http.httpclient_v_4_3_5.HttpUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

public class TestCommonFileUpload {
	public static void main(String[] args) {
		main_3(args);
	}
	
	public static void main_3(String[] args){
	    try {
	        String loginUrl = "http://127.0.0.1:8080/Z_Test_Web/upload.action";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("content", URLEncoder.encode("你说啥咧...", "utf-8")));
	        params.add(new BasicNameValuePair("access_token", "de26a9cbd44860ec4c8578f5cfeee05c"));
	        params.add(new BasicNameValuePair("game_id", "246148"));
	              
	        	HttpUtils.requestPost(loginUrl,params);
	        
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
