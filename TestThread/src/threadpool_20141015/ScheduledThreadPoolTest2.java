package threadpool_20141015;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest2 {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledThreadPool = Executors
				.newScheduledThreadPool(5);
		for(int i=0; i<2; i++){
			scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
				public void run() {
					System.out
					.println("delay 1 seconds, and excute every 3 seconds");
				}
			}, 1, 3, TimeUnit.SECONDS);
		}
	}
}