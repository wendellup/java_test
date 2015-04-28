package test.open;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	
    public static void main(String[] args) throws UnsupportedEncodingException {
    	Utils.initLog4j();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(GregorianCalendar.DAY_OF_YEAR, -182);
        long timeMillis = calendar.getTimeInMillis();
        System.out.println(timeMillis);
        
        System.out.println(URLEncoder.encode("我勒个擦擦擦", "utf-8"));
        System.out.println(URLEncoder.encode("2907,2908,2909", "utf-8"));
        System.out.println(URLEncoder.encode("15366189928", "utf-8"));
        
        logger.info("xxxxxxxxxx");
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        System.out.println(Utils.toDateString(1380358500000L, dateFormat));
        System.out.println(Utils.toDateString(1358621768000L, dateFormat));
        
        
    }
}
