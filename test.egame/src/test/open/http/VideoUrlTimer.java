package test.open.http;

import java.util.Date;
import java.util.Timer;

public class VideoUrlTimer {
	public static void main(String[] args) {
		Timer timer = null;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TestVideoUrlTask(), new Date(), 60*1000*30);
	}
}	
