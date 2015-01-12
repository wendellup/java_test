package test.open.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import cn.egame.common.web.ExceptionWeb;

public class Utils2 {
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
}
