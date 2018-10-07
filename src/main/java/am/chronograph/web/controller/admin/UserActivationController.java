package am.chronograph.web.controller.admin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import am.chronograph.domain.user.User;
import am.chronograph.service.user.UserService;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

@ViewScoped
@Named("activationController")
public class UserActivationController extends BaseController implements Serializable{
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1087198011564895981L;

	/**
	 * The password encrypter.
	 */
	private static final BCryptPasswordEncoder ENCRYPTER = new BCryptPasswordEncoder();
	
	@Inject
	@Param
	private String code;
	
	@Inject
	@Spring
	private transient UserService userService;
	
	private User user;
	
	@PostConstruct
	public void init(){
		user = new User();
		User dbUser = userService.getUserByActivationCode(code);
		if(dbUser == null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Activation Code Not Found"));
			return;
		}
		user.setEmail(dbUser.getEmail());
	}
	
	public String activateUser(){
		User dbUser = userService.getUserByActivationCode(code);
		if(dbUser == null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Activation Code Not Found"));
			return null;
		}
		dbUser.setPassword(ENCRYPTER.encode(user.getPassword()));
		dbUser.setActivationCode(null);
		userService.saveUser(dbUser);
		return "/login?faces-redirect=true&activation=true"; 
	}
	
	
	/**
	 * Returns the user.
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	
	/**
	 * Sets the user.
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Returns the activationCode.
	 * @return the activationCode
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the activationCode.
	 * @param activationCode the activationCode to set
	 */
	public void setCode(String activationCode) {
		this.code = activationCode;
	}
}
