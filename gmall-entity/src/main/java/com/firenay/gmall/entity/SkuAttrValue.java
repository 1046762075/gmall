package com.firenay.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: SkuAttrValue</p>
 * Description：保存平台数据
 * date：2020/4/29 14:35
 */
@Data
@NoArgsConstructor
public class SkuAttrValue implements Serializable {

	@Id
	@Column
	private String id;

	@Column
	private String attrId;

	@Column
	private String valueId;

	@Column
	private String skuId;

	public SkuAttrValue(String skuId) {
		this.skuId = skuId;
	}
}
