package book.深入理解Java虚拟机_第二版.chapter4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test4_3_1_2 {
	public static void createBusyThread(){
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true);
			}
		}, "testBusyThread");
		thread.start();
	}
	
	public static void createLockThread(final Object lock){
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "testLockThread");
		thread.start();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		createBusyThread();
		br.readLine();
		Object obj = new Object();
		createLockThread(obj);
		System.out.println("end.....");
	}
}
