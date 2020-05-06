package com.firenay.gmall.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.firenay.gmall.config.RedisUtils;
import com.firenay.gmall.entity.UserAddress;
import com.firenay.gmall.entity.UserInfo;
import com.firenay.gmall.mapper.UserAddressMapper;
import com.firenay.gmall.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

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

	@Autowired
	private RedisUtils redisUtils;

	public String userKey_prefix = "user:";
	public String userInfoKey_suffix = ":info";
	public int userKey_timeOut = 60 * 60 * 24;


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

	/**
	 * 登录之后将用户信息保存到 redis
	 */
	@Override
	public UserInfo login(UserInfo userInfo) {
		UserInfo info = null;
		Jedis redis = null;
		String passwd = userInfo.getPasswd();
		// 将普通字符串加密存到数据库
		String newPwd = DigestUtils.md5DigestAsHex(passwd.getBytes());
		userInfo.setPasswd(newPwd);
		try {
			info = userInfoMapper.selectOne(userInfo);
			if (info != null) {
				// 获取 redis
				redis = redisUtils.getRedis();
				// 放入 redis ,必须起key = user:userId:info
				String userKey = userKey_prefix + info.getId() + userInfoKey_suffix;
				// 哪种数据类型
				redis.setex(userKey, userKey_timeOut, JSON.toJSONString(info));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(redis != null){
				redis.close();
			}
			return info;
		}
	}

	/**
	 * 去redis里面查看是否有用户的信息
	 */
	@Override
	public UserInfo verify(String userId) {
		// 去redis里面查询用户的资料
//		{"email":"lsl@qq.com","headImg":"3","id":"3","loginName":"lsl","name":"lsl","nickName":"lsl","passwd":"202cb962ac59075b964b07152d234b70","phoneNum":"333","userLevel":"3"}
		UserInfo userInfo = null;
		Jedis redis = null;
		try {
			redis = redisUtils.getRedis();
			String userKey = userKey_prefix + userId + userInfoKey_suffix;
			String userJson = redis.get(userKey);
			if(!StringUtils.isEmpty(userJson)){
				userInfo = JSON.parseObject(userJson, UserInfo.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(redis != null){
				redis.close();
			}
			return userInfo;
		}
	}
}
