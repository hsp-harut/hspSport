package am.chronograph.service.student;

import java.util.List;

import am.chronograph.web.bean.student.StudentBean;

public interface StudentService {
	
	void create();
	
	void update();
	
	void delete();
	
	List<StudentBean> getAllStudent();
	
	StudentBean getStudentById();

}
