package am.chronograph.service;

import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchResult;

/**
 * The service interface which defines a way to add a search support. 
 * 
 * @author tigranbabloyan
 *
 * @param <T>
 */
public interface SearchAware<T> {
	/**
	 * Search by the given search criteria.
	 * @param criteria The criteria on which to search
	 * @return The search result for the given search criteria
	 */
	SearchResult<T> search (SearchCriteria<T> criteria);
	
	/**
	 * Returns the empty/default search criteria.
	 * @return the empty/default search criteria.
	 */
	SearchCriteria<T> getEmptyCriteria ();
	
}
