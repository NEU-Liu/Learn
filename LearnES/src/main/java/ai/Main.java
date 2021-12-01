package ai;


import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @author liujd65
 * @date 2021/12/1 11:07
 **/
public class Main {

    public static RestHighLevelClient HighLevelClient(){
        RestClientBuilder builder = RestClient.builder(new HttpHost("127.0.0.1",9200,"http"));
        return new RestHighLevelClient(builder);
    }

    public static void main(String[] args) throws Exception{
        RestHighLevelClient client = HighLevelClient();
        IndexRequest request = new IndexRequest("shopping","_doc");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        client.close();
    }
}
