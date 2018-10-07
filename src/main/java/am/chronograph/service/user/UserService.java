package am.chronograph.service.user;

import java.util.List;

import javax.faces.model.SelectItem;

import am.chronograph.domain.user.User;
import am.chronograph.service.SearchAware;

/**
 * Service interface which defines all the methods for working with {@link User}
 * domain object.
 * 
 * @author tigranbabloyan
 *
 */
public interface UserService extends SearchAware<User> {

	/**
	 * Saves or updated the user in database.
	 * @param user The user to save.
	 * @return Saved domain object.
	 */
	User saveUser (User user);

	/**
	 * Returns the user with the given internal identifier.
	 * @param id The internal identifier of the user domain object.
	 * @return the user with the given internal identifier.
	 */
	User getUserById (Long id);

	/**
	 * Returns the user with the given email.
	 * @param email The email for which to return the user domain object.
	 * @return the user with the given email.
	 */
	User getUserByEmail (String email);
	
	/**
	 * Send the user activation email.
	 * @param user The user whom to send the activation email.
	 */
	void sendActivation(User user);
	
	/**
	 * Returns the user for the given activation code.
	 * @param activationCode The activation code.
	 * @return the user for the given activation code.
	 */
	User getUserByActivationCode (String activationCode);

	/**
	 * Get SelectItem list of all users...
	 * @return
	 */
	List<SelectItem> getAllUsers(boolean disabledItems);
	
	/**
	 * Sends the forgot password to the suer with the given email.
	 * @param email The email to which to send the email.
	 */
	void sendForgotEmail (String email);
}
