package am.chronograph.web.controller.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import am.chronograph.domain.user.Role;
import am.chronograph.domain.user.User;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.ex.EmailSentFailedException;
import am.chronograph.service.user.RoleService;
import am.chronograph.service.user.UserService;
import am.chronograph.web.controller.base.BaseController;
import am.chronograph.web.integration.Spring;
import am.chronograph.web.model.SearchAwareLazyModel;

@ViewScoped
@Named("userController")
public class UserController extends BaseController implements Serializable {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final BCryptPasswordEncoder ENCRYPTER = new BCryptPasswordEncoder();

	/**
	 * Thr role service.
	 */
	@Inject
	@Spring
	private transient RoleService roleService;
	
	/**
	 * The user service.
	 */
	@Inject
	@Spring
	private transient UserService userService;	
	
	/**
	 * The lazy model for showing roles in a data table.
	 */
	private LazyDataModel<User> users;
	
	/**
	 * The permission list to be used in the picklist.
	 */
	private DualListModel<Role> roles;
	
	/**
	 * Flag if in password edit mode.
	 */
	private boolean editPass = false;	

	/**
	 * Selected user.
	 */
	private User selectedUser;
	
	@PostConstruct	
	public void init(){
		super.init();
		users = new SearchAwareLazyModel<User>(userService);
		reset();
	}
	
	/**
	 * Saves the role into the database.
	 */	
	public void save() {
		selectedUser.setRoles(roles.getTarget());
		User userToSave = null;
		Long userId = selectedUser.getId();
		if (userId != null) {
			selectedUser.setId(null);
			userToSave = userService.getUserById(userId);
			if(editPass){
				userToSave.setActivationCode(null);
				userToSave.setPassword(ENCRYPTER.encode(selectedUser.getPassword()));
			}
		}
		if (userToSave == null) {
			userToSave = new User();
		}
		BeanUtils.copyProperties(selectedUser, userToSave, "id", "password");
		try {
			selectedUser = userService.saveUser(userToSave);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User saved successfully."));
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
	
	private void reset(){
		selectedUser = new User();
		roles = new DualListModel<Role>(roleService.search(roleService.getEmptyCriteria()).getResult(), new ArrayList<>());
		editPass = false;
	}
	
	/**
	 * Is called when user clicks on "create user" button. Just resets all the data.
	 */
	public void newUser() {
		reset();
		
		scrollTo("rootUserInputContainer");
	}
	
	/**
	 * Disables the user and resets the form.
	 * @param user
	 */	
	public void disableUser(User user) {
		User cleanUser = userService.getUserByEmail(user.getEmail());
		cleanUser.setDisabled(true);
		userService.saveUser(cleanUser);
		reset();
	}
	
	/**
	 * Enable the user and resets the form.
	 * @param user
	 */	
	public void enableUser(User user) {
		User cleanUser = userService.getUserByEmail(user.getEmail());
		cleanUser.setDisabled(false);
		userService.saveUser(cleanUser);
		reset();
	}
	
	/**
	 * Enable the user and resets the form.
	 * @param user
	 */	
	public void sendActivationUser(User user) {
		try {
			User cleanUser = userService.getUserByEmail(user.getEmail());
			userService.sendActivation(cleanUser);
			FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Activation email send successfully."));
		} catch (EmailSentFailedException ex){
			// this should not happen
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Error while re-sending activation email. Email Server is not reachable."));
		}
	}
	
	/**
	 * Called when use selects a user in the data table.
	 * @param event The select event.
	 */
	public void onRowSelect(SelectEvent event) {
		User user = (User) event.getObject();
		List<Role> sourceRoles = roleService.search(roleService.getEmptyCriteria()).getResult();
		sourceRoles.removeAll(user.getRoles());
		roles = new DualListModel<Role>(sourceRoles, user.getRoles());
		editPass = false;
		scrollTo("rootUserInputContainer");
		RequestContext.getCurrentInstance().reset("userUpdateForm");
	}
	
	/**
	 * Validates the role name. For now just checks if the role with the same name exists in database.
	 */
	public void validateEmail(FacesContext context, UIComponent comp, Object value) {
		if (selectedUser != null && selectedUser.getId() != null) {
			return;
		}
		String name = (String) value;
		if (StringUtils.isNoneBlank(name) && userService.getUserByEmail(name) != null) {
			((UIInput) comp).setValid(false);
			FacesMessage message = new FacesMessage("User with this email already exists");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(comp.getClientId(context), message);
		}
	}

	/**
	 * Called when use un-selects a user in the data table.
	 * @param event The select event.
	 */
	public void onRowUnselect(UnselectEvent event) {
		reset();
	}
	
	/**
	 * Changed the view to password edit mode.
	 */
	public void editMode(){
		editPass = !editPass;
	}
	
	/**
	 * Returns the roles.
	 * @return the roles
	 */
	public DualListModel<Role> getRoles() {
		return roles;
	}
	
	/**
	 * Sets the roles.
	 * @param roles the roles to set
	 */
	public void setRoles(DualListModel<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * Returns the users.
	 * @return the users
	 */
	public LazyDataModel<User> getUsers() {
		return users;
	}
	
	/**
	 * Sets the users.
	 * @param users the users to set
	 */
	public void setUsers(LazyDataModel<User> users) {
		this.users = users;
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
