package com.firenay.gmall.service;

import com.firenay.gmall.entity.SkuLsInfo;
import com.firenay.gmall.entity.SkuLsParams;
import com.firenay.gmall.entity.SkuLsResult;

/**
 * <p>Title: ListService</p>
 * Description：
 * date：2020/5/2 16:43
 */
public interface ListService {

	/**
	 * 保存数据到ES
	 */
	void saveSkuLsInfo(SkuLsInfo skuLsInfo);

	/**
	 * 检数据
	 */
	SkuLsResult search(SkuLsParams skuLsParams);

	/**
	 * 记录每个商品被访问的次数
	 */
	void incrHotScore(String skuId);
}
