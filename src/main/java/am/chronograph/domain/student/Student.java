package am.chronograph.domain.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to contract table.
 * 
 * @author lusine
 *
 */
@Entity
@Table(name = "student")
public class Student extends AuditAwareEntity {

	private static final long serialVersionUID = -103965845051084220L;

	@Column(name = "firstName", insertable = true, updatable = true)
	private String firstName;

	@Column(name = "lastName",  insertable = true, updatable = true)
	private String lastName;

	@Column(name = "phoneNumber", insertable = true, updatable = true)
	private String phoneNumber;

	@Column(name = "eMail", insertable = true, updatable = true)
	private String eMail;

	/**
	 * Default constructor...
	 */
	public Student() {

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
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

}
