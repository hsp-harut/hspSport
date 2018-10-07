package am.chronograph.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringLocator implements ApplicationContextAware{
	
	private static ApplicationContext context;

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	/**
	 * Returns the context.
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}

}
