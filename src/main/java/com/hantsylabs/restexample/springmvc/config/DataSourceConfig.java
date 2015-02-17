package com.hantsylabs.restexample.springmvc.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Different configurations for different stages.
 * 
 * In development stage using an embedded database to get better performance.
 * 
 * In production, container managed DataSource is highly recommended.
 * 
 * @author hantsy
 *
 */
@Configuration
public class DataSourceConfig {

    @Inject
    private Environment env;

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    @Profile("staging")
    public DataSource testDataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl(env.getProperty("jdbc.url"));
        bds.setUsername(env.getProperty("jdbc.username"));
        bds.setPassword(env.getProperty("jdbc.password"));
        return bds;
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        JndiObjectFactoryBean ds = new JndiObjectFactoryBean();
        ds.setJndiName("jdbc/postDS");
        ds.setCache(true);

        return (DataSource) ds.getObject();
    }

}
