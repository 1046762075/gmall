package com.fireany.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GmAllItemWebApplication {
				// http://localhost:8084/{skuId}.html
				// http://127.0.0.1:8084/35.html
	public static void main(String[] args) {
		SpringApplication.run(GmAllItemWebApplication.class, args);
	}

}
