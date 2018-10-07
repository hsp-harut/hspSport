package am.chronograph.web.controller.admin;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import am.chronograph.service.user.UserService;
import am.chronograph.web.integration.Spring;

@ViewScoped
@Named("forgotController")
public class UserPassForgotController implements Serializable{
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1087198011564895981L;

	private String email;
	
	@Inject
	@Spring
	private transient UserService userService;
	
	public String sendForgot(){
		userService.sendForgotEmail(email);
		return "/login?faces-redirect=true&forgot=true"; 
	}
	
	/**
	 * Returns the email.
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
