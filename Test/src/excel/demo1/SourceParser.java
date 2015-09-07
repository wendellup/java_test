package excel.demo1;

import io.file.FileParse;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SourceParser {
	public static void main(String[] args) {
		// 1.解析source.txt文件,生成SchoolInfo的list
		List<SchoolInfo> schoolInfoList = new SourceParser().parseFile();
		System.out.println(schoolInfoList);
		// 2.将list输出到文件

	}

	/**
	 * 解析source.txt文件,生成SchoolInfo的list
	 */
	private List<SchoolInfo> parseFile(){
		List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new InputStreamReader(FileParse.class
							.getResourceAsStream("/source.txt"),
							"utf-8"));

			String readLine = null;
			SchoolInfo schoolInfo = null;
			while ((readLine = br.readLine()) != null) {
				if ("".equals(readLine)) {
					continue;
				}
				if(readLine.startsWith("schoolInfo=new Array()")){
					if(schoolInfo!=null){
						schoolInfoList.add(schoolInfo);
					}
					schoolInfo = new SchoolInfo();
				}
				if(readLine.startsWith("schoolInfo['")){
					String[] pair = readLine.split("=");
					if(pair.length!=2){
						continue;
					}
					
					String property = readLine.substring(
							readLine.indexOf("schoolInfo['")+"schoolInfo['".length()
							, readLine.indexOf("']="));
					String value = pair[1];
					if(value!=null && value.startsWith("'") && value.endsWith("'")){
						//取出值两边的单引号
						value = value.substring(1, value.length()-1);
					}
					
					//将property首字母大写,反射调用set方法
					property = toUpperCaseFirstOne(property);
		            Method setMethod = SchoolInfo.class.getMethod("set"+property, String.class);
		            setMethod.invoke(schoolInfo, value);
				}
			}
			if(schoolInfo!=null){
				schoolInfoList.add(schoolInfo);
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
		}
		
		return schoolInfoList;
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	/**
	 * 解析source.txt文件,生成SchoolInfo的list
	 */
	private static void generateExcelFile(List<SchoolInfo> list){
		PrintWriter pw = null;	
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					"out.xls"), "utf-8"));
			for(SchoolInfo schoolInfo : list){
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}
	
}
