package am.chronograph.domain.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to 'country' table.
 * 
 * @author HARUT
 */
@Entity
@Table(name = "country")
public class Country extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -1047149246142974966L;
	
	@Column(name = "name", nullable = false, insertable = true, updatable = true)
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}		
}
