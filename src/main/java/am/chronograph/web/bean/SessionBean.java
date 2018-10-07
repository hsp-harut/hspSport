package am.chronograph.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;

import am.chronograph.security.UserDetails;
import am.chronograph.web.integration.Spring;
import am.chronograph.web.util.Constants;

/**
 * Bean class containing info in session scope...
 * @author HARUT
 */
@SessionScoped
@Named("sessionBean")
public class SessionBean implements Serializable {
	
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 795844928537623276L;
		
	/*REMEMBER: this is cause of 
	 * multiple language viewing problem, 
	 * in case of multy-tabbing used...*/
	// for dynamically changing MessageResources...web-base.messages
	private String currentBundle;
	
	// short language -- needed to append the name of bundle file in FacesMessagesUtil class...
	private String languageShort;
	
	@Inject
	@Spring
	private transient Authentication authentication;
	
	private Long loggedInUserId;
	
	private String statusFilterBy;
	private String statusDefaultFilter;
	
	private String dashbaordCompalinsDefaultFilter = "Started";
	
	private String dashbaordProcessesDefaultFilter = "Started";
	private String dashbaordProcessesDescDefaultFilter;
	private String dashbaordProcessesNameDefaultFilter;
	
	@PostConstruct
	private void init(){
		loggedInUserId = ((UserDetails)authentication.getPrincipal()).getId();
		
		String userLanguage = ((UserDetails) authentication.getPrincipal()).getLanguageCode();		
		if(userLanguage == null) {
			userLanguage = Constants.LANGUAGE_ENGLISH;
		}
		
		currentBundle = Constants.LANGUAGE_BUNDLE_MAP.get(userLanguage);
		languageShort = Constants.LANGUAGE_SHORT_MAP.get(userLanguage);			
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
	
	/**
	 * @return the authentication
	 */
	public Authentication getAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication the authentication to set
	 */
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	/**
	 * @return the loggedInUserId
	 */
	public Long getLoggedInUserId() {
		return loggedInUserId;
	}

	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(Long loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	
	
	
	/**
     * @return the statusFilterBy
     */
    public String getStatusFilterBy() {
        return statusFilterBy;
    }

    /**
     * @param statusFilterBy the statusFilterBy to set
     */
    public void setStatusFilterBy(String statusFilterBy) {
        this.statusFilterBy = statusFilterBy;
    }

    /**
     * @return the statusDefaultFilter
     */
    public String getStatusDefaultFilter() {
        return statusDefaultFilter;
    }

    /**
     * @param statusDefaultFilter the statusDefaultFilter to set
     */
    public void setStatusDefaultFilter(String statusDefaultFilter) {
        this.statusDefaultFilter = statusDefaultFilter;
    }

    public String getDashbaordCompalinsDefaultFilter() {
		return dashbaordCompalinsDefaultFilter;
	}
	
	public void setDashbaordCompalinsDefaultFilter(String dashbaordCompalinsDefaultFilter) {
		this.dashbaordCompalinsDefaultFilter = dashbaordCompalinsDefaultFilter;
	}
	
	public String getDashbaordProcessesDefaultFilter() {
		return dashbaordProcessesDefaultFilter;
	}
	
	public void setDashbaordProcessesDefaultFilter(String dashbaordProcessesDefaultFilter) {
		this.dashbaordProcessesDefaultFilter = dashbaordProcessesDefaultFilter;
	}

	public String getDashbaordProcessesDescDefaultFilter() {
		return dashbaordProcessesDescDefaultFilter;
	}

	public void setDashbaordProcessesDescDefaultFilter(String dashbaordProcessesDescDefaultFilter) {
		this.dashbaordProcessesDescDefaultFilter = dashbaordProcessesDescDefaultFilter;
	}

	public String getDashbaordProcessesNameDefaultFilter() {
		return dashbaordProcessesNameDefaultFilter;
	}

	public void setDashbaordProcessesNameDefaultFilter(String dashbaordProcessesNameDefaultFilter) {
		this.dashbaordProcessesNameDefaultFilter = dashbaordProcessesNameDefaultFilter;
	}
}
