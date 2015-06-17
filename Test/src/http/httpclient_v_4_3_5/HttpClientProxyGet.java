package http.httpclient_v_4_3_5;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientProxyGet {
	public static void main(String args[]) throws Exception {
		

		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		// 依次是目标请求地址，端口号,协议类型
		HttpHost target = new HttpHost("192.168.251.52", 8102,
				"http");
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost("192.168.251.52", 8103, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		// 请求地址
//		HttpPost httpPost = new HttpPost("http://192.168.251.52:8102/api/v2/mobile/channel/content.json?channel_id=703&terminal_id=100&current_page=0&rows_of_page=20&imsi=1111");
		HttpGet httpGet = new HttpGet("http://192.168.251.52:8102/api/v2/mobile/channel/content.json?channel_id=703&terminal_id=100&current_page=0&rows_of_page=20&imsi=1111");
		httpGet.setConfig(config);
		// 创建参数队列
//		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		// 参数名为pid，值是2
//		formparams.add(new BasicNameValuePair("pid", "2"));

//		UrlEncodedFormEntity entity;
		try {
//			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//			httpPost.setEntity(entity);
			CloseableHttpResponse response = closeableHttpClient.execute(
					target, httpGet);
			
			
			// getEntity()
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				System.out.println("response:"
						+ EntityUtils.toString(httpEntity, "UTF-8"));
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
