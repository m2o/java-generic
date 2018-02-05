package hr.tennis.bot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	public static String formatDateSimple(Date date){
		if(date!=null){
			return formatDate(date,SIMPLE_DATE_FORMAT);
		}else{
			return null;
		}
	}

	public static String formatDate(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDate(String dateStr,String format) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		return df.parse(dateStr);
	}

	/**
	 * @param date
	 * @param field Calendar.field
	 * @param amount amount to add/subtract to/from date
	 * @return
	 */
	public static Date add(Date date,int field,int amount){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(field,amount);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @return start of day
	 */
	public static Date floor(Date date){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
	/**
	 * @param date
	 * @return start of year
	 */
	public static Date floorToYear(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR,1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}

	public static Date today(){
		return floor(new Date());
	}

	public static Date yesterday(){
		return add(today(),Calendar.DAY_OF_MONTH,-1);
	}

	/**
	 * @param year
	 * @param month - normal 1-12 based months
	 * @param day
	 * @return
	 */
	public static Date createDate(int year,int month,int day){
		return createDate(year,month,day,0,0);
	}

	/**
	 * @param year
	 * @param month  - normal 1-12 based months
	 * @param day
	 * @param hours
	 * @param minutes
	 * @return
	 */
	public static Date createDate(int year,int month,int day, int hours, int minutes){
		return new GregorianCalendar(year, month-1, day, hours, minutes).getTime();
	}

	/**
	 * @param year
	 * @return
	 */
	public static Date createDate(int year){
		return new GregorianCalendar(year, GregorianCalendar.JANUARY,1).getTime();
	}

	/**
	 * @param inputFormat
	 * @param outputFormat
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static String convertDate(String dateStr,String inputFormat, String outputFormat) throws ParseException{
		return formatDate(parseDate(dateStr,inputFormat),outputFormat);
	}

	/**
	 * Use formatDate(today(),format) instead.
	 * @param dateFormat
	 * @return
	 */
	@Deprecated
	public static String getCurrentDate(String dateFormat){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime()).toString();
	}
	
	/**
	 * @param date
	 * @param partOfDate Calendar.YEAR, Calendar.MONTH, Calendar.DATE
	 * @return
	 */
	public static Integer getDatePart(Date date, int partOfDate){
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		if(partOfDate == Calendar.MONTH)
			return cal.get(partOfDate) + 1;	
		else 	
			return cal.get(partOfDate);
		
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(parseDate("2011-08-22","yyyy-MM-dd"));
		System.out.println(createDate(2011,8,2,22,34).toString());
		System.out.println(today());
		System.out.println(formatDate(today(),"yyyy-MM-dd"));
		System.out.println(yesterday());
		System.out.println(DateUtil.convertDate("10. 03. 2011","dd. MM. yyyy", "yyyy-MM-dd"));
		System.out.println(DateUtil.formatDate(today(), "'year='yyyy'&month='MM'&day='dd"));
		System.out.println(DateUtil.getDatePart(today(), Calendar.YEAR));
	}
}
