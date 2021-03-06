package com.firenay.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: SkuImage</p>
 * Description：
 * date：2020/4/29 14:30
 */
@Data
@NoArgsConstructor
public class SkuImage implements Serializable {

	@Id
	@Column
	private String id;

	@Column
	private String skuId;

	@Column
	private String imgName;

	@Column
	private String imgUrl;

	@Column
	private String spuImgId;

	@Column
	private String isDefault;

	public SkuImage(String skuId) {
		this.skuId = skuId;
	}
}
