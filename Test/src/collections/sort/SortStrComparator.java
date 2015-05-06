package collections.sort;

import java.util.Comparator;

//p=0.6*游戏相关度+0.4*下载热度/100+运营干预参数
public class SortStrComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		String[] o1SplitVal = o1.split("-");
		String[] o2SplitVal = o2.split("-");
		if(o1SplitVal.length!=2 || o2SplitVal.length!=2){
			return 0;
		}
		
		
		
		if (null != o1 && null != o2) {
			Double val1 = Double.valueOf(o1SplitVal[1]);
			Double val2 = Double.valueOf(o2SplitVal[1]);
			return val1.compareTo(val2);
		}
		return 0;
	}

}
