package collections.sort;

import java.util.Comparator;

//p=0.6*游戏相关度+0.4*下载热度/100+运营干预参数
public class SortBoComparator implements Comparator<SortBo> {

	@Override
	public int compare(SortBo o1, SortBo o2) {
		int flag = 0;
		if (null != o1 && null != o2) {
			flag = ((o1.getGameRelativeNum()*0.6 + o1.getDownloadHotNum()*0.4/100 + o1.getTagHotNum()) 
						- (o2.getGameRelativeNum()*0.6 + o2.getDownloadHotNum()*0.4/100 + o2.getTagHotNum())) 
						> 0 ? -1 : 1;
		}
		return flag;
	}

}
