package am.chronograph.search;

import java.util.List;
import java.util.Map;

/**
 * Defines an interface to support SearchCriteria API.
 * 
 * @author tigran
 * @param <T>
 */
public interface SearchSupport<T> {
   /**
    * Retrieves paginated result for the given criteria.
    * 
    * @param criteria The criteria to get search result for.
    * @return The search result.
    * 
    * @see SearchCriteria
    * @see SearchResult
    */
   SearchResult<T> search(SearchCriteria<T> criteria);

   /**
    * Retrieves the results for the given query.
    * 
    * @param query The query or query name.
    * @param parameters The parameters.
    * @param firstResult The first result number.
    * @param maxResults The max results.
    * @return The results.
    */
   List<T> list(String query, Map<String, Object> parameters,
         final int firstResult, final int maxResults);

   /**
    * Retrieves the result count for the given query.
    * 
    * @param query The query or query name.
    * @param parameters The parameters.
    * @return The result count.
    */
   int count(String query, Map<String, Object> parameters);
}
