package am.chronograph.dao.student;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.domain.student.Student;

/**
 * class is implementation of StudentDao interface and extends from
 * GenericDaoImpl for getting all methods implementation for students...
 * 
 * @author mona
 *
 */
@Repository
public class StudentDaoImpl extends GenericDaoImpl<Student> implements StudentDao {

}
