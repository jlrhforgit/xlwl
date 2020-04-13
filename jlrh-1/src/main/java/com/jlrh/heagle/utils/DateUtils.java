package com.jlrh.heagle.utils;
import java.text.SimpleDateFormat;
import java.time.LocalDate ;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class DateUtils {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String CURRENT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 当前时间，14位的 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getCurrentTimeStr() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(date);
	}

	/**
	 * 当前日期，yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDefaultDate() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return formatter.format(date);
	}

	/**
	 * 返回当前时间字符串，HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(CURRENT_TIME_FORMAT);
		return formatter.format(date);
	}
	
	
	
	
	/**
	 * s 计算两个时间点之间的天数
	 * 说明：  LocalDate 是jdk1.8之后版本才有的，
	 * 
	 */
	public static long getBetweenDay(LocalDate start, LocalDate end) {
		return end.toEpochDay() - start.toEpochDay() ;
	}
	
	
	public static void main(String [] args) {
		LocalDate start = LocalDate.of(2018, 02, 02) ;
		LocalDate now = LocalDate.now() ;
		System.out.println("两个时间之间的天数是：" + getBetweenDay(start, now) + " 天。") ;
	}
}
