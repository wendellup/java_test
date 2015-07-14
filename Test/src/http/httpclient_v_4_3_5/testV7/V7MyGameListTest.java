package http.httpclient_v_4_3_5.testV7;

import http.httpclient_v_4_3_5.HttpUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

public class V7MyGameListTest {
	private static int Cnt = 0;
	
	public static void main(String[] args) throws InterruptedException {
		int times = 0;
		while(true){
			times++;
			main_3(times);
			Thread.sleep(100);
		}
	}
	
	public static void main_3(int times){
		
		long beginMills = System.currentTimeMillis();
	    try {
	        String loginUrl = "http://open.play.cn/api/v2/mobile/my_game_list.json";
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("imsi", "460031299962412"));
	        params.add(new BasicNameValuePair("msisdn", "18931953419"));
	        	HttpUtils.requestPost(loginUrl,params);
	        
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    long timeCost = System.currentTimeMillis()-beginMills;
	    if(timeCost>1000){
	    	Cnt++;
	    }
	    System.out.println("第"+times+"次请求,耗时:"+timeCost+",Cnt:"+Cnt);
	}
}
