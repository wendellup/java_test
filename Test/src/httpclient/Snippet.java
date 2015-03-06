package httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import sun.nio.cs.SingleByte;

public class Snippet {
	private static Logger LOG = Logger.getLogger(Snippet.class);
	
	public String httpGet(String url) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
	      	HttpGet httpGet = new HttpGet(url);
	      String returnStr = "";
	      try {
	          HttpResponse response = httpclient.execute(httpGet);
	//          System.out.println("-------------------------------------");
	//          System.out.println(response.getStatusLine());
	//          System.out.println("-------------------------------------");
	          HttpEntity entity = response.getEntity();
	          if (entity != null
	        		  && entity.getContentType()!=null
	        		  && entity.getContentType().toString().trim().contains("application/json")) {
	        		  returnStr = EntityUtils.toString(entity);;
	        		  EntityUtils.consume(entity);
	          }else{
	        	  LOG.error(url+"返回的ContentType不是json");  
	          }
	      } catch (Exception ex) {
	    	  LOG.error("", ex);
	      }
	      return returnStr;
		}
	
	public static void main(String[] args) {
		String url = "http://192.168.70.156/api/v2/mobile/channel/content.json?channel_id=701&terminal_id=1&current_page=0&rows_of_page=20";
		new Snippet().httpGet(url);
	}
}

