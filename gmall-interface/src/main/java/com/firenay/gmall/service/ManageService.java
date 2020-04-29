package com.firenay.gmall.service;

import com.firenay.gmall.entity.*;

import java.util.List;

/**
 * <p>Title: ManageService</p>
 * Description：一二三级分类
 * date：2020/4/27 16:38
 */
public interface ManageService {

	/**
	 * 获取所有一级分类数据
	 */
	List<BaseCatalog1> getBaseCatalog1();

	/**
	 * 根据一级分类去查询二级分类数据
	 */
	List<BaseCatalog2> getBaseCatalog2(String catalog1Id);

	/**
	 * 根据二级分类去查询三级分类数据
	 */
	List<BaseCatalog3> getBaseCatalog3(String catalog2Id);

	/**
	 * 根据三级分类id 查询平台属性集合
	 */
	List<BaseAttrInfo> getAttrList(String catalog3Id);


	/**
	 * 后台操作保存平台数据
	 */
	void saveAttrInfo(BaseAttrInfo baseAttrInfo);

	/**
	 * 根据平台属性Id 查询平台属性值集合
	 */
	List<BaseAttrValue> getAttrValueList(String attrId);

	/**
	 * 根据平台属性Id 查询平台属性对象
	 * @param attrId
	 * @return
	 */
	BaseAttrInfo getAttrInfo(String attrId);

	/**
	 * 根据SPU对象属性获取spuInfo集合
	 */
	List<SpuInfo> getSpuList(SpuInfo spuInfo);

	/**
	 * 获取所有的销售属性
	 */
	List<BaseSaleAttr> getBaseSaleAttrList();

	/**
	 * 保存商品的各个属性
	 */
	int saveSpuInfo(SpuInfo spuInfo);

	/**
	 * 获取所有的商品图片
	 */
	List<SpuImage> getSpuImageList(SpuImage spuImage);

	/**
	 * 根据spuID 获取销售属性
	 */
	List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

	/**
	 * 保存SkuInfo数据
	 */
	int saveSkuInfo(SkuInfo skuInfo);
}
