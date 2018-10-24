package am.chronograph.web.controller.passport;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import am.chronograph.service.passport.PassportService;
import am.chronograph.web.bean.contract.ContractBean;
import am.chronograph.web.bean.passport.PassportBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

/**
 * Controller class will handle actions from passport.xhtml page
 * 
 * @author vahagn
 *
 */
@Named("passportController")
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
	 * create passport
	 */
	public void onCreatePassport() {
		/*
		 * if(!isValidContract()) { return; }
		 */

		passportService.create(passportBean);
		passports = passportService.getAll();
		passportBean = new PassportBean();

		addInfoMessage("passportSuccessSave");
	}

	/**
	 * validate all inputs
	 */
	private boolean isValid() {

	}

	/**
	 * @return the passportBean
	 */
	public PassportBean getPassportBean() {
		return passportBean;
	}

	/**
	 * @param passportBean
	 *            the passportBean to set
	 */
	public void setPassportBean(PassportBean passportBean) {
		this.passportBean = passportBean;
	}

	/**
	 * @return the passports
	 */
	public List<PassportBean> getPassports() {
		return passports;
	}

	/**
	 * @param passports
	 *            the passports to set
	 */
	public void setPassports(List<PassportBean> passports) {
		this.passports = passports;
	}

	/**
	 * @return the passportService
	 */
	public PassportService getPassportService() {
		return passportService;
	}

	/**
	 * @param passportService
	 *            the passportService to set
	 */
	public void setPassportService(PassportService passportService) {
		this.passportService = passportService;
	}

}
