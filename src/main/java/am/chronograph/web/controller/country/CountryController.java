package am.chronograph.web.controller.country;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import am.chronograph.service.country.CountryService;
import am.chronograph.web.bean.country.CountryBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

/**
 * Controller class will handle actions from country.xhtml page...
 * 
 * @author HARUT
 */
@Named("countryController")
@ViewScoped
public class CountryController extends BaseController implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 1072023594742791141L;
	
	@Inject
	@Spring
	private transient CountryService countryService;
		
	private CountryBean countryBean;
	private List<CountryBean> countries;
	
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();						
		
		countryBean = new CountryBean();
		
		countries = countryService.getAll();
	}


	/**
	 * @return the countryBean
	 */
	public CountryBean getCountryBean() {
		return countryBean;
	}


	/**
	 * @param countryBean the countryBean to set
	 */
	public void setCountryBean(CountryBean countryBean) {
		this.countryBean = countryBean;
	}


	/**
	 * @return the countries
	 */
	public List<CountryBean> getCountries() {
		return countries;
	}


	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<CountryBean> countries) {
		this.countries = countries;
	}		
}
