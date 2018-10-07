package am.chronograph.web.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The login controller which defines the login page functionality. Also can be
 * used to getter the logged in user details.
 * 
 * @author tigranbabloyan
 */
@Named("loginController")
@RequestScoped
public class LoginController {

	/**
	 * The auth resolver.
	 */
	private static final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	/**
	 * Checks if user is already logged in and redirect to dashboard if needed.
	 * @return
	 */
	public String checkForStatus() {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
			return "/dashboard?faces-redirect=true";
		}
		return null;
	}

}
