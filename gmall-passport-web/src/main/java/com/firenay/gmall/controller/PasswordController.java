package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.config.JwtUtil;
import com.firenay.gmall.entity.UserInfo;
import com.firenay.gmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: PasswordController</p>
 * Description：
 * date：2020/5/4 22:22
 */
@Controller
@Slf4j
public class PasswordController {

	@Reference
	private UserService userService;

	@Value("${token.key}")
	private String key;

	@RequestMapping("index")
	public String index(HttpServletRequest request){
		String originUrl = request.getParameter("originUrl");
		log.info("\noriginUrl:" + originUrl);
		request.setAttribute("originUrl",originUrl);
		return "index";
	}

	@ResponseBody
	@RequestMapping("login")
	public String login(UserInfo userInfo, HttpServletRequest request){
		String salt = request.getHeader("X-firenay-for");
		UserInfo info = userService.login(userInfo);
		if(info != null){
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("userId", info.getId());
			hashMap.put("nickName", info.getNickName());
			String token = JwtUtil.encode(key, hashMap, salt);
			log.info("\nIP:" + salt + "\ttoken:" + token);
			return token;
		}else {
			return "fail";
		}
	}

	/**
	 * 用户登录验证
	 */
	@RequestMapping("verify")
	@ResponseBody
	public String verify(HttpServletRequest request){

		request.getHeader("X-firenay-for");
		String token = request.getParameter("token");
		String salt = request.getParameter("salt");

		Map<String, Object> decode = JwtUtil.decode(token, key, salt);

		if(decode != null && decode.size() > 0){
			String userId = (String) decode.get("userId");

			UserInfo userInfo = userService.verify(userId);
			if(userInfo != null){
				return "success";
			}
		}
		return "fail";
	}
}
