package com.firenay.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/26 19:24
 */
@Data
@NoArgsConstructor
public class BaseCatalog3 implements Serializable {
    @Id
    @Column
    private String id;

    @Column
    private String name;

	/**
	 * 方便二级标题查找三级标题
	 */
	@Column
    private String catalog2Id;

	public BaseCatalog3(String catalog2Id) {
		this.catalog2Id = catalog2Id;
	}
}
