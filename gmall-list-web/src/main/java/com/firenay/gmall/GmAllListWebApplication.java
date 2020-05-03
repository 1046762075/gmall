package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GmAllListWebApplication {
	//  本地访问													nginx
	// 	http://127.0.0.1:8086/fireNaySearch.html?catalog3Id=61		http://list.gmall.com/fireNaySearch.html?catalog3Id=61
	//	http://list.gmall.com/fireNaySearch.html?keyword=nova		http://list.gmall.com/fireNaySearch.html?catalog3Id=61&valueId=82
	public static void main(String[] args) {
		SpringApplication.run(GmAllListWebApplication.class, args);
	}
}
