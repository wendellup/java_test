package http.httpclient_v_4_3_5;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	
	private static Logger LOG = Logger.getLogger(HttpUtils.class);
	
	public static String httpGet(String url) {
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		String returnStr = null;
		try {
		    httpclient = HttpClients.createDefault();
		    httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			// System.out.println("-------------------------------------");
			// System.out.println(response.getStatusLine());
			// System.out.println("-------------------------------------");
			HttpEntity entity = response.getEntity();
			if (entity != null
					&& entity.getContentType() != null
					) {
				returnStr = EntityUtils.toString(entity);
				;
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
	        httppost.setEntity(new UrlEncodedFormEntity(params));
	          
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println("response.toString:"+response.toString());
	        System.out.println("contentEncoding:"+response.getEntity().getContentEncoding());
	        System.out.println("getContentType:"+response.getEntity().getContentType());
	          
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        System.out.println(jsonStr);
	          
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
