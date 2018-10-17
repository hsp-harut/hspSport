package am.chronograph.web.controller.student;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import am.chronograph.service.student.StudentService;
import am.chronograph.web.bean.country.CountryBean;
import am.chronograph.web.bean.student.StudentBean;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

/**
 * Controller class will handle actions from student.xhtml page...
 * 
 * @author HARUT
 */
@Named("studentController")
@ViewScoped
public class StrudentController extends BaseController implements Serializable{
	
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -6666406060400783367L;
	
	@Inject
	@Spring
	private transient StudentService studentService;
		
	private StudentBean studentBean;
	private List<StudentBean> students;
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.web.controller.base.BaseController#init()
	 */
	@PostConstruct
	public void init() {
		super.init();						
		
		studentBean = new StudentBean();		
		students = studentService.getAll();
	}
	
	public void onCreateStudent() {
		
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

	/**
	 * @return the students
	 */
	public List<StudentBean> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<StudentBean> students) {
		this.students = students;
	}	
}
