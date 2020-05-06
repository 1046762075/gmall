package com.firenay.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.firenay.gmall.entity.BaseAttrInfo;
import com.firenay.gmall.entity.BaseAttrValue;
import com.firenay.gmall.entity.SkuLsInfo;
import com.firenay.gmall.entity.SkuLsParams;
import com.firenay.gmall.entity.SkuLsResult;
import com.firenay.gmall.service.ListService;
import com.firenay.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
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

	@Reference
	private ManageService manageService;


	@RequestMapping("fireNaySearch.html")
	public String listData(SkuLsParams skuLsParams , HttpServletRequest request) {
		if(skuLsParams.getKeyword() == null || skuLsParams.getKeyword().trim().equals("") || skuLsParams.getCatalog3Id() == null || skuLsParams.getCatalog3Id().trim().equals("")){
			skuLsParams.setKeyword("nova");
		}
		skuLsParams.setPageSize(2);
		// 按照url里面的参数去ES里面查询
		SkuLsResult skuLsResult = listService.search(skuLsParams);
		List<SkuLsInfo> skuLsInfoList = skuLsResult.getSkuLsInfoList();
		List<String> attrValueIdList = skuLsResult.getAttrValueIdList();
		// 获取平台属性值集合
		List<BaseAttrInfo> attrList = manageService.getAttrs(attrValueIdList);
		// 请求路径的拼接
		String urlParam = makeUrlParam(skuLsParams);
		// 定义一个面包屑的集合
		ArrayList<BaseAttrValue> BreadCrumbs = new ArrayList<>();

		Iterator<BaseAttrInfo> iterator = attrList.iterator();
		while(iterator.hasNext()){
			// 获取其中一个平台属性
			BaseAttrInfo baseAttrInfo = iterator.next();
			// 获取这个平台属性的所有值
			List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
			for (BaseAttrValue baseAttrValue : attrValueList) {
				// 遍历判断这些平台属性值在 URL 里面有没有
				if(skuLsParams.getValueId() != null && skuLsParams.getValueId().length > 0){
					for (String urlId : skuLsParams.getValueId()) {
						// 当地址里面有这个平台属性的时候就移除
						if(urlId.equals(baseAttrValue.getId())){
							iterator.remove();
							// 这里存放面包屑里面的属性
							BaseAttrValue baseAttrValueBread = new BaseAttrValue();
							baseAttrValueBread.setValueName(baseAttrInfo.getAttrName() + ":" + baseAttrValue.getValueName());
							// 重新修改 UrlParam 并设置成最新的地址
							baseAttrValueBread.setUrlParam(makeUrlParam(skuLsParams, urlId));
							BreadCrumbs.add(baseAttrValueBread);
						}
					}
				}
			}
		}

		// 保存分页数据 <span th:if="${keyword} != null" th:utext="${keyword}"></span>
		request.setAttribute("pageNo",skuLsParams.getPageNo());
		request.setAttribute("totalPages",skuLsResult.getTotalPages());
		request.setAttribute("keyword","<font color='red'>[" + skuLsParams.getKeyword() + "]</font>");
		request.setAttribute("BreadCrumbs", BreadCrumbs);
		request.setAttribute("urlParam", urlParam);
		request.setAttribute("baseAttrInfoList", attrList);
		request.setAttribute("skuLsInfoList", skuLsInfoList);
		return "list";
	}

	/**
	 * 判断具体由那些参数
	 * @param excludeValueIds : 点击面包屑时获取的平台属性值id
	 * @return
	 */
	private String makeUrlParam(SkuLsParams skuLsParams,String ...excludeValueIds) {
		String urlParam = "";
		if(skuLsParams.getKeyword() != null && skuLsParams.getKeyword().length() > 0){
			urlParam += "keyword=" + skuLsParams.getKeyword();
		}

		// 拼接3级分类Id
		if(skuLsParams.getCatalog3Id() != null && skuLsParams.getCatalog3Id().length() > 0){
			if(urlParam.length() > 0){
				urlParam += "&";
			}
			urlParam += "catalog3Id=" + skuLsParams.getCatalog3Id();
		}

		// 拼接平台属性Id
		if(skuLsParams.getValueId() != null && skuLsParams.getValueId().length > 0){
			for (String valueId : skuLsParams.getValueId()) {
				if(excludeValueIds != null && excludeValueIds.length > 0){
					if(excludeValueIds[0].equals(valueId)){
						continue;
					}
				}
				if(urlParam.length() > 0){
					urlParam += "&";
				}
				urlParam += "valueId=" + valueId;
			}
		}
		return urlParam;
	}
}
