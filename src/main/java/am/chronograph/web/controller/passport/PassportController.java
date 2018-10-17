package am.chronograph.web.controller.passport;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import am.chronograph.service.passport.PassportService;
import am.chronograph.web.bean.passport.PassportBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

/**
 * Controller class will handle actions from passport.xhtml page
 * 
 * @author vahagn
 *
 */
@Named("countryController")
@ViewScoped
public class PassportController extends BaseController implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 647632524282795064L;

	@Inject
	@Spring
	private transient PassportService passportService;

	private PassportBean passportBean;
	private List<PassportBean> passports;

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();

		passportBean = new PassportBean();

		passports = passportService.getAll();
	}

	/**
	 * @return the countryBean
	 */
	public PassportBean getPassportBean() {
		return passportBean;
	}

	/**
	 * @param passportBean
	 *            the countryBean to set
	 */
	public void setCountryBean(PassportBean passportBean) {
		this.passportBean = passportBean;
	}

	/**
	 * @return the countries
	 */
	public List<PassportBean> getCountries() {
		return passports;
	}

	/**
	 * @param countries
	 *            the countries to set
	 */
	public void setCountries(List<PassportBean> passports) {
		this.passports = passports;
	}
}
