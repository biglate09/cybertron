package thaisamut.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

public class CssConvertUtils {
	private static final Locale LOCALE_TH = new Locale("TH", "th");
	private static final Pattern mobilePattern = Pattern.compile("^(01|06|08|09)\\d{8}$");
	private static final Pattern phonePattern = Pattern.compile("^0\\d{8}$");
	private static final Pattern emailPattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
	private static final Pattern strongPasswordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~]).{8,}$");
	private static final DatatypeFactory DATATYPE_FACTORY;
	static
    {
        try
        {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        }
        catch (DatatypeConfigurationException e)
        {
            throw new IllegalStateException("Unable to instantiate class DatatypeFactory");
        }
    }
	
	
	public static Date toDateFromStringDateThai(String dateThai) {
		try {
			SimpleDateFormat smThPicker = new SimpleDateFormat("dd/MM/yyyy", LOCALE_TH);
			return smThPicker.parse(dateThai);
		} catch (Exception ex) {
			return null;
		}

	}
	public static int getThaiYear(Date dateThai) {
		try {
			SimpleDateFormat smThPicker = new SimpleDateFormat("yyyy", LOCALE_TH);
			return Integer.parseInt(smThPicker.format(dateThai));
		} catch (Exception ex) {
			return 0;
		}

	}

	public static String toDateThaiString(Date compareDate) {
		try {
			SimpleDateFormat smThPicker = new SimpleDateFormat("dd/MM/yyyy", LOCALE_TH); 
			return smThPicker.format(compareDate);
		} catch (Exception ex) {
			return null;
		}
	}

	public static String toDateWithTimeThaiString(Date compareDate) {
		try {
			SimpleDateFormat smThPicker = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", LOCALE_TH); 
			return smThPicker.format(compareDate);
		} catch (Exception ex) {
			return null;
		}
	}

	public static String toStringMMYY(Date nextPaid) {
		try {
			SimpleDateFormat smThPicker2 = new SimpleDateFormat("MM/yy", LOCALE_TH);
			return smThPicker2.format(nextPaid);
		} catch (Exception ex) {
			return StringUtils.EMPTY;
		}
	}

	public static Date addDate(Date date, int add) {
		try {
			Calendar cal = Calendar.getInstance(LOCALE_TH);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, add);
			return cal.getTime();
		} catch (Exception ex) {

		}
		return null;
	}

	public static boolean isBetween(Date compareDate, Date lowerDate, Date upperDate) {
		try {
			return lowerDate.compareTo(compareDate) * compareDate.compareTo(upperDate) > 0;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean isAfter(Date target, Date date) {
		try {
			return date.compareTo(target) >=0;
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean isValideMobileNumber(String mobile) {
		try {
			Matcher m = mobilePattern.matcher(mobile);
			return m.find();
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean isValidePhoneNumber(String phone) {
		try {
			Matcher m = phonePattern.matcher(phone);
			return m.find();
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean isValideEmail(String email) {
		try {
			Matcher m = emailPattern.matcher(email);
			return m.find();
		} catch (Exception ex) {
		}
		return false;
	}

	public static boolean isStrongPassword(String password) {
		try {
			Matcher m = strongPasswordPattern.matcher(password);
			return m.find();
		} catch (Exception ex) {
		}
		return false;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendarfromDate(Date date) {
		try{
		 	GregorianCalendar c = new GregorianCalendar(Locale.US);

	        c.setTimeInMillis(date.getTime());

	        return DATATYPE_FACTORY.newXMLGregorianCalendar(c);
		}catch(Exception ex){
			return null;
		}
	}
	public static boolean isSameDay(Date d1, Date d2){
		Calendar cal1 = Calendar.getInstance(LOCALE_TH);
		Calendar cal2 = Calendar.getInstance(LOCALE_TH);
		cal1.setTime(d1);
		cal2.setTime(d2);
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	}
	
	public static boolean isSameHour(Date d1, Date d2){
		Calendar cal1 = Calendar.getInstance(LOCALE_TH);
		Calendar cal2 = Calendar.getInstance(LOCALE_TH);
		cal1.setTime(d1);
		cal2.setTime(d2);
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)&&
				                  cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY);
	}
	public static long diffHour(Date date1, Date date2){
		DateTime d1 = new DateTime(date1.getTime());
		DateTime d2 = new DateTime(date2.getTime());
		Hours hours = Hours.hoursBetween(d1, d2);
		return hours.getHours();
	}
	public static int diffMinute(Date date1, Date date2){
		DateTime d1 = new DateTime(date1.getTime());
		DateTime d2 = new DateTime(date2.getTime());
		Minutes minutes = Minutes.minutesBetween(d1, d2);
		return minutes.getMinutes();
	}
}
