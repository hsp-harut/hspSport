package am.chronograph;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

import am.chronograph.config.EmailConfig;
import am.chronograph.config.HibernateConfig;
import am.chronograph.config.LiquibaseConfig;
import am.chronograph.config.SecurityConfig;
import am.chronograph.config.ThymeleafConfig;

/**
 * Spring managed application context for running a junit tests.
 * @author tigranbabloyan
 *
 */
@Configuration
@ComponentScan(basePackages = { "am.chronograph.service", "am.chronograph.security", "am.chronograph.dao", "am.chronograph.aop", "am.chronograph.util" })
@Import({EmailConfig.class, SecurityConfig.class, LiquibaseConfig.class, HibernateConfig.class, ThymeleafConfig.class})
@PropertySource({ "classpath:settings/config.${myhost}.properties" })
@EnableAspectJAutoProxy
public class TestApplicationContext{
	
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
