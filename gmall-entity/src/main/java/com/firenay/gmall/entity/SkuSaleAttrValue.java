package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: SkuSaleAttrValue</p>
 * Description：
 * date：2020/4/29 14:36
 */
@Data
public class SkuSaleAttrValue implements Serializable {

	@Id
	@Column
	private String id;

	@Column
	private String skuId;

	@Column
	private String saleAttrId;

	@Column
	private String saleAttrValueId;

	@Column
	private String saleAttrName;

	@Column
	private String saleAttrValueName;
}
