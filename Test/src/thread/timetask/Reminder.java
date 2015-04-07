package thread.timetask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder {
    Timer timer;

    public Reminder(int seconds) {
//        timer = new Timer();
//        timer.schedule(new RemindTask(), seconds*1000);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 28);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();

        timer = new Timer();
        timer.schedule(new RemindTask(), time, 30*1000);
//        timer.scheduleAtFixedRate(new RemindTask(), time, 30*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
        	System.out.println(new Date()+",Time's up!");
//            System.out.println("Time's up!");
//            timer.cancel(); //Terminate the timer thread
        }
    }

    public static void main(String args[]) {
        System.out.println("About to schedule task.");
        new Reminder(5);
        System.out.println("Task scheduled.");
        while(true){
        	try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	System.out.println("xxxxxxxxxxx");
        }
    }
}