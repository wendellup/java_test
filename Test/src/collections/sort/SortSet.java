package collections.sort;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

//p=0.6*游戏相关度+0.4*下载热度/100+运营干预参数
public class SortSet extends Thread {
	static Random random = new Random();
	
	private static int zeroNum = 0;
	@Override
	public void run() {
		while(true){
			test(null);
//			try {
//				sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	public static void test(String[] args) {
//		Set<SortBo> sortBoSet = new TreeSet<SortBo>();
		Set<Integer> sortBoSet = new TreeSet<Integer>();
		long beginMillis = System.currentTimeMillis();
		for(int i=0; i<1000; i++){
			double gameRelativeNum =i;
			double downloadHotNum = i;
			int tagHotNum = 100;
//			SortBo sortBo = new SortBo(i, gameRelativeNum, downloadHotNum, tagHotNum);
			sortBoSet.add(i);
		}
//		Collections.sort(sortBoList, new SortBoComparator());
		long cost = System.currentTimeMillis() - beginMillis;
		if(cost==0){
			zeroNum++;
		}
//		System.out.println("-------------"+cost);
//		System.out.println(sortBoSet);
	}
	
	public static void main(String[] args) {
		for(int i=0; i<100; i++){
			Thread thread = new SortSet();
			thread.start();
			System.out.println("第"+i+"个线程开始执行...");
		}
		System.out.println("zeroNum:"+zeroNum);
	}
	
}

