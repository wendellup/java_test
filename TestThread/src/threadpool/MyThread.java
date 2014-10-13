package threadpool;

public class MyThread extends Thread {
	private int id;
	
	public MyThread(int id){
		this.id = id;
	}
	
	public MyThread(){
	}
	
    @Override
    public void run() {
        System.out.println("id--->"+id+"<---"+Thread.currentThread().getName() + "正在执行。。。");
    }
}
