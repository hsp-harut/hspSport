package am.chronograph.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * The spring managed hibernate configuration.
 * @author tigranbabloyan
 *
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	private static final String[] ENTITY_PACKAGES = { "am.chronograph.domain",
			"org.springframework.data.jpa.convert.threeten" };
	private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";
	private static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DB_URL = "db.url";
	private static final String PROPERTY_NAME_DB_USER = "db.username";
	private static final String PROPERTY_NAME_DB_AUTOCOMMIT = "db.autocommit";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_USE_UNICODE = "hibernate.connection.useUnicode";
	private static final String PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
	private static final String PROPERTY_NAME_HIBERNATE_CHAR_SET = "hibernate.connection.charSet";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.implicit_naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_REGION_CACHE = "hibernate.cache.region.factory_class";
	private static final String PROPERTY_NAME_HIBERNATE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
	private static final String PROPERTY_NAME_HIBERNATE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
	
	/**
	 * Creates and configures the HikariCP datasource bean.
	 * 
	 * @param env
	 *            The runtime environment of our application.
	 * @return
	 */
	@Bean(destroyMethod = "close")
	DataSource dataSource(Environment env) {
		HikariConfig dataSourceConfig = new HikariConfig();
		Boolean bb = env.getProperty(PROPERTY_NAME_DB_AUTOCOMMIT, Boolean.class);
		dataSourceConfig.setAutoCommit(bb);
		dataSourceConfig.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DB_URL));
		dataSourceConfig.setUsername(env.getRequiredProperty(PROPERTY_NAME_DB_USER));
		dataSourceConfig.setPassword(env.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));
		return new HikariDataSource(dataSourceConfig);
	}

	/**
	 * Creates the bean that creates the JPA entity manager factory.
	 * 
	 * @param dataSource
	 *            The datasource that provides the database connections.
	 * @param env
	 *            The runtime environment of our application.
	 * @return
	 */
	@Bean
	LocalSessionFactoryBean sessionFactory(DataSource dataSource, Environment env) {
		LocalSessionFactoryBean entityManagerFactoryBean = new LocalSessionFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);
		Properties jpaProperties = new Properties();
		// Configures the used database dialect. This allows Hibernate to create
		// SQL
		// that is optimized for the used database.
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		// Specifies the action that is invoked to the database when the
		// Hibernate
		// SessionFactory is created or closed.
		// Configures the naming strategy that is used when Hibernate creates
		// new database objects and schema elements
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		// If the value of this property is true, Hibernate will use prettyprint
		// when it writes SQL to the console.
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));

		// Set character encoding to UTF-8
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_USE_UNICODE, true);
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING, "UTF-8");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_CHAR_SET, "UTF-8");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_USE_SECOND_LEVEL_CACHE, "true");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_USE_QUERY_CACHE, "true");
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_REGION_CACHE, "org.hibernate.cache.ehcache.EhCacheRegionFactory");

		entityManagerFactoryBean.setHibernateProperties(jpaProperties);
		return entityManagerFactoryBean;
	}

	/**
	 * Creates the transaction manager bean that integrates the used JPA
	 * provider with the Spring transaction mechanism.
	 * 
	 * @param entityManagerFactory
	 *            The used JPA entity manager factory.
	 * @return
	 */
	@Bean
	HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public TransactionTemplate sharedTransactionTemplate(HibernateTransactionManager transactionManager){
		TransactionTemplate template = new TransactionTemplate();
		template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		template.setTransactionManager(transactionManager);
		return template;
	}
}
