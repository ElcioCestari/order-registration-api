package com.brotherselectronics.orderregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderRegistrationApplication {

	public static void main(String[] args) {
//		var encoder = new BCryptPasswordEncoder();
//		var encoded = encoder.encode("123");
//		System.out.println("encoded = " + encoded);
		SpringApplication.run(OrderRegistrationApplication.class, args);
	}

}
