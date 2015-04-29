package http.httpclient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CTLPTest {
	public static void main(String[] args) {
		CTLPTest lbs = new CTLPTest();
		String ltpUrl = lbs.ltpRequestUrl();
		System.out.println(ltpUrl);
		System.out.println(lbs.ltpRequest(ltpUrl));
	}

	public int ltpRequest(String ltpRequestUrl) {
		int returnCount = -1;
		try {
			URL url = new URL(ltpRequestUrl);
			// http连接
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setUseCaches(false);
			http.connect();
			// 获取http响应流
			InputStream in = http.getInputStream();
			// 解析响应流
			byte[] b = new byte[in.available()];
			in.read(b);
			// 将响应流转换成字符串
			String res = new String(b);
			// 根据实际情况，判断响应结果,并设置返回值
			int of = res.indexOf("sucess");
			if (of < 0) {
				returnCount = -1;
			} else {
				returnCount = 1;
			}
		} catch (Exception e) {
			returnCount = -1;
		}
		return returnCount;
	}

	public String ltpRequestUrl() {
		StringBuilder param = new StringBuilder(
				"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=703&terminal_id=100&current_page=0&rows_of_page=20&imsi=1111");
		return param.toString();
	}
}
