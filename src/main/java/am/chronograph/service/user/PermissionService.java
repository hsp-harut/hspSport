package am.chronograph.service.user;

import am.chronograph.domain.user.Permission;
import am.chronograph.service.SearchAware;

/**
 * Service interface which defines all the methods for working with {@link Permission}
 * domain object.
 * 
 * @author tigranbabloyan
 *
 */
public interface PermissionService extends SearchAware<Permission>{
	/**
	 * Return the permission for the given id.
	 * @param id The internal identifier for the given id.
	 * @return  the permission for the given id.
	 */
	Permission getById (Long id);
}
