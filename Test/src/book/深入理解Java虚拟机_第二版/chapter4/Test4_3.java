package book.深入理解Java虚拟机_第二版.chapter4;

import java.util.ArrayList;
import java.util.List;

public class Test4_3 {
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024];
	}
	
	public static void fillHeap(int num) throws InterruptedException{
		List<OOMObject> list = new ArrayList<OOMObject>();
		for(int i=0; i<num; i++){
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
	}
	
	public static void main(String[] args) {
		try {
			fillHeap(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
