package am.chronograph.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import am.chronograph.search.SearchOrderCriterion;

/**
 * Implementation of GenericDao interface with Springframework's Hibernate
 * support.
 * <p/>
 * Provides uniform data access capabilities for any given Entity type.
 * <p/>
 * Can be extended to support custom finders.
 * 
 * 
 * When using HQL always use 'distinct' keyword.<br/>
 * When using Criteria API use
 * <code>criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);</code>
 * 
 * @author tigran
 * @param <T>
 *            The parameterised entity type.
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	/**
	 * The logger.
	 */
	protected static final Logger LOG = LoggerFactory
			.getLogger(GenericDaoImpl.class);

	/**
	 * Current sessionFactory
	 */
	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * The Class defining Entity type.
	 */
	private Class<T> entityType = null;

	/**
	 * This constructor used when directly extended to be instantiated by a
	 * strict type.
	 * 
	 */
	public GenericDaoImpl() {
		Class<?> clazz = getClass();

		while (clazz != null
				&& !clazz.getSuperclass().equals(GenericDaoImpl.class)) {
			clazz = clazz.getSuperclass();
		}

		Type type = ((ParameterizedType) clazz.getGenericSuperclass())
				.getActualTypeArguments()[0];

		if (type instanceof TypeVariable<?>) {
			TypeVariable<?> typeVar = (TypeVariable<?>) type;
			type = typeVar.getBounds()[0];
		}

		this.entityType = (Class<T>) type;

	}

	/**
	 * This constructor used when instantiating with Springframework via proxy
	 * factory by explicitly providing entity type.
	 * 
	 * @param entityType
	 */
	public GenericDaoImpl(final Class<T> entityType) {
		this.entityType = entityType;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * am.iunetworks.bill.sales.dao.GenericDao#getById(java.io.Serializable)
	 */
	public T getById(final Serializable id) {
		return (T) getSession().get(entityType, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#save(java.lang.Object)
	 */
	public T save(final T entity) {
		getSession().saveOrUpdate(entity);
		// force flush to prevent some problems with transaction rollback
		LOG.trace("Forcing session flush after saving: {}", entity);
		getSession().flush();
		return entity;
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.dao.GenericDao#merge(java.lang.Object)
	 */
	@Override
	public T merge(T object) {
		LOG.trace("Forcing session flush after merging: {}", object);
		T obj = (T) getSession().merge(object);
		getSession().flush();
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#save(java.lang.Object,
	 * boolean)
	 */
	public T save(final T entity, final boolean skipFlush) {
		getSession().saveOrUpdate(entity);

		if (!skipFlush) {
			// force flush to prevent some problems with transaction rollback
			LOG.trace("Forcing session flush after saving: {}", entity);
			getSession().flush();
		}

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#delete(java.lang.Object)
	 */
	public void delete(final T entity) {
		getSession().delete(entity);
		// see issue
		// http://opensource.atlassian.com/projects/hibernate/browse/HHH-2801
		// as of this in some cases when insert after delete a problem will
		// occur
		// therefore delete is flushed to database first to prevent possible
		// problems
		LOG.trace("Forcing session flush after deleting: {}", entity);
		getSession().flush();
		// clean up the session cache
		// ???to be tested before??? evict(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#delete(java.lang.Object,
	 * boolean)
	 */
	public void delete(final T entity, final boolean skipFlush) {
		getSession().delete(entity);
		if (!skipFlush) {
			// see issue
			// http://opensource.atlassian.com/projects/hibernate/browse/HHH-2801
			// as of this in some cases when insert after delete a problem will
			// occur
			// therefore delete is flushed to database first to prevent possible
			// problems
			LOG.trace("Forcing session flush after deleting: {}", entity);
			getSession().flush();
			// clean up the session cache
			// ???to be tested before??? evict(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#evict(java.lang.Object)
	 */
	public void evict(final T entity) {
		getSession().evict(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#list(java.lang.String,
	 * java.util.Map, int, int)
	 */
	public List<T> list(final String query,
			final Map<String, Object> parameters, final int start, final int len) {

		// create the hibernate query
		final org.hibernate.Query qry = GenericDaoImpl.this
				.prepareHibernateQuery(getSession(),
						query, parameters);
		// add the distinct hint
		if (!query.startsWith("select new")) {
			qry.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		// if there is no limit, then get all results
		if (len != -1) {
			qry.setFirstResult(start);
			qry.setMaxResults(len);
			qry.setFetchSize(len);
		}

		// retrieve
		return (List<T>) qry.list();

	}
	
	/* (non-Javadoc)
	 * @see com.jazva.common.dao.GenericDao#list(java.lang.String, java.util.Map)
	 */
	@Override
	public List<T> list(String query, Map<String, Object> parameters) {
		return list(query, parameters, 0, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see am.iunetworks.bill.sales.dao.GenericDao#count(java.lang.String,
	 * java.util.Map)
	 */
	public int count(final String query, final Map<String, Object> parameters) {

		// create the hibernate query
		final org.hibernate.Query qry = GenericDaoImpl.this
				.prepareHibernateQuery(getSession(),
						query, parameters);
		qry.setFirstResult(0);
		qry.setMaxResults(1);
		final Number count = (Number) qry.uniqueResult();
		if (count == null) {
			return 0;
		}

		return count.intValue();
	}
	
	/* (non-Javadoc)
	 * @see am.chronograph.dao.GenericDao#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	/**
	 * Prepares {@link org.hibernate.Query} according to the given query string
	 * and named parameters with values.
	 * 
	 * @param session
	 *            The {@link Session} to use to create the query.
	 * @param queryString
	 *            The query string.
	 * @param parameters
	 *            The parameter map that is used in the query string.
	 * @return The built and prepared query.
	 */
	protected Query prepareHibernateQuery(final Session session,
			final String queryString, final Map<String, Object> parameters) {
		// create the query
		final org.hibernate.Query queryObject = session
				.createQuery(queryString);

		// add the parameters
		if (parameters != null) {
			for (final Entry<String, Object> parameter : parameters.entrySet()) {
				applyNamedParameterToQuery(queryObject, parameter.getKey(),
						parameter.getValue());
			}
		}

		// add the distinct hint
		queryObject
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return queryObject;
	}

	/**
	 * Applies given parameter to the query.
	 * 
	 * @param queryObject
	 *            The query to apply the parameter to.
	 * @param paramName
	 *            The parameter name.
	 * @param value
	 *            The parameter value.
	 * @throws HibernateException
	 * 
	 * @see org.hibernate.Query#setParameterList(String, Collection)
	 * @see org.hibernate.Query#setParameterList(String, Object[])
	 * @see org.hibernate.Query#setParameter(String, Object)
	 */
	protected void applyNamedParameterToQuery(final Query queryObject,
			final String paramName, final Object value)
			throws HibernateException {

		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection<Object>) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	/**
	 * Searches data source with current query and applies the param and it's
	 * value and returns the list of found entities.
	 * 
	 * @param queryString
	 *            The query to execute.
	 * @param paramName
	 *            The parameter name.
	 * @param value
	 *            The parameter value.
	 * @return list of found entities.
	 */
	public List<T> findByNamedParam(final String queryString, final String paramName, final Object value) {
		return findByNamedParam(queryString, new String[] { paramName },
				new Object[] { value });
	}

	/**
	 * Searches data source with current query and applying the params and their
	 * values and returns the list of found entities.
	 * 
	 * @param queryString
	 *            The query string to execute.
	 * @param paramNames
	 *            The param names.
	 * @param values
	 *            The values.
	 * @return the list of found entities.
	 */
	public List<T> findByNamedParam(final String queryString, final String[] paramNames,
			final Object[] values) {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException(
					"Length of paramNames array must match length of values array");
		}

		final Query queryObject = createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			applyNamedParameterToQuery(queryObject, paramNames[i], values[i]);
		}
		return queryObject.list();
	}

	/**
	 * Creates Hibernate query and executes it on current session.
	 * 
	 * @param queryString
	 *            The query string to execute.
	 * @return List of found entities.
	 */
	public List<T> find(final String queryString) {
		return find(queryString, (Object[]) null);
	}

	/**
	 * Creates Hibernate query and executes it on current session and applying
	 * given param.
	 * 
	 * @param queryString
	 *            The query string to execute.
	 * @param value
	 *            The param name.
	 * @return List of found entities.
	 */
	public List<T> find(final String queryString,
			final Object value) {
		return find(queryString, new Object[] { value });
	}

	/**
	 * Creates Hibernate query and executes it on current session and applying
	 * given params in given order.
	 * 
	 * @param queryString
	 *            The query string to execute.
	 * @param values
	 *            The parameter to apply.
	 * @return List of found entities.
	 */
	public List<T> find(final String queryString,
			final Object... values) {
		final Query queryObject = createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject.list();
	}

	/**
	 * Executes bulk update query.
	 * 
	 * @param queryString
	 *            The query to execute.
	 * @return Count of the updated rows.
	 */
	public int bulkUpdate(final String queryString) {
		return bulkUpdate(queryString, (Object[]) null);
	}

	/**
	 * Executes bulk update query.
	 * 
	 * @param queryString
	 *            The query to execute.
	 * @param value
	 *            The parameter value to apply.
	 * @return Count of the updated rows.
	 */
	public int bulkUpdate(final String queryString, Object value) {
		return bulkUpdate(queryString, new Object[] { value });
	}

	/**
	 * Executes bulk update query.
	 * 
	 * @param queryString
	 *            The query to execute.
	 * @param values
	 *            The parameters values to apply.
	 * @return Count of the updated rows.
	 */
	public int bulkUpdate(final String queryString, final Object... values) {
		final Query queryObject = createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject.executeUpdate();
	}

	public Query createQuery(String queryString) {
		return getSession()
				.createQuery(queryString);
	}

	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * Flushes current session.
	 */
	public void flushCurrentSession() {
		getSession().flush();
	}

	/**
	 * Clears current session
	 */
	public void clearCurrentSession() {
		getSession().clear();
	}

	/**
	 * Returns the default session factory. Override this class for custom
	 * session factories.
	 * 
	 * @return the default session factory to be used with the dao.
	 */
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Creates and retrieves SQL order elements for the given order criteria.
	 * Return <code>null</code> if the input order criteria is empty.
	 *
	 * @param orderCriteria
	 *            The order criteria.
	 * @return The SQL order elements.
	 */
	protected static final List<String> createSQLOrders(
			final List<SearchOrderCriterion> orderCriteria) {
		// check if the order criteria is empty
		if (CollectionUtils.isEmpty(orderCriteria)) {
			return null;
		}
		// create sql order
		final List<String> result = new ArrayList<String>(orderCriteria.size());
		for (final SearchOrderCriterion orderCriterion : orderCriteria) {
			result.add(orderCriterion.getOrderBy() + " "
					+ (orderCriterion.isSortAsc() ? "ASC" : "DESC"));
		}
		// return
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.jazva.common.dao.GenericDao#getAll()
	 */
	@Override
	public List<T> getAll() {
		final Session session = getSession();
		final Criteria crit = session.createCriteria(entityType);
		return crit.list();
	}

}
