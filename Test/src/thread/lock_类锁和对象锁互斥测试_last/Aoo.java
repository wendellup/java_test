package thread.lock_类锁和对象锁互斥测试_last;


public class Aoo {
	private static byte[] lock = new byte[0];
	
	public void privateNoLockMethod(){
			int i=0;
			while(i++<5){
				try {
					Thread.currentThread().sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getId()+"这是一个无锁方法");
			}
	}
	
	public void privateLockMethod(){
		synchronized (lock) {
			int i=0;
			while(i++<5){
				try {
					Thread.currentThread().sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getId()+"这是一个私有锁方法");
			}
		}
	}
	
	public  synchronized void method(){
		int i=0;
		while(i++<5){
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getId()+"这是一个对象锁方法");
		}
	}
	
	public  synchronized void method2(){
		int i=0;
		while(i++<5){
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getId()+"这是一个对象锁方法2");
		}
	}
	
	public void classSynMethod(){
		synchronized (Aoo.class) {
			int i=0;
			while(i++<5){
				try {
					Thread.currentThread().sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getId()+"这是一个类锁方法");
			}
		}
	}
	
	public static void main(String[] args) {
		
//		final Aoo aoo = new Aoo();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Aoo aoo = new Aoo();
//				aoo.classSynMethod();
				aoo.method();
//				aoo.method2();
//				aoo.privateLockMethod();
//				aoo.privateNoLockMethod();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Aoo aoo2 = new Aoo();
//				aoo2.classSynMethod();
//				aoo2.privateLockMethod();
				aoo2.method();
//				aoo.method2();
//				aoo.privateLockMethod();
//				aoo.privateNoLockMethod();
			}
		});
		
		t1.start();
		t2.start();
	}
}
