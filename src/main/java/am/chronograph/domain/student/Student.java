package am.chronograph.domain.student;

import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * 
 * @author lusine
 *
 */
@Entity
@Table(name = "student")
public class Student extends AuditAwareEntity{
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String eMail;
	
	

}
