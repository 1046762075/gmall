package com.firenay.gmall.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * <p>Title: UserInfo</p>
 * Description：与ES中自定义mapping一一对应
 * date：2020/5/2 16:24
 */
@Data
public class SkuLsInfo implements Serializable {

	String id;

	BigDecimal price;

	String skuName;

	String catalog3Id;

	String skuDefaultImg;

	/**
	 * 自定义一个字段保存热度评分
	 */
	Long hotScore = 0L;

	List<SkuLsAttrValue> skuAttrValueList;
}
