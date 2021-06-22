package newfeatures.java8.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateTimeTest {
	public static void oldFormat() throws ParseException {
		Date now = new Date();
		// format yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(now);
		System.out.println(String.format("date format : %s", date));

		// format HH:mm:ss
		SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
		String time = sdft.format(now);
		System.out.println(String.format("time format : %s", time));

		// format yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdfdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = sdfdt.format(now);
		System.out.println(String.format("dateTime format : %s", datetime));

		// 已弃用
//		Date date2 = new Date("2021-01-26");
		// 替换为
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date3 = sdf2.parse("2021-01-26");
	}

	public static void newFormat() {
		// format yyyy-MM-dd
		LocalDate date = LocalDate.now();
		System.out.println(String.format("date format : %s", date));

		// format HH:mm:ss
		LocalTime time = LocalTime.now().withNano(0);
		System.out.println(String.format("time format : %s", time));

		// format yyyy-MM-dd HH:mm:ss
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateTimeStr = dateTime.format(dateTimeFormatter);
		System.out.println(String.format("dateTime format : %s", dateTimeStr));

		LocalDate date2 = LocalDate.of(2021, 1, 26);
		LocalDate.parse("2021-01-26");

		LocalDateTime dateTime2 = LocalDateTime.of(2021, 1, 26, 12, 12, 22);
		System.out.println("111 :" + dateTime2);
//	    LocalDateTime.parse("2021-01-26 12:12:22",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime.parse("2021-01-26T12:12:22");

		LocalTime time2 = LocalTime.of(12, 12, 22);
	}

	public void afterDay() throws ParseException {
		// 一周后的日期
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, 7);
		Date d = ca.getTime();
		String after = formatDate.format(d);
		System.out.println("一周后日期：" + after);

		// 算两个日期间隔多少天，计算间隔多少年，多少月方法类似
		String dates1 = "2021-12-23";
		String dates2 = "2021-02-26";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse(dates1);
		Date date2 = format.parse(dates2);
		int day = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
		System.out.println(dates2 + "和" + dates2 + "相差" + day + "天");
		// 结果：2021-12-23和2021-12-23相差300天
	}

	public static void pushWeek() {
		// 一周后的日期
		LocalDate localDate = LocalDate.now();
		// 方法1
		LocalDate after = localDate.plus(1, ChronoUnit.WEEKS);
		// 方法2
		LocalDate after2 = localDate.plusWeeks(1);
		System.out.println("一周后日期：" + after);

		// 算两个日期间隔多少天，计算间隔多少年，多少月
		LocalDate date1 = LocalDate.parse("2021-02-26");
		LocalDate date2 = LocalDate.parse("2021-12-23");
		Period period = Period.between(date1, date2);
		System.out.println(
				"date1 到 date2 相隔：" + period.getYears() + "年" + period.getMonths() + "月" + period.getDays() + "天");
		// 打印结果是 “date1 到 date2 相隔：0年9月27天”
		// 这里period.getDays()得到的天是抛去年月以外的天数，并不是总天数
		// 如果要获取纯粹的总天数应该用下面的方法
		long day = date2.toEpochDay() - date1.toEpochDay();
		System.out.println(date2.toEpochDay());
		System.out.println(date2 + "和" + date2 + "相差" + day + "天");
		// 打印结果：2021-12-23和2021-12-23相差300天
	}

	public static void getDay() {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		String first = format.format(c.getTime());
		System.out.println("first day:" + first);

		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("last day:" + last);

		// 当年最后一天
		Calendar currCal = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, currCal.get(Calendar.YEAR));
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date time = calendar.getTime();
		System.out.println("last day:" + format.format(time));
	}

	public static void getDayNew() {
		LocalDate today = LocalDate.now();
		// 获取当前月第一天：
		LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		// 取本月最后一天
		LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
		// 取下一天：
		LocalDate nextDay = lastDayOfThisMonth.plusDays(1);
		// 当年最后一天
		LocalDate lastday = today.with(TemporalAdjusters.lastDayOfYear());
		// 2021年最后一个周日，如果用Calendar是不得烦死。
		LocalDate lastMondayOf2021 = LocalDate.parse("2021-12-31")
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
	}
	
	public static void getZonedTime() {
		//当前时区时间
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		System.out.println("当前时区时间: " + zonedDateTime);

		//东京时间
		ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("JST"));
		ZonedDateTime tokyoTime = zonedDateTime.withZoneSameInstant(zoneId);
		System.out.println("东京时间: " + tokyoTime);

		// ZonedDateTime 转 LocalDateTime
		LocalDateTime localDateTime = tokyoTime.toLocalDateTime();
		System.out.println("东京时间转当地时间: " + localDateTime);

		//LocalDateTime 转 ZonedDateTime
		ZonedDateTime localZoned = localDateTime.atZone(ZoneId.systemDefault());
		System.out.println("本地时区时间: " + localZoned);
	}

	public static void main(String[] args) throws ParseException {
		oldFormat();
		System.out.println("----------------");
		newFormat();
		System.out.println("----------------");
		pushWeek();
		System.out.println("----------------");
		getDay();
		System.out.println("----------------");
		getDayNew();
		System.out.println("----------------");
		getZonedTime();
	}
}
