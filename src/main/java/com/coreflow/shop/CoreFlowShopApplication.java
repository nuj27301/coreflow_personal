package com.coreflow.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 시큐리티 기능 해제.
public class CoreFlowShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreFlowShopApplication.class, args);
	}

}
