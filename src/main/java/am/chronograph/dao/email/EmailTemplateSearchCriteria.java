package am.chronograph.dao.email;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import am.chronograph.domain.email.EmailTemplate;
import am.chronograph.domain.email.EmailTemplate.Type;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchCriteriaImpl;

/**
 * The implementation of Search API for {@link EmailTemplate} domain object.
 *
 * @author tigran
 *
 */
public class EmailTemplateSearchCriteria extends SearchCriteriaImpl<EmailTemplate>
		implements SearchCriteria<EmailTemplate> {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1525503847670612623L;

	private Type type;

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(type).toHashCode();
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
		if (!(obj instanceof EmailTemplateSearchCriteria)) {
			return false;
		}
		final EmailTemplateSearchCriteria othr = (EmailTemplateSearchCriteria) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(type, othr.type).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).toString();
	}

}
