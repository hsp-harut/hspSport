package am.chronograph.service.user;
import java.util.List;

import javax.faces.model.SelectItem;

import am.chronograph.domain.user.Role;
import am.chronograph.ex.ChronoDataException;
import am.chronograph.service.SearchAware;

/**
 * Service interface which defines all the methods for working with {@link Role}
 * domain object.
 * 
 * @author tigranbabloyan
 *
 */
public interface RoleService extends SearchAware<Role>{

	/**
	 * Save role into database.
	 * @param role The role to save.
	 * @return Saved role.
	 */
	Role saveRole(Role role);

	/**
	 * Deletes the role from the database.
	 * 
	 * @throws ChronoDataException if role has users assigned.
	 * @param role The role to delete.
	 */
	void deleteRole(Role role);
	
	/**
	 * Returns the role for the given name.
	 * @param name The name of the role to return.
	 * @return the role for the given name.
	 */
	Role findByName(String name);
	
	/**
	 * Returns the role for the given name.
	 * @param name The name of the role to return.
	 * @return the role for the given name.
	 */
	Role getById(Long id);
	
	/**
	 * Get SelectItem list of the roles...
	 * @return
	 */
	List<SelectItem> getRoleItems();
	
	/**
	 * Get SelectItem list of the users which have a given role...
	 * @param roleId
	 * @return
	 */
	List<SelectItem> getRoleUserItems(Long roleId);
}
