package am.chronograph.web.controller.base;

import java.text.MessageFormat;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.springframework.core.env.Environment;

import am.chronograph.web.bean.SessionBean;
import am.chronograph.web.integration.Spring;
import am.chronograph.web.util.FacesMessagesUtil;

/**
 * Base class for all Controllers.
 * Will contain general properties of Controllers.
 * @author HARUT
 */
public abstract class BaseController {		

	@Inject
	protected SessionBean sessionBean;
	
	@Inject
	@Spring
	private transient Environment env;		
	
	/* Page message bundle to use. We place this property here, because in case of 
	 * binding loadBunde property with sessionBean corresponding property, causes 
	 * multiple language viewing problem, in case when multy-tabbing used...*/
	protected String currentBundle; 		// for dynamically changing MessageResources...messages.messages_*
	protected String languageShort;
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.web.controller.base.SpringAwareController#init()
	 */
	@PostConstruct
	public void init() {
		initBundle();
	}
	
	/**
	 * This method must be called in every Controller class,
	 * to initialize bundle related properties for current page...
	 */
	private void initBundle() {
		currentBundle = sessionBean.getCurrentBundle();
		languageShort = sessionBean.getLanguageShort();
	}
	
    /**
     * Get corresponding string from the messageg.properties files. 
     * @param key
     * @return
     */
	protected String getString(String key) {
    	return FacesMessagesUtil.getMessageResourceString(key, languageShort);
    }
    
    /**
     * Get request parameter by given name...
     * @param paramName
     * @return
     */
	protected String getParam(String paramName) {
    	return FacesMessagesUtil.getParam(paramName);
    }
	
	/**
     * Get request Integer parameter by given name...
     * @param paramName
     * @return
     */
	protected Integer getIntParam(String paramName) {
    	String paramValue = FacesMessagesUtil.getParam(paramName);
    	if(paramValue == null) {
    		return null;
    	}
    	
    	try {
    		return Integer.valueOf(paramValue);
    	} catch(NumberFormatException e) {
    		return null;
    	}
    }
    
    /**
     * Get request Long parameter by given name...
     * @param paramName
     * @return
     */
	protected Long getLongParam(String paramName) {
    	String paramValue = FacesMessagesUtil.getParam(paramName);
    	if(paramValue == null) {
    		return null;
    	}
    	
    	try {
    		return Long.valueOf(paramValue);
    	} catch(NumberFormatException e) {
    		return null;
    	}
    }
	
	/**
	 * Add Information message...
	 * @param messageKey
	 */
	protected void addInfoMessage(String messageKey) {
		FacesMessagesUtil.addMessage(getString(messageKey));
	}
	
	/**
	 * Add Warning message...
	 * @param messageKey
	 */
	protected void addWarningMessage(String messageKey) {
		FacesMessagesUtil.addWarning(getString(messageKey));
	}
	
	/**
	 * Add Error message...
	 * @param componentId
	 * @param messageKey
	 */
	protected void addErrorMessage(String messageKey) {
		FacesMessagesUtil.addError(getString(messageKey));		
	}
	
	/**
	 * Add Error message for the given web component...
	 * @param componentId
	 * @param messageKey
	 */
	protected void addErrorMessage(String componentId, String messageKey) {
		FacesMessagesUtil.addError(componentId, getString(messageKey));		
	}
	
	/**
	 * Add Error message for the given web component by adding dynamic value parameter into message...
	 * @param componentId
	 * @param messageKey
	 * @param value
	 */
	protected void addErrorMessage(String componentId, String messageKey, Object... value) {
		FacesMessagesUtil.addError(componentId, MessageFormat.format(getString(messageKey), value));		
	}
	
	/**
	 * Made scroll to given web component... 
	 * @param scrollToId
	 */
	protected void scrollTo(String scrollToId) {
		RequestContext.getCurrentInstance().scrollTo(scrollToId);
	}
	
	/**
	 * Updates the given components at front end. 
	 * @param components The ids of the components.
	 */
	protected void update(Collection<String> components) {
		RequestContext.getCurrentInstance().update(components);
	}
	
	/**
	 * Updates the given component at front end. 
	 * @param component The id of the component.
	 */
	protected void update(String component) {
		RequestContext.getCurrentInstance().update(component);
	}
	
	/**
	 * Returns true if system id is chrono. That is the chronograph instance.
	 * @return true if system id is chrono. That is the chronograph instance.
	 */
	public boolean isChrono(){
		return "chrono".equalsIgnoreCase(env.getProperty("system.id"));
	}
    
	/**
	 * @return the sessionBean
	 */
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	/**
	 * @param sessionBean the sessionBean to set
	 */
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	/**
	 * @return the currentBundle
	 */
	public String getCurrentBundle() {
		return currentBundle;
	}

	/**
	 * @param currentBundle the currentBundle to set
	 */
	public void setCurrentBundle(String currentBundle) {
		this.currentBundle = currentBundle;
	}

	/**
	 * @return the languageShort
	 */
	public String getLanguageShort() {
		return languageShort;
	}

	/**
	 * @param languageShort the languageShort to set
	 */
	public void setLanguageShort(String languageShort) {
		this.languageShort = languageShort;
	}		
}
