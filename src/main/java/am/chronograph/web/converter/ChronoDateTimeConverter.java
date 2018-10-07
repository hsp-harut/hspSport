package am.chronograph.web.converter;

import java.util.TimeZone;

import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

import am.chronograph.web.util.Constants;

/**
 * We need this because by default JSF using GMT time-zone to convert the date into display format,
 * and that's why in outputTexts date decreased 1 day...
 * 
 * @author HARUT
 */
@FacesConverter("chronoDateTimeConverter")
public class ChronoDateTimeConverter extends DateTimeConverter {

	/**
	 * Default constructor...
	 */
	public ChronoDateTimeConverter() {
		setTimeZone(TimeZone.getDefault());
		setPattern(Constants.DEFAULT_DATE_TIME_PATTERN);
	}
}
