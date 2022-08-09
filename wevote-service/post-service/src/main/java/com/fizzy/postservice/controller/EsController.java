package com.fizzy.postservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.fizzy.core.entity.Post;
import com.fizzy.es.entity.EsPage;
import com.fizzy.es.util.ElasticsearchUtil;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/es")
public class EsController {
    /**
     * 测试索引
     */
    private String indexName = "wevote";

    /**
     * 类型
     */
    private String esType = "postVo";

    /**
     * 创建索引
     * http://127.0.0.1:8080/es/createIndex
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/createIndex")
    public String createIndex(HttpServletRequest request, HttpServletResponse response) {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName);
        } else {
            return "索引已经存在";
        }
        return "索引创建成功";
    }

    /**
     * 查询日期范围数据
     *
     * @return
     */
    @RequestMapping("/queryDateRangeData")
    public String queryDateRangeData() {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.rangeQuery("id").from("1")
                .to("50"));
        List<Map<String, Object>> list = ElasticsearchUtil.searchListData(indexName, esType, boolQuery, 10, null, null, null);
        return JSONObject.toJSONString(list);
    }

    /**
     * 查询数据
     * 模糊查询
     *
     * @return
     */
    @GetMapping("/queryMatchData")
    public String queryMatchData(@RequestParam String keyword) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolean matchPhrase = false;
        if (matchPhrase == Boolean.TRUE) {
            //不进行分词搜索
            boolQuery.must(QueryBuilders.matchPhraseQuery("content", keyword));
        } else {
            boolQuery.must(QueryBuilders.matchQuery("content", keyword));
        }
        List<Map<String, Object>> list = ElasticsearchUtil.
                searchListData(indexName, esType, boolQuery, 10, "content,title", null, "content");
        return JSONObject.toJSONString(list);
    }

    @GetMapping("/queryMultiMatchData")
    public String queryMultiMatchData(@RequestParam String keyword){
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword,
                "content", "title");
        List<Map<String, Object>> list = ElasticsearchUtil.
                searchListData(indexName, esType, queryBuilder, 10, "content,title,create_time", null, "content,title");
        return JSONObject.toJSONString(list);
    }

    @GetMapping("/queryMultiMatchDataPage")
    public String queryMultiMatchDataPage(@RequestParam String keyword, @RequestParam int pageNum, @RequestParam int pageSize){
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", keyword);
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("content", keyword);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(queryBuilder);
        boolQueryBuilder.should(queryBuilder2);
        EsPage list = ElasticsearchUtil.
                searchDataPage(indexName, esType, pageNum,pageSize,  boolQueryBuilder,"content,title,create_time,name,postuserhead", "create_time", "title");
        return JSONObject.toJSONString(list);
    }


}
