package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.entity.UserAddress;
import com.firenay.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>Title: OrderController</p>
 * Description：
 * date：2020/4/26 18:34
 */
@Controller
public class OrderController {

	/**
	 * 启动dubbo注册中心的服务
	 */
	@Reference
	private UserService userService;

	@RequestMapping("trade")
	public String trade(){
		return "index";
	}

	@ResponseBody
	@RequestMapping("getAddress")
	public List<UserAddress> getAddress(String userId){
		return userService.getUserAddressList(userId);
	}
}
