package test.open;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	
    public static void main(String[] args) throws Exception {
//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.add(GregorianCalendar.DAY_OF_YEAR, -182);
//        long timeMillis = calendar.getTimeInMillis();
//        System.out.println(timeMillis);
//        
//        System.out.println(URLEncoder.encode("我勒个擦擦擦", "utf-8"));
//        System.out.println(URLEncoder.encode("2907,2908,2909", "utf-8"));
//        System.out.println(URLEncoder.encode("15366189928", "utf-8"));
//        
//        logger.info("xxxxxxxxxx");
//        String dateFormat = "yyyy-MM-dd HH:mm:ss";
//        System.out.println(Utils.toDateString(1380358500000L, dateFormat));
//        System.out.println(Utils.toDateString(1358621768000L, dateFormat));
//        System.out.println(System.currentTimeMillis());
//    	new Test().new ThreadA().start();
//    	new Test().new ThreadB().start();
        
    	String id = "dc912141h1aa6ef5";
    	System.out.println(Utils.getFileId(id, -1));
    }
    
    class ThreadA extends Thread{
    	@Override
    	public void run() {
    		super.run();
    		while(true){
    			try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			System.out.println("in ThreadA");
    		}
    	}
    }
    
    class ThreadB extends Thread{
    	@Override
    	public void run() {
    		super.run();
    		while(true){
    			try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			System.out.println("in ThreadB");
    		}
    	}
    }
}
