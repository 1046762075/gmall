package com.firenay.gmall.mapper;

import com.firenay.gmall.entity.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/29 21:52
 */
public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
	/**
	 * 自定义查询  内连接
	 * 多表查询必须使用 SpuSaleAttrMapper.xml
	 */
	List<SpuSaleAttr> selectSpuSaleAttrList(String spuId);
}
