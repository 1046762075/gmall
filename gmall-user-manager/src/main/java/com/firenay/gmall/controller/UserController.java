package com.firenay.gmall.controller;

import com.firenay.gmall.entity.UserInfo;
import com.firenay.gmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: UserController</p>
 * Description：
 * date：2020/4/26 18:19
 */
@RestController
@Slf4j
public class UserController {

	@Resource
	private UserService userService;

	@GetMapping("findAll")
	public List<UserInfo> findAll(){
		return userService.findAll();
	}
}
