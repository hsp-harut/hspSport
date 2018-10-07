package am.chronograph.search;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of the interface {@link SearchResult}. <p/> By
 * which a Client (probably JSF Controller, Component or such) can navigate to
 * the result and scroll page by page <p/> This implementation uses cached
 * SearchCriteria and SearchSupport for navigating with result pages
 * 
 * @author tigran
 * @param <T>
 */
public class SearchResultImpl<T> implements SearchResult<T>, Serializable{
	
	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -8437867882146812619L;

	/**
	 * The logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SearchResultImpl.class);

	/**
	 * The page result.
	 */
	private List<T> result = null;

	/**
	 * The search support associated with the a result.
	 */
	private transient SearchSupport<T> searchSupport = null;
	
	/**
	 * The search criteria associated with the a result.
	 */
	private SearchCriteria<T> searchCriteria = null;
	
	/**
	 * The page number that should be retrieved.
	 */
	private int currentPage = -1;

	/**
	 * The page size.
	 */
	private int pageSize = -1;

	/**
	 * The number of elements that match the query.
	 */
	private int elementCount = -1;

	/**
	 * The number of pages. When calculated it has values &gt;= 0, otherwise its
	 * value is -1.
	 */
	private int pageCount = -1;

	/**
	 * The select query.
	 */
	private String selectQuery = null;

	/**
	 * The count query.
	 */
	private String countQuery = null;

	/**
    * The parameters.
    */
   private Map<String, Object> parameters = new HashMap<String, Object>();
   
   /**
    * Need to populate empty list...
    */
   public SearchResultImpl() {
       result = Collections.emptyList();
       elementCount = 0;
   }

   /**
    * Constructs a search result for the given queries, parameters,
    * search support, criteria and result cache.
    * 
    * @param selectQuery The select query or query name.
    * @param countQuery The count query or query name.
    * @param parameters The query parameters.
    * @param searchSupport The search support.
    * @param searchCriteria The search criteria.
    * 
    * @see SearchSupport
    * @see SearchCriteria
    */
   public SearchResultImpl(
         final String selectQuery, final String countQuery,
         final Map<String, Serializable> parameters,
         final SearchSupport<T> searchSupport,
         final SearchCriteria<T> searchCriteria) {
      this.selectQuery = selectQuery;
      this.countQuery = countQuery;
      if(parameters != null) {
    	  for(Entry<String, Serializable> entry : parameters.entrySet()) {
    		  this.parameters.put(entry.getKey(), entry.getValue());
    	  }
      }
      this.searchSupport = searchSupport;
      
      this.currentPage = searchCriteria.getPage();
      this.pageSize = searchCriteria.getPageSize();
      
      this.searchCriteria = searchCriteria;
      
      if(currentPage <= 0) {
         currentPage = 1;
      }
      
      retrieve();
   }
   
	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#getCurrentPage()
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#getPageCount()
	 */
	public int getPageCount() {
		return pageCount;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#getTotalResultCount()
	 */
	public int getTotalResultCount() {
		return elementCount;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#getPageSize()
	 */
	public int getPageSize() {
		return pageSize;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#getResult()
	 */
	public List<T> getResult() {
		return result;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#hasNextPage()
	 */
	public boolean hasNextPage() {
		return getPageCount() > getCurrentPage();
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#hasPreviousPage()
	 */
	public boolean hasPreviousPage() {
		return currentPage > 1;
	}

	// search methods

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#nextPage()
	 */
	public List<T> nextPage() {
		currentPage++;
		retrieve();
		return result;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#previousPage()
	 */
	public List<T> previousPage() {
		currentPage--;
		retrieve();
		return result;
	}

	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#page(int)
	 */
	public List<T> page(final int pageNumber) {
		currentPage = pageNumber;
		retrieve();
		return result;
	}
	
	/* (non-Javadoc)
	 * @see de.virtualsolution.commons.search.SearchResult#getSearchCriteria()
	 */
	public SearchCriteria<T> getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * Refresh SearchResult according to the criteria.
	 */
	protected void retrieve() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Retrieving result for query {}, length={}", selectQuery, pageSize);	
		}

		// do retrieve

		// if the page size is not specified (is -1) then there should be just
		// one page and all the elements should be in the result
		if (pageSize == -1) {
		   result = searchSupport.list(selectQuery, parameters, 0, -1);
			
			elementCount = result.size();
			currentPage = 1;
			pageCount = 1;
		} else {
			// calculate the number of pages
			if (pageCount == -1) {
				retrieveCount();
			}

			// fit the page number
			if (currentPage > pageCount) {
				currentPage = pageCount;
			} else if (currentPage < 1) {
				currentPage = 1;
			}

			// retrieve the result
			if (elementCount > 0) {
				result = searchSupport.list(
				      selectQuery, parameters,
				      (currentPage - 1) * pageSize, pageSize);
			} else if (elementCount == 0) {
				result = Collections.emptyList();
			}

		}
	}

	/**
	 * Retrieves the element count.
	 */
	private void retrieveCount() {
	   elementCount = searchSupport.count(countQuery, parameters);

		// get the number of pages
		pageCount = (int) Math.ceil((double) elementCount / (double) pageSize);
		if(LOG.isDebugEnabled()) {
			LOG.debug("There are {} results for query {}", elementCount, countQuery);
		}
	}

	// ~search methods
}

