package http.urlconnection.testV7;

import http.httpclient_v_4_3_5.HttpUtils;
import http.httpclient_v_4_3_5.V7SendComment;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class V7CommentReturnTest {
public static Logger logger = Logger.getLogger(V7CommentReturnTest.class);
	
	public static void main(String[] args) {
		main_3(args);
	}
	
	public static void main_3(String[] args){
	    try {
	        String loginUrl = "http://192.168.251.52:8102/api/v2/mobile/comment/send.json";
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
