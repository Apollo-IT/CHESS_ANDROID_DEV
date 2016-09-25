package com.app.hrms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

public class DateUtils {

	public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
	
	public static long getcurrentTimeStamp() {
		return System.currentTimeMillis();
	}
	
	public static Calendar convertDateToTimestamp(String dateString) {
		long timestamp = -1;
		if(dateString != null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
			Date date;
			try {
				date = format.parse(dateString);
				timestamp = date.getTime();
			} catch (ParseException e) {
				timestamp = getcurrentTimeStamp();
			}	
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		return calendar;
	}

    public static long getChildrenBirthdayTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - 13;

        calendar.set(year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return calendar.getTime().getTime();
    }
    
    public static String convertDateToString(Date date) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);

		Date today = new Date();
		String str1 = format.format(today);
		String str2 = format.format(date);
		if(str1.equals(str2)) {
			format = new SimpleDateFormat("HH:mm");
		} else {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}

    	String datetime = format.format(date);
		return datetime;	
    }

	public static String getTimeStamp(String dateString) {
		long timestamp = -1;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.JAPAN);
		Date date;
		try {
			date = format.parse(dateString);
			timestamp = date.getTime();
		} catch (ParseException e) {
			timestamp = getcurrentTimeStamp();
		}
		
		return Long.toString(timestamp);
	}
	
	public static Date getDate(String dateString, String dateFromat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFromat, Locale.JAPAN);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	public static Date getDate(String dateString) { 
		return getDate(dateString, DEFAULT_DATE_FORMAT);
	}
	
	public static int getPremiumRemainDays(String expire) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
		try {
			Date date = format.parse(expire);
			long nMiliseconds = date.getTime() - (new Date()).getTime();
			if(nMiliseconds > 0) {
			 return (int)(nMiliseconds / (3600 * 1000 * 24));
			} else {
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return 0;
	}
	
	public static String convertTimestampToDate(long timestamp) {
		Date date = new Date(timestamp);
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.JAPAN);
		return format.format(date);
	}
	
	public static String convertTimestampToDate(long timestamp, String format_string) {
		Date date = new Date(timestamp);
		SimpleDateFormat format = new SimpleDateFormat(format_string, Locale.JAPAN);
		return format.format(date);
	}
	
	public static String getFormattedDate(int currentYear,int currentMonth,int currentDay){
		/*Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, currentYear);
		calendar.set(Calendar.MONTH, currentMonth);
		calendar.set(Calendar.DAY_OF_MONTH, currentDay);
		return convertTimestampToDate(calendar.getTimeInMillis());*/
		
		return currentYear + "年 " + currentMonth + "月" + currentDay + "日";
	}
	
	public static String getFormattedDate(int currentYear,int currentMonth,int currentDay, String strFormat){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, currentYear);
		calendar.set(Calendar.MONTH, currentMonth);
		calendar.set(Calendar.DAY_OF_MONTH, currentDay);
		
		Date date = new Date(calendar.getTimeInMillis());
		SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.JAPAN);
		return format.format(date);
	}
	
	public static String getCurrentDate(){
		return convertTimestampToDate(getcurrentTimeStamp());
	}
	
	public static String changeDateFormatToTeamKnectDateFormat(String date) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy",Locale.US).format(new SimpleDateFormat("yyyy-MM-dd",Locale.US).parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String changeDataFormat(String oldFormat, String newFormat, String date) {
		try {
			return new SimpleDateFormat(newFormat, Locale.US).format(new SimpleDateFormat(oldFormat, Locale.US).parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "";		
	}
	
	@SuppressWarnings("unused")
	public static String formatToYesterdayOrToday(long timestamp, Context context) {
		
		try {
			Calendar calendar = Calendar.getInstance(Locale.JAPAN);
			calendar.setTimeInMillis(timestamp);
			Calendar today = Calendar.getInstance();
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DATE, -1);
			Calendar weekagoday = Calendar.getInstance();
			weekagoday.add(Calendar.DATE, -7);
			
			DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
			
			if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
				return /*"Today: " + */timeFormatter.format(calendar.getTime());
			} else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
				return /*"Yesterday: " + */timeFormatter.format(calendar.getTime());
			} else if (calendar.get(Calendar.YEAR) == weekagoday.get(Calendar.YEAR) && calendar.after(weekagoday)) {
				String day = android.text.format.DateUtils.formatDateTime(context, timestamp,  android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY);
				DateFormat format = new SimpleDateFormat("HH:mm");
				return /*day + ": " + */format.format(calendar.getTime());
			} else {
				String date = android.text.format.DateUtils.formatDateTime(context, timestamp,  android.text.format.DateUtils.FORMAT_SHOW_DATE);
				DateFormat format = new SimpleDateFormat("HH:mm");
				return /*date + ": " + */format.format(calendar.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
