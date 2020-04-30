package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
/**
 * <p>Title: UserInfo</p>
 * Description：销售属性
 * date：2020/4/28 21:42
 */
@Data
public class SpuSaleAttr  implements Serializable {

    @Id
    @Column
    String id ;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrName;

    @Transient
	List<SpuSaleAttrValue> spuSaleAttrValueList;
}
