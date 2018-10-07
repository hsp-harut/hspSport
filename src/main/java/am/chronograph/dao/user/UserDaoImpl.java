package am.chronograph.dao.user;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import am.chronograph.config.SecurityConfig;
import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.HqlQueryBuilder;
import am.chronograph.domain.user.User;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultImpl;

/**
 * The Dao interface for accessing {@link User} domain object.
 *
 */
@Repository
class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.dao.user.UserDao#findByEmail(java.lang.String)
	 */
	@Override
	public User findByEmail(String email) {
		List<User> users = list("FROM User where email = :email", Collections.singletonMap("email", email));
		return users.isEmpty() ? null : users.get(0);
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.dao.user.UserDao#getUserByActivationCode(java.lang.String)
	 */
	@Override
	public User getUserByActivationCode(String activationCode) {
		List<User> users = list("FROM User where activationCode = :activationCode", Collections.singletonMap("activationCode", activationCode));
		return users.isEmpty() ? null : users.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.
	 * SearchCriteria)
	 */
	@Override
	public SearchResult<User> search(SearchCriteria<User> criteria) {
		HqlQueryBuilder queryBuilder = new HqlQueryBuilder("User", "user");
		final Map<String, Serializable> params = new HashMap<String, Serializable>();

		// Apply filters
		if (criteria instanceof UserSearchCriteria) {
			UserSearchCriteria uCriteria = (UserSearchCriteria) criteria;
			if (uCriteria.getDeleted() != null) {
				params.put("deleted", uCriteria.getDeleted());
				queryBuilder.addAnd("deleted", "=", ":deleted");
			}
			if (uCriteria.getDisabled() != null) {
				params.put("disabled", uCriteria.getDisabled());
				queryBuilder.addAnd("disabled", "=", ":disabled");
			}
		}
		
		// always hide admin user from global searches
		queryBuilder.addAnd("email", "!=", ":adminEmail");
		params.put("adminEmail", SecurityConfig.ADMIN_EMAIL);

		// Apply sorting
		for (final SearchOrderCriterion orderCriterion : criteria.getSearchOrderCriteria()) {
			queryBuilder.addSortProperty(orderCriterion.getOrderBy(), orderCriterion.isSortAsc());
		}
		return new SearchResultImpl<User>(queryBuilder.toSelectQuery(), queryBuilder.toCountQuery(), params, this,
				criteria);
	}

}
