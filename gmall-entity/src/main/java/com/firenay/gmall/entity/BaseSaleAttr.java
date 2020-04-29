package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: BaseSale</p>
 * Description：所有商品的属性
 * date：2020/4/28 21:20
 */
@Data
public class BaseSaleAttr implements Serializable {

	@Id
	@Column
	String id ;

	@Column
	String name;
}
