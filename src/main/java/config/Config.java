package config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"repo"})
public class Config {
	
	@Bean 
	public DataSource dataSource() {  
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/practice");
		ds.setUsername("root");
	    ds.setPassword("root");	 
	    ds.setInitialSize(5);
	    ds.setMaxActive(10);
		return ds;  
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter =new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.MYSQL);
		return adapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds,
			JpaVendorAdapter jpaVendorAdapter){
		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", String.valueOf(true));
		LocalContainerEntityManagerFactoryBean emf = 
				new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(ds);
		emf.setPackagesToScan("model");
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setJpaProperties(props);
		return emf;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		return new JpaTransactionManager(emf);
	}
	
	@Bean
	public BeanPostProcessor persistentTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public PersistenceAnnotationBeanPostProcessor paPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
}
}
