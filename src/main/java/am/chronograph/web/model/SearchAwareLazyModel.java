package am.chronograph.web.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import am.chronograph.domain.AbstractEntity;
import am.chronograph.domain.AuditAwareEntity;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.service.SearchAware;

/**
 * A custom {@link LazyDataModel} implementation which is based on the
 * {@link SearchAware}.
 * 
 * @author tigranbabloyan
 *
 * @param <T>
 */
public class SearchAwareLazyModel<T> extends LazyDataModel<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAwareLazyModel.class);

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 8006095643353415934L;

	/**
	 * The search aware to be used to load data.
	 */
	private final SearchAware<T> searchAware;

	/**
	 * The cached search result.
	 */
	protected SearchResult<T> searchResult;

	public SearchAwareLazyModel(SearchAware<T> searchAware) {
		this.searchAware = searchAware;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String,
	 * org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		SearchCriteria<T> criteria = searchAware.getEmptyCriteria();
		if(filters != null && !filters.isEmpty()){
			for(Entry<String, Object> entry : filters.entrySet()){
				String property = entry.getKey();
				Object value = entry.getValue();
				if(PropertyUtils.isWriteable(criteria, property)){
					try {
						BeanUtils.setProperty(criteria, property, value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						LOGGER.error("Unable to set filter value {}", e);
					}
				}
			}
		}
		criteria.setPage((first / pageSize) + 1);
		criteria.setPageSize(pageSize);
		if (StringUtils.isNoneBlank(sortField) && sortOrder != null && !SortOrder.UNSORTED.equals(sortOrder)) {
			criteria.addSearchOrderCriteria(new SearchOrderCriterion(sortField, SortOrder.ASCENDING.equals(sortOrder)));
		}
		searchResult = searchAware.search(criteria);
		return searchResult.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.util.List,
	 * java.util.Map)
	 */
	@Override
	public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		SearchCriteria<T> criteria = searchAware.getEmptyCriteria();
		criteria.setPage((first / pageSize) + 1);
		criteria.setPageSize(pageSize);
		if(filters != null && !filters.isEmpty()){
			for(Entry<String, Object> entry : filters.entrySet()){
				String property = entry.getKey();
				Object value = entry.getValue();
				if(PropertyUtils.isWriteable(criteria, property)){
					try {
						PropertyUtils.setSimpleProperty(criteria, property, value);
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						LOGGER.error("Unable to set filter value {}", e);
					}
				}
			}
		}
		if (!CollectionUtils.isEmpty(multiSortMeta)) {
			for (SortMeta sortMeta : multiSortMeta) {
				String sortField = sortMeta.getSortField();
				SortOrder sortOrder = sortMeta.getSortOrder();
				if (StringUtils.isNoneBlank(sortField) && sortOrder != null && !SortOrder.UNSORTED.equals(sortOrder)) {
					criteria.addSearchOrderCriteria(
							new SearchOrderCriterion(sortField, SortOrder.ASCENDING.equals(sortOrder)));
				}
			}
		}
		searchResult = searchAware.search(criteria);
		return searchResult.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if (searchResult != null) {
			return searchResult.getTotalResultCount();
		}
		return super.getRowCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(T object) {
		if (object instanceof AbstractEntity) {
			return ((AbstractEntity) object).getId();
		} else if (object instanceof AuditAwareEntity) {
			return ((AuditAwareEntity) object).getId();
		}
		return object.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public T getRowData(String rowKey) {
		if (StringUtils.isNoneBlank(rowKey)) {
			Optional<T> result = searchResult.getResult().stream().filter(o -> {
				if (o instanceof AuditAwareEntity) {
					AuditAwareEntity e = (AuditAwareEntity) o;
					if (e.getId().equals(Long.parseLong(rowKey))) {
						return true;
					}
				} else if (o instanceof AbstractEntity) {
					AbstractEntity e = (AbstractEntity) o;
					if (e.getId().equals(Long.parseLong(rowKey))) {
						return true;
					}
				} else {
					if (rowKey.equals(o.hashCode())) {
						return true;
					}
				}
				return false;
			}).findFirst();
			return result.orElse(null);
		}
		return null;
	}

}
