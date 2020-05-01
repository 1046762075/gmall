package com.fireany.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.firenay.gmall.entity.SkuInfo;
import com.firenay.gmall.entity.SkuSaleAttrValue;
import com.firenay.gmall.entity.SpuSaleAttr;
import com.firenay.gmall.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: ItemController</p>
 * Description：前端页面展示
 * date：2020/4/29 21:13
 */
@Controller
@Slf4j
public class ItemController {

	@Reference
	private ManageService manageService;

	// localhost:8084/33.html
	// 控制器
	@RequestMapping("{skuId}.html")
	public String item(@PathVariable String skuId, HttpServletRequest request){
		// 根据 skuId 获取数据
		SkuInfo skuInfo = manageService.getSkuInfo(skuId);
		// 查询销售属性，销售属性值集合 spuId，skuId
		List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrListCheckBySku(skuInfo);

		request.setAttribute("spuSaleAttrList",spuSaleAttrList);

		// 获取销售属性值Id  33 、37就是一个skuId
		List<SkuSaleAttrValue> skuSaleAttrValueList = manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());
		// 遍历集合拼接字符串 {"167|170|163":"45" ,"153|151|152":"37"}
		// key = 167|170|163
		// map.put("167|170|163"，"45") 		JSON.toJSONString(map);
		String key = "";
		HashMap<String, Object> map = new HashMap<>();

		for (int i = 0; i < skuSaleAttrValueList.size(); i++) {
			SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueList.get(i);
			// 当本次循环的 skuId 与 下次循环的 skuId 不一致的时候！停止拼接。
			if (key.length() > 0){
				key += "|";
			}
			key += skuSaleAttrValue.getSaleAttrValueId();
			// 当循环到最后的的时候或者 商品属性值的id不等于下一个商品属性值的id时 就清空key
			if ((i + 1) == skuSaleAttrValueList.size() || !skuSaleAttrValue.getSkuId().equals( skuSaleAttrValueList.get(i + 1).getSkuId())){
				// 写 map  key : 所有的商品属性值    value : skuId
				map.put(key,skuSaleAttrValue.getSkuId());
				key = "";
			}
		}
		// 将map 转换为json 字符串 "用户访问：" + skuId + "号商品"
		String valuesSkuJson  = JSON.toJSONString(map);
		int i = 0;
		key ="\n用户访问：" + skuId + "号商品	拼接JSON [SaleAttrValueId... : skuId] => \n";
		for (String mapK : map.keySet()) {
			key += mapK + " : " + map.get(mapK) + "号";
			if(++i % 3 != 0){
				key +=  "\t";
			} else {
				key += "\r\n";
			}
		}
		log.info(key);
		// 保存json
		request.setAttribute("valuesSkuJson", valuesSkuJson);
		request.setAttribute("spuSaleAttrList", spuSaleAttrList);
		// 保存到作用域
		request.setAttribute("skuInfo",skuInfo);
		return "item";
	}
}

