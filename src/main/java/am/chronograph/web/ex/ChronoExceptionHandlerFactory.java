package am.chronograph.web.ex;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * The {@link ExceptionHandlerFactory} which creates the custom exception
 * handlers.
 * 
 * @author tigranbabloyan
 *
 */
public class ChronoExceptionHandlerFactory extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory parent;

	// this injection handles jsf
	public ChronoExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerFactory#getExceptionHandler()
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = new ChronoExceptionHandler(parent.getExceptionHandler());
		return handler;
	}

}
