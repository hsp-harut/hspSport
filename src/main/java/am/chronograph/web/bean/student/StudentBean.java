package am.chronograph.web.bean.student;

import java.util.Date;

import am.chronograph.web.bean.BaseBean;
import am.chronograph.web.bean.country.CountryBean;

/**
 * Bean class corresponds to students. Will contain presentation layer related data...
 * 
 * @author moan
 */
public class StudentBean extends BaseBean {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 5964979443776005956L;
	
	private String firstName;
	private String lastName;
	private Date birthday;
	
	/**
	 * default constructor...
	 */
	public StudentBean() {
	}
	
	/**
	 * Copy constructor...
	 * @param contractBean
	 */
	public StudentBean(StudentBean studentBean) {
		id = studentBean.getId();
		firstName = studentBean.getFirstName();
		lastName = studentBean.getLastName();
		birthday = studentBean.getBirthday();
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	


}
