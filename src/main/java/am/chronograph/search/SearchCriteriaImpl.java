package am.chronograph.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Adapter implementation of SearchCriteria interface, specific entity
 * implementations should extend this class.
 * 
 * <p/>
 * NOTE: the subclass should overwrite <code>equals</code> and
 * <code>hashcode</code> methods if it introduced its own property.
 * 
 * @author tigran
 * @param <T>
 */
public class SearchCriteriaImpl<T> implements SearchCriteria<T>, Serializable {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 5095290823043481926L;
	/**
	 * The page number.
	 */
	private int page = 0;
	/**
	 * The page size.
	 */
	private int pageSize = -1;

	/**
	 * The order criteria.
	 */
	private List<SearchOrderCriterion> searchOrderCriteria = new ArrayList<SearchOrderCriterion>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.virtualsolution.commons.search.SearchCriteria#getPage()
	 */
	public int getPage() {
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.virtualsolution.commons.search.SearchCriteria#getPageSize()
	 */
	public int getPageSize() {
		return pageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.virtualsolution.commons.search.SearchCriteria#setPage(int)
	 */
	public void setPage(int firstResult) {
		this.page = firstResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.virtualsolution.commons.search.SearchCriteria#setPageSize(int)
	 */
	public void setPageSize(int maxResults) {
		this.pageSize = maxResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.virtualsolution.commons.search.SearchCriteria#addSearchOrderCriteria
	 * (de.virtualsolution.commons.search.SearchOrderCriterion[])
	 */
	public void addSearchOrderCriteria(SearchOrderCriterion... soc) {
		searchOrderCriteria.addAll(Arrays.asList(soc));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.virtualsolution.commons.search.SearchCriteria#getSearchOrderCriteria()
	 */
	public List<SearchOrderCriterion> getSearchOrderCriteria() {
		return searchOrderCriteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + page;
		result = prime * result + pageSize;
		result = prime
				* result
				+ ((searchOrderCriteria == null) ? 0 : searchOrderCriteria
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SearchCriteriaImpl))
			return false;
		final SearchCriteriaImpl<?> other = (SearchCriteriaImpl<?>) obj;
		if (page != other.page)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (searchOrderCriteria == null) {
			if (other.searchOrderCriteria != null)
				return false;
		} else if (!searchOrderCriteria.equals(other.searchOrderCriteria))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("page", page)
				.append("pageSize", pageSize)
				.append("searchOrderCriteria", searchOrderCriteria).toString();
	}
}
