package com.fireany.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.firenay.gmall")
public class GmAllItemWebApplication {
	//  本地访问											nginx
	// http://localhost:8084/{skuId}.html
	// http://127.0.0.1:8084/45.html						http://item.firenay.com/45.html
	// http://127.00.1:8084/38.html						http://item.firenay.com/38.html
	public static void main(String[] args) {
		SpringApplication.run(GmAllItemWebApplication.class, args);
	}
}
