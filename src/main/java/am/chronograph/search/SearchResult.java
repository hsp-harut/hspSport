package am.chronograph.search;

import java.util.List;

/**
 * Represents SearchResult by which a Client
 * (probably Struts2 Action, JSF Controller, Component or such)
 * can navigate to the result and scroll page by page.
 * 
 * @author tigran
 * @param <T>
 */
public interface SearchResult<T> extends Cloneable {
	/**
	 * Returns the page size.
	 * 
	 * @return The page size.
	 */
	int getPageSize();

	/**
	 * Returns number of available pages.
	 * 
	 * @return The number of pages.
	 */
	int getPageCount();
	
	/**
	 * Returns total result count.
	 * 
	 * @return The total result count.
	 */
	int getTotalResultCount();

	/**
	 * Returns the current page number.
	 * 
	 * @return The current page number.
	 */
	int getCurrentPage();

	/**
	 * Returns the results of current page.
	 * 
	 * @return The page results.
	 */
	List<T> getResult();

	/**
	 * Retrieves if there is a next page.
	 * 
	 * @return If there is a next page.
	 */
	boolean hasNextPage();

	/**
	 * Retrieves if there is a previous page.
	 * 
	 * @return If there is a previous page. 
	 */
	boolean hasPreviousPage();

	/**
	 * Moves to the next page and retrieves the results.
	 * 
	 * @return The next page results.
	 */
	List<T> nextPage();

	/**
	 * Moves to the previous page and retrieves the results.
	 * 
	 * @return The previous page results.
	 */
	List<T> previousPage();

	/**
	 * Moves to the given page and retrieves the page results.
	 * 
	 * @param pagePosition The page number.
	 * @return The page results.
	 */
	List<T> page(int pagePosition);
	
	/**
	 * The search criteria associated with the result.
	 * 
	 * @return The search criteria. 
	 */
	SearchCriteria getSearchCriteria();
}

