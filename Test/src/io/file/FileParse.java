package io.file;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileParse {
	
	public static void main(String[] args) {
		testParse();
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
