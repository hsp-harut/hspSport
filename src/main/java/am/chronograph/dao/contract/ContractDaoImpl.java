package am.chronograph.dao.contract;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import am.chronograph.dao.GenericDaoImpl;
import am.chronograph.dao.HqlQueryBuilder;
import am.chronograph.domain.contract.Contract;
import am.chronograph.search.SearchCriteria;
import am.chronograph.search.SearchOrderCriterion;
import am.chronograph.search.SearchResult;
import am.chronograph.search.SearchResultImpl;
import am.chronograph.search.SearchSupport;
import am.chronograph.util.ChronoUtil;
import am.chronograph.web.util.Constants;
import am.chronograph.web.util.Constants.ContractStatus;
import am.chronograph.web.util.DateUtil;

/**
 * Implements ContractDao interface.
 * 
 * @author HARUT
 */
@Repository
public class ContractDaoImpl extends GenericDaoImpl<Contract> implements ContractDao , SearchSupport<Contract> {
    
    public static final int AGENT_TPE_MAX_LENGTH = 4;
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#getContractByNumberNotById(java.lang.String, java.lang.Long)
	 */
	public Contract getContractByNumberNotById(String contractNumber, Long id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("contractNumber", contractNumber);		
		if(id != null) {
			parameters.put("id", id);
		}
		
		List<Contract> contracts = list("FROM Contract contract WHERE contract.contractNumber = :contractNumber " + ((id != null) ? "AND contract.id != :id" : ""), parameters);
				
		if(contracts.isEmpty()) {
			return null;
		}
		
		return contracts.get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#getContractByBankNotById(java.lang.String, java.lang.Long)
	 */
	public List<Contract> getContractsByBankNotById(String bankAccountNumber, Long id) {
	    boolean likeBank = false;
	    
	    final int bankAccountValidLength = ChronoUtil.getValidBankAccountLength(bankAccountNumber);
	    if(bankAccountNumber.length() >= bankAccountValidLength) {
	        likeBank = true;
	        bankAccountNumber = bankAccountNumber.substring(0, bankAccountValidLength);
	    }
	    
        String queryString = "FROM Contract contract WHERE contract.bankAccountNumber " + (likeBank ? "LIKE '" + bankAccountNumber + "%'" : " = :bankAccountNumber ") + ((id != null) ? "AND contract.id != :id" : "") 
                + " ORDER BY contract.amountRemains DESC";
        
        Query query = getSession().createQuery(queryString);
        if(id != null) {            
            query.setParameter("id", id);
        }
        
        if(!likeBank) {
            query.setParameter("bankAccountNumber", bankAccountNumber);
        }
        
        List<Contract> contracts = query.list();        
        
        return contracts;
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#getContractsByBank(java.lang.String)
	 */
	public List<Contract> getContractsByBank(String bankAccountNumber, List<String> agentTypes) {
	    boolean likeBank = false;
        
        final int bankAccountValidLength = ChronoUtil.getValidBankAccountLength(bankAccountNumber);
        if(bankAccountNumber.length() >= bankAccountValidLength) {
            likeBank = true;
            bankAccountNumber = bankAccountNumber.substring(0, bankAccountValidLength);
        }
        
        String queryString = "FROM Contract contract WHERE contract.bankAccountNumber " + (likeBank ? "LIKE '" + bankAccountNumber + "%'" : " = :bankAccountNumber ") 
                        + initAgentTypeQuery(agentTypes)
                        + " ORDER BY contract.enterDate";
        
        Query query = getSession().createQuery(queryString);       
        
        if(!likeBank) {
            query.setParameter("bankAccountNumber", bankAccountNumber);
        }
        
        List<Contract> contracts = query.list();        
        
        return contracts;
	}
	
	/**
	 * Initialize "Like" query part for agent types...
	 * @param agentTypes
	 * @return
	 */
	private String initAgentTypeQuery(List<String> agentTypes) {
	    if(agentTypes.size() == AGENT_TPE_MAX_LENGTH) {
	        return "";
	    }
	    
	    StringBuilder agentTypesQuery = new StringBuilder(" AND (");
	    for(int i = 0; i < agentTypes.size(); i++) {
	        String agentType = agentTypes.get(i);
	        if(i > 0) {
	            agentTypesQuery.append(" OR ");
	        }
	        
	        agentTypesQuery.append(" contract.agent LIKE '").append(agentType).append(" %' ");
	    }
	    
	    agentTypesQuery.append(")");
	    
	    return agentTypesQuery.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#getContractsStartedLaterThanDaysNumber(int)
	 */
	public List<Contract> getContractsStartedLaterThanDaysNumber(int days) {
//	    return list("FROM Contract contract WHERE contract.status IS NULL AND datediff(NOW(), contract.startDate) >= :days", Collections.singletonMap("days", days));
	    return list("FROM Contract contract WHERE contract.status IS NULL AND datediff(NOW(), contract.periodicLastUpdatedAt) >= :days", Collections.singletonMap("days", days));
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#setCopyContractsByBank(java.lang.String)
	 */
	public void setCopyContractsByBank(String bankAccountNumber) {
	    Query query = getSession().createQuery("UPDATE Contract contract SET contract.copy = true WHERE contract.bankAccountNumber = :bankAccountNumber AND contract.copy = false");
	    query.setParameter("bankAccountNumber", bankAccountNumber);
	    
	    query.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#checkAndUpdateStatusToPaid()
	 */
	public void checkAndUpdateStatusToPaid() {	    
        getSession().createSQLQuery("UPDATE contract c SET c.status = 'Paid' WHERE c.status IS NULL AND c.amount_remains = 0").executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see am.chronograph.dao.contract.ContractDao#checkAndUpdateStatusToOverPaid()
	 */
	public void checkAndUpdateStatusToOverPaid() {	  
        getSession().createSQLQuery("UPDATE contract c SET c.status = 'Overpaid' WHERE c.status IS NULL AND c.amount_remains < 0").executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see am.chronograph.search.SearchSupport#search(am.chronograph.search.SearchCriteria)
	 */
    @Override
    public SearchResult<Contract> search(SearchCriteria<Contract> criteria) {
        HqlQueryBuilder queryBuilder = new HqlQueryBuilder("Contract", "item");
        final Map<String, Serializable> params = new HashMap<String, Serializable>();

        // Apply filters        
        ContractSearchCriteria uCriteria = (ContractSearchCriteria) criteria;
        
        if (ChronoUtil.isValidDateRange(uCriteria.getEnterDate())) {
        	initDateRangeWhereQuery(queryBuilder, params, uCriteria.getEnterDate(), "enterDate");        	
        } else if(StringUtils.isNotBlank(uCriteria.getEnterDate())) {
        	return new SearchResultImpl<Contract>();
        }
                
        if (StringUtils.isNotBlank(uCriteria.getStartDate())) {
            final Date startDate = DateUtil.getDateByString(uCriteria.getStartDate(), Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
            if(startDate == null) {
                return new SearchResultImpl<Contract>();
            }
            
            params.put("startDate", DateUtil.getLocalDateTimeByDate(startDate));
            queryBuilder.addAnd("startDate", ">=", ":startDate");
        }
        
        if (StringUtils.isNotBlank(uCriteria.getEndDate())) {
            final Date endDate = DateUtil.getDateByString(uCriteria.getEndDate(), Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
            if(endDate == null) {
                return new SearchResultImpl<Contract>();
            }
            
            params.put("endDate", DateUtil.getLocalDateTimeByDate(endDate));
            queryBuilder.addAnd("endDate", "<=", ":endDate");
        }
        
        if (uCriteria.getId() != null) {
            queryBuilder.addAnd("id", "like", "'%" + uCriteria.getId() + "%'");
        }
        
        if (StringUtils.isNotBlank(uCriteria.getContractNumber())) {
            queryBuilder.addAnd("contractNumber", "like", "'%" + uCriteria.getContractNumber() + "%'");
        }
        
        if (StringUtils.isNotBlank(uCriteria.getBankAccountNumber())) {
            queryBuilder.addAnd("bankAccountNumber", "like", "'%" + uCriteria.getBankAccountNumber() + "%'");
        }
        
        if (StringUtils.isNotBlank(uCriteria.getAddress())) {
            queryBuilder.addAnd("address", "like", "'%" + uCriteria.getAddress() + "%'");
        }
        
        if (StringUtils.isNotBlank(uCriteria.getWorkingPlace())) {
            queryBuilder.addAnd("workingPlace", "like", "'%" + uCriteria.getWorkingPlace() + "%'");
        }
       
        if (ChronoUtil.isValidDoubleRange(uCriteria.getAmountOld())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getAmountOld(), "amountOld");        	
        } else if(StringUtils.isNotBlank(uCriteria.getAmountOld())) {
        	return new SearchResultImpl<Contract>();
        }
        
        if (ChronoUtil.isValidDoubleRange(uCriteria.getAmountNew())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getAmountNew(), "amountNew");        	
        } else if(StringUtils.isNotBlank(uCriteria.getAmountNew())) {
        	return new SearchResultImpl<Contract>();
        }        
      
        if (ChronoUtil.isValidDoubleRange(uCriteria.getAmountRelax())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getAmountRelax(), "amountRelax");        	
        } else if(StringUtils.isNotBlank(uCriteria.getAmountRelax())) {
        	return new SearchResultImpl<Contract>();
        }
     
        if (ChronoUtil.isValidDoubleRange(uCriteria.getAmountPersonal())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getAmountPersonal(), "amountPersonal");        	
        } else if(StringUtils.isNotBlank(uCriteria.getAmountPersonal())) {
        	return new SearchResultImpl<Contract>();
        }
        
        if (ChronoUtil.isValidDoubleRange(uCriteria.getAmountTotal())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getAmountTotal(), "amountTotal");        	
        } else if(StringUtils.isNotBlank(uCriteria.getAmountTotal())) {
        	return new SearchResultImpl<Contract>();
        }
        
        if (ChronoUtil.isValidDoubleRange(uCriteria.getPeriodicPayment())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getPeriodicPayment(), "periodicPayment");        	
        } else if(StringUtils.isNotBlank(uCriteria.getPeriodicPayment())) {
        	return new SearchResultImpl<Contract>();
        }        
        
        if (ChronoUtil.isValidDoubleRange(uCriteria.getAmountRemains())) {
        	initDoubleRangeWhereQuery(queryBuilder, params, uCriteria.getAmountRemains(), "amountRemains");        	
        } else if(StringUtils.isNotBlank(uCriteria.getAmountRemains())) {
        	return new SearchResultImpl<Contract>();
        }
        
        if (StringUtils.isNotBlank(uCriteria.getBankAccountNumber())) {
            queryBuilder.addAnd("bankAccountNumber", "like", "'%" + uCriteria.getBankAccountNumber() + "%'");            
        }
        
        if (StringUtils.isNotBlank(uCriteria.getHotel())) {
            queryBuilder.addAnd("hotel", "like", "'%" + uCriteria.getHotel() + "%'");
        }
        
        if (uCriteria.getNightsNumber() != null) {
//            queryBuilder.addAnd("nightsNumber", "like", "'%" + uCriteria.getNightsNumber() + "%'");
            queryBuilder.addAnd("nightsNumber", "=", ":nightsNumber");            
            params.put("nightsNumber", uCriteria.getNightsNumber());
        }
        
        if (StringUtils.isNotBlank(uCriteria.getAgent())) {            
            queryBuilder.addAnd("agent", "like", "'%" + uCriteria.getAgent() + "%'");
        }
        
        if (uCriteria.getStatusFilterBy() != null) {
            if(uCriteria.getStatusFilterBy().equals("copy")) {
                queryBuilder.addAnd("copy", "=", ":copy");            
                params.put("copy", true);
            } else {
                queryBuilder.addAnd("status", "=", ":status");            
                params.put("status", ContractStatus.valueOf(uCriteria.getStatusFilterBy()));
            }
        }
        
        if (StringUtils.isNotBlank(uCriteria.getFullName())) {
        	StringBuilder whereBuilder = queryBuilder.getWhereBuilder();
        	if (whereBuilder.length() > 0) {
    			whereBuilder.append(" and (");
    		} else {
    			whereBuilder.append("(");
    		}
        	
        	final String[] tokens = uCriteria.getFullName().split(" ");
        	final String firstToken = tokens[0];
        	final String secondToken = tokens.length > 1 ? tokens[1] : null;
        	final String thirdToken = tokens.length > 2 ? tokens[2] : null;
        	
            params.put("firstToken", "%" + firstToken + "%");
            if(secondToken != null) {
            	params.put("secondToken", "%" + secondToken + "%");
            	if(thirdToken != null) {
            		params.put("thirdToken", "%" + thirdToken + "%");
            		whereBuilder.append("((firstName like :firstToken and lastName like :secondToken) or (firstName like :secondToken and lastName like :firstToken)) and middleName like :thirdToken").append(")");
            	} else {
            		whereBuilder.append("(firstName like :firstToken and lastName like :secondToken) or (firstName like :secondToken and lastName like :firstToken)").append(")");
            	}
            } else {
            	whereBuilder.append("firstName like :firstToken or lastName like :firstToken or middleName like :firstToken").append(")");
            }
        }
        
        // Apply sorting
        for (final SearchOrderCriterion orderCriterion : criteria.getSearchOrderCriteria()) {
            queryBuilder.addSortProperty(orderCriterion.getOrderBy(), orderCriterion.isSortAsc());
            if(orderCriterion.getOrderBy().equals("status")) {
                queryBuilder.addSortProperty("copy", orderCriterion.isSortAsc());
            }
        }
        
        return new SearchResultImpl<Contract>(queryBuilder.toSelectQuery(), queryBuilder.toCountQuery(), params, this,
                criteria);
    }
    
    /*
     * (non-Javadoc)
     * @see am.chronograph.dao.contract.ContractDao#getTotals(am.chronograph.dao.contract.ContractSearchCriteria)
     */
    public Object[] getTotals(ContractSearchCriteria criteria) {
        
        final Object[] noData = {0d, 0d, 0d, 0d, 0d, 0d, 0d};
        
        /*HQL query parts*/             
        String selectPart = "SELECT SUM(amount_total) amountTotal, SUM(periodic_payment) periodicPayment,"
        		+ "SUM(amount_old) amountOld, SUM(amount_new) amountNew,"
        		+ "SUM(amount_relax) amountRelax, SUM(amount_personal) amountPersonal, SUM(amount_remains) amountRemains FROM contract ";                                                                 
        
        StringBuilder wherePart = new StringBuilder(" WHERE 1=1 ");
        
        if (ChronoUtil.isValidDateRange(criteria.getEnterDate())) {
        	initDateRangeWhereTotalQuery(wherePart, criteria.getEnterDate(), "contract_enter_date");        	
        } else if(StringUtils.isNotBlank(criteria.getEnterDate())) {
        	return noData;
        }        
        
        if (StringUtils.isNotBlank(criteria.getStartDate())) {
            final Date startDate = DateUtil.getDateByString(criteria.getStartDate(), Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
            if(startDate == null) {
                return noData;
            }
            
            wherePart.append(" AND contract_start_date >= '").append(DateUtil.getDateStringByPattern(startDate, Constants.MYSQL_DATE_PATTERN)).append("' ");            
        }
        
        if (StringUtils.isNotBlank(criteria.getEndDate())) {
            final Date endDate = DateUtil.getDateByString(criteria.getEndDate(), Constants.DEFAULT_DATE_PATTERN, Constants.DEFAULT_DATE_TIME_PATTERN);
            if(endDate == null) {
                return noData;
            }
            
            wherePart.append(" AND contract_end_date <= '").append(DateUtil.getDateStringByPattern(endDate, Constants.MYSQL_DATE_PATTERN)).append("' ");
        }
        
        if (criteria.getId() != null) {            
            wherePart.append(" AND id like '%").append(criteria.getId()).append("%'");
        }
        
        if (StringUtils.isNotBlank(criteria.getContractNumber())) {            
            wherePart.append(" AND contractNumber like '%").append(criteria.getContractNumber()).append("%'");
        }
        
        if (StringUtils.isNotBlank(criteria.getBankAccountNumber())) {            
            wherePart.append(" AND bankAccountNumber like '%").append(criteria.getBankAccountNumber()).append("%'");
        }
        
        if (StringUtils.isNotBlank(criteria.getAddress())) {            
            wherePart.append(" AND address like '%").append(criteria.getAddress()).append("%'");
        }
        
        if (StringUtils.isNotBlank(criteria.getWorkingPlace())) {            
            wherePart.append(" AND working_place like '%").append(criteria.getWorkingPlace()).append("%'");
        }
             
        if (ChronoUtil.isValidDoubleRange(criteria.getAmountOld())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getAmountOld(), "amount_old");        	
        } else if(StringUtils.isNotBlank(criteria.getAmountOld())) {
        	return noData;
        }
        
        if (ChronoUtil.isValidDoubleRange(criteria.getAmountNew())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getAmountNew(), "amount_new");        	
        } else if(StringUtils.isNotBlank(criteria.getAmountNew())) {
        	return noData;
        }      
        
        if (ChronoUtil.isValidDoubleRange(criteria.getAmountRelax())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getAmountRelax(), "amount_relax");        	
        } else if(StringUtils.isNotBlank(criteria.getAmountRelax())) {
        	return noData;
        }                        
        
        if (ChronoUtil.isValidDoubleRange(criteria.getAmountPersonal())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getAmountPersonal(), "amount_personal");        	
        } else if(StringUtils.isNotBlank(criteria.getAmountPersonal())) {
        	return noData;
        }              
        
        if (ChronoUtil.isValidDoubleRange(criteria.getAmountTotal())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getAmountTotal(), "amount_total");        	
        } else if(StringUtils.isNotBlank(criteria.getAmountTotal())) {
        	return noData;
        }
      
        if (ChronoUtil.isValidDoubleRange(criteria.getPeriodicPayment())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getPeriodicPayment(), "periodic_payment");        	
        } else if(StringUtils.isNotBlank(criteria.getPeriodicPayment())) {
        	return noData;
        }
        
        if (ChronoUtil.isValidDoubleRange(criteria.getAmountRemains())) {
        	initDoubleRangeWhereTotalQuery(wherePart, criteria.getAmountRemains(), "amount_remains");        	
        } else if(StringUtils.isNotBlank(criteria.getAmountRemains())) {
        	return noData;
        }
        
        if (StringUtils.isNotBlank(criteria.getBankAccountNumber())) {            
            wherePart.append(" AND bankAccountNumber like '%").append(criteria.getBankAccountNumber()).append("%'");
        }
        
        if (StringUtils.isNotBlank(criteria.getHotel())) {            
            wherePart.append(" AND hotel like '%").append(criteria.getHotel()).append("%'");
        }
        
        if (criteria.getNightsNumber() != null) {            
            wherePart.append(" AND nights_number = ").append(criteria.getNightsNumber());
        }
        
        if (StringUtils.isNotBlank(criteria.getAgent())) {                        
            wherePart.append(" AND agent like '%").append(criteria.getAgent()).append("%'");
        }
        
        if (criteria.getStatusFilterBy() != null) {
            if(criteria.getStatusFilterBy().equals("copy")) {
                wherePart.append(" AND isCopy = 1");                
            } else {
                wherePart.append(" AND status = '").append(criteria.getStatusFilterBy()).append("'");                
            }
        }
        
        if (StringUtils.isNotBlank(criteria.getFullName())) {           
            wherePart.append(" and (");
            
            final String[] tokens = criteria.getFullName().split(" ");
            final String firstToken = "'%" + tokens[0] + "%'";
            final String secondToken = tokens.length > 1 ? "'%" + tokens[1] + "%'" : null;
            final String thirdToken = tokens.length > 2 ? "'%" + tokens[2] + "%'" : null;
                        
            if(secondToken != null) {            
                if(thirdToken != null) {                    
                    wherePart.append("((first_name like " + firstToken + " and last_name like " + secondToken + ") or (first_name like " + secondToken + "and last_name like " + firstToken + ")) and middle_name like " + thirdToken).append(")");
                } else {
                    wherePart.append("(first_name like " + firstToken + " and last_name like " + secondToken + ") or (first_name like " + secondToken + " and last_name like " + firstToken + ")").append(")");
                }
            } else {
                wherePart.append("first_name like " + firstToken + " or last_name like " + firstToken + " or middle_name like " + firstToken).append(")");
            }
        }
        
        final Query query = getSession().createSQLQuery(selectPart + wherePart.toString());
        
        return (Object[])query.list().get(0);
    }
    
    /**
     * Initialize where query part for TOTAL result for double range data.
     * @param wherePart
     * @param dbColumn
     */
    private void initDoubleRangeWhereTotalQuery(StringBuilder wherePart, String value, String dbColumn) {
    	Double[] doubleRange = ChronoUtil.getDoubleRange(value);
    	if(doubleRange.length == 1) {
    		wherePart.append(" AND ").append(dbColumn).append(" = ").append(ChronoUtil.getDoubleWithoutDotZero(doubleRange[0]));	            
    	} else {
    		wherePart.append(" AND ").append(dbColumn).append(" >= ").append(ChronoUtil.getDoubleWithoutDotZero(doubleRange[0]));
    		wherePart.append(" AND ").append(dbColumn).append(" <= ").append(ChronoUtil.getDoubleWithoutDotZero(doubleRange[1]));
    	}
    }
    
    /**
     * Initialize where query part for TOTAL result for date range data.
     * @param wherePart
     * @param dbColumn
     */
    private void initDateRangeWhereTotalQuery(StringBuilder wherePart, String value, String dbColumn) {
    	String dateFrom = null;
    	String dateTo = null;
    	
    	Date[] dateRange = ChronoUtil.getDateRange(value);
    	dateFrom = DateUtil.getDateStringByPattern(DateUtil.setStartEndTimeForDate(dateRange[0], true), Constants.MYSQL_DATE_PATTERN);
    	if(dateRange.length == 1) {    		    		
            dateTo = DateUtil.getDateStringByPattern(DateUtil.setStartEndTimeForDate(dateRange[0], false), Constants.MYSQL_DATE_PATTERN);                        
    	} else {
    		dateTo = DateUtil.getDateStringByPattern(DateUtil.setStartEndTimeForDate(dateRange[1], false), Constants.MYSQL_DATE_PATTERN);
    	}
    	
    	wherePart.append(" AND ").append(dbColumn).append(" >= '").append(dateFrom).append("' ");
        wherePart.append(" AND ").append(dbColumn).append(" <= '").append(dateTo).append("' ");
    }
    
    /**
     * Initialize where query part for result for double range data.
     * @param wherePart
     * @param dbColumn
     */
    private void initDoubleRangeWhereQuery(HqlQueryBuilder queryBuilder, Map<String, Serializable> params, String value, String dbColumn) {
    	Double[] doubleRange = ChronoUtil.getDoubleRange(value);
    	if(doubleRange.length == 1) {
    		queryBuilder.addAnd(dbColumn, "=", ":" + dbColumn);            
            params.put(dbColumn, doubleRange[0]);
    	} else {
    		queryBuilder.addAnd(dbColumn, ">=", ":amountFrom");            
    		queryBuilder.addAnd(dbColumn, "<=", ":amountTo");
            params.put("amountFrom", doubleRange[0]);
            params.put("amountTo", doubleRange[1]);
    	}
    }
    
    /**
     * Initialize where query part for result for date range data.
     * @param wherePart
     * @param dbColumn
     */
    private void initDateRangeWhereQuery(HqlQueryBuilder queryBuilder, Map<String, Serializable> params, String value, String dbColumn) {    	
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
    	
    	Date[] dateRange = ChronoUtil.getDateRange(value);
    	dateFrom = DateUtil.getLocalDateTimeByDate(DateUtil.setStartEndTimeForDate(dateRange[0], true));
    	if(dateRange.length == 1) {    		    		
            dateTo = DateUtil.getLocalDateTimeByDate(DateUtil.setStartEndTimeForDate(dateRange[0], false));                        
    	} else {
    		dateTo = DateUtil.getLocalDateTimeByDate(DateUtil.setStartEndTimeForDate(dateRange[1], false));
    	}
    	
    	params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);
        
        queryBuilder.addAnd(dbColumn, ">=", ":dateFrom");
        queryBuilder.addAnd(dbColumn, "<=", ":dateTo");
    }
}

