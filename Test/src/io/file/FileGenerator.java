package io.file;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

import org.junit.Test;

import cn.egame.common.util.Utils;
import cn.egame.common.web.WebUtils;

public class FileGenerator {
	
	@Test
	public void generator721() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					"721_random_imsi.txt")));
			for (int i = 100; i < 100000 + 100; i++) {
				String str = "/api/v2/mobile/channel/content.json?channel_id=721&terminal_id=2&current_page=0&rows_of_page=20&imsi="
						+randomImsi();
				pw.println(str);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}

	}
	
	private static String randomImsi(){
		Random random = new Random();
//		460036660132243
		String imsi = "";
		for(int i=0; i<15; i++){
			imsi = imsi + random.nextInt(10);
		}
		return imsi;
	}
	
	@Test
	public void generatorHallUpgrade() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					"out.txt")));
			for (int i = 100; i < 100000 + 100; i++) {
				String str = "/api/v2/mobile/hall/check_version.json?model="
						+ i
						+ "&screen_px=320*480&os=android&memory=256&version_code=11&api_level=13";
				pw.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}

	}
	
	@Test
	public void generatorImsi() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					"201512251412.txt")));
			long initImsi = 5000000000000L;
			for (long i = 0; i < 5000; i++) {
				String str = initImsi + i + "";
				pw.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	@Test
	public void sendGetToImsi() {
		try {
			long initImsi = 5000000000000L;
			for (long i = 0; i < 5000; i++) {
				String str = initImsi + i + "";
				System.out.println(str);
				String urlStr = "http://192.168.70.159:8808/api/v2/push/sdk/memory_msg.json?imsi="+str;
				try {
					WebUtils.http(urlStr, "GET", "UTF-8", null, null, null);
				} catch (Exception e) {
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
