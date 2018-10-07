package am.chronograph.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * Contains methods for working with Date data...
 * 
 * @author HARUT
 */
public final class DateUtil {

	/**
	 * Default constructor - PRIVATE!
	 */
	private DateUtil() {		
	}
	
	/**
	 * Get Date which corresponds to given LocalDateTime object...
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static Date getDateByLocalDateTime(LocalDateTime localDateTime) {
		return (localDateTime != null) ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
	}
	
	/**
	 * Get LocalDateTime object which corresponds to given Date...
	 * @param date
	 * @return
	 */
	public static LocalDateTime getLocalDateTimeByDate(Date date) {
		return (date != null) ? LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()) : null;
	}
	
	/**
     * Get LocalDateTime object which corresponds to given String by given Pattern...
     *
     * @param date {@link String} representation of date
     * @param pattern pattern of provided date
     * @return parsed {@link LocalDateTime}
     */
    public static LocalDateTime getLocalDateTimeByString(String date, String pattern) {
        if(StringUtils.isBlank(date)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                //
        return LocalDateTime.parse(date, formatter);
    }
    
    /**
     * Get Date object by given dateString...
     *  
     * @param dateString
     * @param pattern
     * @return
     */
    public static Date getDateByString(String dateString, String... patterns) {
        Date date = null;
        DateFormat dateFormat = null;        
        for(String pattern : patterns) {
            try {            
                dateFormat = new SimpleDateFormat(pattern);           
                date = dateFormat.parse(dateString);
                
                return date;                
            } catch(ParseException e) {                            
            }            
        }
        
        return date;
    }
	
	/**
	 * Get date String value by given pattern.
	 * @param localDateTime
	 * @param pattern
	 * @return
	 */
	public static String getDateStringByPattern(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
		String dateString = localDateTime.format(formatter);
    	
    	return dateString;
    }
	
	/**
     * Get date String value by given pattern.
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateStringByPattern(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        
        return dateFormat.format(date);            
    }
	
	/**
     * get day count between dates.
     * @param date1
     * @param date2
     * @return
     */
    public static int getDayCountBetewenDates(Date date1, Date date2) {
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date1);
		
		Calendar c2 = new GregorianCalendar();
		c2.setTime(date2);
		
		int numberBeetwenDates =  (int)( (c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		
		return numberBeetwenDates;		
    }    
    
    /**
     * Get date after or before given days number...
     * @param date
     * @param daysNumber
     * @param before
     * @return
     */
    public static Date getDateBeforeAfterDays(Date date, int daysNumber, boolean before) {
        LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime dateAfterOrBefore = before ? localDate.minusDays(daysNumber) : localDate.plusDays(daysNumber);
        
        return Date.from(dateAfterOrBefore.atZone(ZoneId.systemDefault()).toInstant());
    }
	
	/**
     * Get 'time ago' message for the given date compared with current date...
     * @param date
     * @return
     */
    public static String getTimeAgoFromNow(LocalDateTime localDateTime, String locale) {
    	Date date = getDateByLocalDateTime(localDateTime);
    	
    	return (date != null) ? getTimeAgoFromNow(date, locale) : null;
    }
	
	/**
     * Get 'time ago' message for the given date compared with current date...
     * @param date
     * @return
     */
    public static String getTimeAgoFromNow(Date date, String locale) {
    	if(date == null) {
    		return null;
    	}
    	
    	PrettyTime prettyTime = (locale == null) ? new PrettyTime() : new PrettyTime(new Locale(locale));
    	
    	return prettyTime.format(date);
    }
    
    /**
     * Change the time of given date to 00:00 (start time) or to 23:59(end time).
     * 
     * @param startTime
     * @return
     */
    public static Date setStartEndTimeForDate(Date date, boolean startTime) {
        if(date == null) {
            return null;
        }
        
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if(startTime) {         
            calendar.set(Calendar.AM_PM, Calendar.AM);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        } else {            
            calendar.set(Calendar.AM_PM, Calendar.PM);
            calendar.set(Calendar.HOUR, 11);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }
        
        return calendar.getTime();
    }
}
