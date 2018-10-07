package am.chronograph.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.omnifaces.filter.CacheControlFilter;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.util.HttpSessionMutexListener;

/**
 * The entry point for Spring initialization.
 * 
 * @author tigranbabloyan
 *
 */
public class ApplicationInitializer implements WebApplicationInitializer {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		setContextParams(servletContext);
		// Load application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationContext.class);
		rootContext.setDisplayName("Bonako Intranet"); //TODO HARRRRRRRR 
		// Context loader listener
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.addListener(HttpSessionMutexListener.class);
		FilterRegistration.Dynamic noCacheFilter = servletContext.addFilter("noCache", CacheControlFilter.class);
		noCacheFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.FORWARD,DispatcherType.REQUEST,DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.INCLUDE), false, "/*");
		FilterRegistration.Dynamic uploadFilter = servletContext.addFilter("PrimeFaces FileUpload Filter", FileUploadFilter.class);
		uploadFilter.addMappingForServletNames(EnumSet.of(DispatcherType.FORWARD,DispatcherType.REQUEST), false, "FacesServlet");
	}

	private void setContextParams(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.numberOfViewsInSession", "5");
		servletContext.setInitParameter("com.sun.faces.serializeServerState", "false");

		servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
		servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
		servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
		servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES",
				"/WEB-INF/primefaces-modena.taglib.xml;/WEB-INF/spring-security.taglib.xml");
		servletContext.setInitParameter("com.sun.faces.defaultResourceMaxAge",
				"0");
		servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
		servletContext.setInitParameter("primefaces.UPLOADER", "auto");
		servletContext.setInitParameter("primefaces.THEME", "modena");
		servletContext.setInitParameter("org.omnifaces.FACES_VIEWS_SCAN_PATHS", "/*.xhtml");		
	}
}
