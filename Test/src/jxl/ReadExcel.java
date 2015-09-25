package jxl;

// 读取Excel的类 
 import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import cn.egame.interfaces.fl.FileUsedType;
import cn.egame.interfaces.fl.FileUtils;

 public   class  ReadExcel  {
     public   static   void  main(String args[])  {
    	 
    	 PrintWriter pw = null;
         try   {
        	 pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
  					"out.txt")));
        	 
            Workbook book  =  Workbook.getWorkbook( new  File( "待处理实体包.xls" ));
             //  获得第一个工作表对象 
             Sheet sheet  =  book.getSheet( 0 );
             //  得到第一列第一行的单元格 
             for(int i=0; i<50; i++){
            	 Cell cell1  =  sheet.getCell( 0 ,  i );
            	 Cell cell2  =  sheet.getCell( 1 ,  i );
            	 String result  =  cell1.getContents().trim();
            	 String result2  =  cell2.getContents().trim();
            	 String filePath = FileUtils.getFilePath(FileUsedType.game, Integer.parseInt(result), result2);
                 String filePath2 = FileUtils.getFilePath(FileUsedType.file, Integer.parseInt(result), result2);
                 pw.println(filePath2 + "  " + filePath);
             }
            book.close();
        } catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			pw.flush();
 			pw.close();
 		}
    } 
} 