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
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/26 19:24
 */
@Data
@NoArgsConstructor
public class BaseAttrInfo implements Serializable {

    @Id
    @Column
	// 开启自增
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String attrName;

    @Column
    private String catalog3Id;

	public BaseAttrInfo(String catalog3Id) {
		this.catalog3Id = catalog3Id;
	}

	/**
	 * 用于后台操作来保存数据
	 * Transient :表示当前字段不是表中的字段是 业务需要使用的
	 */
	@Transient
	private List<BaseAttrValue> attrValueList;
}
