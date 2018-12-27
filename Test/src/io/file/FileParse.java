package io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import cn.egame.common.util.Utils;

public class FileParse {
	
	public static void main(String[] args) {
//		testParse();
		convertEfsId();
		
	}

	private static void convertEfsId() {
		BufferedReader br = null;
		PrintWriter pw = null;	
		try {
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("C:\\Users\\yuchao\\Desktop\\efsId.txt")),
							"utf-8"));

			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					"C:\\Users\\yuchao\\Desktop\\.txt"), "utf-8"));

			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if ("".equals(readLine)) {
//					pw.println("");
					continue;
				}
				if(Utils.toInt(readLine, 0)==0){
					continue;
				}
				String convertEfsId = Utils.toFileName(Utils.toInt(readLine, 0));
				pw.println(convertEfsId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}
	
	private static void testParse() {
		BufferedReader br = null;
		PrintWriter pw = null;	
		try {
			br = new BufferedReader(
					new InputStreamReader(FileParse.class
							.getResourceAsStream("/现网my_game_list超时包名.txt"),
							"utf-8"));

			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					"out.txt"), "utf-8"));

			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if ("".equals(readLine)) {
					pw.println("");
					continue;
				}
				readLine = readLine.trim().toLowerCase();
				int beginIdx = readLine.indexOf("access_token=");
				if(beginIdx==-1){
					continue;
				}
				int endIdx = readLine.indexOf("/api/v2/mobile/my_game_list.json", beginIdx);
				String interceptStr = readLine.substring(beginIdx, endIdx);
				pw.println(interceptStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}
}
