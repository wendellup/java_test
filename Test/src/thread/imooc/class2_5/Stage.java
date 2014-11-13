package thread.imooc.class2_5;

import thread.imooc.class2_4.ArmyRunnable;

/**
 * 隋唐演义大戏舞台
 * @author thinkpad
 *
 */
public class Stage extends Thread {
	@Override
	public void run() {
		System.out.println("欢迎收看舞台大剧...");
		try {
			sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		ArmyRunnable armyTaskOfSuiDynasty = 
				new ArmyRunnable();
		
		ArmyRunnable armyTaskOfRevolt = 
				new ArmyRunnable();
		
		Thread armyOfSuiDynasty = 
				new Thread(armyTaskOfSuiDynasty, "隋军");
		Thread armyOfRevolt = 
				new Thread(armyTaskOfRevolt, "农民起义军");
		
		armyOfRevolt.start();
		armyOfSuiDynasty.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		armyTaskOfRevolt.keepRunning = false;
		armyTaskOfSuiDynasty.keepRunning = false;
		
		try {
			armyOfRevolt.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("程咬金出现");
		Thread mrCheng = new KeyPersonThread();
		mrCheng.setName("程咬金");
		mrCheng.start();
		try {
			mrCheng.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("战争结束");
		
		
//		try {
//			armyOfRevolt.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		new Stage().run();
	}
	
}
