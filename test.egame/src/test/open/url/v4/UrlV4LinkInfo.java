package test.open.url.v4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class UrlV4LinkInfo implements Serializable{
	
	Logger logger = Logger.getLogger(UrlV4LinkInfo.class);

	private static final long serialVersionUID = -7876108728063834519L;
	
	private String method;
	private String desc; 
	private String url;
	private String orgUrl;
	private HashMap<String, String> params
		= new HashMap<String, String>();
	private Pattern pattern;
	
	
	public UrlV4LinkInfo(){
		
	}
	
	public UrlV4LinkInfo(String line, String prefixUrl) {
		String[] strs = line.split("\\|");
		if(strs.length!=3 && strs.length!=4){
			logger.error("url格式错误:"+line);
			throw new RuntimeException("url格式错误:"+line);
		}
//		if(strs.length == 3){
		method = strs[0].trim();
		desc = strs[1].trim();
		orgUrl = prefixUrl + strs[2].trim();
		String natureUrl = prefixUrl + strs[2].trim();
		if(natureUrl.indexOf("?")!=-1){
			url = natureUrl.substring(0, natureUrl.indexOf("?"));
			String paramsStr = natureUrl.substring(natureUrl.indexOf("?")+1);
			String[] paramsAry = paramsStr.split("&");
			for(String param : paramsAry){
				String[] keyValue = param.split("=");
				if(keyValue.length!=2){
					continue;
				}
				params.put(keyValue[0], keyValue[1]);
			}
		}else{
			url = natureUrl;
		}
		
		if(strs.length==4 && strs[3].trim()!=null && ""!=strs[3].trim()){
			this.pattern = Pattern.compile(strs[3].trim());
		}
		
//		}else if(strs.length == 4){
//			method = strs[0].trim();
//			desc = strs[1].trim();
//			orgUrl = prefixUrl + strs[2].trim();
//			String natureUrl = prefixUrl + strs[2].trim();
//			if(natureUrl.indexOf("?")!=-1){
//				url = natureUrl.substring(0, natureUrl.indexOf("?"));
//				String paramsStr = natureUrl.substring(natureUrl.indexOf("?")+1);
//				String[] paramsAry = paramsStr.split("&");
//				for(String param : paramsAry){
//					String[] keyValue = param.split("=");
//					if(keyValue.length!=2){
//						continue;
//					}
//					params.put(keyValue[0], keyValue[1]);
//				}
//			}else{
//				url = natureUrl;
//			}
//		}
		
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public HashMap<String, String> getParams() {
		return params;
	}
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	public String getOrgUrl() {
		return orgUrl;
	}
	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
}
