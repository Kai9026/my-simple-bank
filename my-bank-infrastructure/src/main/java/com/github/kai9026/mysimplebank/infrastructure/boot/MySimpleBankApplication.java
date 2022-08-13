package com.github.kai9026.mysimplebank.infrastructure.boot;

import com.github.kai9026.mysimplebank.infrastructure.configuration.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfiguration.class)
public class MySimpleBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySimpleBankApplication.class, args);
  }

}
