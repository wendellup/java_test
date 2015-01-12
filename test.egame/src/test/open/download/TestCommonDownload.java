package test.open.download;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import cn.egame.common.util.Utils;

public class TestCommonDownload {
    public static void main(String[] args) throws IOException {
        Utils.initLog4j();
        //测试渠道号为20031019客户端下载
        List<Integer> gameIdList = new ArrayList<Integer>();
//        gameIdList.add(245945);
//        gameIdList.add(5007369);
//        gameIdList.add(250158);
//        gameIdList.add(5008330);
//        gameIdList.add(250318);
//        gameIdList.add(5014189);
//        gameIdList.add(5014668);
//        gameIdList.add(5012991);
//        gameIdList.add(5011171);
//        gameIdList.add(5008918);
//        gameIdList.add(5010269);
//        gameIdList.add(5010904);
//        gameIdList.add(5007385);
//        gameIdList.add(5010979);
//        gameIdList.add(5013938);
        
//        gameIdList.add(5010979);
        gameIdList.add(5013938);
        
        
//        while(true){
            for(Integer gId :gameIdList){
//                String downloadUrlAndroid = "http://open.play.cn/api/v2/mobile/down.json?terminal_id=-1&game_id="+
//                        gId  +"&download_from=list_download&app_key=8888029&imsi=204043015244603&meid=99000206382398&screen_px=720*1280&version=1.0&network=wifi&model=SCH-R530U&api_level=15&&terminal_id=100&channel_code=20011021";
                String downloadUrlAndroid = "http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id="+
                        gId  +"&download_from=list_download&app_key=8888029&imsi=204043015244603&meid=99000206382398&screen_px=720*1280&version=1.0&network=wifi&model=SCH-R530U&api_level=15&&terminal_id=100&channel_code=20011021";
                String downloadUrl = downloadUrlAndroid+"&timestamps="+System.currentTimeMillis();
                long fileSize = getInputStreamLengthFromUrl(downloadUrl);
                System.out.println(fileSize);
            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }
    
    public static long getInputStreamLengthFromUrl(String url) throws IOException{
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpContext localContext = new BasicHttpContext();
        HttpParams params = httpClient.getParams();    
        params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);   
        HttpResponse response = httpClient.execute(httpGet, localContext);
        
        HttpEntity entity = response.getEntity();
        
        int statusCode = response.getStatusLine().getStatusCode();
        
        System.out.println("http.connection-----"
        		+localContext.getAttribute(ExecutionContext.HTTP_CONNECTION));
        
        System.out.println("http.request-----"
        		+localContext.getAttribute(ExecutionContext.HTTP_REQUEST));
        
        System.out.println("http.response-----"
        		+localContext.getAttribute(ExecutionContext.HTTP_RESPONSE));
        
        System.out.println("http.target_host-----"
        		+localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST));
        
        System.out.println("http.proxy_host-----"
        		+localContext.getAttribute(ExecutionContext.HTTP_PROXY_HOST));
        
        System.out.println("http.request_sent-----"
        		+localContext.getAttribute(ExecutionContext.HTTP_REQ_SENT));
        
        RequestWrapper requestWrapper = (RequestWrapper) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
        
        Header[] headers = requestWrapper.getAllHeaders();
        System.out.println(requestWrapper.getURI());;
        System.out.println("------------- requestWrapper headers begin ------------");
        for(Header header : headers){
        	if(header != null){
        		System.out.println(header.getName()+":"+header.getValue());
        	}
        }
        System.out.println("------------- requestWrapper headers end ------------");
//        public static final String HTTP_CONNECTION = "http.connection";
//        public static final String HTTP_REQUEST = "http.request";
//        public static final String HTTP_RESPONSE = "http.response";
//        public static final String HTTP_TARGET_HOST = "http.target_host";
//        public static final String HTTP_PROXY_HOST = "http.proxy_host";
//        public static final String HTTP_REQ_SENT = "http.request_sent";
        
        if (statusCode == 302) {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                }
            }
//            if (getMethod != null) {
//                getMethod.abort();
//            }
            Header[] headers3 = response.getHeaders("Location");
            for (Header header : headers3) {
            	System.out.println("---------------");
            	System.out.println(header.getValue());
//                success = downFile(header.getValue(), file);
//                break;
            }
        }
        
        Header[] headers2 = response.getAllHeaders();
        System.out.println("------------- all headers begin ------------");
        for(Header header : headers2){
        	if(header != null){
        		System.out.println(header.getName()+":"+header.getValue());
        	}
        }
        System.out.println("------------- all headers end ------------");
        System.out.println("request url:" + url);
        System.out.println("response status:" + statusCode );
//        Header[] headers = response.getAllHeaders();
//        for(Header header : headers){
//            System.out.println(header.getName()+":"+header.getValue());
//        }
        Header contentType = response.getFirstHeader("Content-Type");
        if(contentType!=null && 
                !"application/vnd.android.package-archive".equals(contentType.getValue())){
            System.out.println(contentType.getValue());
            System.out.println(response.getEntity().getContentLength());
            throw new RuntimeException("下载错误...url为"+url);
        }
        
//        if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
//            Header header = response.getFirstHeader("location");
//            String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
//            System.out.println(newuri);
//        }
        
        if (statusCode == HttpStatus.SC_OK) {
        }
        return response.getEntity().getContentLength();
    }
    
    public static String getEncodeUrl(String url)
            throws UnsupportedEncodingException {
        if(url==null){
            return url;
        }
        int idx = url.lastIndexOf("/");
        if(idx==-1 || idx+1>=url.length()){
            return url;
        }
        String subUrl = url.substring(0,idx+1);
        String name = url.substring(idx+1);
        name = URLEncoder.encode(name, "utf-8");
//        if (name != null) {
//            name = name.replace("+", "%20");
//        }
        return subUrl+name;
    }
    
    public static String getDecodeStr(String str) 
            throws UnsupportedEncodingException {
        return URLDecoder.decode(str, "utf-8");
    }
    
    @Test
    public void testGetLoginPage() {
        final String host = "1.1.1.1";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpHost target = new HttpHost(host, 80, "http");
            HttpGet hg = new HttpGet("http://210.36.16.134/");
            HttpContext context = new BasicHttpContext();
            HttpResponse response = httpClient.execute(hg, context);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new IOException("request failed");
            }
            HttpUriRequest curReq = (HttpUriRequest) context
                    .getAttribute(ExecutionContext.HTTP_REQUEST);
            HttpHost curHost = (HttpHost) context
                    .getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            System.out.println("curReq:url:" + curReq.getURI().toString());
            // curReq:url:/(cnsxbo450a1tudbjubm0mzmf)/default2.aspx
            String curUrl = (curReq.getURI().isAbsolute()) ? curReq.getURI()
                    .toString() : (curHost.toURI() + curReq.getURI());
                    System.out.println(curUrl);
            // curUrl:
            // http://host/(cnsxbo450a1tudbjubm0mzmf)/default2.aspx
                    org.apache.http.Header[] headers = response.getAllHeaders();
     
                    System.out.println("===headers");
                    for (org.apache.http.Header h : headers) {
                        System.out.println(h.toString());
                    }
     
                    HttpEntity entity = response.getEntity();
     
                    System.out
                    .println("====Login form get:" + response.getStatusLine());
                    System.out.println(EntityUtils.toString(entity));
                    System.out.println("===Initial set of cookies:");
                    List<Cookie> cookies = httpClient.getCookieStore().getCookies();
                    if (cookies.isEmpty()) {
                        System.out.println("None");
                    } else {
                        for (Cookie c : cookies) {
                            System.out.println("-" + c.toString());
                        }
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }
}

