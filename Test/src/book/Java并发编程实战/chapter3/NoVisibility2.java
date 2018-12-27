package book.Java并发编程实战.chapter3;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NoVisibility2 extends Thread{
	
	private boolean ready;
	private int number;
	
	private class ReaderThread extends Thread{
		private NoVisibility2 noVisibility2;
		public ReaderThread(NoVisibility2 noVisibility2){
			this.noVisibility2 = noVisibility2;
		}
		
		public void run(){
			while(!noVisibility2.ready){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread.yield()"+Thread.activeCount());
				Thread.yield();
			}
			
			System.out.println(noVisibility2.number+", "+System.currentTimeMillis());
//			PrintWriter pw = null;
//			try {
//				pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
//						"NoVisibility2.txt", true)),true);
//					pw.println(noVisibility2.number);
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				pw.flush();
//				pw.close();
//			}
		}
	}
	
	@Override
	public void run() {
		NoVisibility2 noVisibility2 = new NoVisibility2();
		new ReaderThread(noVisibility2).start();
		noVisibility2.number = 42;
		noVisibility2.ready = true;
		
	}
//	public static void main(String[] args) {
//		new ReaderThread().start();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		number = 42;
//		ready = true;
//	}
	
	//线程池相关配置
	static int BLOCK_SIZE = 5000;
	static int CORE_POOL_SIZE = 5;
	static int MAXIMUM_POOL_SIZE = 5;
	static long KEEP_ALIVE_TIME = 500;

static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(BLOCK_SIZE);
private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE,
        KEEP_ALIVE_TIME, TimeUnit.SECONDS, queue,
        new ThreadPoolExecutor.CallerRunsPolicy());
	
	public static void main(String[] args) {
		
		
			
		for(int i=0; i<100000; i++){
			
			threadPool.execute(new NoVisibility2());
		}
	}
}
