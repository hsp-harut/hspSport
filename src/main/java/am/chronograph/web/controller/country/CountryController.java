package am.chronograph.web.controller.country;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import am.chronograph.web.controller.base.BaseController;

/**
 * Controller class will handle actions from country.xhtml page...
 * 
 * @author HARUT
 */
@Named("countryController")
@ViewScoped
public class CountryController extends BaseController implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 6304423142083144352L;
	
//	@Inject
//	@Spring
//	private transient CountryService countryService;
	
//	private CountryBean countryBean;
//	private List<CountryBean> countries;
	
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();						
		
//		countryBean = new CountryBean();
		
//		countries = countryService.getAll();
	}
	
	
}
