package com.github.kai9026.mysimplebank.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.github.kai9026.mysimplebank")
public class MySimpleBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySimpleBankApplication.class, args);
	}

}
