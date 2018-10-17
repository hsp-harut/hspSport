package am.chronograph.service.student;

import java.util.List;

import am.chronograph.web.bean.student.StudentBean;

/**
 * Provides services for getting, managing students....
 * 
 * @author mona
 */
public interface StudentService {
	
	/**
	 * Creates given student...
	 * @param countryBean
	 */
	void create(StudentBean studentBean);
	
	/**
	 * Updates student by given one...
	 * @param countryBean
	 */
	void update(StudentBean studentBean);
	
	/**
	 * Delets student by given id...
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Get all students...
	 * @return
	 */
	List<StudentBean> getAll();

}
