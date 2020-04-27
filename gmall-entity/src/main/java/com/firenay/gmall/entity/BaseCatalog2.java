package com.firenay.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseCatalog2 implements Serializable {

    @Id
    @Column
    private String id;

    @Column
    private String name;

	/**
	 * 方便一级标题查找二级标题
	 */
	@Column
    private String catalog1Id;

	public BaseCatalog2(String catalog1Id) {
		this.catalog1Id = catalog1Id;
	}
}
