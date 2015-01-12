package test.open.url.v7;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class UrlV7LinkInfo implements Serializable{
	
	Logger logger = Logger.getLogger(UrlV7LinkInfo.class);

	private static final long serialVersionUID = -7876108728063834519L;
	
	private String method;
	private String desc; 
	private String url;
	private String orgUrl;
	private HashMap<String, String> params
		= new HashMap<String, String>();
	
	public UrlV7LinkInfo(){
		
	}
	
	public UrlV7LinkInfo(String line, String prefixUrl) {
		String[] strs = line.split("\\|");
		if(strs.length != 3){
			 logger.error("url格式错误:"+line);
			 throw new RuntimeException("url格式错误:"+line);
		}
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
	
}
