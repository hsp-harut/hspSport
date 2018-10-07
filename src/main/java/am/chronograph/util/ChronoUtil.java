package am.chronograph.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import am.chronograph.web.util.Constants;
import am.chronograph.web.util.DateUtil;


/**
 * Contains general methods...
 * @author HARUT
 */
public final class ChronoUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChronoUtil.class);
	
	/**
	 * Checks whether given bank account has a valid length or not...
	 * @param bankAccount
	 * @return
	 */
	public static int getValidBankAccountLength(String bankAccount) {
	    
	    /*Har jan 151ov ev 193 ov sksvox  hashvehamarner@ tox arajin 14 tiv@ vercni nuynacnelu hamar*/
	    return (bankAccount.startsWith("115") || bankAccount.startsWith("151") || bankAccount.startsWith("193")) ? Constants.BANK_ACCOUNT_VALID_LENGTH_2 : Constants.BANK_ACCOUNT_VALID_LENGTH;	    
	}
	
	/**
	 * When we have big value in the Double object - then after toString method - String contains 'E' symbol.
	 * Also when Double String ends with '.0' - method removed that part... 
	 * 
	 * So this method returns normal String format for the Double.  
	 * @param doubleString
	 * @return
	 */
	public static String getDoubleWithoutExponentialFormatAndDotZero(String doubleString) {
	    if(doubleString == null) {
	        return null;
	    }
		
		// Case when double string value has an exponential value...
		if(doubleString.contains("E")) {
			doubleString = getDoubleWithoutExponentialFormat(doubleString);
		}		
		
		return getDoubleWithoutDotZero(doubleString);
	}
	
	/**
	 * When we have big value in the Double object - then after toString method - String contains 'E' symbol.
	 * So this method returns normal String format for the Double...
	 * @param doubleString
	 * @return
	 */
	public static String getDoubleWithoutExponentialFormat(String doubleString) {
		Double number = Double.parseDouble(doubleString);
		NumberFormat formatter = new DecimalFormat("#0.0");

		return formatter.format(number);
	}
	
	/**
	 * When Double String ends with '.0' - method removed that part... 
	 * 
	 * So this method returns normal String format for the Double.  
	 * @param doubleString
	 * @return
	 */
	public static String getDoubleWithoutDotZero(String doubleString) {				
		if(doubleString.endsWith(".0")) {
			doubleString = doubleString.replace(".0", "");
			doubleString = doubleString.replace(",0", "");
		}
		
		return doubleString;
	}
	
	/**
	 * Returns Double String without '.0'... 
	 * 
	 * So this method returns normal String format for the Double.  
	 * @param anyDouble
	 * @return
	 */
	public static String getDoubleWithoutDotZero(Double anyDouble) {				
		if(anyDouble != null) {
			return anyDouble.toString().replace(".0", "");
		}
		
		return null;
	}
	
	/**
	 * Checks whether given date string is the valid date range string.
	 * Date range string valid formats: 
	 * 1. "dd-MM-yyyy dd-MM-yyyy": from to format
	 * 2. "dd-MM-yyyy"   : equals format
	 * 
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean isValidDateRange(String dateString) {
		if(StringUtils.isBlank(dateString)) {
			return false;
		}
		
		String[] fromToDates = dateString.split(" ");
		
		/*more than 2 tokens are invalid case*/
		if(fromToDates.length > 2) {
			return false;
		}
		
		/*if only one date was available in the String*/
		if(fromToDates.length == 1) {
			return isValidDateString(fromToDates[0]);
		}
		
		return isValidDateString(fromToDates[0]) && isValidDateString(fromToDates[1]);		
	}
	
	/**
	 * Checks whether given double string is the valid double range string.
	 * Double range string valid formats: 
	 * 1. "10-25": from to format
	 * 2. "10"   : equals format
	 * 
	 * 
	 * @param doubleString
	 * @return
	 */
	public static boolean isValidDoubleRange(String doubleString) {
		if(StringUtils.isBlank(doubleString)) {
			return false;
		}
		
		String[] fromToDoubles = doubleString.split("-");
		
		/*more than 2 tokens are invalid case*/
		if(fromToDoubles.length > 2) {
			return false;
		}
		
		/*if only one double was available in the String*/
		if(fromToDoubles.length == 1) {
			return isValidDoubleString(fromToDoubles[0]);
		}
		
		return isValidDoubleString(fromToDoubles[0]) && isValidDoubleString(fromToDoubles[1]);		
	}
	
	/**
	 * Get double from to array by given double range string...
	 * @param doubleString
	 * @return
	 */
	public static Double[] getDoubleRange(String doubleString) {
		Double[] doubleRange = null;
		String[] fromToDoubles = doubleString.split("-");
				
		/*if only one double was available in the String*/
		if(fromToDoubles.length == 1) {
			doubleRange = new Double[1];
			doubleRange[0] = Double.parseDouble(fromToDoubles[0]);
			
			return doubleRange;
		}	
		
		doubleRange = new Double[2];
		doubleRange[0] = Double.parseDouble(fromToDoubles[0]);
		doubleRange[1] = Double.parseDouble(fromToDoubles[1]);		
		
		return doubleRange;
	}
	
	/**
	 * Get double from to array by given date range string...
	 * @param dateString
	 * @return
	 */
	public static Date[] getDateRange(String dateString) {
		Date[] doubleRange = null;
		String[] fromToDates = dateString.split(" ");
				
		/*if only one date was available in the String*/
		if(fromToDates.length == 1) {
			doubleRange = new Date[1];
			doubleRange[0] = DateUtil.getDateByString(fromToDates[0], Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
			
			return doubleRange;
		}	
		
		doubleRange = new Date[2];
		doubleRange[0] = DateUtil.getDateByString(fromToDates[0], Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
		doubleRange[1] = DateUtil.getDateByString(fromToDates[1], Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
		
		return doubleRange;
	}
	
	/**
	 * Validates given string is a double or not...
	 * @param doubleString
	 * @return
	 */
	public static boolean isValidDoubleString(String doubleString) {
		if(StringUtils.isBlank(doubleString)) {
			return false;
		}
		
		try {
			Double.parseDouble(doubleString);
			
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Validates given string is a date or not...
	 * @param dateString
	 * @return
	 */
	public static boolean isValidDateString(String dateString) {
		if(StringUtils.isBlank(dateString)) {
			return false;
		}
		
		Date date = DateUtil.getDateByString(dateString, Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
		
		return date != null;
	}
	
	/**
     * Get double value of given String...
     * @param doubleString
     * @return
     */
    public static Double getDoubleByString(String doubleString) {
        if(StringUtils.isBlank(doubleString)) {
            return null;
        }
        
        try {
            return Double.parseDouble(doubleString);
        } catch(NumberFormatException e) {
            return null;
        }
    }
	
	/**
	 * Get double value of given String...
	 * @param doubleString
	 * @return
	 */
	public static Double getDoubleOrZeroByString(String doubleString) {
	    if(StringUtils.isBlank(doubleString)) {
            return 0d;
        }
	    
	    try {
	        return Double.parseDouble(doubleString);
	    } catch(NumberFormatException e) {
	        return 0d;
	    }
	}
	
	/**
	 * Recursively delete directory from the file system...
	 * @param dirPath
	 */
	public static void deleteDirectory(String dirPath) {
		Path rootPath = Paths.get(dirPath);
		try {
		Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS)
		    .sorted(Comparator.reverseOrder())
		    .map(Path::toFile)		    
		    .forEach(File::delete);
		} catch(IOException e) {
			LOGGER.error("ERROR REMOVING DIRECTORY: {}", dirPath);
		}
	}
	
	/**
	 * Deletes file content from the system...
	 */
	public static void deleteFromSystem(String filePath) {
		Path path = Paths.get(filePath);		
		try {
			if(Files.exists(path)){
				Files.delete(path);	
			}
		} catch (IOException e) {
			LOGGER.warn("Problem occured while removing file: {} []", path, e);
		}
	}
	
	/**
	 * Subtracts 2 doubles using BigDecimal...
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double subtract(Double d1, Double d2) {
		return BigDecimal.valueOf(d1).subtract(BigDecimal.valueOf(d2)).doubleValue();
	}
	
	/**
	 * Subtracts 2 doubles using BigDecimal...
	 * @param sd1
	 * @param sd2
	 * @return
	 */
	public static Double subtract(String sd1, String sd2) {
		return new BigDecimal(sd1).subtract(new BigDecimal(sd2)).doubleValue();
	}
	
	/**
	 * Subtracts 2 doubles using BigDecimal...
	 * @param d1
	 * @param sd2
	 * @return
	 */
	public static Double subtract(Double d1, String sd2) {
		return BigDecimal.valueOf(d1).subtract(new BigDecimal(sd2)).doubleValue();
	}
	
	/**
	 * Adds 2 doubles using BigDecimal...
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double add(Double d1, Double d2) {
		return BigDecimal.valueOf(d1).add(BigDecimal.valueOf(d2)).doubleValue();
	}
	
	/**
	 * Adds 2 doubles using BigDecimal...
	 * @param sd1
	 * @param sd2
	 * @return
	 */
	public static Double add(String sd1, String sd2) {
		return new BigDecimal(sd1).add(new BigDecimal(sd2)).doubleValue();
	}
	
	/**
	 * Adds 2 doubles using BigDecimal...
	 * @param d1
	 * @param sd2
	 * @return
	 */
	public static Double add(Double d1, String sd2) {
		return BigDecimal.valueOf(d1).add(new BigDecimal(sd2)).doubleValue();
	}
	
	/**
	 * Multiply 2 doubles using BigDecimal...
	 * @param sd1
	 * @param sd2
	 * @return
	 */
	public static Double multiply(String sd1, String sd2) {
		return new BigDecimal(sd1).multiply(new BigDecimal(sd2)).doubleValue();
	}
	
	/**
	 * Rounds given double by given precision...
	 * @param precision
	 * @return
	 */
	public static Double roundDouble(Double anyDouble, int precision) {
	    if(anyDouble == null) {
	        return null;
	    }
	    
	    BigDecimal bigDecimalToRound = BigDecimal.valueOf(anyDouble);
	    bigDecimalToRound = bigDecimalToRound.setScale(precision, BigDecimal.ROUND_HALF_UP);
	    
	    return bigDecimalToRound.doubleValue();
	}
}