package http.httpclient_v_4_3_5;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientProxy {
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
		HttpPost httpPost = new HttpPost("http://192.168.251.52:8102/api/v2/mobile/game/check_version.json");
//		HttpGet httpPost = new HttpGet("http://192.168.251.52:8102/api/v2/mobile/channel/content.json?channel_id=703&terminal_id=100&current_page=0&rows_of_page=20&imsi=1111");
		httpPost.setConfig(config);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// 参数名为pid，值是2
		formparams.add(new BasicNameValuePair("pid", "2"));
		formparams.add(new BasicNameValuePair("terminal_id", "1"));
		formparams.add(new BasicNameValuePair("packages", "[{\"com.tencent.mobileqq\":1}]"));

		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse response = closeableHttpClient.execute(
					target, httpPost);
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
