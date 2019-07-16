package am.chronograph.service.student;

import java.util.List;

import am.chronograph.domain.student.Student;
import am.chronograph.service.SearchBeanAware;
import am.chronograph.web.bean.student.StudentBean;

public interface StudentService extends SearchBeanAware<StudentBean, Student> {
	/**
	 * Create a new user...
	 * 
	 * @param studentBean
	 */
	void create(StudentBean studentBean);

	/**
	 * Update given user..
	 * 
	 * @param studentBean
	 * @param id
	 */
	void update(StudentBean studentBean, Long id);

	/**
	 * Delete given user...
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Return all list in Student...
	 * 
	 * @return
	 */
	List<StudentBean> getAllStudent();

	/**
	 * Return student in given id...
	 * 
	 * @param id
	 * @return
	 */
	StudentBean getStudentById(Long id);

}
