package com.github.kai9026.mysimplebank.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.github.kai9026.mysimplebank.infrastructure")
@EnableJpaRepositories(basePackages = "com.github.kai9026.mysimplebank.infrastructure.database.repository")
@EntityScan(basePackages = "com.github.kai9026.mysimplebank.infrastructure.database.entity")
public class AppConfiguration {

}
