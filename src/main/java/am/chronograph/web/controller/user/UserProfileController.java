package am.chronograph.web.controller.user;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import am.chronograph.domain.user.User;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.ex.EmailSentFailedException;
import am.chronograph.security.UserDetails;
import am.chronograph.service.user.UserService;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;

@ViewScoped
@Named("userProfileController")
public class UserProfileController extends BaseController implements Serializable {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final BCryptPasswordEncoder ENCRYPTER = new BCryptPasswordEncoder();

	/**
	 * The user service.
	 */
	@Inject
	@Spring
	private transient UserService userService;
	
	/**
	 * The user service.
	 */
	@Inject
	@Spring
	private transient Authentication authentication;
	
	/**
	 * Selected user.
	 */
	private User selectedUser;
	
	/**
	 * Flag if in password edit mode.
	 */
	private boolean editPass = false;
	
	@PostConstruct
	public void init(){
		super.init();
		UserDetails userDetaiils = (UserDetails) authentication.getPrincipal();
		selectedUser = userService.getUserByEmail(userDetaiils.getEmail());
	}
	
	/**
	 * Changed the view to password edit mode.
	 */
	public void editMode(){
		editPass = !editPass;
	}
	
	/**
	 * Saves the role into the database.
	 */
	public void save() {
		UserDetails userDetaiils = (UserDetails) authentication.getPrincipal();
		User userToSave = userService.getUserByEmail(userDetaiils.getEmail());
		if(editPass){
			userToSave.setPassword(ENCRYPTER.encode(selectedUser.getPassword()));	
		}
		userToSave.setFirstName(selectedUser.getFirstName());
		userToSave.setLastName(selectedUser.getLastName());
		try {
			selectedUser = userService.saveUser(userToSave);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Your profile saved successfully."));
			userDetaiils.setFirstName(selectedUser.getFirstName());
			userDetaiils.setLastName(selectedUser.getLastName());
			editPass = false;
		} catch (ChronoDataException ex) {
			// this should not happen
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Error while saving user. Please try again."));
		} catch (EmailSentFailedException ex){
			// this should not happen
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Error while saving user. Email Server is not reachable."));
			// as emails are sent on initial creation set the id back to null
			selectedUser.setId(null);
		}
	}
	
	
	/**
	 * Returns the selectedUser.
	 * @return the selectedUser
	 */
	public User getSelectedUser() {
		return selectedUser;
	}
	
	/**
	 * Sets the selectedUser.
	 * @param selectedUser the selectedUser to set
	 */
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	/**
	 * Returns the editPass.
	 * @return the editPass
	 */
	public boolean isEditPass() {
		return editPass;
	}
	
	/**
	 * Sets the editPass.
	 * @param editPass the editPass to set
	 */
	public void setEditPass(boolean editPass) {
		this.editPass = editPass;
	}
}
