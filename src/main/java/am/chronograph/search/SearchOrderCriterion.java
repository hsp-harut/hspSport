package am.chronograph.search;

import java.io.Serializable;

/**
 * Represents an Order property for SearchCriteria, composed from property name
 * and order direction.
 *
 * @author tigran
 */
public class SearchOrderCriterion implements Serializable {
	/**
	 * the serial id.
	 */
	private static final long serialVersionUID = 4026565535300510L;

	/**
	 * The order property.
	 */
	private String orderBy = null;

	/**
	 * The sort order ascending.
	 */
	private Boolean sortAsc = true;

	/**
	 * Initializes order criterion for the given property and ascending flag.
	 * 
	 * @param orderPropertyName
	 *            The order property.
	 * @param sortAsc
	 *            The ascending flag.
	 */
	public SearchOrderCriterion(final String orderPropertyName,
			final Boolean sortAsc) {
		this.orderBy = orderPropertyName;
		this.sortAsc = sortAsc;
	}
	
	/**
	 * Initializes order criterion for the given property in ascending order.
	 * 
	 * @param orderPropertyName
	 *            The order property.
	 */
	public SearchOrderCriterion(final String orderPropertyName) {
		this.orderBy = orderPropertyName;
	}

	/**
	 * Gets the order by.
	 *
	 * @return the order by
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the order by.
	 *
	 * @param orderBy the new order by
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Gets the sort asc.
	 *
	 * @return the sort asc
	 */
	public Boolean getSortAsc() {
		return sortAsc;
	}

	/**
	 * Sets the sort asc.
	 *
	 * @param sortAsc the new sort asc
	 */
	public void setSortAsc(Boolean sortAsc) {
		this.sortAsc = sortAsc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result + ((sortAsc == null) ? 0 : sortAsc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchOrderCriterion other = (SearchOrderCriterion) obj;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (sortAsc == null) {
			if (other.sortAsc != null)
				return false;
		} else if (!sortAsc.equals(other.sortAsc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SearchOrderCriterion [orderBy=" + orderBy + ", sortAsc="
				+ sortAsc + "]";
	}

	public Boolean isSortAsc() {
		return sortAsc;
	}


	
}
