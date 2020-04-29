package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Title: UserInfo</p>
 * Description：往平台保存数据
 * date：2020/4/29 14:24
 */
@Data
public class SkuInfo implements Serializable {

    @Id
    @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

	@Column
	private String spuId;

	@Column
	private BigDecimal price;

    @Column
    private String skuName;

    @Column
    private String weight;

	@Column
	private String skuDesc;

	@Column
	private String catalog3Id;

	@Column
	private String skuDefaultImg;

	@Transient
	List<SkuImage> skuImageList;

	@Transient
	List<SkuAttrValue> skuAttrValueList;

	@Transient
	private List<SkuSaleAttrValue> skuSaleAttrValueList;
}
