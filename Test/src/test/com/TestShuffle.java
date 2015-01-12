package test.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestShuffle extends Thread{
	private static List<Integer> list = new ArrayList<Integer>();
	
	static{
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
	}
	
	@Override
	public void run() {
		while(true){
			list = new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			list.add(3);
			list.add(4);
			list.add(5);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Collections.shuffle(list);
			System.out.println(list);
		}
	}
	
	public static void main(String[] args) {
		TestShuffle testShuffle1 = new TestShuffle();
		TestShuffle testShuffle2 = new TestShuffle();
		testShuffle1.start();
		testShuffle2.start();

	}

}
