package am.chronograph.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import am.chronograph.domain.user.Role;
import am.chronograph.domain.user.User;
import am.chronograph.service.user.UserService;

/**
 * The implementation of the {@link UserDetailsService} based on email based
 * user load.
 * 
 * @author tigranbabloyan
 *
 */
@Service
public class UserDeatailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userRepository;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		user.getRoles().stream().map(Role::getPermissions).forEach(Collection::size);
		return new am.chronograph.security.UserDetails(user);
	}

}
