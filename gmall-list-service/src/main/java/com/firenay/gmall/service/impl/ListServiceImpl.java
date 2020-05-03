package com.firenay.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.firenay.gmall.entity.SkuLsInfo;
import com.firenay.gmall.entity.SkuLsParams;
import com.firenay.gmall.entity.SkuLsResult;
import com.firenay.gmall.service.ListService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ListServiceImpl</p>
 * Description：ES查询数据返回给用户
 * date：2020/5/2 16:44
 */
@Service
@Slf4j
public class ListServiceImpl implements ListService {

	@Autowired
	private JestClient jestClient;

	/**
	 * 这里的字段值必须跟ES中自定义Mapping中的保持一致
	 */
	public static final String ES_INDEX = "gmall";

	public static final String ES_TYPE = "SkuInfo";

	/**
	 * 往ES中保存数据
	 */
	@Override
	public void saveSkuLsInfo(SkuLsInfo skuLsInfo) {

		Index index = new Index.Builder(skuLsInfo).index(ES_INDEX).type(ES_TYPE).id(skuLsInfo.getId()).build();
		// 执行
		try {
			jestClient.execute(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SkuLsResult search(SkuLsParams skuLsParams) {
		SearchResult result = null;
		String query = makeQueryStringForSearch(skuLsParams);
		Search search = new Search.Builder(query).addIndex(ES_INDEX).addType(ES_TYPE).build();

		try {
			result = jestClient.execute(search);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SkuLsResult skuLsResult = makeResultForSearch(result,skuLsParams);
		return skuLsResult;
	}

	/**
	 * 将查询到的数据进行封装
	 * @param result 通过dsl查询出来的结果
	 * @param skuLsParams 入列参数
	 * @return
	 */
	private SkuLsResult makeResultForSearch(SearchResult result, SkuLsParams skuLsParams) {
		SkuLsResult skuLsResult = new SkuLsResult();
		// 用来存储 SkuLsInfo 的数据
		ArrayList<SkuLsInfo> skuInfoLsArrayList = new ArrayList<>();
		// 给集合赋值
		List<SearchResult.Hit<SkuLsInfo, Void>> hits = result.getHits(SkuLsInfo.class);
		for (SearchResult.Hit<SkuLsInfo, Void> hit : hits) {
			SkuLsInfo skuLsInfo = hit.source;
			// 获取SkuName的高亮
			if(hit.highlight != null && hit.highlight.size() > 0){
				Map<String, List<String>> highlight = hit.highlight;
				// 需要高亮的名字 此时skuName的集合中只有一个值
				String skuName = highlight.get("skuName").get(0);
				// 完成设置商品名字高亮
				skuLsInfo.setSkuName(skuName);
			}
			skuInfoLsArrayList.add(skuLsInfo);
		}
		skuLsResult.setSkuLsInfoList(skuInfoLsArrayList);

		// 设置能显示的数据总条数
//		System.out.println("可以显示的总条数：" + result.getTotal());
//		skuLsResult.setTotal(result.getTotal());
//
//		// 设置总页数
////		skuLsResult.setTotalPages((int)(result.getTotal() / skuLsParams.getPageSize() + 0.9999999));
//		skuLsResult.setTotalPages((result.getTotal() + skuLsParams.getPageSize() -1) / skuLsParams.getPageSize());

		// 获取平台属性值Id
		MetricAggregation aggregations = result.getAggregations();
		TermsAggregation groupby_attr = aggregations.getTermsAggregation("groupby_attr");
		List<TermsAggregation.Entry> buckets = groupby_attr.getBuckets();
		// 添加所有的平台属性Id
		ArrayList<String> attrIds = new ArrayList<>();
		for (TermsAggregation.Entry bucket : buckets) {
			attrIds.add(bucket.getKey());
		}
		skuLsResult.setAttrValueIdList(attrIds);
		return skuLsResult;
	}

	/**
	 * 完全根据手写的DSL语句 | 这里所有的字段必须跟自定义的一致
	 */
	private String makeQueryStringForSearch(SkuLsParams skuLsParams) {
		// 定义一个查询器
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		// 创建bool
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

		// 判断 keyword
		if (skuLsParams.getKeyword() != null && skuLsParams.getKeyword().length() > 0){
			MatchQueryBuilder skuName = new MatchQueryBuilder("skuName", skuLsParams.getKeyword());
			queryBuilder.must(skuName);
			// SkuName 不为空就设置高亮
			HighlightBuilder highlight = sourceBuilder.highlight();
			// 设置高亮的字段
			highlight.field("skuName");
			highlight.preTags("<span style=color:red>");
			highlight.postTags("</span>");
			// 将设置好的高亮对象放入
			sourceBuilder.highlight(highlight);
		}

		// 判断平台属性Id
		if(skuLsParams.getValueId() != null && skuLsParams.getValueId().length > 0){
			// 平台属性可能有多个
			for (String valueId : skuLsParams.getValueId()) {
				// 创建term
				TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",valueId);
				// 创建filter 并添加term
				queryBuilder.filter(termQueryBuilder);
			}
		}

		// 判断三级分类Id
		if(skuLsParams.getCatalog3Id() != null && skuLsParams.getCatalog3Id().length() > 0){
			// 创建term
			TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",skuLsParams.getCatalog3Id());
			// 创建filter 并添加term
			queryBuilder.filter(termQueryBuilder);
		}

		// 查询
		sourceBuilder.query(queryBuilder);

		// 设置分页
		int from = (skuLsParams.getPageNo()-1) * skuLsParams.getPageSize();
		sourceBuilder.from(from);
		sourceBuilder.size(skuLsParams.getPageSize());

		// 设置排序  排序字段 -- 排序规则
		sourceBuilder.sort("hotScore", SortOrder.DESC);

		// 聚合 aggs -> terms											聚合的名称
		TermsBuilder groupby_attr = AggregationBuilders.terms("groupby_attr");
//		"field": "skuAttrValueList.valueId"
		groupby_attr.field("skuAttrValueList.valueId.keyword");
		// 最后将 aggs 放入查询器
		sourceBuilder.aggregation(groupby_attr);
		String query = sourceBuilder.toString();
		log.info("\nGET /gmall/SkuInfo/_search\n" + query);
		return query;
	}
}
