package am.chronograph.search;

import java.util.List;

/**
 * Interface for defining Search Criteria,
 * should be extended per searchable entity.
 * 
 * @author tigran
 * @param <T>
 */
public interface SearchCriteria<T> {
   /**
    * Returns the page number, by default it is 1.
    * 
    * @return The page number.
    */
   int getPage();

   /**
    * Sets the page number.
    * 
    * @param page The page number.
    */
   void setPage(int page);

   /**
    * Returns expected number of results to be retrieved in a page.
    * -1 means unlimited (will return all available results).
    * 
    * @return The page size.
    */
   int getPageSize();

   /**
    * Sets expected number of results in a page.
    * 
    * @param pageSize The page size.
    */
   void setPageSize(int pageSize);

   /**
    * Adds search order criteria one or more.
    * 
    * @param soc The search order criteria.
    */
   void addSearchOrderCriteria(SearchOrderCriterion... soc);

   /**
    * Returns the search order criteria, only for view.
    * 
    * @return The search order criteria.
    */
   List<SearchOrderCriterion> getSearchOrderCriteria();
}