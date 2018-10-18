package am.chronograph.domain.tournament;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import am.chronograph.domain.AuditAwareEntity;

/**
 * Domain object. ORM to 'tournamnet' table.
 * 
 * @author davitpetrosyan
 *
 */
@Entity
@Table(name = "tournamnet")
public class Tournament extends AuditAwareEntity {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 3579719179241184347L;

	@Column(name = "name", nullable = false, insertable = true, updatable = true)
	private String name;

	@Column(name = "startDate", nullable = false, insertable = true, updatable = true)
	private LocalDateTime startDate;

	@Column(name = "maxPartCount", nullable = false, insertable = true, updatable = true)
	private Integer maxPartCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Integer getMaxPartCount() {
		return maxPartCount;
	}

	public void setMaxPartCount(Integer maxPartCount) {
		this.maxPartCount = maxPartCount;
	}

}