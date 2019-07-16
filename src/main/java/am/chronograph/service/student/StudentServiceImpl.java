package am.chronograph.service.student;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.student.StudentDao;
import am.chronograph.domain.student.Student;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResultBean;
import am.chronograph.web.bean.student.StudentBean;
import am.chronograph.web.integration.Spring;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService{

	@Autowired
    private StudentDao studentDao;  
	
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#create(am.chronograph.web.bean.student.StudentBean)
	 */
	@Override
	public void create(StudentBean studentBean) {
		studentDao.create(initDomainByGivenBean(studentBean));
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#update(am.chronograph.web.bean.student.StudentBean, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(StudentBean studentBean, Long id) {
		studentDao.update(id, initDomainByGivenBean(studentBean));
		
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#delete(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		studentDao.delete(studentDao.getStudentByGivenId(id));
		
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#getAllStudent()
	 */
	@Override
	public List<StudentBean> getAllStudent() {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#getStudentById(java.lang.Long)
	 */
	@Override
	public StudentBean getStudentById(Long id) {
	
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.SearchBeanAware#search(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResultBean<StudentBean> search(SearchCriteria<Student> criteria) {
	
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.SearchBeanAware#getEmptyCriteria()
	 */
	@Override
	public SearchCriteria<Student> getEmptyCriteria() {
		
		return null;
	}
	
	/**
	 * method for init {@link StudentBean} by Domain
	 * 
	 * @param student
	 * @return
	 */
	private StudentBean initStudentBeanByGivenDomain(Student student) {
		StudentBean studentBean = new StudentBean();
		
		
		studentBean.setId(student.getId());
		studentBean.setFirstName(student.getFirstName());
		
		return studentBean;
	}
	
	/**
	 * method for init {@link Student} by bean
	 * 
	 * @param studentBean
	 * @return
	 */
	private Student initDomainByGivenBean(StudentBean studentBean) {
		Student student = new Student();
		
		student.setId(studentBean.getId());
		student.setFirstName(studentBean.getFirstName());
		student.setLastName(studentBean.getLastName());
		student.setPhoneNumber(studentBean.getPhoneNumber());
		student.seteMail(studentBean.geteMail());
		
		return student;
	}



}
