package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 服务于8084
 */
// 这里使用了 redis 就去扫描 redis 的组件
@ComponentScan(basePackages = "com.firenay.gmall.config")
@MapperScan(basePackages = "com.firenay.gmall.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class GmAllManageServiceApplication {

	//  本地访问											nginx
	// http://localhost:8084/{skuId}.html
	// http://127.0.0.1:8084/45.html						http://item.firenay.com/45.html
	public static void main(String[] args) {
		SpringApplication.run(GmAllManageServiceApplication.class, args);
	}

}
