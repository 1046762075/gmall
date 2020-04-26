package com.firenay.gmall.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/25 22:10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

	@Id
	@Column
	// 获取主键自增
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column
	private String loginName;

	@Column
	private String nickName;

	private String passwd;

	@Column
	private String name;

	@Column
	private String phoneNum;

	@Column
	private String email;

	@Column
	private String headImg;

	@Column
	private String userLevel;

}
