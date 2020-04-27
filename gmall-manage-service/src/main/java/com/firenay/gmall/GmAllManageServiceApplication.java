package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.firenay.gmall.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class GmAllManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmAllManageServiceApplication.class, args);
	}

}
