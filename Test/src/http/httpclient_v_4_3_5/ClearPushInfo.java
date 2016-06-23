package http.httpclient_v_4_3_5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;
import cn.egame.server.open.biz.v2.ThreadPoolManager;



public class ClearPushInfo {
	
	public static Logger logger = Logger.getLogger(ClearPushInfo.class);
	
	public static List<String> imsiMd5List = new ArrayList<String>(); 
	
		public static void main(String[] args) throws Exception {
			Utils.initLog4j();
			long beginMills = System.currentTimeMillis();
			int num = 0;
			int okNum = 0;
			BufferedReader br = null;
			try {
				br = new BufferedReader(
						new InputStreamReader(new FileInputStream(new File("/test/push/pushimsi.txt")),
								"utf-8"));

				String readLine = null;
				while ((readLine = br.readLine()) != null) {
//					num++;
					logger.info(num++);
					if ("".equals(readLine)) {
						continue;
					}
					readLine = readLine.trim().toLowerCase();
					String md5Str = Utils.encryptMD5(readLine);
					imsiMd5List.add(md5Str);
//					String wholeUrl = url + md5Str;
//					int httpCode = HttpClientUtils.simpleGetInvokeRetHttpCode(wholeUrl, null, "UTF-8");
//					logger.info("current num:"+ num+",imsi:"+readLine+",md5Str:"+md5Str+",retCode:"+httpCode);
//					if(httpCode==200){
//						okNum++;
//					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			logger.info("init md5ImsiList ok .........oknum:"+okNum+",cost:"+(System.currentTimeMillis()-beginMills)/1000+"seconds");
			System.out.println(imsiMd5List.size());
			for(int i=0; i<imsiMd5List.size(); i++){
				HttpGetUrlThread httpGetUrlThread = new HttpGetUrlThread(i, null, imsiMd5List.get(i));
				ThreadPoolManager.threadPool.execute(httpGetUrlThread);
			}
			
			logger.info("init md5ImsiList ok .");
		}
}

class HttpGetUrlThread extends Thread{
	public static Logger logger = Logger.getLogger(HttpGetUrlThread.class);
	private int num;
	private String url;
	private String md5Imsi;
	
	public HttpGetUrlThread(int num, String url, String md5Imsi){
		this.num = num;
		this.url = url;
		this.md5Imsi = md5Imsi;
	}
	
        @Override
        public void run() {
        	String wholeUrl = "http://push.vcgame.cn/api/v2/mcore/sdk/mm?uii="+md5Imsi;
            try {
				int httpCode = HttpClientUtils.simpleGetInvokeRetHttpCode(wholeUrl, null, "UTF-8");
				logger.info("current num:"+ num+",md5Str:"+md5Imsi+",retCode:"+httpCode);
			} catch (Exception e) {
				e.printStackTrace();
			} 
        }
}
