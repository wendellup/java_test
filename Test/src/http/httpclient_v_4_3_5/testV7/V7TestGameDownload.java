package http.httpclient_v_4_3_5.testV7;

import http.httpclient_v_4_3_5.HttpUtils;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.log4j.Logger;
import org.junit.Test;

public class V7TestGameDownload {
	
	private static Logger LOG = Logger.getLogger(V7TestGameDownload.class);
	
	public static int httpGetCode(String url) {
//		CloseableHttpClient httpclient = null;
		HttpClient httpclient = null;
		HttpGet httpGet = null;
		try {
		    httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();;
		    httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			// System.out.println("-------------------------------------");
			// System.out.println(response.getStatusLine());
			// System.out.println("-------------------------------------");
			return response.getStatusLine().getStatusCode();
//			if (entity != null
//					&& entity.getContentType() != null
//					&& entity.getContentType().toString().trim()
//							.contains("application/json")) {
//				returnStr = EntityUtils.toString(entity);
//				;
//				EntityUtils.consume(entity);
//			}
		} catch (Exception ex) {
			return -1;
		} finally{
		    if(httpGet!=null){
		        httpGet.releaseConnection();
		    }
		    if(httpclient!=null){
		        try {
                    ((Closeable) httpclient).close();
                } catch (IOException e) {
                	LOG.error("", e);
                }
		    }
		}
	}
	

	
	@Test
	public void testGameDownload(){
//		String downloadUrl = "http://open.play.cn/api/v2/mobile/down.json?terminal_id=178&game_id=100000077&access_token=79c187531333e527ba9c97452204db0e&download_from=752&app_key=8888007&channel_id=20310023&imsi=460030426316930&msisdn=&user_id=&version=753&network=ct3g&meid=A0000043A1EBD8&model=HUAWEI+A199&terminal_id=178&screen_px=720*1280&api_level=16&brand=Huawei";
		String downloadUrl = "http://192.168.251.52:8102/api/v2/mobile/down.json?terminal_id=1&game_id=235529";
		int responseCode = HttpUtils.httpGetCode(downloadUrl);
		System.out.println(responseCode);
	}
}
