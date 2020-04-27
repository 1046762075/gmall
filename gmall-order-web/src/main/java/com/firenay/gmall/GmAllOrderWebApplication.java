package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GmAllOrderWebApplication {
						//	http://127.0.0.1:8081/getAddress
						//	http://127.0.0.1:8081/trade
	public static void main(String[] args) {
		SpringApplication.run(GmAllOrderWebApplication.class, args);
	}

}
