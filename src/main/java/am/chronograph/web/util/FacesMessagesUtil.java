package am.chronograph.web.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/** 
 * Helper class to add faces messages into the faces context.
 * 
 * @author HARUT 
 */
public final class FacesMessagesUtil {

	/**
	 * Default constructor - PRIVATE!
	 */
	private FacesMessagesUtil() {		
	}
	
	/** 
	 * Adds an error.
	 * @param msg The message
	 */
	public static void addError(String msg) {
		addError(null, msg, "");
	}
	
	/** 
	 * Adds an error.
	 * @param msg The message
	 */
	public static void addError(String id, String msg) {
		addError(id, msg, "");
	}

	/** 
	 * Adds an error.
	 * @param msg The message
	 * @param detail The detailed message.
	 */
	public static void addError(String id, String msg, String detail) {
		FacesMessage msgObj = new FacesMessage(FacesMessage.SEVERITY_ERROR, detail, msg);
		FacesContext.getCurrentInstance().addMessage(id, msgObj);
	}

	/** 
	 * Adds a warning.
	 * @param msg The message
	 */
	public static void addWarning(String msg) {
		addWarning(msg, "");
	}

	/** 
	 * Adds a warning.
	 * @param msg The message
	 * @param detail The detailed message.
	 */
	public static void addWarning(String msg, String detail) {
		FacesMessage msgObj = new FacesMessage(FacesMessage.SEVERITY_WARN, detail, msg);
		FacesContext.getCurrentInstance().addMessage(null, msgObj);
	}

	/** 
	 * Adds a message (for a specific component).
	 * @param msg The message
	 */
	public static void addMessage(String clientId, String msg) {
		addMessage(clientId, msg, "");
	}
	
	/** 
	 * Adds a message.
	 * @param msg The message
	 */
	public static void addMessage(String msg) {
		addMessage(null, msg, "");
	}

	/** 
	 * Adds a message.
	 * @param msg The message
	 * @param detail The detailed message
	 */
	public static void addMessage(String clientId, String msg, String detail) {		
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, detail, msg));
	}	

	/** 
	 * Returns the message resource string from the bundle.
	 * @param key The key.
	 * @param params The object array with the parameters.
	 * @return
	 */
	public static String getMessageResourceString(String key, String lang) {
		if(lang == null) {
			lang = "en";
		}
		
		ResourceBundle bundle = ResourceBundle.getBundle(Constants.RESOURCE_BUNDLE + "_" + lang);		
		
		String text = null;
		try {
			text = bundle.getString(key);
		} catch (MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}
		
		return text;
	}
	
	/**
     * Get request parameter by given name...
     * @param paramName
     * @return
     */
    public static String getParam(String paramName) {
    	return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
    }
}