package am.chronograph.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * A web servlet used to map the default urls.
 * 
 * @author tigranbabloyan
 *
 */
@WebServlet(urlPatterns = { "" })
public class WelcomeServlet extends HttpServlet {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -595782365577654858L;
	
	private static final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl ();

	/**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// forward to mypage.html
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
			response.sendRedirect(response.encodeRedirectURL("dashboard"));
		} else {
			response.sendRedirect(response.encodeRedirectURL("login"));
		}

	}

}
