package com.firenay.gmall;

import com.firenay.gmall.config.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmAllPassportWebApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testJWT() {
		String key = "firenay";
		HashMap<String, Object> map = new HashMap<>();
		map.put("userId", 1001);
		map.put("nickName", "admin");
		String salt = "192.168.1.100";
		String token = JwtUtil.encode(key, map, salt);

		System.out.println("token: " + token);
		// token = eyJhbGciOiJIUzI1NiJ9.eyJuaWNrTmFtZSI6ImFkbWluIiwidXNlcklkIjoxMDAxfQ.oFhNSzhtVmzKs2WDR1VW3FlyGBkGtqCsis3plvAODVY

		// 解密token
		Map<String, Object> maps = JwtUtil.decode(token, key, "192.168.1.100");
		System.out.println(maps);

	}
}
