/**
 * 
 */
package am.chronograph.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import am.chronograph.domain.user.User;

/**
 * Simple JavaBean domain object with an id property and audit data. Used as a base class for
 * objects needing this properties.
 * 
 * @author tigran
 * 
 */
@MappedSuperclass
public abstract class AuditAwareEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7863032622421570282L;

	/**
	 * The internal identifier. See # {@link #getId()} see {@link #setId(Long)}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id = null;

	@Column(name = "created_at", nullable = false)
    private LocalDateTime createdDate;
	
	@Column(name = "modified_at", nullable = true)
	private LocalDateTime modifiedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "modified_by")
	private User modifiedBy;
	
	/**
	 * Returns the createdDate.
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * Sets the createdDate.
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * Sets the createdBy.
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * Sets the modifiedAt.
	 * @param modifiedAt the modifiedAt to set
	 */
	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
	/**
	 * Sets the modifiedBy.
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	/**
	 * Returns the createdBy.
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * Returns the modifiedAt.
	 * @return the modifiedAt
	 */
	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}
	
	/**
	 * Returns the modifiedBy.
	 * @return the modifiedBy
	 */
	public User getModifiedBy() {
		return modifiedBy;
	}
	
	/**
	 * Returns the id.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// you pick a hard-coded, randomly chosen, non-zero, odd number
		// ideally different for each class
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AuditAwareEntity)) {
			return false;
		}
		final AuditAwareEntity othr = (AuditAwareEntity) obj;
		return new EqualsBuilder().append(id, othr.id).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).toString();
	}

}
