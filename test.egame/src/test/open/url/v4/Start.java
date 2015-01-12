package test.open.url.v4;

public class Start {
	// 6楼: , 202

	public static void main(String[] args) {
		String[] prefixUrls = {
		// "http://192.168.251.53:8102"
//				"http://192.168.251.52:8102"
//		 "http://127.0.0.1:8080"
		// "http://61.160.129.2",
		"http://202.102.39.23"
//		 "http://180.96.49.16"
//		 , "http://180.96.49.15"
//		 "http://open.play.cn"
		};
		V4UrlHandler v4UrlTest = new V4UrlHandler(prefixUrls);
		v4UrlTest.test(true);
	}
}
