package decode;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StrEncoder {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "{\"code\":701,\"text\":\"success\"}";
		
		String str2 = "192.168.243.220 POST 200 2015-02-15 09:13:46 0.002 version=110&app_key=8888007&from=phone_client&channel_id=000000&imsi=460036800994173&mac=eth70723ce57fb3&network=wifi&meid=A0000043271D15&model=HUAWEI+C8813&build_id=C8813V100R001C92B173&screen_px=480*854&agency=1&timestamp=1423962785756&api_level=16&cpu_abi=armeabi-v7a&app_ver=7.5.3_0203&signal_cdma=4&event_value=%7B%22key_name%22%3A%22com.baidu.input%22%2C%22error_code%22%3A%22-100%22%2C%22error_msg%22%3A%22Connection+to+http%3A%5C%2F%5C%2F172.27.35.1%3A9888+refused%22%2C%22last_speed%22%3A%22-1%22%7D&event_key=cn.egame.terminal.download.Error  /api/v2/egame/log.json resp_log:user_id=0";
		
		String str3 = "192.168.243.220 POST 200 2015-02-15 09:24:39 0.003 version=110&app_key=8888007&from=phone_client&channel_id=000000&imsi=460036800994173&mac=eth70723ce57fb3&network=wifi&meid=A0000043271D15&model=HUAWEI+C8813&build_id=C8813V100R001C92B173&screen_px=480*854&agency=1&timestamp=1423963480183&api_level=16&cpu_abi=armeabi-v7a&app_ver=7.5.3_0203&signal_cdma=4  /api/v2/egame/log_error.json resp_log:client_error_log=%3D%3DEGAME%3D%3DLOG%3D%3D%0AAsia%2FShanghai+2015-02-15+09%3A24%3A22%0Aversion%3D110%26app_key%3D8888007%26from%3Dphone_client%26channel_id%3D000000%26imsi%3D460036800994173%26mac%3Deth70723ce57fb3%26network%3Dwifi%26meid%3DA0000043271D15%26model%3DHUAWEI+C8813%26build_id%3DC8813V100R001C92B173%26screen_px%3D480*854%26agency%3D1%26timestamp%3D1423963462787%26api_level%3D16%26cpu_abi%3Darmeabi-v7a%26app_ver%3D7.5.3_0203%26signal_cdma%3D4%0Ajava.lang.NullPointerException%0A%09at+android.content.ComponentName.%3Cinit%3E%28ComponentName.java%29%0A%09at+android.content.Intent.%3Cinit%3E%28Intent.java%29%0A%09at+com.egame.utils.common.CommonUtil.loadUrl%28CommonUtil.java%3A994%29%0A%09at+com.egame.app.activity.EgameHomeNewActivity.onClick%28EgameHomeNewActivity.java%3A430%29%0A%09at+android.view.View.performClick%28View.java%29%0A%09at+android.view.View%24PerformClick.run%28View.java%29%0A%09at+android.os.Handler.handleCallback%28Handler.java%29%0A%09at+android.os.Handler.dispatchMessage%28Handler.java%29%0A%09at+android.os.Looper.loop%28Looper.java%29%0A%09at+android.app.ActivityThread.main%28ActivityThread.java%29%0A%09at+java.lang.reflect.Method.invokeNative%28Native+Method%29%0A%09at+java.lang.reflect.Method.invoke%28Method.java%3A511%29%0A%09at+com.android.internal.os.ZygoteInit%24MethodAndArgsCaller.run%28ZygoteInit.java%29%0A%09at+com.android.internal.os.ZygoteInit.main%28ZygoteInit.java%29%0A%09at+dalvik.system.NativeStart.main%28Native+Method%29%0A%0A&user_id=0";
		System.out.println(URLEncoder.encode(str, "utf-8"));;
	}
}
