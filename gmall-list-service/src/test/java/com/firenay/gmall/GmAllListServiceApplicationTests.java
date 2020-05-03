package com.firenay.gmall;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmAllListServiceApplicationTests {

	@Autowired
	private JestClient jestClient;

	@Test
	public void testES() throws IOException {
		// GET /movie_chn/movie/_search
		String query = "{\n" +
				"  \"query\": {\n" +
				"    \"match\": {\n" +
				"      \"name\": \"红海战役\"\n" +
				"    }\n" +
				"  }\n" +
				"}";
		// 查询Get
		Search search = new Search.Builder(query).addIndex("movie_chn").addType("movie").build();

		// 执行动作
		SearchResult searchResult = jestClient.execute(search);

		// 获取数据
		List<SearchResult.Hit<Map, Void>> hits = searchResult.getHits(Map.class);

		// 循环遍历集合 	"红海行动 " "红海事件"
		for (SearchResult.Hit<Map, Void> hit : hits) {
			Map map = hit.source;
			System.out.println(map.get("name"));
		}
	}
}
