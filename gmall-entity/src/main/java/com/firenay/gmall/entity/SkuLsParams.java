package com.firenay.gmall.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuLsParams implements Serializable {


	/**
	 * 相当于SkuName
	 */
	String keyword;

	String catalog3Id;

	/**
	 * 平台属性值ID
	 */
	String[] valueId;

	int pageNo = 1;

	int pageSize = 2;
}
