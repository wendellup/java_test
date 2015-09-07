package excel.demo1;

import io.file.FileParse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SourceParser {
	public static void main(String[] args) {
		// 1.解析source.txt文件,生成SchoolInfo的list
		List<SchoolInfo> schoolInfoList = new SourceParser().parseFile();
		// 2.将list输出到文件
		generateExcelFile(schoolInfoList);

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
					if(value!=null && value.startsWith("'") && value.endsWith(";")){
						//取出值两边的单引号
						value = value.substring(1, value.length()-2);
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
		File file = new File("out.xls");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			createExcel(fos, SchoolInfo.class, list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(fos!=null){
					fos.flush();
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createExcel(OutputStream os, Class cls, List<SchoolInfo> list) throws Exception{
    	//创建工作薄
    	WritableWorkbook workbook = Workbook.createWorkbook(os);
    	//创建新的一页
    	WritableSheet sheet = workbook.createSheet("First Sheet",0);
    	
    	//反射创建excel列标题
    	Field[] fs = cls.getDeclaredFields();  
    	int cols = 0;
    	for(Field field : fs){
    		Label colName = new Label(cols,0,field.getName());
    		sheet.addCell(colName);
    		cols++;
    	}
    	
    	int rows = 1;
    	for(SchoolInfo si : list){
    		cols = 0;
    		for(Field field : fs){
    			String fieldName = field.getName();
    			Method method = SchoolInfo.class.getMethod("get"+toUpperCaseFirstOne(fieldName));
    			String value = (String) method.invoke(si);
    			Label colName = new Label(cols, rows, value);
        		sheet.addCell(colName);
        		cols++;
        	}
    		rows++;
    	}
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
    }
}
