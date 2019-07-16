package am.chronograph.dao.student;

import java.util.List;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.student.Student;
import am.chronograph.search.SearchSupport;

public interface StudentDao extends GenericDao<Student>, SearchSupport<Student> {

	/**
	 * Get student by given id...
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	Student getStudentByGivenId(Long id);

	/**
	 * Create new user...
	 */
	void create(Student student);
	
	/**
	 * update student by given id
	 * 
	 * @param id
	 * @param student
	 * @return
	 */
	Student update(Long id, Student student);
	
	/**
	 * Return List of all student...
	 * @return
	 */
	List<Student> getAllStudent();
	
	

}
