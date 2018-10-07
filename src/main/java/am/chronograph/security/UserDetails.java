package am.chronograph.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import am.chronograph.config.SecurityConfig;
import am.chronograph.domain.user.Permission;
import am.chronograph.domain.user.User;
import am.chronograph.service.user.PermissionService;
import am.chronograph.util.SpringLocator;

/**
 * The user details which are store in the security context.
 * 
 * @author tigranbabloyan
 *
 */
public class UserDetails extends User implements org.springframework.security.core.userdetails.UserDetails {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 8130936934804687495L;
	
	
	public UserDetails(User user) {
		super(user);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (SecurityConfig.ADMIN_EMAIL.equals(getEmail())) {
			PermissionService service = SpringLocator.getContext().getBean(PermissionService.class);
			List<String> allPermissions = service.search(service.getEmptyCriteria()).getResult().stream().map(Permission::getKey).collect(Collectors.toList());
			allPermissions.add("ROLE_" + SecurityConfig.ROLE_ADMIN);
			return AuthorityUtils.createAuthorityList(allPermissions.toArray(new String[allPermissions.size()]));
		}
		List<String> permissionName = getRoles().stream().flatMap(r->r.getPermissions().stream()).map(Permission::getKey)
				.collect(Collectors.toList());
		permissionName.add("ROLE_" + SecurityConfig.ROLE_USER);
		return AuthorityUtils.createAuthorityList(permissionName.toArray(new String[permissionName.size()]));
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Transient
	@Override
	public String getUsername() {
		return getEmail();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return !isDeleted();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Transient
	@Override
	public boolean isEnabled() {
		return !isDisabled();
	}

}
