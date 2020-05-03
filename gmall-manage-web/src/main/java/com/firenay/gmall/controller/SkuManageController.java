package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.entity.SkuInfo;
import com.firenay.gmall.entity.SkuLsInfo;
import com.firenay.gmall.entity.SpuImage;
import com.firenay.gmall.entity.SpuSaleAttr;
import com.firenay.gmall.service.ListService;
import com.firenay.gmall.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title: SkuManageController</p>
 * Description：Sku的数据的查询与保存到ES
 * date：2020/4/29 11:12
 */
@RestController
@CrossOrigin
@Slf4j
public class SkuManageController {

	@Reference
	private ManageService manageService;

	@Reference
	private ListService listService;


	@RequestMapping("spuImageList")
	public List<SpuImage> spuImageList(SpuImage spuImage){
		return manageService.getSpuImageList(spuImage);
	}

	/**
	 *  返回所有销售属性
	 */
	@RequestMapping("spuSaleAttrList")
	public List<SpuSaleAttr> spuSaleAttrList(String spuId){
		return manageService.getSpuSaleAttrList(spuId);
	}

	/**
	 * 保存的东西的属性全部来自平台 即数据库
	 */
	@RequestMapping("saveSkuInfo")
	public String saveSkuInfo(@RequestBody SkuInfo skuInfo){
		String flag = "保存失败";
		if(skuInfo != null){
			flag = manageService.saveSkuInfo(skuInfo) > 0 ? "保存成功":"保存失败";
		}
		log.info(flag + ": " + skuInfo);
		return flag;
	}

	/**
	 * 上传一个商品
	 */
	@RequestMapping("/onSale")
	public void onSale(String skuId){

		SkuLsInfo skuLsInfo = new SkuLsInfo();

		SkuInfo skuInfo = manageService.getSkuInfo(skuId);

		// 从skuInfo拷贝数据到skuLsInfo
		BeanUtils.copyProperties(skuInfo, skuLsInfo);
		listService.saveSkuLsInfo(skuLsInfo);
	}
}
