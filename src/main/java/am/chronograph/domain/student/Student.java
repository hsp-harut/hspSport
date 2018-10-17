package am.chronograph.domain.student;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to 'student' table.
 * 
 * @author mona
 */
@Entity
@Table(name = "student")
public class Student extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 7921297159465513950L;
	
	@Column(name = "firstName", nullable = false, insertable = true, updatable = true)
	private String firstName;
	
	@Column(name = "lastName", nullable = false, insertable = true, updatable = true)
	private String lastName;
	
	@Column(name = "birthday", nullable = false, insertable = true, updatable = true)
	private LocalDateTime birthday;

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
	public LocalDateTime getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}
}
