package http.httpclient_v_4_3_5.testV7;

import http.httpclient_v_4_3_5.HttpUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import cn.egame.common.util.Utils;

public class TestStream {
	
	public static void main(String[] args) {
		Utils.initLog4j();
		main_3(args);
	}
	
	
	public static void main_3(String[] args){
	    try {
	        String loginUrl = "http://127.0.0.1:8080/api/v2/egame/log_error.json?type=3";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("content2", URLEncoder.encode("你说啥咧...", "utf-8")));
	        params.add(new BasicNameValuePair("content", URLEncoder.encode("ccc...", "utf-8")));
	              
	        HttpUtils.requestPost(loginUrl,params);
	        
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
