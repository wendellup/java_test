import java.util.HashMap;

import cn.egame.common.web.ExceptionWeb;
import cn.egame.common.web.WebUtils;



public class Test {
	public static void main(String[] args) {
		//404
//		String url = "http://192.168.251.53:18103/f/pkg/gm/000/001/766/14ee4fbch1af3bb5/5013347_1.apk";
		//网络不通
		String url = "http://18.251.53:18103/f/pkg/gm/000/001/766/14ee4fbch1af3bb5/5013347_1.apk";
		try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("code", "0");
            // TODO - fill msg
            params.put("msg", "msg");
            params.put("req_id", "reqId");
            String callBackStr = WebUtils.http(url, "GET", "UTF-8", null, params, null);
        } catch (ExceptionWeb e) {
            System.out.println("ExceptionWeb");
        } catch (Exception e) {
        	System.out.println("Exception");
        }
	}
}
