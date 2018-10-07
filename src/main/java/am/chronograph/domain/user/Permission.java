package am.chronograph.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import am.chronograph.domain.AbstractEntity;

/**
 * The permission domain object. Store permission data.
 * 
 * @author tigranbabloyan
 *
 */
@Entity
@Table(name = "permissions")
public class Permission extends AbstractEntity {

	/**
	 * Te serial id.
	 */
	private static final long serialVersionUID = -3908106303806550845L;

	/**
	 * The name of permission.
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * The key of the permission.
	 */
	@Column(name = "key", nullable = false)
	private String key;

	/**
	 * The i18n key for permission.
	 */
	@Column(name = "ikey", nullable = false)
	private String iKey;

	/**
	 * Returns the iKey.
	 * 
	 * @return the iKey
	 */
	public String getiKey() {
		return iKey;
	}

	/**
	 * Sets the iKey.
	 * 
	 * @param iKey
	 *            the iKey to set
	 */
	public void setiKey(String iKey) {
		this.iKey = iKey;
	}

	/**
	 * Returns the key.
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 * 
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("name", name).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 39).appendSuper(super.hashCode()).append(key).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Permission other = (Permission) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(key, other.key).isEquals();
	}
}
