package am.chronograph.dao.student;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.domain.student.Student;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchSupport;

@Repository
public class StudentDaoImpl extends GenericDaoImpl<Student> implements StudentDao, SearchSupport<Student> {

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResult<Student> search(SearchCriteria<Student> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.student.StudentDao#getStudentByGivenId(java.lang.Long)
	 */
	@Override
	public Student getStudentByGivenId(Long id) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.student.StudentDao#create(am.chronograph.domain.student.Student)
	 */
	@Override
	public void create(Student student) {
	
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
 
	    student.setFirstName("User");
	    student.setLastName("lastName");
	    student.setPhoneNumber("phone");
	    student.seteMail("mail");
			 
	    session.save(student); 
	   
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.student.StudentDao#getAllStudent()
	 */
	@Override
	public List<Student> getAllStudent() {
	
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.student.StudentDao#update(java.lang.Long, am.chronograph.domain.student.Student)
	 */
	@Override
	public Student update(Long id, Student student) {
		// TODO Auto-generated method stub
		return null;
	}
}
