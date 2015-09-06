package http.httpclient_v_4_3_5.testV7;

import http.httpclient_v_4_3_5.HttpUtils;
import io.file.FileParse;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

public class V7MyGameListTest {
	private static int Cnt = 0;

	public static void main(String[] args) throws InterruptedException {
		main_3(0);
//		int times = 0;
		// while(true){
		// times++;
		// main_3(times);
		// Thread.sleep(100);
		// }
	}

	public static void main_3(int times) {

		
		BufferedReader br = null;
		try {
			String loginUrl = "http://127.0.0.1:8080/api/v2/mobile/my_game_list.json";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// params.add(new BasicNameValuePair("imsi", "460031299962412"));
			// params.add(new BasicNameValuePair("msisdn", "18931953419"));

			br = new BufferedReader(
					new InputStreamReader(FileParse.class
							.getResourceAsStream("/out.txt"),
							"utf-8"));


			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if ("".equals(readLine)) {
					continue;
				}
				readLine = readLine.trim().toLowerCase();
				int beginIdx = readLine.indexOf("access_token=");
				if (beginIdx == -1) {
					continue;
				}
				String[] paramsPairs = readLine.split("&");
				for(String paramsPair : paramsPairs){
					String[] pair = paramsPair.split("=");
					if(pair.length!=2){
						continue;
					}
					params.add(new BasicNameValuePair(pair[0], pair[1]));
				}
				long beginMills = System.currentTimeMillis();
				HttpUtils.requestPost(loginUrl, params);
				long timeCost = System.currentTimeMillis() - beginMills;
				System.out.println("cost:"+timeCost);
				params.clear();
			}


		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
