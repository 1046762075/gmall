package com.firenay.gmall.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.firenay.gmall.entity.UserAddress;
import com.firenay.gmall.entity.UserInfo;
import com.firenay.gmall.mapper.UserAddressMapper;
import com.firenay.gmall.mapper.UserInfoMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: UserServiceImpl</p>
 * Description：交给 dubbo 管理
 * date：2020/4/26 18:16
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserInfoMapper userInfoMapper;

	@Resource
	private UserAddressMapper userAddressMapper;

	@Override
	public List<UserInfo> findAll() {
		return userInfoMapper.selectAll();
	}

	/**
	 * @param 传入用户的 id
	 * @return
	 */
	@Override
	public List<UserAddress> getUserAddressList(String userId) {
		UserAddress userAddress = new UserAddress();
		userAddress.setUserId(userId);
		return userAddressMapper.select(userAddress);
	}
}
