package http.prj;

import org.apache.tools.ant.taskdefs.Sleep;

import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.web.WebUtils;

public class DomainSearch {
	private static String[] baseStrAry = {"fuli", "go", "ba", "qun", "ti", "gu", "tang", "123", "dao", "mi", "li"};
	
	private static String WAN_WANG_SEARCH_BASE_URL = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=";
	
	public static void main(String[] args) {
		for(String baseStr : baseStrAry){
			String queryDomain = "";
			queryDomain = queryDomain+baseStr;
			for(String baseStr2 : baseStrAry){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				String queryDomain2 = queryDomain+baseStr2+".com";
				String fullURL = WAN_WANG_SEARCH_BASE_URL+queryDomain2;
				try {
					String retStr = WebUtils.http(fullURL, "GET", "UTF-8", null, null, null);
					if(retStr.contains("210 : Domain name is available")){
						System.out.println("域名可以使用--->"+queryDomain2);
					}else{
						System.out.println("域名已经被占用--->"+queryDomain2);
					}
				} catch (ExceptionCommonBase e) {
					e.printStackTrace();
				}
			}
		}
	}
}
