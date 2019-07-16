package am.chronograph.web.controller.student;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import am.chronograph.service.student.StudentService;
import am.chronograph.web.bean.contract.ContractBean;
import am.chronograph.web.bean.student.StudentBean;
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
	private transient StudentService studentService;

	private StudentBean studentBean;

	/**
	 * ActionListener method called when 'Create' clicked - to create
	 * new user...
	 */
	public void onCreate(StudentBean student) {

		studentService.create(student);
		
		  studentBean = new StudentBean();       
		addInfoMessage("contractSuccessSave");

	}

	/**
	 * @return the studentService
	 */
	public StudentService getStudentService() {
		return studentService;
	}

	/**
	 * @param studentService the studentService to set
	 */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * @return the studentBean
	 */
	public StudentBean getStudentBean() {
		return studentBean;
	}

	/**
	 * @param studentBean the studentBean to set
	 */
	public void setStudentBean(StudentBean studentBean) {
		this.studentBean = studentBean;
	}

}
