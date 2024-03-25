package com.warner.lcs.common.config;


import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ResourceBundle;


@Configuration
@EnableTransactionManagement
public class LcsConfiguration {

    @Autowired
    private Environment environment;

    public LcsConfiguration() { super(); }

    @Bean
    public DataSource getMysqlDataSource() throws Exception
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean
    @Qualifier("lcsDataSourceTemplate")
    public JdbcTemplate lcsDataSource() throws Exception
    {
        return  new JdbcTemplate(getMysqlDataSource());
    }

}
