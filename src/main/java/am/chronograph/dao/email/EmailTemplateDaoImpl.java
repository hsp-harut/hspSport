package am.chronograph.dao.email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.HqlQueryBuilder;
import am.chronograph.domain.email.EmailTemplate;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultImpl;

/**
 * The implementation of Dao interface for accessing {@link EmailTemplate}
 * domain object.
 */
@Repository
class EmailTemplateDaoImpl extends GenericDaoImpl<EmailTemplate> implements EmailTemplateDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.
	 * SearchCriteria)
	 */
	@Override
	public SearchResult<EmailTemplate> search(SearchCriteria<EmailTemplate> criteria) {
		HqlQueryBuilder queryBuilder = new HqlQueryBuilder("EmailTemplate", "template");
		final Map<String, Serializable> params = new HashMap<String, Serializable>();

		// Apply filters
		if (criteria instanceof EmailTemplateSearchCriteria) {
			EmailTemplateSearchCriteria uCriteria = (EmailTemplateSearchCriteria) criteria;
			if (uCriteria.getType() != null) {
				params.put("type", uCriteria.getType());
				queryBuilder.addAnd("type", "=", ":type");
			}

		}

		// Apply sorting
		for (final SearchOrderCriterion orderCriterion : criteria.getSearchOrderCriteria()) {
			queryBuilder.addSortProperty(orderCriterion.getOrderBy(), orderCriterion.isSortAsc());
		}
		return new SearchResultImpl<EmailTemplate>(queryBuilder.toSelectQuery(), queryBuilder.toCountQuery(), params,
				this, criteria);
	}

}
