package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.entity.SpuInfo;
import com.firenay.gmall.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title: SpuManageController</p>
 * Description：专门管理 SPU 的 Controller
 * date：2020/4/28 12:06
 */
@RestController
@CrossOrigin
public class SpuManageController {

	@Reference
	private ManageService manageService;
//
//	@RequestMapping("spuList")
//	public List<SpuInfo> spuInfoList(String catalog3Id){
//		return manageService.getSpuList(new SpuInfo(catalog3Id));
//	}

	@RequestMapping("spuList")
	public List<SpuInfo> spuInfoList(SpuInfo spuInfo){
		return manageService.getSpuList(spuInfo);
	}
}
