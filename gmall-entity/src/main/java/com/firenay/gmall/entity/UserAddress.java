package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/26 19:24
 */
@Data
public class UserAddress implements Serializable {

	@Column
	@Id
	private String id;

	@Column
	private String userAddress;

	@Column
	private String userId;

	@Column
	private String consignee;

	@Column
	private String phoneNum;

	@Column
	private String isDefault;
}
