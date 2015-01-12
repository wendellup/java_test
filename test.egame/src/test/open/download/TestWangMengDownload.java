package test.open.download;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import cn.egame.common.util.Utils;

public class TestWangMengDownload {
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
        
        gameIdList.add(5010979);
        gameIdList.add(5013938);
        
        
        while(true){
            for(Integer gId :gameIdList){
                String downloadUrlAndroid = "http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id="+
                        gId  +"&download_from=list_download&app_key=8888029&imsi=204043015244603&meid=99000206382398&screen_px=720*1280&version=1.0&network=wifi&model=SCH-R530U&api_level=15&&terminal_id=100&channel_code=20011021";

                
                String downloadUrl = downloadUrlAndroid+"&timestamps="+System.currentTimeMillis();
                System.out.println(downloadUrl);
                long fileSize = getInputStreamLengthFromUrl(downloadUrl);
                System.out.println(fileSize);
            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static long getInputStreamLengthFromUrl(String url) throws IOException{
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = httpClient.execute(httpGet, localContext);
        int statusCode = response.getStatusLine().getStatusCode();
//        Header[] headers = response.getAllHeaders();
//        for(Header header : headers){
//            System.out.println(header.getName()+":"+header.getValue());
//        }
        Header contentType = response.getFirstHeader("Content-Type");
        if(!"application/vnd.android.package-archive".equals(contentType.getValue())){
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
}

