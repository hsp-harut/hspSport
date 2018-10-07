package am.chronograph.dao.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import am.chronograph.domain.user.Role;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchCriteriaImpl;

/**
 * The implementation of Search API for {@link Role} domain object.
 *
 * @author tigran
 *
 */
public class RoleSearchCriteria extends SearchCriteriaImpl<Role> implements SearchCriteria<Role> {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1525503847670612623L;

	private Boolean hidden;

	/**
	 * Returns the hidden.
	 * 
	 * @return the hidden
	 */
	public Boolean getHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden.
	 * 
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
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
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(hidden).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RoleSearchCriteria)) {
			return false;
		}
		final RoleSearchCriteria othr = (RoleSearchCriteria) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(hidden, othr.hidden).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("hidden", hidden).toString();
	}

}
