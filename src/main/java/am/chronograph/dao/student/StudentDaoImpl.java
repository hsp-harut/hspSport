package am.chronograph.dao.student;

import java.io.Serializable;
import java.util.List;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.contract.ContractDao;
import am.chronograph.domain.contract.Contract;
import am.chronograph.domain.student.Student;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchSupport;

public class StudentDaoImpl extends GenericDaoImpl<Student> implements StudentDao, SearchSupport<Student> {

	@Override
	public SearchResult<Student> search(SearchCriteria<Student> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student getStudentByGivenId(Long id) {

		return null;
	}

	@Override
	public void create(Student student) {
	
		
	}

	@Override
	public List<Student> getAllStudent() {
	
		return null;
	}



}
