package com.StockAlarms.StockAlarmsApplication.configuration;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Turian Ovidiu.
 * This class represent the repository configuration for the tests.
 */

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.StockAlarms.StockAlarmsApplication.domain"})
@EnableJpaRepositories(basePackages = {"com.StockAlarms.StockAlarmsApplication.repositories"})
@EnableTransactionManagement
public class RepositoryConfig {
}
