package am.chronograph.web.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResultBean;
import am.chronograph.service.SearchBeanAware;
import am.chronograph.web.bean.BaseBean;
import am.chronograph.web.integration.Spring;

/**
 * Lazy search model for primefaces data table...
 * 
 * @author HARUT
 *
 * @param <B>
 * @param <D>
 * @param <S>
 */
public class SearchAwareBeanLazyModel<B extends BaseBean, D, S extends SearchBeanAware<B, D>> extends LazyDataModel<B> {
	
    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 6977716104041320461L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchAwareBeanLazyModel.class);
    
    @Inject
    @Spring
    protected transient S searchService;

    private SearchResultBean<B> searchResult;
    
    /**
     * Initialize contractService...
     * @param contractService
     */
    public SearchAwareBeanLazyModel(S searchService) {
        this.searchService = searchService;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String,
	 * org.primefaces.model.SortOrder, java.util.Map)
	 */
	@Override
	public List<B> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		SearchCriteria<D> criteria = searchService.getEmptyCriteria();
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
		
		searchResult = searchService.search(criteria);
		setRowCount(searchResult.getCount());
		
		if(searchResult.getReRenderJsFunction() != null) {
		    RequestContext.getCurrentInstance().execute(searchResult.getReRenderJsFunction());
		}
		
		return searchResult.getResult();
	}		

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(B bean) {		
		return bean.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
	 */
	@Override
	public B getRowData(String rowKey) {
	    Long id = Long.parseLong(rowKey);
	    for(B bean : searchResult.getResult()) {
            if(bean.getId().equals(id)) {
                return bean;
            }
        }
 
        return null;
	}

    /**
     * @return the searchResult
     */
    public SearchResultBean<B> getSearchResult() {
        return searchResult;
    }

    /**
     * @param searchResult the searchResult to set
     */
    public void setSearchResult(SearchResultBean<B> searchResult) {
        this.searchResult = searchResult;
    }	
}
