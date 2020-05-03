package com.firenay.gmall.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuLsResult implements Serializable {

	/**
	 * 查出来的总条数
	 */
	List<SkuLsInfo> skuLsInfoList;

	long total;

	long totalPages;

	List<String> attrValueIdList;
}
