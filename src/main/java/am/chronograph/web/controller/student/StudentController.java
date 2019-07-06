package am.chronograph.web.controller.student;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import am.chronograph.service.contract.ContractService;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

/**
 * Controller class will handle actions from contractManagement.xhtml page...
 * 
 * @author HARUT
 */
@Named("studentController")
@ViewScoped
public class StudentController extends BaseController implements Serializable {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 6304423142083144352L;
	
	@Inject
	@Spring
	private transient ContractService contractService;
	
	//private StudentBean studentBean;
	
	
}
