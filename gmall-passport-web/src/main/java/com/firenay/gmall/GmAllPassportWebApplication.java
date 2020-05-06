package com.firenay.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan("com.firenay.gmall.config")
@SpringBootApplication
public class GmAllPassportWebApplication {

//	URL转义：http://localhost:8087/index?originUrl=http%3A%2F%2Fwww.firenay.com%2F
//	Nginx: http://passport.firenay.com/index?originUrl=http%3A%2F%2Fwww.firenay.com%2F
//	token认证登录：http://passport.firenay.com/verify?token=eyJhbGciOiJIUzI1NiJ9.eyJuaWNrTmFtZSI6ImxzbCIsInVzZXJJZCI6IjMifQ.VIP0A6C-BhQePnZt8oTRn4op04p0rPGdeB8xgrGLzxs&salt=10.43.1.1
//	认证测试登录：http://passport.firenay.com/index?originUrl=http%3A%2F%2Fitem.firenay.com%2F38.html
	public static void main(String[] args) {
		SpringApplication.run(GmAllPassportWebApplication.class, args);
	}
}
