package am.chronograph.dao.user;

import am.chronograph.domain.user.Permission;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchCriteriaImpl;

/**
 * The implementation of Search API for {@link Permission} domain object.
 *
 * @author tigran
 *
 */
public class PermissionSearchCriteria extends SearchCriteriaImpl<Permission> implements
		SearchCriteria<Permission> {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 1525503847670612623L;
}
