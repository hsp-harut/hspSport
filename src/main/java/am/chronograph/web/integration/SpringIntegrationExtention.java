package am.chronograph.web.integration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import am.chronograph.util.SpringLocator;

/**
 * Custom CDI extension to make Spring injection available into CDI beans.
 * 
 * @author tigranbabloyan
 *
 */
public class SpringIntegrationExtention implements Extension {
	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringIntegrationExtention.class);

	/**
	 * The discovered spring beans.
	 */
	private Map<String, SpringBean> springBeans = new HashMap<String, SpringBean>();

	public void processInjectionTarget(@Observes ProcessInjectionTarget<?> pit, BeanManager bm) {
		Set<InjectionPoint> injectionPoints = pit.getInjectionTarget().getInjectionPoints();
		synchronized (springBeans) {
			for (InjectionPoint point : injectionPoints) {

				if (!(point.getType() instanceof Class<?>)) {
					continue;
				}

				Class<?> injectionType = (Class<?>) point.getType();
				Spring spring = point.getAnnotated().getAnnotation(Spring.class);
				if (spring != null) {
					SpringBean springBean = new SpringBean(pit.getAnnotatedType(), spring, injectionType, bm);
					// we can do some validation to make sure that this bean is
					// compatible with the one we are replacing.
					springBeans.put(springBean.key(), springBean);
				}
			}
		}
	}

	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
		// for now we do not need this
	}

	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
		synchronized (springBeans) {
			for (SpringBean bean : springBeans.values()) {
				abd.addBean(bean);
			}
		}
	}

	/**
	 * Wrapper CDI bean to be used to wrap over spring managed beans.
	 * 
	 * @author tigranbabloyan
	 *
	 */
	class SpringBean implements Bean<Object> {
		// InjectionTarget<Object> it;
		Spring spring;
		Class<?> injectionType;
		BeanManager bm;

		AnnotatedType<?> annotatedType;

		SpringBean(AnnotatedType<?> annotatedType, Spring spring, Class<?> injectionType, BeanManager bm) {
			this.spring = spring;
			this.injectionType = injectionType;
			this.bm = bm;
			this.annotatedType = annotatedType;
			// it = bm.createInjectionTarget(at);
		}

		public String key() {
			return "" + this.getName() + "::" + injectionType.toString();
		}

		@SuppressWarnings("all")
		class NamedLiteral extends AnnotationLiteral<Named> implements Named {

			@Override
			public String value() {
				return StringUtils.hasText(spring.name()) ? spring.name() : injectionType.getSimpleName();
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.Bean#getBeanClass()
		 */
		@Override
		public Class<?> getBeanClass() {
			return this.injectionType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.Bean#getInjectionPoints()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Set<InjectionPoint> getInjectionPoints() {
			return Collections.EMPTY_SET;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.BeanAttributes#getName()
		 */
		@Override
		public String getName() {
			return StringUtils.hasText(spring.name()) ? spring.name() : injectionType.getSimpleName();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.BeanAttributes#getQualifiers()
		 */
		@Override
		public Set<Annotation> getQualifiers() {
			Set<Annotation> qualifiers = new HashSet<Annotation>();
			qualifiers.add(new NamedLiteral()); // Added this because it
			qualifiers.add(spring);
			return qualifiers;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.BeanAttributes#getScope()
		 */
		@Override
		public Class<? extends Annotation> getScope() {
			return Dependent.class;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.BeanAttributes#getStereotypes()
		 */
		@Override
		public Set<Class<? extends Annotation>> getStereotypes() {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.BeanAttributes#getTypes()
		 */
		@Override
		public Set<Type> getTypes() {
			Set<Type> types = new HashSet<Type>();
			types.add(this.injectionType);
			types.add(Object.class);
			return types;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.BeanAttributes#isAlternative()
		 */
		@Override
		public boolean isAlternative() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.inject.spi.Bean#isNullable()
		 */
		@Override
		public boolean isNullable() {
			return spring != null ? !spring.required() : false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.enterprise.context.spi.Contextual#create(javax.enterprise.
		 * context.spi.CreationalContext)
		 */
		@Override
		public Object create(CreationalContext<Object> ctx) {
			ApplicationContext applicationContext = SpringLocator.getContext();
			if(applicationContext == null){
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				ServletContext servletContext = (ServletContext) externalContext.getContext();
				applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			}
			if (applicationContext == null) {
				if (spring != null) {
					LOGGER.error("############## spring name={} type={} \n\n\n", spring.name(), spring.type());
				}
				throw new IllegalStateException("applicationContext was null");
			}
			Object instance = null;
			if (spring != null) {
				if (StringUtils.hasText(spring.name())) {
					if (!spring.required()) {
						if (applicationContext.containsBean(spring.name())) {
							instance = applicationContext.getBean(spring.name(), spring.type());
						}
					} else {
						instance = applicationContext.getBean(spring.name(), spring.type());
					}
				} else {
					Class<?> beanType = Object.class.equals(spring.type()) ? injectionType : spring.type();
					instance = applicationContext.getBean(beanType);
				}
			}
			return instance;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.enterprise.context.spi.Contextual#destroy(java.lang.Object,
		 * javax.enterprise.context.spi.CreationalContext)
		 */
		@Override
		public void destroy(Object instance, CreationalContext<Object> ctx) {
			ctx.release();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return String.format("SpringBean(hc=%d, hc=%d, annotatedType=%s, qualifiers=%s)", this.hashCode(),
					SpringIntegrationExtention.this.hashCode(), this.annotatedType, this.getQualifiers());
		}

	}

}
