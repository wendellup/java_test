package test.open.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.egame.common.web.ExceptionWeb;

public class BaseUtils {
	
	private static final Logger LOG = Logger.getLogger(BaseUtils.class);
	
    public static long getInputStreamLengthFromUrl(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = httpClient.execute(httpGet, localContext);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return response.getEntity().getContentLength();
        }
        return 0;
    }

    public static InputStream getInputStreamFromUrl(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = httpClient.execute(httpGet, localContext);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new ExceptionWeb(url + "对应的文件不存在");
        }
        return response.getEntity().getContent();
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
    
    public static String httpGet(String url) {
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		String returnStr = null;
		try {
		    httpclient = HttpClients.createDefault();
		    httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			returnStr = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
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
}
