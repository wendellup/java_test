package test.open.url;

import java.io.IOException;

import test.open.utils.Utils2;


public class MobileSearchConsumeTimeTest {
    
    static String searchUrl = "/api/v2/mobile/search/result.json?terminal_id=383&current_page=0&rows_of_page=20&keyword=";
    
    static String [] searchWords = new String[]{"捕鱼", "达人", "天天", "小鸟"};
    static String [] ips = new String[]{"http://192.168.251.53:8102", "http://192.168.251.52:8102"};
    
    public static void main(String[] args) throws IOException {
        for(String str : searchWords){
            for(String ip : ips){
                
                String url = ip + searchUrl + str;
                long urlNewMillisBegin = System.currentTimeMillis();
                String urlNewContent = Utils2.inputStream2String(Utils2.getInputStreamFromUrl(url));
                String searchNewCount = urlNewContent.substring(urlNewContent.indexOf("total")
                        , urlNewContent.indexOf(",", urlNewContent.indexOf("total"))); 
                long urlNewMillisEnd = System.currentTimeMillis();
                System.out.println("searchUrl " + url +"\n search " + str 
                        + ",\n cost" + (urlNewMillisEnd - urlNewMillisBegin) + "millis" 
                        + ",\n searchResultCount:" + searchNewCount);
            }
            
            System.out.println("<------------------------------------>");
        }
    }
}
