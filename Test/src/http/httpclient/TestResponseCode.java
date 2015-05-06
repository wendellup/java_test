package http.httpclient;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;

public class TestResponseCode {
	private static Logger LOG = Logger.getLogger(TestResponseCode.class);
	
	public static int httpGetCode(String url) {
		int statuCode = -1;
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		try {
		    httpclient = HttpClients.createDefault();
		    httpGet = new HttpGet(url);
		    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
		    httpGet.setConfig(requestConfig);
		    
			HttpResponse response = httpclient.execute(httpGet);
//			HttpEntity entity = response.getEntity();
			return response.getStatusLine().getStatusCode();
//			if (entity != null
//					&& entity.getContentType() != null
//					&& entity.getContentType().toString().trim()
//							.contains("application/json")) {
//				returnStr = EntityUtils.toString(entity);
//				;
//				EntityUtils.consume(entity);
//			} else {
//				LOG.error("httpGet" + url + "返回的ContentType不是json,为"
//						+ entity.getContentType().toString());
//			}
		} catch (Exception ex) {
			LOG.error("url :"+url, ex);
		} finally{
		    if(httpGet!=null){
		        httpGet.releaseConnection();
		    }
		    if(httpclient!=null){
		        try {
                    httpclient.close();
                } catch (IOException e) {
                    LOG.error("", e);
                }
		    }
		}
		return statuCode;
	}
	
	
	public static void main(String[] args) {
		while(true){
			String url12 = "http://180.96.49.12/f/pkg/gm/000/002/154/89e9c3f5h20df65f/245533_1.apk";
			String url11 = "http://180.96.49.11/f/pkg/gm/000/002/154/89e9c3f5h20df65f/245533_1.apk";
			
//			String url12 = "http://cdn.play.cn/f/pkg/gm/000/002/171/f0cf9b3ch21231e6/5022271_1.apk";
//			String url11 = "http://cdn.play.cn/f/pkg/gm/000/002/171/096a63b1h2122285/10000774_1.apk";
			
			try {
				
				System.out.println(Utils.toDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				
				int httpCode11 = httpGetCode(url11);
				System.out.println(url11);
				System.out.println(httpCode11);
				System.out.println("---------------------------");
				int httpCode12 = httpGetCode(url12);
				System.out.println(url12);
				System.out.println(httpCode12);
				System.out.println("---------------------------");
			} catch (Exception e) {
				LOG.error("", e);
			}
			try {
				Thread.currentThread().sleep(1000*60*2);
			} catch (InterruptedException e) {
				LOG.error("", e);
			}
		}
	}
}
