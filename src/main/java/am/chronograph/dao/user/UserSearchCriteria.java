package am.chronograph.dao.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import am.chronograph.domain.user.User;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchCriteriaImpl;

/**
 * The implementation of Search API for {@link User} domain object.
 *
 * @author tigran
 *
 */
public class UserSearchCriteria extends SearchCriteriaImpl<User> implements SearchCriteria<User> {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1525503847670612623L;

	private Boolean disabled;

	private Boolean deleted;
	
	/**
	 * Returns the deleted.
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}
	
	/**
	 * Sets the deleted.
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	/**
	 * Returns the disabled.
	 * @return the disabled
	 */
	public Boolean getDisabled() {
		return disabled;
	}
	
	/**
	 * Sets the disabled.
	 * @param disabled the disabled to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
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
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(deleted).append(disabled).toHashCode();
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
		if (!(obj instanceof UserSearchCriteria)) {
			return false;
		}
		final UserSearchCriteria othr = (UserSearchCriteria) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(deleted, othr.deleted)
				.append(disabled, othr.disabled).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("deleted", deleted).append("disabled", disabled).toString();
	}

}
