package com.firenay.gmall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>Title: BaseCatalog1</p>
 * Description：一级标题
 * date：2020/4/27 16:32
 */
@Data
public class BaseCatalog1 implements Serializable {

	@Id
	@Column
	private String id;

	@Column
	private String name;

}
