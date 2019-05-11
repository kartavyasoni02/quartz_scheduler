package com.scheduler.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:quartz.properties" })
public class QuartzConfig  extends WebMvcConfigurerAdapter implements TransactionManagementConfigurer {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QuartzConfig.class);
	
	
	@Autowired
	private Environment environment;
	
	@Bean
	public SchedulerFactoryBean quartzScheduler(JobFactory jobFactory) {
		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
		quartzScheduler.setQuartzProperties(quartzProperties());
		quartzScheduler.setJobFactory(jobFactory);
		quartzScheduler.setWaitForJobsToCompleteOnShutdown(true);
		return quartzScheduler;
	}

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		RegisterSpringBeanJobFactory jobFactory = new RegisterSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}


	@Bean
	public Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		Properties properties = null;
		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();
		}
		catch (IOException e) {
			LOGGER.error("Exception in quartzProperties()",e);
		}
		return properties;
	}
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		LOGGER.info("datasource loading... ");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("org.quartz.dataSource.quartzDataSource.driver"));
		dataSource.setUrl(environment.getRequiredProperty("org.quartz.dataSource.quartzDataSource.URL"));
		dataSource.setUsername(environment.getRequiredProperty("org.quartz.dataSource.quartzDataSource.user"));
		dataSource.setPassword(environment.getRequiredProperty("org.quartz.dataSource.quartzDataSource.password"));
		LOGGER.info("datasource loading completed ");
		return dataSource;
	}
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	@Bean
	public InternalResourceViewResolver viewResolver() throws Exception{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(environment.getRequiredProperty("resolver.setPrefix"));
		resolver.setSuffix(environment.getRequiredProperty("resolver.setSuffix"));
		return resolver;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
	    return new DataSourceTransactionManager(dataSource());
	}
	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception{
		return new JdbcTemplate(dataSource());
	}

	@Override
	 public PlatformTransactionManager annotationDrivenTransactionManager() {
	     return transactionManager();
	 }
	 
}