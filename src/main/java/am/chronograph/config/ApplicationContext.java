package am.chronograph.config;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main configuration entry point.
 * @author tigranbabloyan
 *
 */
@Configuration
@ComponentScan(basePackages = { "am.chronograph.service", "am.chronograph.util", "am.chronograph.security", "am.chronograph.dao", "am.chronograph.aop" }, excludeFilters = {@Filter(Named.class),@Filter(Inject.class)})
@Import({EmailConfig.class, SecurityConfig.class, LiquibaseConfig.class, HibernateConfig.class, ThymeleafConfig.class})
@PropertySource(value = { "classpath:settings/config.${myhost}.properties", "file:${user.home}/.chrono/chrono3.properties" }, ignoreResourceNotFound = true)
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
public class ApplicationContext {
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("messages.messages", "messages.mail", "messages.custom", "messages.decls");
		return source;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
