package collections.sort;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
//p=0.6*游戏相关度+0.4*下载热度/100+运营干预参数
public class SortMap {
	public static void main(String[] args) {
		long beginMillis = System.currentTimeMillis();
		Random random = new Random();
//		List<SortBo> sortBoList = new ArrayList<SortBo>();
//		Map<Integer, Double> sortMap = new HashMap<Integer, Double>();
		Map<Integer, Double> sortMap = new LinkedHashMap<Integer, Double>();
		
		
		
		for(int i=0; i<5000; i++){
			double gameRelativeNum = random.nextDouble()*100;
			double downloadHotNum = random.nextDouble()*1000;
			int tagHotNum = 100;
//			SortBo sortBo = new SortBo(i, gameRelativeNum, downloadHotNum, tagHotNum);
			sortMap.put(i, (0.6*gameRelativeNum + 0.4*downloadHotNum/100 + tagHotNum));
//			sortBoList.add(sortBo);
		}
		
		List list = MapUtil.sortByValue(sortMap);
//		List<I> = new ArrayList<Map.Entry<Integer, Double>>(sortMap.entrySet());
//        Collections.sort(similarityList, new GameSimilarityComparator());
//		Collections.sor
		
		System.out.println(System.currentTimeMillis() - beginMillis);
	}
}
