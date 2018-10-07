package am.chronograph.web.integration;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * A marker annotation to be used to inject Spring managed beans into CDI
 * managed ones.
 * 
 * @author tigranbabloyan
 *
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER })
public @interface Spring {
	/**
	 * Type of the injection bean. If set the type based search will be
	 * performed against the Spring application context.
	 */
	Class<?> type() default Object.class;

	/**
	 * Name of the injection bean. If set the name based search will be
	 * performed against the Spring application context.
	 */
	String name() default "";

	/**
	 * Will require the spring bean to exist.
	 */
	@Nonbinding
	boolean required() default true;
}