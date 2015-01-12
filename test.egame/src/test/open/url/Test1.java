package test.open.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.egame.common.util.Utils;
import cn.egame.interfaces.fl.FileUtils;

public class Test1 {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        String url = "http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=250158&download_from=list_download%26app_key%3D8888029%26imsi%3D204043015244603%26meid%3D99000206382398%26screen_px%3D720*1280%26version%3D1.0%26network%3Dwifi%26model%3DSCH-R530U%26api_level%3D15&terminal_id=100&channel_code=20031059";
//        String url = "param: access_token=3992b8240d2964f7f0b738c8a6cf2c64&client_id=8888007&vc=749&imsi=460030774198010&terminal_id=8023&packages=%5B%7B%22com.happyelements.AndroidAnimal%2F%25E5%25BC%2580%25E5%25BF%2583%25E6%25B6%2588%25E6%25B6%2588%25E4%25B9%2590%22%3A17%7D%2C%7B%22com.tencent.mm%2F%25E5%25BE%25AE%25E4%25BF%25A1%22%3A501%7D%2C%7B%22com.dashi.shuajizhuanjia%2F%25E8%25BF%259E%25E6%258E%25A5%25E5%258A%25A9%25E6%2589%258B%22%3A505%7D%2C%7B%22com.qihoo.secstore%2F%25E5%25AE%2589%25E5%2585%25A8%25E5%25B8%2582%25E5%259C%25BA%22%3A202000001%7D%2C%7B%22com.qihoo360.mobilesafe%2F%25C2%25A0360%25E5%258D%25AB%25E5%25A3%25AB%22%3A231%7D%2C%7B%22com.qihoo.appstore%2F360%25E6%2589%258B%25E6%259C%25BA%25E5%258A%25A9%25E6%2589%258B%22%3A120000010%7D%2C%7B%22sh.lilith.dgame.s360%2F%25E5%2588%2580%25E5%25A1%2594%25E4%25BC%25A0%25E5%25A5%2587%22%3A26604%7D%2C%7B%22com.kunpo.babaqunaer2%2F%25E7%2588%25B8%25E7%2588%25B8%25E5%258E%25BB%25E5%2593%25AA%25E5%2584%25BF2%22%3A11%7D%2C%7B%22com.dashi.mtkcamera%2FMTKCamera%22%3A1%7D%2C%7B%22com.qihoo.cleandroid_cn%2F360%25E6%25B8%2585%25E7%2590%2586%25E5%25A4%25A7%25E5%25B8%2588%22%3A37%7D%2C%7B%22com.tencent.mobileqq%2FQQ%22%3A182%7D%2C%7B%22net.qihoo.launcher.widget.clockweather%2F360%25E5%25A4%25A9%25E6%25B0%2594%22%3A40%7D%2C%7B%22com.egame%2F%25E7%2588%25B1%25E6%25B8%25B8%25E6%2588%258F%22%3A749%7D%5D&vc_type=4&access_token=3992b8240d2964f7f0b738c8a6cf2c64&client_id=8888007&imsi=460030774198010&msisdn=18112996732";
        String url = "%26app_key%3D8888029%26imsi%3D460036800994173%26meid%3DA0000043271D15%26screen_px%3D480*854%26version%3D1.0%26network%3Dwifi%26model%3DHUAWEI+C8813%26api_level%3D16%26prev_page%3D%26curr_page%3D%26duration%3D0%26event_key%3Ddetail_download%26event_value%3D5010979%26timestamp%3D1416536367763%26show_type%3D1";
        String decodeUrl = URLDecoder.decode(url, "utf-8");
        System.out.println(decodeUrl);
        
        System.out.println(Utils.getFileId("/677/0b29656dh199a3e6", 0));
    }
}
