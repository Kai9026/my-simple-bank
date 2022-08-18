package com.github.kai9026.mysimplebank.infrastructure.configuration;

import java.util.Locale;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@ComponentScan(basePackages = "com.github.kai9026.mysimplebank.infrastructure")
@EnableJpaRepositories(basePackages = "com.github.kai9026.mysimplebank.infrastructure.database.repository")
@EntityScan(basePackages = "com.github.kai9026.mysimplebank.infrastructure.database.entity")
public class AppConfiguration {

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    Locale locale = new Locale("en", "UK");
    sessionLocaleResolver.setDefaultLocale(locale);
    return sessionLocaleResolver;
  }
}
