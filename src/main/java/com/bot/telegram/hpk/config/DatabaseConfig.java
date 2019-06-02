package com.bot.telegram.hpk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean(name = "hpkBotDatasource")
    @Primary
    public DataSource hpkBotDatasource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("hpk-bot-database.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("hpk-bot-database.datasource.url"));
        dataSource.setUsername(env.getProperty("hpk-bot-database.datasource.username"));
        dataSource.setPassword(env.getProperty("hpk-bot-database.datasource.password"));

        return dataSource;
    }

    @Bean(name = "hpkApiDatasource")
    @ConfigurationProperties("hpk-api-database.datasource")
    public DataSource hpkApiDatasource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("hpk-api-database.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("hpk-api-database.datasource.url"));
        dataSource.setUsername(env.getProperty("hpk-api-database.datasource.username"));
        dataSource.setPassword(env.getProperty("hpk-api-database.datasource.password"));

        return dataSource;
    }

    @Bean(name = "hpkBotDb")
    @Primary
    public NamedParameterJdbcTemplate primaryJdbcTemplate(@Qualifier("hpkBotDatasource") DataSource hpkBotDatasource) {
        return new NamedParameterJdbcTemplate(hpkBotDatasource);
    }

    @Bean(name = "hpkApiDb")
    public NamedParameterJdbcTemplate secondaryJdbcTemplate(@Qualifier("hpkApiDatasource") DataSource hpkApiDatasource) {
            return new NamedParameterJdbcTemplate(hpkApiDatasource);
    }
}
