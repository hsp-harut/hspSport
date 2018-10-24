package am.chronograph.service.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.dao.student.StudentDao;
import am.chronograph.domain.country.Country;
import am.chronograph.domain.student.Student;
import am.chronograph.web.bean.country.CountryBean;
import am.chronograph.web.bean.student.StudentBean;
import am.chronograph.web.util.DateUtil;

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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void create(StudentBean studentBean) {
		studentDao.save(initDomainByBean(studentBean));		
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#update(am.chronograph.web.bean.student.StudentBean)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(StudentBean studentBean) {
		studentDao.save(initDomainByBean(studentBean));				
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#delete(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		Student student = studentDao.getById(id);
		studentDao.delete(student);		
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.service.student.StudentService#getAll()
	 */
	@Override
	public List<StudentBean> getAll() {
		List<Student> students = studentDao.getAll();
		
		return initBeanListByDomainList(students);
	}
	
	/**
	 * Initialize Student Domain by given Bean...
	 * @param studentBean
	 * @return
	 */
	private Student initDomainByBean(StudentBean studentBean) {
		Student student = (studentBean.getId() != null) ? studentDao.getById(studentBean.getId()) : new Student();
		
		student.setFirstName(studentBean.getFirstName());
		student.setLastName(studentBean.getLastName());
		student.setBirthday(DateUtil.getLocalDateTimeByDate(studentBean.getBirthday()));
		return student;
	}
	
	/**
	 * Initialize Student Beans List by given Student Domains list...
	 * @param countries
	 * @return
	 */
	private List<StudentBean> initBeanListByDomainList(List<Student> students) {
		List<StudentBean> studentBeans = new ArrayList<>();
		for(Student student : students) {
			studentBeans.add(initBeanByDomain(student));
		}
		
		return studentBeans;
	}

	/**
	 * Initialize Student Bean by given Student Domain...
	 * @param country
	 * @return
	 */
	private StudentBean initBeanByDomain(Student student) {
		StudentBean studentBean = new StudentBean();
		
		studentBean.setId(student.getId());
		studentBean.setFirstName(student.getFirstName());
		studentBean.setLastName(student.getLastName());
		studentBean.setBirthday(DateUtil.getDateByLocalDateTime(student.getBirthday()));
		
		return studentBean;
	}
}
