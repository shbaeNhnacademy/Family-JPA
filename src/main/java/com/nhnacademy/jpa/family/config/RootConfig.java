package com.nhnacademy.jpa.family.config;

import com.fasterxml.jackson.databind.util.Converter;
import com.nhnacademy.jpa.family.Base;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@ComponentScan(basePackageClasses = Base.class,
        excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
        basicDataSource.setUrl("jdbc:mysql://localhost/Family");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("123456789");
        basicDataSource.setInitialSize(2);
        basicDataSource.setMaxTotal(2);
        basicDataSource.setMinIdle(2);
        basicDataSource.setMaxIdle(2);

        basicDataSource.setMaxWaitMillis(1000);

        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setTestOnReturn(true);
        basicDataSource.setTestWhileIdle(true);

        return basicDataSource;
    }


}
