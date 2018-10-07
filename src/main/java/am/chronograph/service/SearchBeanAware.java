package am.chronograph.service;

import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResultBean;

/**
 * The service interface which defines a way to add a search support. 
 * Need this new class to add one more property in the generic part...
 * @author tigranbabloyan
 *
 * @param <B> - bean class
 * @param <D> - domain class
 */
public interface SearchBeanAware<B, D> {
	/**
	 * Search by the given search criteria.
	 * @param criteria The criteria on which to search
	 * @return The search result for the given search criteria
	 */
    SearchResultBean<B> search (SearchCriteria<D> criteria);
	
	/**
	 * Returns the empty/default search criteria.
	 * @return the empty/default search criteria.
	 */
	SearchCriteria<D> getEmptyCriteria ();
	
}
