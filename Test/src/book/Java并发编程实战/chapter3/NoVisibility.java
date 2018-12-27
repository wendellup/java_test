package book.Java并发编程实战.chapter3;

public class NoVisibility {
	private static boolean ready;
	private static int number;
	
	private static class ReaderThread extends Thread{
		public void run(){
			while(!ready){
				System.out.println("Thread.yield()"+Thread.activeCount());
				Thread.yield();
			}
			System.out.println(number);
		}
	}
	
	public static void main(String[] args) {
		new ReaderThread().start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		number = 42;
		ready = true;
	}
}
