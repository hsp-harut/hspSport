/**
 * 
 */
package am.chronograph.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for
 * objects needing this property.
 * 
 * @author tigran
 * 
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3621388844411429663L;
	/**
	 * The internal identifier. See # {@link #getId()} see {@link #setId(Long)}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;

	// properties() -----------------------------------------------------------
	/**
	 * Returns internal identifier.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the given internal identifier.
	 * 
	 * @param id
	 *            The identifier.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	// ~properties()------------------------------------------------------------

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
		if (!(obj instanceof AbstractEntity)) {
			return false;
		}
		final AbstractEntity othr = (AbstractEntity) obj;
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
