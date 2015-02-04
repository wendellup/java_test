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
	
	// 6楼: , 202
	
		public static void main(String[] args) {
			String[] prefixUrls = {
//					"http://192.168.106.41:8102"
					
//					"http://192.168.251.52:8102"
//					"http://192.168.251.53:8102"
//					  "http://192.168.70.123:8102"
					"http://127.0.0.1:8080"
//					"http://61.160.129.2",
//					"http://202.102.39.23"
//					,"http://180.96.49.16"
//					, "http://180.96.49.15"
//					"http://open.play.cn"
					};
			V7UrlTest v7UrlTest = new V7UrlTest(prefixUrls);
			v7UrlTest.test(true);
		}
		
	
	public static Logger logger = Logger.getLogger(V7UrlTest.class);
	private List<UrlV7LinkInfo> linkInfos = new ArrayList<UrlV7LinkInfo>();
	private List<UrlV7LinkInfo> errorlinkInfos = new ArrayList<UrlV7LinkInfo>();;
	
	public V7UrlTest(String[] prefixUrls) {
		init(prefixUrls);
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
						 UrlV7LinkInfo linkInfo = new UrlV7LinkInfo(line, prefixUrl);
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
	
	private void test(boolean random){
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
