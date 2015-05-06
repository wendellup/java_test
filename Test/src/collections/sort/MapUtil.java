package collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.easymock.internal.matchers.CompareTo;

import cn.egame.common.util.Utils;

public class MapUtil {
	public static List sortByValue(Map<Integer, Double> map) {
		Map<Integer, Double> treeMap = new TreeMap<Integer, Double>(map);
		System.out
				.println("Dispaly words and their count in ascending order of the word");
//		System.out.println(treeMap);

		List arrayList = new ArrayList(map.entrySet());
		Collections.sort(arrayList, new Comparator() {
			public int compare(Object o1, Object o2) {
				Map.Entry obj1 = (Map.Entry) o1;
				Map.Entry obj2 = (Map.Entry) o2;
				return ((Double)obj1.getValue()).compareTo((Double)obj2.getValue());
			}
		});

//		System.out.println(arrayList);
		
		return arrayList;

	}
}
