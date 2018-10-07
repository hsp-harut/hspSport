package am.chronograph.dao.user;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.user.Role;
import am.chronograph.search.SearchSupport;

/**
 * The Dao interface for accessing {@link Role} domain object.
 *
 */
public interface RoleDao extends GenericDao<Role>, SearchSupport<Role> {
	/**
	 * Returns the role for the given name.
	 * @param name The name of the role.
	 * @return  the role for the given name.
	 */
	Role findByName (String name);

}
