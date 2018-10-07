package am.chronograph.dao.user;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.user.User;
import am.chronograph.search.SearchSupport;

/**
 * The Dao interface for accessing {@link User} domain object.
 *
 */
public interface UserDao extends GenericDao<User>, SearchSupport<User> {
	/**
	 * Returns the user for the given email. 
	 * @param email The email for which to return the user.
	 * @return  the user for the given email.
	 */
	User findByEmail (String email);
	
	/**
	 * Returns the user for the given activation code.
	 * @param activationCode The activation code.
	 * @return the user for the given activation code.
	 */
	User getUserByActivationCode (String activationCode);
}
