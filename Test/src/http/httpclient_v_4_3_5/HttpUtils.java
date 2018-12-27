package http.httpclient_v_4_3_5;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	
	private static Logger LOG = Logger.getLogger(HttpUtils.class);
	
	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/api/v2/mobile/memory_msg.json?terminal_id=100&advert_topic_id=1&advert_game_id=1&advert_web_id=1&advert_notify_id=1&advert_sns_msg_id=1&advert_other_tmp_id=1&imsi=460036660132243&client_id=8888007";
		
		httpGet(url);
	}
	
//	private static final Logger LOG = LogManager.getLogger(HttpClient.class);
	public static CloseableHttpClient httpClient = null;
	public static HttpClientContext context = null;
	public static CookieStore cookieStore = null;
	public static RequestConfig requestConfig = null;

	
	public static void init() {
		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
				.setConnectionRequestTimeout(60000).build();
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
	}

	public static String httpGetKeepSession(String url) {
//		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		String returnStr = null;
		try {
//		    httpclient = HttpClients.createDefault();
//		    httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
//		    httpclient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
//		    httpclient.getParams().setCookiePolicy(CookiePolicy.);
		    httpGet = new HttpGet(url);
//		    httpGet.setHeader("x-forwarded-for", " 124.160.75.205");   
			HttpResponse response = httpClient.execute(httpGet);
			// System.out.println("-------------------------------------");
			// System.out.println(response.getStatusLine());
			// System.out.println("-------------------------------------");
			HttpEntity entity = response.getEntity();
			if (entity != null
					&& entity.getContentType() != null
					) {
				returnStr = EntityUtils.toString(entity);
				System.out.println(returnStr);
				EntityUtils.consume(entity);
			}
		} catch (Exception ex) {
			LOG.error("", ex);
		} finally{
		    if(httpGet!=null){
		        httpGet.releaseConnection();
		    }
//		    if(httpClient!=null){
//		        try {
//		        	httpClient.close();
//                } catch (IOException e) {
//                    LOG.error("", e);
//                }
//		    }
		}
		return returnStr;
	}
	
	
	public static String httpGet(String url) {
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		String returnStr = null;
		try {
		    httpclient = HttpClients.createDefault();
//		    httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
//		    httpclient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
//		    httpclient.getParams().setCookiePolicy(CookiePolicy.);
		    httpGet = new HttpGet(url);
//		    httpGet.setHeader("x-forwarded-for", " 124.160.75.205");   
			HttpResponse response = httpclient.execute(httpGet);
			// System.out.println("-------------------------------------");
			// System.out.println(response.getStatusLine());
			// System.out.println("-------------------------------------");
			HttpEntity entity = response.getEntity();
			if (entity != null
					&& entity.getContentType() != null
					) {
				returnStr = EntityUtils.toString(entity);
				System.out.println(returnStr);
				EntityUtils.consume(entity);
			}
		} catch (Exception ex) {
			LOG.error("", ex);
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
		return returnStr;
	}
	
	public static int httpGetCode(String url) {
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		try {
		    httpclient = HttpClients.createDefault();
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
                    httpclient.close();
                } catch (IOException e) {
                	LOG.error("", e);
                }
		    }
		}
	}
	
	/**
	 * 表单上传UrlEncodedFormEntity
	 * @param url
	 * @param params
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void requestPost(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	    HttpPost httppost = new HttpPost(url);
//	        httppost.setEntity(new UrlEncodedFormEntity(params));
	        
	        httppost.setEntity(new ByteArrayEntity("{a:1,b:2}".getBytes()));
	          
	        CloseableHttpResponse response = httpclient.execute(httppost);
//	        System.out.println("response.toString:"+response.toString());
//	        System.out.println("contentEncoding:"+response.getEntity().getContentEncoding());
//	        System.out.println("getContentType:"+response.getEntity().getContentType());
	          
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
//	        System.out.println(jsonStr);
	          
	        httppost.releaseConnection();
	}
	
	/**
	 * 文件上传UrlEncodedFormEntity
	 * @param url
	 * @param params
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void filePost(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	    HttpPost httppost = new HttpPost(url);
	        
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println("response.toString:"+response.toString());
	        System.out.println("contentEncoding:"+response.getEntity().getContentEncoding());
	        System.out.println("getContentType:"+response.getEntity().getContentType());
	          
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        System.out.println(jsonStr);
	          
	        httppost.releaseConnection();
	}
}
