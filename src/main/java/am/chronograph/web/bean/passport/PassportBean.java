package am.chronograph.web.bean.passport;

import java.time.LocalDateTime;
import java.util.Date;

import am.chronograph.web.bean.BaseBean;

/**
 * Bean class corresponds to passports. Will contain presentation layer related
 * data...
 * 
 * @author vahagn
 *
 */
public class PassportBean extends BaseBean {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -442813629571321129L;

	private String firstName;
	private String lastName;
	private String passNumber;
	private Date birthDay;

	/**
	 * Default constructor...
	 */
	public PassportBean() {
	}

	/**
	 * Copy constructor...
	 * 
	 * @param contractBean
	 */
	public PassportBean(PassportBean passportBean) {
		firstName = passportBean.getFirstName();
		lastName = passportBean.getLastName();
		passNumber = getPassNumber();
		birthDay = getBirthDay();

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
	 * @return the passNumber
	 */
	public String getPassNumber() {
		return passNumber;
	}

	/**
	 * @param passNumber the passNumber to set
	 */
	public void setPassNumber(String passNumber) {
		this.passNumber = passNumber;
	}

	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

}
