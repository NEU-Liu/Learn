package elasticsearch;


import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import elasticsearch.dao.Article;
import elasticsearch.dao.Good;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.xpack.XPackInfoRequest;
import org.elasticsearch.client.xpack.XPackInfoResponse;
import org.elasticsearch.client.xpack.XPackUsageRequest;
import org.elasticsearch.client.xpack.XPackUsageResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;

/**
 * @author liujd65
 * @date 2021/12/1 11:07
 **/
@Data
public class Main {

    private final int MAX_SIZE = 10_000;

    public static RestHighLevelClient HighLevelClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
        return new RestHighLevelClient(builder);
    }

    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = HighLevelClient();
        IndexRequest request = new IndexRequest("shopping", "_doc");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);


        client.close();
    }

    RestHighLevelClient client = null;

    @Before
    public void client() {
        client = HighLevelClient();
    }

    @After
    public void clientClose() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println("client close error!");
                //nothing to do!
            }
        }
    }

    @Test
    public void CreateIndexAPI() throws Exception {
        RestHighLevelClient client = HighLevelClient();
        CreateIndexRequest request = new CreateIndexRequest("goods");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }

    ActionListener<CreateIndexResponse> listener =
            new ActionListener<CreateIndexResponse>() {

                @Override
                public void onResponse(CreateIndexResponse response) {
                    System.out.println(response);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("Failure!");
                }
            };


    @Test
    public void DeleteIndexAPI() throws Exception {
        RestHighLevelClient client = HighLevelClient();
        DeleteIndexRequest request = new DeleteIndexRequest("liu");

        client.close();
    }

    @Test
    public void IndexExistsAPI() throws Exception {
        RestHighLevelClient client = HighLevelClient();
        GetIndexRequest request = new GetIndexRequest("liu");
        request.humanReadable(true);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
        client.close();
    }


    @Test
    public void SearchAPI() throws Exception {
        RestHighLevelClient client = HighLevelClient();
        SearchRequest searchRequest = new SearchRequest("shakespeare");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }

    @Test
    public void SearchAPI1() throws Exception {
        RestHighLevelClient client = HighLevelClient();
        SearchRequest searchRequest = new SearchRequest("shakespeare");
        SearchRequest routing = searchRequest.routing("0");
        System.out.println(routing);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }


    @Test
    public void IndexAPI1() throws Exception {
        RestHighLevelClient client = HighLevelClient();
        SearchRequest searchRequest = new SearchRequest("shakespeare");
        SearchRequest routing = searchRequest.routing("0");
        System.out.println(routing);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        client.close();
    }


    @Test
    public void IndexAPI() throws Exception {

        IndexRequest request = new IndexRequest("liu");
        request.routing("routingPath1");
        String json = Article.randomInstance();
        System.out.println(json);
        request.source(json, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response);


    }

    @Test
    public void SearchAPI2() throws Exception {
        SearchRequest searchRequest = new SearchRequest("liu");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("id", "1424716751"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(MAX_SIZE);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("TotalHits:" + response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits()) {
            String json = hit.getSourceAsString();
            System.out.println(json);
        }
    }


    @Test
    public void SearchAPI3() throws Exception {
        SearchRequest searchRequest = new SearchRequest("liu");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders.matchQuery("authorName", "SsjOlh")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3)
                .maxExpansions(10);

        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(MAX_SIZE);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("TotalHits:" + response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits()) {
            String json = hit.getSourceAsString();
            System.out.println(json);
        }
    }


    @Test
    public void SearchAPI4() throws Exception {
        SearchRequest searchRequest = new SearchRequest("liu");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(MAX_SIZE);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.fetchSource(false);// The Method Key Line!
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("TotalHits:" + response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits()) {
            String json = hit.getSourceAsString();
            System.out.println(json);
        }
    }

    @Test
    public void SearchAPI5() throws Exception {
        SearchRequest searchRequest = new SearchRequest("liu");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(MAX_SIZE);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        String[] includeFields = new String[]{"id", "authorName"};
        String[] excludeFields = new String[]{"content"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);// The Method Key Line!
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("TotalHits:" + response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits()) {
            String json = hit.getSourceAsString();
            System.out.println(json);
        }
    }


    @Test
    public void SearchAPI6() throws Exception {
        SearchRequest searchRequest = new SearchRequest("liu");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle =
                new HighlightBuilder.Field("id");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("authorName");
        highlightBuilder.field(highlightUser);
        searchSourceBuilder.highlighter(highlightBuilder);


        searchSourceBuilder.from(0);
        searchSourceBuilder.size(MAX_SIZE);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("TotalHits:" + response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits()) {
            String json = hit.getSourceAsString();
            System.out.println(json);
        }
    }


    @Test
    public void CreateGoodAPI() throws Exception {
        IndexRequest request = new IndexRequest("goods");
        int goodNum = 10000;
        for (int i = 0; i < goodNum; i++) {
            String json = Good.randomInstance();
            request.source(json, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            RestStatus status = response.status();
            if (status != RestStatus.CREATED) {
                System.out.println("数据初始化错误!");
            }
        }
    }


    @Test
    public void SearchAPI7() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(MAX_SIZE);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        CountRequest countRequest = new CountRequest("liu");
        countRequest.source(searchSourceBuilder);
        CountResponse response = client.count(countRequest, RequestOptions.DEFAULT);


        Boolean terminatedEarly = response.isTerminatedEarly();
        System.out.println(terminatedEarly);

        System.out.println(response);
    }


    @Test
    public void MiscellaneousInfoAPI() throws Exception {


        MainResponse response = client.info(RequestOptions.DEFAULT);
        String clusterName = response.getClusterName();
        System.out.println("clusterName:" + clusterName);
        String clusterUuid = response.getClusterUuid();
        System.out.println("clusterUuid:" + clusterUuid);
        String nodeName = response.getNodeName();
        System.out.println("nodeName:" + nodeName);
        MainResponse.Version version = response.getVersion();
        String buildDate = version.getBuildDate();
        System.out.println("buildDate:" + buildDate);
        String buildFlavor = version.getBuildFlavor();
        System.out.println("buildFlavor:" + buildFlavor);
        String buildHash = version.getBuildHash();
        System.out.println("buildHash:" + buildHash);
        String buildType = version.getBuildType();
        System.out.println("buildType:" + buildType);
        String luceneVersion = version.getLuceneVersion();
        System.out.println("luceneVersion:" + luceneVersion);
        String minimumIndexCompatibilityVersion = version.getMinimumIndexCompatibilityVersion();
        System.out.println("minimumIndexCompatibilityVersion:" + minimumIndexCompatibilityVersion);
        String minimumWireCompatibilityVersion = version.getMinimumWireCompatibilityVersion();
        System.out.println("minimumWireCompatibilityVersion:" + minimumWireCompatibilityVersion);
        String number = version.getNumber();
        System.out.println("number:" + number);
    }


    @Test
    public void MiscellaneousPingAPI() throws Exception {
        boolean response = client.ping(RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    public void MiscellaneousXPackInfoAPI() throws Exception {
        XPackInfoRequest request = new XPackInfoRequest();
        request.setVerbose(true);
        request.setCategories(EnumSet.of(
                XPackInfoRequest.Category.BUILD,
                XPackInfoRequest.Category.LICENSE,
                XPackInfoRequest.Category.FEATURES));
        XPackInfoResponse response = client.xpack().info(request, RequestOptions.DEFAULT);


        XPackInfoResponse.BuildInfo build = response.getBuildInfo();
        XPackInfoResponse.LicenseInfo license = response.getLicenseInfo();
        XPackInfoResponse.FeatureSetsInfo features = response.getFeatureSetsInfo();
        System.out.println("build:" + build);
        System.out.println("license::" + license);
        System.out.println("features:" + features);

        System.out.println(response);
    }

    @Test
    public void MiscellaneousXPackUsageAPI() throws Exception {
        XPackUsageRequest request = new XPackUsageRequest();
        XPackUsageResponse response = client.xpack().usage(request, RequestOptions.DEFAULT);

        Map<String, Map<String, Object>> usages = response.getUsages();
        Map<String, Object> monitoringUsage = usages.get("monitoring");

        System.out.println(monitoringUsage.get("available"));
        System.out.println(monitoringUsage.get("enabled"));
        System.out.println(monitoringUsage.get("collection_enabled"));


    }


    @Test
    public void ListTaskAPI() throws Exception {
        ListTasksRequest request = new ListTasksRequest();

        ListTasksResponse response = client.tasks().list(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }


    @Test
    public void DeleteAPI() throws Exception {
        DeleteRequest request = new DeleteRequest("goods", "oPMQf30BedPhONF_G1PF");
        request.timeout(TimeValue.timeValueMillis(5));
        request.version(2);
        request.versionType(VersionType.EXTERNAL);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }


    @Test
    public void SearchScrollAPI() throws Exception {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchScrollAPI(searchRequest);
    }


    public void SearchScrollAPI(SearchRequest searchRequest) throws Exception {
        Scroll scroll = null;
        if (null == (scroll = searchRequest.scroll())) {
            scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            searchRequest.scroll(scroll);
        }


        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();

        SearchHit[] searchHits = searchResponse.getHits().getHits();

        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            displaySearchHit(searchHits);
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();
        if (!succeeded) {
            System.out.println("Clear Scroll Error!");
        }
    }

    public void displaySearchHit(SearchHit[] searchHits) {
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }


    @Test
    public void MultiSearchAPI() throws Exception {
        MultiSearchRequest request = new MultiSearchRequest();

        SearchRequest firstSearchRequest = new SearchRequest("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);

        SearchRequest secondSearchRequest = new SearchRequest("goods");
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);

        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);


        MultiSearchResponse.Item firstResponse = response.getResponses()[0];
        System.out.println("firstResponse.getFailure():" + firstResponse.getFailure());
        SearchResponse searchResponse = firstResponse.getResponse();

        MultiSearchResponse.Item secondResponse = response.getResponses()[1];
        System.out.println("secondResponse.getFailure():" + secondResponse.getFailure());
        searchResponse = secondResponse.getResponse();

    }


    @Test
    public void FieldCapabilitiesAPI() throws Exception {
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest()
                .fields("id", "price")
                .indices("goods", "liu", "liu2", "shakespeare");
        FieldCapabilitiesResponse response = client.fieldCaps(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }


    @Test
    public void AggregationAvgMinMaxSum() throws Exception {
        SearchRequest request = new SearchRequest("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.avg("AVG_PRICE").field("price"));
        searchSourceBuilder.aggregation(AggregationBuilders.min("MAX_PRICE").field("price"));
        searchSourceBuilder.aggregation(AggregationBuilders.max("MIN_PRICE").field("price"));
        searchSourceBuilder.aggregation(AggregationBuilders.sum("SUM_PRICE").field("price"));
        searchSourceBuilder.aggregation(AggregationBuilders.count("COUNT_PRICE").field("price"));

        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        ParsedAvg AVG_Price = response.getAggregations().get("AVG_PRICE");
        ParsedMin MIN_Price = response.getAggregations().get("MAX_PRICE");
        ParsedMax MAX_Price = response.getAggregations().get("MIN_PRICE");
        ParsedSum SUM_Price = response.getAggregations().get("SUM_PRICE");
        ValueCount COUNT_Price = response.getAggregations().get("COUNT_PRICE");
        System.out.println("AVG_Price:" + AVG_Price.getValue());
        System.out.println("MIN_Price:" + MIN_Price.getValue());
        System.out.println("MAX_Price:" + MAX_Price.getValue());
        System.out.println("SUM_Price:" + SUM_Price.getValue());
        System.out.println("COUNT_Price:" + COUNT_Price.getValue());
    }

    @Test
    public void AggregationStats() throws Exception {
        SearchRequest request = new SearchRequest("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.stats("Price").field("price")).size(0);
        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        ParsedStats aggregation = search.getAggregations().get("Price");
        System.out.println(aggregation.getMax());
        System.out.println(aggregation.getAvg());
        System.out.println(aggregation.getCount());
        System.out.println(aggregation.getMin());
        System.out.println(aggregation.getSum());
    }

    //https://elasticsearchjava-api.readthedocs.io/en/latest/aggregation.html


    @Test
    public void PercentilesAggregationStats() throws Exception {


        SearchRequest request = new SearchRequest("website");


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        PercentilesAggregationBuilder percentilesAggregationBuilder =
                AggregationBuilders
                        .percentiles("Latency")
                        .field("latency");


        searchSourceBuilder.aggregation(percentilesAggregationBuilder);
        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        Percentiles aggregation = search.getAggregations().get("Latency");


        for (Percentile entry : aggregation) {
            double percent = entry.getPercent();
            double value = entry.getValue();
            System.out.println(percent + "\t" + value);
        }

    }


    @Test //todo 有待矫正
    public void Cardinality() throws IOException {

        SearchRequest request = new SearchRequest("website");


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("DistinctZone").field("website.zone");


        searchSourceBuilder.aggregation(cardinalityAggregationBuilder);
        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        Cardinality distinctZone = search.getAggregations().get("DistinctZone");
        System.out.println(distinctZone.getValue());


    }


    @Test
    public void Spring() throws IOException {


    }


}
