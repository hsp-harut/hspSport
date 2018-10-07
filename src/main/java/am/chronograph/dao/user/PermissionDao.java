package am.chronograph.dao.user;

import am.chronograph.dao.GenericDao;
import am.chronograph.domain.user.Permission;
import am.chronograph.search.SearchSupport;

/**
 * The Dao interface for accessing {@link Permission} domain object.
 *
 */
public interface PermissionDao extends GenericDao<Permission>, SearchSupport<Permission>{

}
