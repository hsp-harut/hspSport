package am.chronograph.dao;

import org.springframework.util.StringUtils;

/**
 * The <code>HqlQueryBuilder</code> should be used to build easily HQL queries.
 *
 * <p>
 * Here are some code samples: <code><pre>
 *     // create the builder
 *     final HqlQueryBuilder qb = new HqlQueryBuilder("USer", "obj");
 *     
 *     // add some conditions
 *     qb.addAnd("obj.country", "=", ":country");
 *     qb.addAnd("obj.companyName", "like", ":name");
 *     
 *     // add some conditions with relations
 *     qb.addInnerJoin("obj.geoEntities", "geo");
 *     qb.addAnd("geo", "in", "(:geoEntities)");
 *     
 *     // you may disable fetching of all lazy properties as well
 *     qb.setFetchAllProperties(false);
 * </pre></code>
 * 
 * @author tigran
 * 
 */
public class HqlQueryBuilder {
	/**
	 * The main query part.
	 */
	private StringBuilder qryBuilder = new StringBuilder();
	/**
	 * The join query part.
	 */
	private StringBuilder joinBuilder = new StringBuilder();

	/**
	 * The part under `where` condition.
	 */
	private StringBuilder whereBuilder = new StringBuilder();
	/**
	 * The part under `order by` condition.
	 */
	private StringBuilder orderBuilder = new StringBuilder();
	/**
	 * The part under `group by` part.
	 */
	private StringBuilder groupBuilder = new StringBuilder();

	/**
	 * The type alias.
	 */
	private String alias = null;
	/**
	 * The object to select.
	 */
	private String select = null;
	
	/**
	 * Do we need the join in select query.
	 */
	private boolean joinForCount = false;

	/**
	 * If the lazy properties should be fetched or not.
	 */
	private boolean fetchAllProperties = false;

	/**
	 * Processes the given text value for participation in the (HQL) query.
	 * <p>
	 * The value is trimmed and '%'
	 *
	 * @param value
	 *            The text value to process.
	 * @return The processed value.
	 */
	public static String prepareValue(final String value) {
		if (value == null || "".equals(value)) {
			return "";
		}

		return value.trim().replace("\\%", "");
	}

	/**
	 * Initialises a <code>HqlQueryBuilder</code> for the given type and type
	 * alias.
	 *
	 * @param type
	 *            The type to create query for.
	 * @param alias
	 *            The alias for the type.
	 */
	public HqlQueryBuilder(final String type, final String alias) {
		super();

		this.alias = alias;
		this.select = alias;

		qryBuilder.append("from ").append(type).append(' ').append(alias);
	}

	/**
	 * Initialises a <code>HqlQueryBuilder</code> for the given type and type
	 * alias that will select the given object.
	 *
	 * @param type
	 *            The type to create query for.
	 * @param alias
	 *            The alias for the type.
	 * @param select
	 *            The select query that overrides the default one.
	 */
	public HqlQueryBuilder(final String type, final String alias,
			final String select) {
		super();

		this.alias = alias;
		this.select = select;

		qryBuilder.append("from ").append(type).append(' ').append(alias);
	}

	/**
	 * Retrieves if the flag fetchAllProperties is on or off.
	 *
	 * @return If the flag fetchAllProperties is on or off.
	 */
	public boolean isFetchAllProperties() {
		return fetchAllProperties;
	}

	/**
	 * Sets the given fetchAllProperties flag.
	 *
	 * @param fetchAllProperties
	 *            The fetchAllProperties flag value.
	 */
	public void setFetchAllProperties(final boolean fetchAllProperties) {
		this.fetchAllProperties = fetchAllProperties;
	}
	
	/**
	 * Sets the joinForCount.
	 * @param joinForCount the joinForCount to set
	 */
	public void setJoinForCount(boolean joinForCount) {
		this.joinForCount = joinForCount;
	}

	/**
	 * Adds INNER JOIN relation to the (HQL) query.
	 *
	 * @param property
	 *            The property to join.
	 * @param alias
	 *            The alias for the joined property.
	 */
	public void addInnerJoin(final String property, final String alias) {
		joinBuilder.append(" inner join ").append(property).append(' ')
				.append(alias);
	}
	
	/**
	 * Adds INNER JOIN relation to the (HQL) query.
	 *
	 * @param property
	 *            The property to join.
	 * @param alias
	 *            The alias for the joined property.
	 */
	public void addInnerJoinFetch(final String property, final String alias) {
		joinBuilder.append(" inner join fetch").append(property).append(' ')
				.append(alias);
	}

	/**
	 * Adds LEFT JOIN relation to the (HQL) query.
	 *
	 * @param property
	 *            The property to join.
	 * @param alias
	 *            The alias for the joined property.
	 */
	public void addLeftJoin(final String property, final String alias) {
		joinBuilder.append(" left join ").append(property).append(' ')
				.append(alias);
	}

	/**
	 * Adds LEFT JOIN WITH relation to the (HQL) query.
	 *
	 * @param property
	 *            The property to join.
	 * @param alias
	 *            The alias for the joined property.
	 * @param with
	 *            The with for the join.
	 */
	public void addLeftJoinFetch(final String property, final String alias) {
		joinBuilder.append(" left join fetch ").append(property).append(' ')
				.append(alias);
	}

	/**
	 * Adds LEFT JOIN WITH relation to the (HQL) query.
	 *
	 * @param property
	 *            The property to join.
	 * @param alias
	 *            The alias for the joined property.
	 * @param with
	 *            The with for the join.
	 */
	public void addLeftJoin(final String property, final String alias,
			final String with) {
		joinBuilder.append(" left join ").append(property).append(' ')
				.append(alias).append(' ').append("with ").append(with);
	}

	/**
	 * Adds a condition with AND to the (HQL) query.
	 *
	 * @param property
	 *            The property to check.
	 * @param condition
	 *            The condition for the property.
	 * @param paramName
	 *            The parameter name in the condition.
	 */
	public void addAnd(final String property, final String condition,
			final String paramName) {
		if (whereBuilder.length() > 0) {
			whereBuilder.append(" and (");
		} else {
			whereBuilder.append('(');
		}
		whereBuilder.append(property).append(' ').append(condition).append(' ')
				.append(paramName).append(')');
	}

	/**
	 * Adds a condition with OR to the (HQL) query.
	 *
	 * @param property
	 *            The property to check.
	 * @param condition
	 *            The condition for the property.
	 * @param paramName
	 *            The parameter name in the condition.
	 */
	public void addOr(final String property, final String condition,
			final String paramName) {
		if (whereBuilder.length() > 0) {
			whereBuilder.append(" or (");
		} else {
			whereBuilder.append('(');
		}
		whereBuilder.append(property).append(' ').append(condition).append(' ')
				.append(paramName).append(')');
	}

	/**
	 * Adds sort property with the corresponding sorting (ascending or
	 * descending) order to the (HQL) query.
	 *
	 * @param sortProperty
	 *            The sort property.
	 * @param ascending
	 *            The sort order.
	 */
	public void addSortProperty(final String sortProperty,
			final Boolean ascending) {
		if (orderBuilder.length() > 0) {
			orderBuilder.append(", ");
		}

		orderBuilder.append(sortProperty);

		if (ascending != null) {
			orderBuilder.append(" ").append(ascending ? "asc" : "desc");
		}
	}

	/**
	 * Adds grouping property.
	 *
	 * @param groupProperty
	 *            Grouping property to add.
	 */
	public void addGroup(final String groupProperty) {
		if (!StringUtils.hasLength(groupProperty)) {
			return;
		}

		if (groupBuilder.length() > 0) {
			groupBuilder.append(", ");
		}

		groupBuilder.append(groupProperty);
	}

	/**
	 * Builds and retrieves the `select` (HQL) query which will retrieve all the
	 * results for the query.
	 *
	 * @return The select query.
	 */
	public String toSelectQuery() {
		final StringBuilder result = new StringBuilder("select ");
		result.append(select).append(' ').append(qryBuilder);
		if (fetchAllProperties) {
			result.append(" fetch all properties ");
		}
		result.append(joinBuilder);
		if (whereBuilder.length() > 0) {
			result.append(" where ").append(whereBuilder);
		}
		if (groupBuilder.length() > 0) {
			result.append(" group by ").append(groupBuilder);
		}
		if (orderBuilder.length() > 0) {
			result.append(" order by ").append(orderBuilder);
		}
		return result.toString();
	}

	/**
	 * Builds and retrieves the `count` (HQL) query which will retrieve the
	 * number of results for the query.
	 * 
	 * @return The count query.
	 */
	public String toCountQuery() {
		final StringBuilder result = new StringBuilder("select count(distinct ");
		result.append(alias).append(".id) ").append(qryBuilder).append(' ');
		if(joinForCount){
			result.append(joinBuilder);
		}
				
		if (whereBuilder.length() > 0) {
			result.append(" where ").append(whereBuilder);
		}
		return result.toString();
	}

	/**
	 * @return the whereBuilder
	 */
	public StringBuilder getWhereBuilder() {
		return whereBuilder;
	}

	/**
	 * @param whereBuilder the whereBuilder to set
	 */
	public void setWhereBuilder(StringBuilder whereBuilder) {
		this.whereBuilder = whereBuilder;
	}		
}
