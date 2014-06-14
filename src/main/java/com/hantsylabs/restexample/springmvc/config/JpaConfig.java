package com.hantsylabs.restexample.springmvc.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hantsylabs.restexample.springmvc.model.User;
import com.hantsylabs.restexample.springmvc.security.SecurityUtil;

@Configuration
@EnableJpaRepositories(basePackages = {"com.hantsylabs.restexample.springmvc"})
@EnableJpaAuditing(auditorAwareRef = "auditor")
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class JpaConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);

    @Inject
    private Environment env;

    @Inject
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.hantsylabs.restexample.springmvc");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    private Properties jpaProperties() {
        Properties extraProperties = new Properties();
        extraProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        extraProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        extraProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        if (log.isDebugEnabled()) {
            log.debug(" hibernate.dialect @" + env.getProperty("hibernate.dialect"));
        }
        if (env.getProperty("hibernate.dialect") != null) {
            extraProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        }
        return extraProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    public AuditorAware<User> auditor() {
        return new AuditorAware<User>() {

            @Override
            public User getCurrentAuditor() {
                return SecurityUtil.currentUser();
            }
        };
    }

}
