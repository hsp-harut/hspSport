package am.chronograph.web.controller.student;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import am.chronograph.service.student.StudentService;
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
public class StudentController extends BaseController implements Serializable{
	
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
	
	/**
	 * method called from jsf actionListener when create button is submited...
	 */
	public void onCreateStudent() {
		if(!isValidStudent()) {
            return;
        }      
        studentService.create(studentBean);
        
        students = studentService.getAll();
        
        studentBean = new StudentBean();        
        
        addInfoMessage("studentSuccessSave");
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
	
	private boolean isValidStudent() {
        boolean noError = true;
        
        if(StringUtils.isBlank(studentBean.getFirstName())) {
        	 addErrorMessage("studentForm:firstName", "studentValidationMandatory");             
             noError = false;
        }
        
        if(StringUtils.isBlank(studentBean.getLastName())) {
       	 addErrorMessage("studentForm:lastName", "studentValidationMandatory");             
            noError = false;
       }
        
        if(studentBean.getBirthday() == null) {
       	 addErrorMessage("studentForm:lastName", "studentValidationMandatory");             
            noError = false;
       }

		return noError;
	}
}
