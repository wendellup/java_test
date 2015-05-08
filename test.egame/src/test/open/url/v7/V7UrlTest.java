package test.open.url.v7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import test.open.url.http.HttpRequester;
import test.open.url.http.HttpRespons;
import test.open.url.mail.MailSenderInfo;
import cn.egame.common.util.Utils;

public class V7UrlTest {
	
	private static String accessTokenUrl = "/oauth/access_token?grant_type=password&client_id=8888007&client_secret=5da590de538da6a59c723b6cb092b210&imsi=460036580106912&fromer=10010107";
	private static String access_token = "";
	// 6楼: , 202
	
	
		public static void main(String[] args) {
			String[] prefixUrls = {
//					"http://192.168.106.41:8102"
					
//					"http://192.168.251.17:8102"
					"http://192.168.251.52:8102"
					
//					"http://127.0.0.1:8080"
					/*
					//重要,三楼接口服务器
					  "http://192.168.106.25:8102",
					"http://192.168.106.26:8102",
					"http://192.168.106.27:8102",
					"http://192.168.106.28:8102",
					//重要,六楼接口服务器		
					"http://192.168.70.123:8102",
					"http://192.168.70.124:8102",
					"http://192.168.70.125:8102",
					"http://192.168.70.126:8102",
					
//					"http://61.160.129.2",
					
					//非重要,三楼接口服务器
					"http://192.168.106.31:8102",
					"http://192.168.106.32:8102",
//					//非重要,六楼接口服务器	
					"http://192.168.70.127:8102",
					"http://192.168.70.128:8102"
					 */
					
					
					/*
					"http://202.102.39.23"
					,"http://180.96.49.16"
					, "http://180.96.49.15"
					
					
					 */
//					"http://open.play.cn"
					
//					"http://192.168.106.41:8102"
					};
			V7UrlTest v7UrlTest = new V7UrlTest(prefixUrls);
//			/*
			while(true){
				try {
					Thread.currentThread().sleep(5);
					v7UrlTest.test();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
//			*/
//			v7UrlTest.test();
		}
		
	
	public static Logger logger = Logger.getLogger(V7UrlTest.class);
	private List<UrlV7LinkInfo> linkInfos = new ArrayList<UrlV7LinkInfo>();
	private List<UrlV7LinkInfo> errorlinkInfos = new ArrayList<UrlV7LinkInfo>();;
	
	public V7UrlTest(String[] prefixUrls){
		getAccessToken(prefixUrls);
		init(prefixUrls);
	}
	
	/**
	 * 获取用户token...
	 * @param prefixUrls
	 */
	private void getAccessToken(String[] prefixUrls) {
		try {
			
			HttpRespons httpRespons = 
					new HttpRequester("UTF-8").sendGet(prefixUrls[0]+accessTokenUrl);
			
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES , true); 
			String content = encode(httpRespons.getContent(), "UTF-8");
			JsonNode jn = jsonMapper.readTree(content);
			if(jn.get("ext")!=null){
				jn = jn.get("ext");
				access_token = (String) jn.get("access_token").getValueAsText();
			}
			logger.info("access_token------------------------->"+access_token);
		} catch (Exception e) {
			logger.info("", e);
		}
	}

	public void init(String[] prefixUrls){
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new InputStreamReader(
							V7UrlTest.class.getResourceAsStream("/url_v7.txt"), "utf-8"));
			String line = null;
			while((line = br.readLine())!=null){
				 line = line.trim();
				 if(line.equals("") || line.startsWith("#")){
					 continue;
				 }
				 
				 try {
					 for(String prefixUrl : prefixUrls){
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("access_token", access_token);
						UrlV7LinkInfo linkInfo = new UrlV7LinkInfo(line,
								prefixUrl, params);
						linkInfos.add(linkInfo);
					 }
				 } catch (Exception e) {
					 logger.error("", e);
					 continue;
				 }
				 
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}
	
	private void test(){
		for(UrlV7LinkInfo urlV7LinkInfo : linkInfos){
			try {
				long startMillis = System.currentTimeMillis();
//				if(random){
					HashMap<String, String> params = urlV7LinkInfo.getParams();
					params.put("time_stamp", System.currentTimeMillis()+"");
					params.put("access_token", access_token);
					urlV7LinkInfo.setParams(params);
//				}
				
				HttpRespons httpRespons = null;
				if("GET".equals(urlV7LinkInfo.getMethod())){
					httpRespons = new HttpRequester("UTF-8").sendGet(urlV7LinkInfo.getUrl(), 
							urlV7LinkInfo.getParams());
				}else if("POST".equals(urlV7LinkInfo.getMethod())){
					httpRespons = new HttpRequester("UTF-8").sendPost(urlV7LinkInfo.getUrl(), 
							urlV7LinkInfo.getParams());
				}
//				System.out.println(httpRespons.content);
				long endMillis = System.currentTimeMillis();
				
				ObjectMapper jsonMapper = new ObjectMapper();
				jsonMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES , true); 
				String content = encode(httpRespons.getContent(), "UTF-8");
	            JsonNode jn = jsonMapper.readTree(content);
	            if(jn.get("code")!=null){
	            	int code = jn.get("code").getIntValue();
	            	if(code == 0){
	            		logger.info("ok - "+urlV7LinkInfo.getDesc()+"("+(endMillis-startMillis)+"ms) - "+ urlV7LinkInfo.getOrgUrl());
	            		continue;
	            	}
	            }
	            errorlinkInfos.add(urlV7LinkInfo);
	            
			} catch (Exception e) {
				logger.error("", e);
				errorlinkInfos.add(urlV7LinkInfo);
			}
		}
		
		//显示出错的url...
		StringBuilder sb = new StringBuilder();
		for(UrlV7LinkInfo urlV7LinkInfo : errorlinkInfos){
			sb.append("error - "+urlV7LinkInfo.getDesc()+" - "+ urlV7LinkInfo.getOrgUrl()).append("\r\n");
			logger.info("error - "+urlV7LinkInfo.getDesc()+" - "+ urlV7LinkInfo.getOrgUrl());
		}
		
		// 发邮件
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("connectionCheck@163.com");
		mailInfo.setPassword("abcd1234");// 您的邮箱密码
		mailInfo.setFromAddress("connectionCheck@163.com");
		mailInfo.setToAddress("15366189928@189.cn");
		mailInfo.setSubject("7版现网出现问题"
				+ Utils.toDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//		mailInfo.setContent(sb.toString());
//		// 这个类主要来发送邮件
//		SimpleMailSender sms = new SimpleMailSender();
//		sms.sendTextMail(mailInfo);// 发送文体格式
	}
	
	private void test(boolean random, String[] prefixUrl){
		for(UrlV7LinkInfo urlV7LinkInfo : linkInfos){
			try {
				long startMillis = System.currentTimeMillis();
				if(random){
					HashMap<String, String> params = urlV7LinkInfo.getParams();
					params.put("timestamp", System.currentTimeMillis()+"");
					urlV7LinkInfo.setParams(params);
				}
				
				HttpRespons httpRespons = null;
				if("GET".equals(urlV7LinkInfo.getMethod())){
					httpRespons = new HttpRequester("UTF-8").sendGet(urlV7LinkInfo.getUrl(), 
							urlV7LinkInfo.getParams());
				}else if("POST".equals(urlV7LinkInfo.getMethod())){
					httpRespons = new HttpRequester("UTF-8").sendPost(urlV7LinkInfo.getUrl(), 
							urlV7LinkInfo.getParams());
				}
//				System.out.println(httpRespons.content);
				long endMillis = System.currentTimeMillis();
				
				ObjectMapper jsonMapper = new ObjectMapper();
				jsonMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES , true); 
				String content = encode(httpRespons.getContent(), "UTF-8");
	            JsonNode jn = jsonMapper.readTree(content);
	            if(jn.get("code")!=null){
	            	int code = jn.get("code").getIntValue();
	            	if(code == 0){
	            		logger.info("ok - "+urlV7LinkInfo.getDesc()+"("+(endMillis-startMillis)+"ms) - "+ urlV7LinkInfo.getOrgUrl());
	            		continue;
	            	}
	            }
	            errorlinkInfos.add(urlV7LinkInfo);
	            
			} catch (Exception e) {
				logger.error("", e);
				errorlinkInfos.add(urlV7LinkInfo);
			}
		}
		
		//显示出错的url...
		StringBuilder sb = new StringBuilder();
		for(UrlV7LinkInfo urlV7LinkInfo : errorlinkInfos){
			sb.append("error - "+urlV7LinkInfo.getDesc()+" - "+ urlV7LinkInfo.getOrgUrl()).append("\r\n");
			logger.info("error - "+urlV7LinkInfo.getDesc()+" - "+ urlV7LinkInfo.getOrgUrl());
		}
		
		/*
		// 发邮件
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("connectionCheck@163.com");
		mailInfo.setPassword("abcd1234");// 您的邮箱密码
		mailInfo.setFromAddress("connectionCheck@163.com");
		mailInfo.setToAddress("15366189928@189.cn");
		mailInfo.setSubject("7版现网出现问题"
				+ Utils.toDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
		mailInfo.setContent(sb.toString());
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		*/
//		sms.sendTextMail(mailInfo);// 发送文体格式
	}
	

	public static String encode(String url, String charset) {
		try {
			Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
//			int count = 0;
			while (matcher.find()) {
//				System.out.println(matcher.group());
				String tmp = matcher.group();
				url = url.replaceAll(tmp,
						java.net.URLEncoder.encode(tmp, charset));
			}
//			System.out.println(count);   
			//url = java.net.URLEncoder.encode(url,"gbk");  
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}

		return url;
	}

}
