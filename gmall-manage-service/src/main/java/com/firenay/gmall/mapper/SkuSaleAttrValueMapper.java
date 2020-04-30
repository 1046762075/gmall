package com.firenay.gmall.mapper;

import com.firenay.gmall.entity.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>Title: UserInfo</p>
 * Description：
 * date：2020/4/29 14:42
 */
public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {
	List<SkuSaleAttrValue> selectSkuSaleAttrValueListBySpu(String spuId);
}
