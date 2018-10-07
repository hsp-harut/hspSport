package am.chronograph.search;

import java.util.List;

/**
 * Search result related data. 
 * @author HARUT
 * @param <T> - corresponding DTO class...
 */
public class SearchResultBean<T> {

	private int count;
	private List<T> result;
	
	/*JS function name which is needed to rerender component(s) 
	 * after lazy model is loading in datatable...*/
	private String reRenderJsFunction;
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * @return the result
	 */
	public List<T> getResult() {
		return result;
	}
	
	/**
	 * @param result the result to set
	 */
	public void setResult(List<T> result) {
		this.result = result;
	}

    /**
     * @return the reRenderJsFunction
     */
    public String getReRenderJsFunction() {
        return reRenderJsFunction;
    }

    /**
     * @param reRenderJsFunction the reRenderJsFunction to set
     */
    public void setReRenderJsFunction(String reRenderJsFunction) {
        this.reRenderJsFunction = reRenderJsFunction;
    }	
}
