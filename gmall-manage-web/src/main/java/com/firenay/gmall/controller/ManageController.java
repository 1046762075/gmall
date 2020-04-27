package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.entity.BaseAttrInfo;
import com.firenay.gmall.entity.BaseAttrValue;
import com.firenay.gmall.entity.BaseCatalog1;
import com.firenay.gmall.entity.BaseCatalog2;
import com.firenay.gmall.entity.BaseCatalog3;
import com.firenay.gmall.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title: ManageController</p>
 * Description：http://localhost:520/getCatalog2
 * date：2020/4/27 16:19
 */
@RestController
// 跨域请求
@CrossOrigin
public class ManageController {

	@Reference
	private ManageService manageService;

	/**
	 * 获取一级分类
	 * http://localhost:520/getCatalog1
	 */
	@RequestMapping("getCatalog1")
	public List<BaseCatalog1> getCatalog1(){
		return manageService.getBaseCatalog1();
	}

	/**
	 * 获取二级分类
	 * http://localhost:520/getCatalog2?catalogId=2
	 */
	@RequestMapping("getCatalog2")
	public List<BaseCatalog2> getCatalog2(String catalogId){
		return manageService.getBaseCatalog2(catalogId);
	}

	/**
	 * 获取三级分类
	 * http://localhost:520/getCatalog3?catalog2Id=2
	 */
	@RequestMapping("getCatalog3")
	public List<BaseCatalog3> getCatalog3(String catalog2Id){
		return manageService.getBaseCatalog3(catalog2Id);
	}

	/**
	 * 获取数据
	 * http://localhost:520/attrInfoList
	 * http://localhost:520/attrInfoList?catalog3Id=61
	 */
	@RequestMapping("attrInfoList")
	public List<BaseAttrInfo> attrInfoList(String catalog3Id){
		return manageService.getAttrList(catalog3Id);
	}



	@RequestMapping("saveAttrInfo")
	public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
		System.out.println(baseAttrInfo);
		manageService.saveAttrInfo(baseAttrInfo);
	}

	@RequestMapping("getAttrValueList")
	public List<BaseAttrValue> getAttrValueList(String attrId){
		// 获取 fireany 商城已有商品
		BaseAttrInfo attrInfo = manageService.getAttrInfo(attrId);
		// 查询已有的商品
		return attrInfo.getAttrValueList();
	}
}
