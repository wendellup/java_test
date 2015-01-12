package test.open.url.v4;

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

public class V4UrlHandler {
	public static Logger logger = Logger.getLogger(V4UrlHandler.class);
	private List<UrlV4LinkInfo> linkInfos = new ArrayList<UrlV4LinkInfo>();
	private List<UrlV4LinkInfo> errorlinkInfos = new ArrayList<UrlV4LinkInfo>();;
	
	public V4UrlHandler(String[] prefixUrls) {
		init(prefixUrls);
	}
	
	public void init(String[] prefixUrls){
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new InputStreamReader(
							V4UrlHandler.class.getResourceAsStream("/url_v4.txt"), "utf-8"));
			String line = null;
			while((line = br.readLine())!=null){
				 line = line.trim();
				 if(line.equals("") || line.startsWith("#")){
					 continue;
				 }
				 
				 try {
					 for(String prefixUrl : prefixUrls){
						 UrlV4LinkInfo linkInfo = new UrlV4LinkInfo(line, prefixUrl);
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
	
	void test(boolean random){
		for(UrlV4LinkInfo urlV4LinkInfo : linkInfos){
			try {
				long startMillis = System.currentTimeMillis();
				if(random){
					HashMap<String, String> params = urlV4LinkInfo.getParams();
					params.put("timestamp", System.currentTimeMillis()+"");
					urlV4LinkInfo.setParams(params);
				}
				
				HttpRespons httpRespons = null;
				if("GET".equals(urlV4LinkInfo.getMethod())){
					httpRespons = new HttpRequester("UTF-8").sendGet(urlV4LinkInfo.getUrl(), 
							urlV4LinkInfo.getParams());
				}else if("POST".equals(urlV4LinkInfo.getMethod())){
					httpRespons = new HttpRequester("UTF-8").sendPost(urlV4LinkInfo.getUrl(), 
							urlV4LinkInfo.getParams());
				}
				long endMillis = System.currentTimeMillis();
				
				ObjectMapper jsonMapper = new ObjectMapper();
				jsonMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES , true); 
				String content = encode(httpRespons.getContent(), "UTF-8");
				if(urlV4LinkInfo.getPattern()==null){
					JsonNode jn = jsonMapper.readTree(content);
					if(jn.get("result")!=null){
						String result = jn.get("result").toString();
						if(result!=null){
							JsonNode resultTree = jsonMapper.readTree(result);
							if(resultTree.get("resultcode")!=null){
								int resultCode = resultTree.get("resultcode").getIntValue();
								if (resultCode == 0) {
									logger.info("ok - " + urlV4LinkInfo.getDesc()
											+ "(" + (endMillis - startMillis)
											+ "ms) - " + urlV4LinkInfo.getOrgUrl());
									continue;
								}
							}
						}
					}
				}else{
					Matcher matcher = urlV4LinkInfo.getPattern().matcher(content);
					if(matcher.find()){
						logger.info("ok - " + urlV4LinkInfo.getDesc()
								+ "(" + (endMillis - startMillis)
								+ "ms) - " + urlV4LinkInfo.getOrgUrl());
						continue;
					}
				}
	            errorlinkInfos.add(urlV4LinkInfo);
	            
			} catch (Exception e) {
				logger.error("", e);
				errorlinkInfos.add(urlV4LinkInfo);
			}
		}
		
		//显示出错的url...
		StringBuilder sb = new StringBuilder();
		for(UrlV4LinkInfo urlV7LinkInfo : errorlinkInfos){
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
	
	public static void main(String[] args) {
		String str = "\"resultcode\":\\d+";
		Pattern pattern = Pattern.compile(str);
		
		
		String content = "{\"result\":{\"resultmsg\":\"%E7%BB%9F%E8%AE%A1%E5%90%AF%E5%8A%A8%E7%88%B1%E6%B8%B8%E6%88%8F%E7%82%B9%E5%87%BB%E5%90%8C%E6%84%8F%E4%B8%8D%E5%90%8C%E6%84%8F%E6%88%90%E5%8A%9F\",\"resultcode\":1},\"showTimeMap\":{\"showBeginTime\":\"1408364527026\",\"showEndTime\":\"1408364527026\"}}";
		Matcher matcher = pattern.matcher(content);
		System.out.println(matcher.find());
				
		 
	}
	
	public static String encode(String url, String charset) {
		try {
			Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
			while (matcher.find()) {
				String tmp = matcher.group();
				url = url.replaceAll(tmp,
						java.net.URLEncoder.encode(tmp, charset));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}

		return url;
	}

}
