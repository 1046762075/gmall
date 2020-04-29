package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/27 21:40
 */
@Data
public class SpuSaleAttrValue implements Serializable {

    @Id
    @Column
    String id ;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;

	/**
	 * 当前的属性值是否被选中
	 */
	@Transient
    String isChecked;
 }
