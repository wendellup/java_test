package test.open.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import test.open.url.http.HttpRequester;
import test.open.url.http.HttpRespons;

public class Test701Game {
	public static void main(String[] args) throws IOException {
		String url = "http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=701&terminal_id=401&rows_of_page=200&current_page=";
		for(int currentPage=0; currentPage<40; currentPage++){
			HttpRespons httpRespons = 
					new HttpRequester("UTF-8").sendGet(url+currentPage);
			String content = encode(httpRespons.getContent(), "UTF-8");
			boolean isContains = content.contains("\"game_id\":248965,");
			System.out.println("第"+(currentPage+1)+"页, isContains="+isContains);
		}
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
			System.out.println(e);
		}

		return url;
	}
}
