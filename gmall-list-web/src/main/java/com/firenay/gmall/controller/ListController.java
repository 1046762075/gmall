package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.entity.SkuLsParams;
import com.firenay.gmall.entity.SkuLsResult;
import com.firenay.gmall.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ListController {

	@Reference
	private ListService listService;

//	@RequestMapping("fireNaySearch.html")
//	@ResponseBody
//	public String listData(SkuLsParams skuLsParams) {
//
//		SkuLsResult skuLsResult = listService.search(skuLsParams);
//
//		return JSON.toJSONString(skuLsResult);
//	}
	@RequestMapping("fireNaySearch.html")
	public String listData(SkuLsParams skuLsParams , HttpServletRequest request) {
		SkuLsResult skuLsResult = listService.search(skuLsParams);
		request.setAttribute("skuLsInfoList",skuLsResult.getSkuLsInfoList());
		return "list";
	}
}
