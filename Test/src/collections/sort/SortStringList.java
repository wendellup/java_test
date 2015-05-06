package collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//p=0.6*游戏相关度+0.4*下载热度/100+运营干预参数
public class SortStringList {
	
	public static void main(String[] args) {
		Random random = new Random();
		List<String> sortStrList = new ArrayList<String>();
		
		for(int i=0; i<5000; i++){
			double gameRelativeNum = random.nextDouble()*100;
			double downloadHotNum = random.nextDouble()*1000;
			int tagHotNum = 100;
			double pVal = gameRelativeNum*0.6 + downloadHotNum*0.4/100 + tagHotNum;
			String sortStr = i + "-" + pVal;
			sortStrList.add(sortStr);
		}
		
		long beginMillis = System.currentTimeMillis();
		Collections.sort(sortStrList, new SortStrComparator());
		System.out.println(System.currentTimeMillis() - beginMillis);
	}
	
}
