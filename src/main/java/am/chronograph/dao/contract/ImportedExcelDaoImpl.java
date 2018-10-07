package am.chronograph.dao.contract;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.HqlQueryBuilder;
import am.chronograph.domain.contract.Contract;
import am.chronograph.domain.contract.ImportedExcel;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultImpl;

/**
 * Implements ImportedExcelDao interface.
 * 
 * @author HARUT
 */
@Repository
public class ImportedExcelDaoImpl extends GenericDaoImpl<ImportedExcel> implements ImportedExcelDao {
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.dao.contract.ImportedExcelDao#getByNameNotById(java.lang.String, java.lang.Long)
     */
    @Override
    public ImportedExcel getByName(String fileName) {       
        List<ImportedExcel> excels = list("FROM ImportedExcel excel WHERE excel.fileName = :fileName", Collections.singletonMap("fileName", fileName));
                
        if(excels.isEmpty()) {
            return null;
        }
        
        return excels.get(0);
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.dao.contract.ImportedExcelDao#getFileLikeName(java.lang.String)
     */
    @Override
    public List<ImportedExcel> getFileLikeName(String fileNameNoFormat) {
    	String queryString = "FROM ImportedExcel excel WHERE excel.fileName LIKE '" + fileNameNoFormat + "%'";

		Query query = getSession().createQuery(queryString);
		
		List<ImportedExcel> excels = query.list();        
		
		return excels;
    	
    }

    /*
     * (non-Javadoc)
     * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.SearchCriteria)
     */
    @Override
    public SearchResult<ImportedExcel> search(SearchCriteria<ImportedExcel> criteria) {
        HqlQueryBuilder queryBuilder = new HqlQueryBuilder("ImportedExcel", "item");
        final Map<String, Serializable> params = new HashMap<String, Serializable>();

        // Apply sorting
        for (final SearchOrderCriterion orderCriterion : criteria.getSearchOrderCriteria()) {
            queryBuilder.addSortProperty(orderCriterion.getOrderBy(), orderCriterion.isSortAsc());
        }
        
        return new SearchResultImpl<ImportedExcel>(queryBuilder.toSelectQuery(), queryBuilder.toCountQuery(), params, this,
                criteria);
    }	
}
