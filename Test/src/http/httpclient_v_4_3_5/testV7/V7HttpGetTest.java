package http.httpclient_v_4_3_5.testV7;

import http.httpclient_v_4_3_5.HttpUtils;

public class V7HttpGetTest {
	public void testGet(){
		String loginUrl = "http://192.168.251.53:8102/api/v2/mobile/channel/content.json?channel_id=701&terminal_id=100&current_page=0&rows_of_page=20&imsi=1111";
        String retStr = HttpUtils.httpGet(loginUrl);
        System.out.println(retStr);
	}
	
	public static void main(String[] args) {
		new V7HttpGetTest().testGet();
	}
}
