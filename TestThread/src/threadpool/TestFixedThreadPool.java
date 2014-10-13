package threadpool;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFixedThreadPool {
	public static transient int num = 0;
	
	public static void  main(String[] args) throws InterruptedException {
		
		long startMillis = System.currentTimeMillis();
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new FillingThread(1);
        Thread t2 = new FillingThread(2);
        Thread t3 = new FillingThread(3);
        Thread t4 = new FillingThread(4);
        Thread t5 = new FillingThread(5);
        
        Callable c1 = new FillingCall(1);
        Callable c2 = new FillingCall(2);
        Callable c3 = new FillingCall(3);
        Callable c4 = new FillingCall(4);
        Callable c5 = new FillingCall(5);
//        //将线程放入池中进行执行
//        pool.execute(t1);
//        pool.execute(t2);
//        pool.execute(t3);
//        pool.execute(t4);
//        pool.execute(t5);
        
        ArrayList<Callable<FillingCall>> callers = new ArrayList<Callable<FillingCall>>();  
        callers.add(c1);
        callers.add(c2);
        callers.add(c3);
        callers.add(c4);
        callers.add(c5);
        
        pool.invokeAll(callers);
        
        long endMillis = System.currentTimeMillis();
        System.out.println("cosumes:-->"+(endMillis-startMillis));
        
        //关闭线程池
        pool.shutdown();
    }
	
	public static class FillingThread extends Thread{
		private int id;
		
		public FillingThread(int id){
			this.id = id;
		}
		
		@Override
		public void run() {
//			synchronized (FillingThread.class) {
				while(num<=1000){
					try {
						sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("id--->"+id+"<----"+(num++));
				}
//			}
			
		}
	}
	
	public static class FillingCall implements Callable<FillingCall>{
		private int id;
		
		public FillingCall(int id){
			this.id = id;
		}
		
		public FillingCall() {
		}

		@Override
		public FillingCall call() throws Exception {
//			synchronized (FillingThread.class) {
				while(num<=1000){
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("id--->"+id+"<----"+(num++));
				}
//			}
			return null;
		}
		
	}
}
