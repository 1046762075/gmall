package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.firenay.gmall.mapper")
@SpringBootApplication
public class GmAllUserManagerApplication {

					//http://127.0.0.1:8080/findAll
	public static void main(String[] args) {
		SpringApplication.run(GmAllUserManagerApplication.class, args);
	}

}
