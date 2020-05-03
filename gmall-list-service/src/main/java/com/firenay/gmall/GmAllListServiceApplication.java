package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GmAllListServiceApplication {
//	单个属性查找									多级属性查找
//	http://127.0.0.1:8086/fireNaySearch.html?catalog3Id=307
// 	http://127.0.0.1:8086/fireNaySearch.html?catalog3Id=61	http://list.gmall.com/fireNaySearch.html?catalog3Id=61&valueId=82
	public static void main(String[] args) {
		SpringApplication.run(GmAllListServiceApplication.class, args);
	}

}
