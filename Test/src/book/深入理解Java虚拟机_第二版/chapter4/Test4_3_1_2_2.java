package book.深入理解Java虚拟机_第二版.chapter4;

import thread.lock.Test;

public class Test4_3_1_2_2 {
	static class SynAddRunnable implements Runnable{
		int a, b;
		public SynAddRunnable(int a, int b){
			this.a = a;
			this.b = b;
		}
		@Override
		public void run() {
			synchronized (Integer.valueOf(a)) {
				synchronized (Integer.valueOf(b)) {
					System.out.println(a + b);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("begin");
		for(int i = 0; i<100; i++){
			new Thread(new SynAddRunnable(1, 2)).start();
			new Thread(new SynAddRunnable(2, 1)).start();
		}
	}
}
