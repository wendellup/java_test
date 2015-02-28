package test.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestDate {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date(1423557284000L)));
	}
	
	
	@Test
	public void getTimeStr() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = "2015-02-09 12:00:00";
		Date date = (Date) sdf.parseObject(dateStr);
		long dateMillis = date.getTime();
		System.out.println(dateMillis);
	}
}
