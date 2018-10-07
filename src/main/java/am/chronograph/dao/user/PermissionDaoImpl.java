package am.chronograph.dao.user;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.HqlQueryBuilder;
import am.chronograph.domain.user.Permission;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultImpl;

/**
 * The implementation of Dao interface for accessing {@link Permission} domain
 * object.
 */
@Repository
class PermissionDaoImpl extends GenericDaoImpl<Permission> implements PermissionDao {

	/* (non-Javadoc)
	 * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.SearchCriteria)
	 */
	@Override
	public SearchResult<Permission> search(SearchCriteria<Permission> criteria) {
		HqlQueryBuilder queryBuilder = new HqlQueryBuilder("Permission", "perm");
		// Apply sorting
		for (final SearchOrderCriterion orderCriterion : criteria.getSearchOrderCriteria()) {
			queryBuilder.addSortProperty(orderCriterion.getOrderBy(), orderCriterion.isSortAsc());
		}
		return new SearchResultImpl<Permission>(queryBuilder.toSelectQuery(), queryBuilder.toCountQuery(), new HashMap<>(), this,
				criteria);
	}

}
