package thread.imooc.class2_4;

//军队线程
//模拟作战双方的行为
public class ArmyRunnable implements Runnable {
	//volatile能确保线程能读取其他线程的写入的值
	//可见性 ref JMM 原则
	public volatile boolean keepRunning = true;
	
	@Override
	public void run() {
		while(keepRunning){
			//发动5连击
			for(int i=0; i<5; i++){
				System.out.println(Thread.currentThread().getName()
						+ "进攻对方["+i+"]次");
				//让出了处理器时间, 下次该谁进攻还不一定呢!
				Thread.yield();
			}
		}
	}

}
