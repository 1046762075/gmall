package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.firenay.gmall")
@SpringBootApplication
public class GmAllManageWebApplication {
	//  本地访问												nginx
	//	http://localhost:520/getCatalog1						http://manage.gmall.com/getCatalog1
	//	http://localhost:520/getCatalog2						http://manage.gmall.com/getCatalog2
	//	http://localhost:520/getCatalog3 						http://manage.gmall.com/getCatalog3

	//  http://10.43.1.52/group1/M00/00/00/CisBNF6pOu2AbyrhAAWnckuboSE242.jpg
	//  向ES保存数据：	http://localhost:520/onSale?skuId=45	http://manage.gmall.com/onSale?skuId=45
	public static void main(String[] args) {
		SpringApplication.run(GmAllManageWebApplication.class, args);
	}
}
