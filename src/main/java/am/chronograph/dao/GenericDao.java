package am.chronograph.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO interface for a generic way of interacting with the domain Entity
 * objects.
 * 
 * @author tigran
 * @param <T>
 *            The parametrisation entity type.
 * 
 */
public interface GenericDao<T> {
	/**
	 * Saves or Updates the given persistent entity.
	 * 
	 * @param entity
	 *            The entity.
	 * @return The saved entity.
	 */
	T save(T entity);

	/**
	 * Saves or Updates the given persistent entity.
	 * 
	 * @param entity
	 *            The entity.
	 * @param skipFlush
	 *            The skip flush flag.
	 * @return The saved entity.
	 */
	T save(T entity, boolean skipFlush);

	/**
	 * Copy the state of the given object onto the persistent object with the
	 * same identifier. If there is no persistent instance currently associated
	 * with the session, it will be loaded. Return the persistent instance. If
	 * the given instance is unsaved, save a copy of and return it as a newly
	 * persistent instance. The given instance does not become associated with
	 * the session. This operation cascades to associated instances if the
	 * association is mapped with {@code cascade="merge"}
	 * <p/>
	 * The semantics of this method are defined by JSR-220.
	 *
	 * @param object
	 *            a detached instance with state to be copied
	 *
	 * @return an updated persistent instance
	 */
	T merge(T object);

	/**
	 * Re-read the state of the given instance from the underlying database. It
	 * is inadvisable to use this to implement long-running sessions that span
	 * many business tasks. This method is, however, useful in certain special
	 * circumstances. For example
	 * <ul>
	 * <li>where a database trigger alters the object state upon insert or
	 * update
	 * <li>after executing direct SQL (eg. a mass update) in the same session
	 * <li>after inserting a <tt>Blob</tt> or <tt>Clob</tt>
	 * </ul>
	 *
	 * @param object
	 *            a persistent or detached instance
	 */
	void refresh(T entity);

	/**
	 * Delete the given persistent instance.
	 * 
	 * @param entity
	 *            to delete.
	 */
	void delete(T entity);

	/**
	 * Delete the given persistent instance.
	 * 
	 * @param skipFlush
	 *            The skip flush flag.
	 * @param entity
	 *            The entity.
	 */
	void delete(T entity, boolean skipFlush);

	/**
	 * Delete the given persistent instance from session cache.
	 * 
	 * @param entity
	 *            The persistent instance.
	 */
	void evict(T entity);

	/**
	 * Return the persistent instance of <code>T</code> Entity with the given
	 * identifier, or null if not found.
	 * 
	 * @param id
	 *            the identifier of persistent instance.
	 * @return a persistent instance, or null if not found.
	 */
	T getById(Serializable id);

	/**
	 * Retrieves the results for the given query.
	 * 
	 * @param query
	 *            The query.
	 * @param parameters
	 *            The parameters.
	 * @param firstResult
	 *            The first result number.
	 * @param maxResults
	 *            The max results.
	 * @return The results.
	 */
	List<T> list(String query, Map<String, Object> parameters, final int firstResult, final int maxResults);

	/**
	 * Retrieves the all results for the given query.
	 * 
	 * @param query
	 *            The query.
	 * @param parameters
	 *            The parameters.
	 * @return The results.
	 */
	List<T> list(String query, Map<String, Object> parameters);

	/**
	 * Retrieves the result count for the given query.
	 * 
	 * @param query
	 *            The query.
	 * @param parameters
	 *            The parameters.
	 * @return The result count.
	 */
	int count(String query, Map<String, Object> parameters);

	/**
	 * Retrieves the results for the given class.
	 * 
	 * @return The results.
	 */
	public List<T> getAll();
	
	/**
	 * Flushes current session.
	 */
	void flushCurrentSession();

	/**
	 * Clears current session
	 */
	void clearCurrentSession();
}
