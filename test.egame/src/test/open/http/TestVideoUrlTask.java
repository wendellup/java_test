package test.open.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.TimerTask;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import test.open.utils.BaseUtils;
import cn.egame.common.util.Utils;

public class TestVideoUrlTask extends TimerTask {
	public static String BASEURL_PART1 = "http://thirdparty.mofang.com/video_soruce_api/partner=qq&type=1&packages=";
	public static String BASEURL_PART2 = "&videoBack=jquery8823";
	public static String packageNames[] = {"com.tencent.clover", "com.kilogames.subwayserferam_chineses"};

	public static String FILE_PATH="/test/video.test/out.txt";
//	public static String FILE_PATH="C:\\Users\\thinkpad\\Desktop\\out.txt";
	
	@Override
	public void run() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(FILE_PATH), true), "utf-8") );
			pw.println("当前时间:"+Utils.toDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
			for(String pkgName : packageNames){
				pw.println("包名:"+pkgName);
				String url = BASEURL_PART1 + pkgName + BASEURL_PART2;
				String jsonStr = BaseUtils.httpGet(url);
				if(jsonStr!=null){
					jsonStr = jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf(")"));
					ObjectMapper jsonMapper = new ObjectMapper();
					JsonNode jn = jsonMapper.readTree(jsonStr);
					pw.println("返回结果:"+jsonStr);
					if (jn.get("data") != null && jn.get("data").get(pkgName)!=null) {
						JsonNode node = jn.get("data").get(pkgName);
						String videoId = node.get("video_id")==null ? null: node.get("video_id").getTextValue();
						String urlVal = node.get("url")==null ? null: node.get("url").getTextValue();
						String letv_id = node.get("letv_id")==null ? null: node.get("letv_id").getTextValue();
						
						int originalUrlSize = node.get("originUrl")==null ? 0: node.get("originUrl").size();
						pw.println("videoId    :"+videoId);
						pw.println("url        :"+urlVal);
						pw.println("letv_id    :"+letv_id);
						pw.println("originalUrl:");
						for(int i=0; i<originalUrlSize; i++){
							pw.println("\t"+node.get("originUrl").get(i));
						}
					}
				}
				pw.println();
			}
			pw.println();
			pw.println();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(pw!=null){
				pw.flush();
				pw.close();
			}
		}
	}

}
