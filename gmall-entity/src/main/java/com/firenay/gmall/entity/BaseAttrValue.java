package com.firenay.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
/**
 * <p>Title: UserInfo</p>
 * Description：urlParam：存放面包屑的值
 * date：2020/4/26 21:24
 */
@Data
@NoArgsConstructor
public class BaseAttrValue implements Serializable {

    @Id
    @Column
    private String id;

    @Column
    private String valueName;

    @Column
    private String attrId;

    @Transient
    private String urlParam;

	public BaseAttrValue(String attrId) {
		this.attrId = attrId;
	}
}
