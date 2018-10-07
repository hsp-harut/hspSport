package am.chronograph.web.ex;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import am.chronograph.service.EmailService;
import am.chronograph.util.SpringLocator;

/**
 * Custom exception handler which redirect to the error.jsf page on every
 * exception.
 * 
 * @author tigranbabloyan
 *
 */
public class ChronoExceptionHandler extends ExceptionHandlerWrapper {
	private static final Logger log = LoggerFactory.getLogger(ChronoExceptionHandler.class);
	private ExceptionHandler wrapped;

	ChronoExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	@Override
	public void handle() throws FacesException {

		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			Throwable t = context.getException();

			final FacesContext fc = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = fc.getExternalContext();
			// here you do what ever you want with exception
			try {
				// redirect error page
				if((t instanceof ViewExpiredException) || (t.getCause() != null && t.getCause() instanceof ViewExpiredException)){
					externalContext.redirect(externalContext.getRequestContextPath() + "/dashboard");
				} else {
					log.warn("Critical Exception!", t);
					externalContext.redirect(externalContext.getRequestContextPath() + "/error");
					EmailService emailService = SpringLocator.getContext().getBean(EmailService.class);
					emailService.emailError(t);
				}
			} catch (IOException ex) {

			} finally {
				i.remove();
			}
		}
		// parent handle
		getWrapped().handle();
	}
}