package am.chronograph.domain.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import am.chronograph.domain.AuditAwareEntity;

/**
 * The role domain object. Store role data.
 * 
 * @author tigranbabloyan
 *
 */
@Entity
@Table(name = "roles")
public class Role extends AuditAwareEntity {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 2022320478631138494L;

	/**
	 * The name of the permission
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * The users of the role.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(inverseJoinColumns = {
			@JoinColumn(name = "user", nullable = false) }, name = "user_role", joinColumns = {
					@JoinColumn(nullable = false, name = "role") })
	private Set<User> users = new HashSet<>();

	/**
	 * The permissions assigned to the role.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(inverseJoinColumns = {
			@JoinColumn(name = "permission", nullable = false) }, name = "role_permission", joinColumns = {
					@JoinColumn(nullable = false, name = "role") })
	private Set<Permission> permissions = new HashSet<>();

	/**
	 * If the role hidden from the UI.
	 */
	@Column(name = "hidden", nullable = false)
	private boolean hidden;

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the users.
	 * 
	 * @param users
	 *            the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * Returns the users.
	 * 
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
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
	 * Returns the permissions.
	 * 
	 * @return the permissions
	 */
	public Set<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * Sets the permissions.
	 * 
	 * @param permissions
	 *            the permissions to set
	 */
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Returns the hidden.
	 * 
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden.
	 * 
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("name", name).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 39).appendSuper(super.hashCode()).append(name).toHashCode();
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
		Role other = (Role) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(name, other.name).isEquals();
	}

}
