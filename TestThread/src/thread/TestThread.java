package thread;

public class TestThread {

	public static void main(String[] args) {
//		new Thread(MyThread.getInstance(1)).start();
		new Thread(MyThread.getInstance(2)).start();
	}

}


class MyThread implements Runnable{
	private static byte[] lock = new byte[1];
	private int num;
	private static MyThread myThread;
	
	private MyThread(int num){
		this.num = num;
	}
	
	public static MyThread getInstance(int num){
		if(myThread==null){
			synchronized (lock) {
				myThread = new MyThread(num);
			}
		}
		return myThread;
	}
	
//	public MyThread(int num){
//		this.num = num;
//	}
	
	public void funA(){
		synchronized (this) {
			while(true){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(num);
			}
		}
	}
	
	@Override
	public void run() {
		funA();
	}
}