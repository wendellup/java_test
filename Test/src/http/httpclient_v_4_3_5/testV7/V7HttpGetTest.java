package http.httpclient_v_4_3_5.testV7;

import http.httpclient_v_4_3_5.HttpUtils;

import java.net.CookiePolicy;
import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.tools.ant.taskdefs.Sleep;

import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.web.WebUtils;
import cn.egame.common.web.WebUtils.HeaderProperty;

public class V7HttpGetTest {
	public void testGet(){
		String loginUrl = "http://192.168.251.53:8102/api/v2/mobile/channel/content.json?channel_id=701&terminal_id=100&current_page=0&rows_of_page=20&imsi=1111";
        String retStr = HttpUtils.httpGet(loginUrl);
        System.out.println(retStr);
	}
	
	public static void _main(String[] args) {
		new V7HttpGetTest().testGet();
	}
	
	public static HashMap<HeaderProperty, String> headers = new HashMap<HeaderProperty, String>();
	static{
//		headers.put(HeaderProperty.Cookie, "__jsluid=a24913af67d5180974c18523e24d08a0; JSESSIONID=YKxXWw1fRJCVSClWvv8brv2ZZrJsBVLgpQCjDxGnJHNbvSMKn021!-618727874");
////		method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
//		headers.put(HeaderProperty.User_Agent, "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
//		
//		headers.put(HeaderProperty.Refer, "http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_showPage.action");
		
		HttpUtils.init();
	}
	
	public static void main(String[] args) throws ExceptionCommonBase {
		String url = "http://192.168.243.225:8081/api/v2/mobile/ext/member/game_info.json?game_id=5137186&channel_code=92000013";
//		String urlStr, String method, String charset, HashMap header, HashMap params, HashMap response)
		String result = WebUtils.http(url, "GET", "UTF-8", headers, null, null);
		String resul2t = WebUtils.http(url, "GET", "UTF-8", headers, null, null);
		System.out.println(result);
	}
	
	public static void main3(String[] args) throws InterruptedException {
		String url = "http://192.168.243.225:8081/api/v2/mobile/ext/member/game_info.json?game_id=5137186&channel_code=92000013";
        String retStr = HttpUtils.httpGetKeepSession(url);
        Thread.sleep(2000);
        String retStr2 = HttpUtils.httpGetKeepSession(url);
	}
}

