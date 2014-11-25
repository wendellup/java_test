package thread.imooc.class2_4;

/**
 * 隋唐演义大戏舞台
 * @author thinkpad
 *
 */
public class Stage extends Thread {
	@Override
	public void run() {
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
