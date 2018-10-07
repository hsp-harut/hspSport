package am.chronograph.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import am.chronograph.domain.AuditAwareEntity;

/**
 * The user domain object.
 * 
 * @author tigranbabloyan
 *
 */
@Entity
@Table(name = "users")
public class User extends AuditAwareEntity {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -3989080409950824629L;

	/**
	 * Users email
	 */
	@NotNull
	@Length(max = 255)
	@Column(name = "email", nullable = false, updatable = true)
	private String email;

	/**
	 * The users first name.
	 */
	@Column(name = "first_name", nullable = false)
	private String firstName;

	/**
	 * The users last name.
	 */
	@Column(name = "last_name", nullable = true)
	private String lastName;

	/**
	 * The users encrypted password.
	 */
	@Column(name = "password", nullable = true)
	private String password;

	/**
	 * The activation code of the new user.
	 */
	@Column(name = "activation_code", nullable = true)
	private String activationCode;

	/**
	 * The flag if the suer is deleted.
	 */
	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

	/**
	 * The flag if the user is disabled.
	 */
	@Column(name = "disabled", nullable = false)
	private boolean disabled = false;
	
	/**
	 * The language code.
	 */
	@Column(name = "language_code")
	private String languageCode;	

	/**
	 * The role of the user.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(inverseJoinColumns = {
			@JoinColumn(name = "role", nullable = false) }, name = "user_role", joinColumns = {
					@JoinColumn(nullable = false, name = "user") })
	private List<Role> roles = new ArrayList<>();

	public User() {
	}

	public User(User user) {
		setId(user.getId());
		setPassword(user.getPassword());
		setDeleted(user.isDeleted());
		setDisabled(user.isDisabled());
		setEmail(user.getEmail());
		setRoles(user.getRoles());
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setLanguageCode(user.getLanguageCode());		
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	public String getLanguageCode() {
		return languageCode;
	}
	
	@Transient
	public String getFullName() {
		StringBuilder nameBuilder = new StringBuilder();
		if (StringUtils.isNoneBlank(lastName)) {
			nameBuilder.append(lastName);
			nameBuilder.append(' ');
		}
		if (StringUtils.isNoneBlank(firstName)) {
			nameBuilder.append(firstName);
		}
		return nameBuilder.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.domain.AuditAwareEntity#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("email", email).toString();
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.domain.AuditAwareEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 39).appendSuper(super.hashCode()).append(email).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.domain.AuditAwareEntity#equals(java.lang.Object)
	 */
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
		User other = (User) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(email, other.email).isEquals();
	}
}
