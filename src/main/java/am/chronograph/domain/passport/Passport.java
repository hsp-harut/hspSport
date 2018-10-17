package am.chronograph.domain.passport;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to 'passport' table.
 * 
 * @author vahagn
 *
 */
@Entity
@Table(name = "passport")
public class Passport extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 4594227715921167255L;

	@Column(name = "firstName", nullable = false, insertable = true, updatable = true)
	private String firstName;

	@Column(name = "lastName", nullable = false, insertable = true, updatable = true)
	private String lastName;

	@Column(name = "passNumber", nullable = false, insertable = true, updatable = true)
	private String passNumber;

	@Column(name = "birthDay", nullable = false, insertable = true, updatable = true)
	private LocalDateTime birthDay;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
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
	 * @param lastName
	 *            the lastName to set
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
	 * @param passNumber
	 *            the passNumber to set
	 */
	public void setPassNumber(String passNumber) {
		this.passNumber = passNumber;
	}

	/**
	 * @return the birthDay
	 */
	public LocalDateTime getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay
	 *            the birthDay to set
	 */
	public void setBirthDay(LocalDateTime birthDay) {
		this.birthDay = birthDay;
	}

}
