package am.chronograph.dao.user;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.HqlQueryBuilder;
import am.chronograph.domain.user.Role;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultImpl;
import am.chronograph.search.SearchSupport;

/**
 * The Dao interface for accessing {@link Role} domain object.
 *
 */
@Repository
class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao, SearchSupport<Role> {
	
	/* (non-Javadoc)
	 * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResult<Role> search(SearchCriteria<Role> criteria) {
		HqlQueryBuilder queryBuilder = new HqlQueryBuilder("Role", "role");
		queryBuilder.setFetchAllProperties(true);
		queryBuilder.addLeftJoinFetch("role.users", "u");
		queryBuilder.addLeftJoinFetch("role.permissions", "p");
		final Map<String, Serializable> params = new HashMap<String, Serializable>();

		// Apply filters
		if (criteria instanceof RoleSearchCriteria) {
			RoleSearchCriteria uCriteria = (RoleSearchCriteria) criteria;
			if (uCriteria.getHidden() != null) {
				params.put("hidden", uCriteria.getHidden());
				queryBuilder.addAnd("role.hidden", "=", ":hidden");
			}
		}

		// Apply sorting
		for (final SearchOrderCriterion orderCriterion : criteria.getSearchOrderCriteria()) {
			queryBuilder.addSortProperty(orderCriterion.getOrderBy(), orderCriterion.isSortAsc());
		}
		return new SearchResultImpl<Role>(queryBuilder.toSelectQuery(), queryBuilder.toCountQuery(), params, this,
				criteria);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.dao.user.RoleDao#findByName(java.lang.String)
	 */
	@Override
	public Role findByName(String name) {
		List<Role> roles = list("FROM Role where name = :name", Collections.singletonMap("name", name));
		return roles.isEmpty() ? null : roles.get(0);
	}

}
