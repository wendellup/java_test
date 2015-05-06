package collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//p=0.6*游戏相关度+0.4*下载热度/100+运营干预参数
public class SortList extends Thread{
	private static Random random = new Random();
	
	@Override
	public void run() {
		while(true){
			test(null);
			try {
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void test(String[] args) {
		long beginMillis = System.currentTimeMillis();
//		List<SortBo> sortBoList = new ArrayList<SortBo>();
		List<Integer> sortBoList = new ArrayList<Integer>();
		
		for(int i=0; i<1000; i++){
			double gameRelativeNum = i;
			double downloadHotNum = i;
			int tagHotNum = 100;
//			SortBo sortBo = new SortBo(i, gameRelativeNum, downloadHotNum, tagHotNum);
			sortBoList.add(i);
		}
//		System.out.println(System.currentTimeMillis() - beginMillis);
		Collections.sort(sortBoList);
//		long cost = System.currentTimeMillis() - beginMillis;
		System.out.println(System.currentTimeMillis() - beginMillis);
//		System.out.println(sortBoList);
	}
	
	public static void main(String[] args) {
		for(int i=0; i<100; i++){
			Thread thread = new SortList();
			thread.start();
			System.out.println("第"+i+"个线程开始执行...");
		}
	}
	
}

