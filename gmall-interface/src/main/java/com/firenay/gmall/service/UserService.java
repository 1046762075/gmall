package com.firenay.gmall.service;

import com.firenay.gmall.entity.UserAddress;
import com.firenay.gmall.entity.UserInfo;

import java.util.List;

/**
 * <p>Title: UserService</p>
 * Description：
 * date：2020/4/26 17:40
 */
public interface UserService {

	List<UserInfo> findAll();

	List<UserAddress> getUserAddressList(String userId);

	/**
	 * 用户登录方法
	 */
	UserInfo login(UserInfo userInfo);

	/**
	 * 根据用户Id判断用户的token是否正确从而得知用户是否登录
	 */
	UserInfo verify(String userId);
}
