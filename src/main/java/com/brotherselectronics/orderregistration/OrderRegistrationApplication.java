package com.brotherselectronics.orderregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderRegistrationApplication.class, args);
	}

}
