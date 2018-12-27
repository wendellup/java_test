import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.lang3.StringEscapeUtils;

import cn.egame.common.web.ExceptionWeb;
import cn.egame.common.web.WebUtils;



public class Test {
	
	private static String root = "PUSH-";
	
	public static void getPushImsiModK() {
		String str =  root + "pushImsiModK";
	}
	
	public static void main(String[] args) {
		System.out.println(7&2);
	}
	
	public static void main3(String[] args) throws UnsupportedEncodingException {
//		String str = "<p>先支持再说( &acute;◔‸◔`)</p>";
		String str = "<p>星际&times;魔兽&times;皇室，我该理解为抄袭还是致敬经典</p>";
//		String str = "<p>111\%又开始投毒了吗</p>";
//		String str = "<p>&nbsp; &nbsp; &nbsp; 这游戏刚开服玩的，进去一切都还好说，拉队友，注重团队。前期的发展至上让我盟会排行第二，然而第一是一群氪金玩家，当然，这样也无可厚非，不氪金怎么能变强，但是这环境，这对平民玩家保护真的是存在的？且不说游戏氪金性价比久近是否平易近人，就说这才开服第二天，就有一些素质低的玩家仗着自己实力强大对其余玩家进行骚扰。</p>"
//+ "<p>&nbsp; &nbsp; &nbsp; &nbsp; 须知，这游戏对方只要比你强大一些，对你进行骚扰，你就需要花两倍乃至4倍精力去防御。去夺回由于接壤导致摩擦无可厚非，可是自己院子里白菜多的数不胜数还要抢别人手里不多的而且都已经吃下去还要给人打吐出来试问是有多恶心？而且对他们来说的小雨滴，可能就会砸的你喘不过气来。</p>"
//+ "<p>&nbsp; &nbsp; &nbsp; &nbsp;游戏本身做工就并不精美，理念也不新颖，不少人是冲着这招牌来的。但是就这样的游戏给的游戏体验还极其糟糕，对非氪玩家也不友好，真的是拿不出多少卖点了。</p>"
//+ "<p>&nbsp; &nbsp; &nbsp; 我们不是氪很多，大部分人只是首充或者月票，并沒有氪很多。氪也是玩的愉快，或者看见胡莱这个标志看见了以前怀念的东西，想要重拾。</p>"
//+ "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;现在。。。。胡莱你的游戏理念还在么？还能在空间里，能在生活中给我们带来多少欢乐？别让谁氪谁王道，哪怕没素质，这样的中国市场毁了这块招牌。。。好么？</p>";
		str = StringEscapeUtils.unescapeHtml4(str);
		System.out.println(str);
		
		String str2 = "packages=[{\"com.happyelements.AndroidAnimal\":0}]";
		System.out.println(URLEncoder.encode(str2, "UTF-8"));
//		System.out.println(URLDecoder.decode(str, "UTF-8"));
//		getPushImsiModK();
//		String regex = "^.{6,20}$";
//		String word1 = ".........adb.";
//		System.out.println(word1.matches(regex));
		
	}
	
	public static void main_(String[] args) {
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
