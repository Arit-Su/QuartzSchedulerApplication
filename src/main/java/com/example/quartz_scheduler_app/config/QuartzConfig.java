package com.example.quartz_scheduler_app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Configuration class to manually initialize the Quartz database schema.
 * This provides a more robust alternative to the automatic initialization
 * when it fails for environmental reasons.
 */
@Configuration
public class QuartzConfig {

    @Bean
    public DataSourceInitializer quartzDataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        // The path to the official Quartz H2 schema script within the library's JAR file
        resourceDatabasePopulator.addScript(new ClassPathResource("org/quartz/impl/jdbcjobstore/tables_h2.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }
}