package com.firenay.gmall.mapper;

import com.firenay.gmall.entity.BaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {
	/**
	 * 根据三级分类ID 查询平台属性
	 */
	List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(String catalog3Id);

	/**
	 * 平台属性值Id查询
	 */
	List<BaseAttrInfo> selectAttrInfoListByIds(@Param("valueIds") String valueIds);
}
