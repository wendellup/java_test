package test.open.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.common.util.Utils;

public class TestUtils {
	private static Logger logger = Logger.getLogger(TestUtils.class);
	
	private Integer[] intAry = new Integer[]{1,2,3,4,5};
	private Integer[] intAry2 = new Integer[]{8,2,5,3,7,6,1};
	
	
	@Test
	public void collectionsRetainTest(){
		List<Integer> list1 = Arrays.asList(intAry);
		List<Integer> list2 = Arrays.asList(intAry2);
		
		System.out.println(Utils.collectionsRetain(list1, list2));
	}
	
	@Test
	public void collectionsSortTest(){
		List<Integer> list1 = Arrays.asList(intAry);
		List<Integer> list2 = Arrays.asList(intAry2);
		List<Integer> removeList = Utils.collectionRemove(list2, list1);
		list2 = Utils.collectionsRetain(list1, list2);
		list2.addAll(removeList);
		System.out.println(list2);
	}
	
	@Test
	public void collectionRemoveTest(){
		List<Integer> list1 = Arrays.asList(intAry);
		List<Integer> list2 = Arrays.asList(intAry2);
		
		System.out.println(Utils.collectionRemove(list2, list1));
	}
}
