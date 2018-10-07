package am.chronograph.web.util;

import java.util.HashMap;
import java.util.Map;


/**
 * Constant properties...
 * @author HARUT
 */
public final class Constants {		
	
	/**
	 * Default constructor - PRIVATE!
	 */
	private Constants() {		
	}
	
	/*
	 * Contains possible statuses of Contracts...
	 */
	public enum ContractStatus {
		Overdue,
		Paid,
		Overpaid
	}
	
	/*
	 * Contains possible statuses of Process...
	 */
	public enum Status {
		Not_Started,
		Finished,
		Started,
		Terminated;
	}
	
	/*
	 * Contains possible statuses of Process Step...
	 */
	public enum StepStatus {
		Not_Started,
		Finished,
		Started,
		Skipped,
		Skip_Request,
		Postponed,
		Postpone_Request,
		Terminated,
		Terminate_Request;
		
		public static StepStatus valueByInt(int statusInt) {
			switch(statusInt) {
				case 1: {
					return Started;
				}
				
				case 2: {
					return Finished;
				}
				
				default: {
					return Started;
		        }
			}
		}
	}
	
	/*
	 * Contains possible levels of Process Step
	 */
	public enum StepLevel {
		First,
		Middle,
		Last;		
	}
	
	/*
	 * Contains possible types of connection.
	 */
	public enum ConnectionType {
		Connection,		
		Notification;		
	}
	
	/*
	 * Contains possible statuses of Exams...
	 */
	public enum ExamStatus {
		Not_Started,
		Started,
		Finished,
		Time_Up;
	}
	
	/*
	 * Contains possible statuses of Exams Question...
	 */
	public enum QuestionStatus {
		Not_Asked,
		Answered_Correct,
		Answered_Wrong,
		Time_Up;
	}
	
	public static final String RESOURCE_BUNDLE = "messages.messages";
	
	public static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";
	public static final String DEFAULT_DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm";
	public static final String DEFAULT_DATE_TIME_SEC_PATTERN = "dd-MM-yyyy HH:mm:ss";
	public static final String MYSQL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";	
	public static final String PAYPALL_DATE_PATTERN = "HH:mm:ss MMM d, yyyy z";
	
	public static final String MONTH_YEAR_DATE_PATTERN = "MMM-yyyy";
	public static final String YEAR_DATE_PATTERN = "yyyy";	
	
	/*Layout Colors START*/	
	public static final String PAGE_GENERAL_PATTERN_CSS_CLASS = "mainPageBackgroundStyle";
	public static final String PAGE_GREEN_PATTERN_CSS_CLASS = "greenBackgroundStyle";	
	public static final String PAGE_VIOLET_PATTERN_CSS_CLASS = "violetBackgroundStyle";	
	/*Layout Colors END*/
	
	public static final String CSS_STYLE_DISPLAY_NONE = "display: none;";
	
	/*Languages START*/
	public static final String LANGUAGE_ENGLISH = "English";
	public static final String LANGUAGE_FRANCE = "French";
	public static final String LANGUAGE_RUSSIAN = "Russian";
	public static final String LANGUAGE_GERMAN = "German";
	public static final String LANGUAGE_ARMENIAN = "Armenian";
	public static final String LANGUAGE_SPANISH = "Spanish";			
	
	public static final Map<String, String> LANGUAGE_BUNDLE_MAP = new HashMap<String, String>();
	static{
		LANGUAGE_BUNDLE_MAP.put(LANGUAGE_ENGLISH, "messages.messages_en");
		LANGUAGE_BUNDLE_MAP.put(LANGUAGE_FRANCE, "messages.messages_fr");
		LANGUAGE_BUNDLE_MAP.put(LANGUAGE_RUSSIAN, "messages.messages_ru");
		LANGUAGE_BUNDLE_MAP.put(LANGUAGE_GERMAN, "messages.messages_de");
		LANGUAGE_BUNDLE_MAP.put(LANGUAGE_ARMENIAN, "messages.messages_arm");
		LANGUAGE_BUNDLE_MAP.put(LANGUAGE_SPANISH, "messages.messages_es");		
	}
	
	public static final Map<String, String> LANGUAGE_SHORT_MAP = new HashMap<String, String>();
	static{
		LANGUAGE_SHORT_MAP.put(LANGUAGE_ENGLISH, "en");
		LANGUAGE_SHORT_MAP.put(LANGUAGE_FRANCE, "fr");
		LANGUAGE_SHORT_MAP.put(LANGUAGE_RUSSIAN, "ru");
		LANGUAGE_SHORT_MAP.put(LANGUAGE_GERMAN, "de");
		LANGUAGE_SHORT_MAP.put(LANGUAGE_ARMENIAN, "arm");
		LANGUAGE_SHORT_MAP.put(LANGUAGE_SPANISH, "es");		
	}
	/*Languages END*/		
	
	public static int BIGDECIMAL_PRECISION = 5;
	
	public static int PROGRESS_VIEW = 6;
	public static int PROGRESS_EDIT = 1;
	public static int PROGRESS_FINISH = 2;
	public static int PROGRESS_TERMINATE = 3;
	public static int PROGRESS_SKIP = 4;
	public static int PROGRESS_POSTPONE = 5;
	
	public static int VIEW_PROCESS_FLOW_LINE_MODE = 0;
	public static int VIEW_PROCESS_FLOW_HIERARCHICAL_MODE = 1;
	public static int VIEW_PROCESS_FLOW_TIMELINE_MODE = 2;
	
	public static final int BANK_ACCOUNT_VALID_LENGTH = 12;
	public static final int BANK_ACCOUNT_VALID_LENGTH_2 = 14;
}