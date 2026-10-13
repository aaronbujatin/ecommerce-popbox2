package org.xyz.cartsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CartsvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartsvcApplication.class, args);
	}

}
