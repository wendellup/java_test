package jxl;

import java.io.File;

import cn.egame.common.util.Utils;
import jxl.service.GameTagService;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class V7SearchHot {
	private static int ROWS_NUM = 905;
	
	public static void main(String args[]) {
		try {
			// Excel获得文件
			Workbook wb = Workbook.getWorkbook(new File("分词搜索结果V4.xls"));
			// 打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					"分词搜索结果V4.xls"), wb);
			// 添加一个工作表
			WritableSheet sheet = book.getSheet(0);
			
			for(int i=0; i<ROWS_NUM; i++){
				Cell cell  =  sheet.getCell( 1 ,  i );
				String gId = cell.getContents();
				System.out.println(gId);
				if(gId.matches("^\\d+$")){
					int gIdNum = Utils.toInt(gId, 0);
					if(gIdNum!=0){
						int sort = GameTagService.getInstance().getGameDownloadPoint(gIdNum);
						sheet.addCell(new Label(5, i, sort+""));
					}
				}
				
			}
			book.write();
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
