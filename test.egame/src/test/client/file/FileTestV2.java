package test.client.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import test.client.file.dao.GameService;
import test.open.url.v7.V7UrlTest;
import cn.egame.common.util.Utils;

public class FileTestV2 {
	
	public static void _main(String[] args) {
//		String readLine = "http://cdn.play.cn/f/pkg/gm/000/002/371/7d60e7cdh242f655/1432711385465.apk";
//		readLine = readLine.substring(0, readLine.lastIndexOf("/"));
//		readLine = readLine.substring(readLine.lastIndexOf("/")+1);
//		System.out.println(readLine);
		
		
			String readLine = "http://cdn.play.cn/f/pkg/ph/000/001/991/117bcf92h1e64f85.jpg";
			readLine = readLine.substring(readLine.lastIndexOf("/")+1);
			readLine = readLine.substring(0, readLine.indexOf("."));
		System.out.println(readLine);
	}
	
	public static void main(String[] args){
		Utils.initLog4j();
		//读取文件,遍历每行
		//如果是apk类型,则先查出efsId,然后去t_game_file查gameId
		//如果是image类型,则先查出efsId,然后去t_game_image查gameId
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
//			br = new BufferedReader(new 
//					InputStreamReader(new FileInputStream(new File("dd.txt")),"utf-8")) ;
			br = new BufferedReader(new InputStreamReader(
					V7UrlTest.class.getResourceAsStream("/dd.txt"), "utf-8"));
			
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/out.txt"), "utf-8"));
			
			String readLine = null;
			while((readLine = br.readLine())!=null){
				if("".equals(readLine)){
					pw.println("");
					continue;
				}
				readLine = readLine.trim().toLowerCase();
				int type = 0;
				try {
					if(readLine.endsWith("apk")){
						//apk类型, 截取倒数第二个字符串
						readLine = readLine.substring(0, readLine.lastIndexOf("/"));
						readLine = readLine.substring(readLine.lastIndexOf("/")+1);
					}else{
						type = 1;
						//图片类型, 截取最后字符串
						readLine = readLine.substring(readLine.lastIndexOf("/")+1);
						readLine = readLine.substring(0, readLine.indexOf("."));
					}
				} catch (Exception e) {
					e.printStackTrace();
					pw.println("");
					continue;
				}
				long efsId = Utils.getFileId(readLine, 0);
				if(efsId==0){
					pw.println("");
					continue;
				}
				
				if(type==0){
					//查t_game_file表获取gId
					int gId = GameService.getInstance().getGIdByEfsIdFromGameFile(efsId);
					pw.println(gId);
					continue;
				}else if(type==1){
					//查t_game_image表获取gId
					int gId = GameService.getInstance().getGIdByEfsIdFromGameImage(efsId);
					pw.println(gId);
					continue;
				}
			}
					
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null){
				pw.close();
			}
		}
		
	}
	
}
