package test.com;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.egame.common.util.Utils;
import test.com.entity.AOO;




public class TestRemoveFromList {
//	private List<AOO> aooList = new ArrayList<AOO>();
//	private List<Integer> idList = new ArrayList<Integer>();
	private List<AOO> aooList = new LinkedList<AOO>();
	private List<Integer> idList = new LinkedList<Integer>();
	
	private void initList(){
		for(int i=0; i<10000; i++){
			AOO aoo = new AOO(10, "a1", "desc1");
			AOO aoo2 = new AOO(20, "a2", "desc1");
			AOO aoo3 = new AOO(30, "a3", "desc1");
			aooList.add(aoo);
			aooList.add(aoo2);
			aooList.add(aoo3);
		}
		
		for(int j=0; j<20; j++){
			idList.add(j);
		}
	}
	
	public List<AOO> removeByNewList(){
		List<AOO> retList = new LinkedList<AOO>();
		for(int i=0; i<aooList.size(); i++){
			if(idList.contains(aooList.get(i).getId())){
				retList.add(aooList.get(i));
			}
		}
		return retList;
	}
	
	public List<AOO> removeByIt(){
		Iterator<AOO> it = aooList.iterator();
		while(it.hasNext()){
			AOO aoo = it.next();
			if(!idList.contains(aoo.getId())){
				it.remove();
			}
		}
		return aooList;
	}
	
	public static void main(String[] args) {
//		TestRemoveFromList test = new TestRemoveFromList();
//		test.initList();
//		long beginMillis = System.currentTimeMillis();
////		System.out.println(test.removeByIt().size());;
//		System.out.println(test.removeByNewList().size());;
//		long endMillis = System.currentTimeMillis();
//		System.out.println(endMillis-beginMillis);
		
		long beginMillis = System.currentTimeMillis();
		
		List<AOO> aooList = new ArrayList<AOO>();
		for(int i=0; i<100000; i++){
			AOO aoo = new AOO(10, "a1", "desc1");
			AOO aoo2 = new AOO(20, "a2", "desc1");
			AOO aoo3 = new AOO(30, "a3", "desc1");
			aooList.add(aoo);
			aooList.add(aoo2);
			aooList.add(aoo3);
		}
		
		long endMillis = System.currentTimeMillis();
		System.out.println(endMillis-beginMillis);
	}
}
