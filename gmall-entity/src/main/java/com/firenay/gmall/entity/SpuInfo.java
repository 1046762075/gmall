package com.firenay.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: SupInfo</p>
 * Description：
 * date：2020/4/28 11:58
 */
@Data
@NoArgsConstructor
public class SpuInfo implements Serializable {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column
	private String spuName;

	@Column
	private String description;

	@Column
	private  String catalog3Id;

	/**
	 * 销售属性集合
	 */
	@Transient
	private List<SpuSaleAttr> spuSaleAttrList;

	/**
	 * 图片列表
	 */
	@Transient
	private List<SpuImage> spuImageList;


	public SpuInfo(String catalog3Id) {
		this.catalog3Id = catalog3Id;
	}
}
