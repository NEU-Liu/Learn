package elasticsearch.work;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caoyouyuan
 * @version 1.0
 * @date 2021/8/10 22:37
 * @desc
 */


public class RestHighLevelClientConfig {


    private EsConfig esConfig = new EsConfig("elastic", "123456", "172.24.131.104", 29200);


    private final int connectTimeoutMillis = 20 * 1000;
    private final int socketTimeoutMillis = 30 * 1000;
    private final int connectionRequestTimeoutMillis = 20 * 1000;
    private final int maxConnectPerRoute = 300;
    private final int maxConnectTotal = 500;


    public RestHighLevelClient createSimpleElasticClient() throws Exception {

        try {

            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                    esConfig.getUsername(), esConfig.getPassword()
            ));


            SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true);
            final SSLContext sslContext = sslBuilder.build();

            List<HttpHost> httpHostList = new ArrayList<>();

            HttpHost httpHost;
            httpHost = new HttpHost(esConfig.getHostname(), esConfig.getPort(), "https");
            httpHostList.add(httpHost);

            HttpHost[] httpHosts = httpHostList.stream().toArray(HttpHost[]::new);


            RestHighLevelClient client = new RestHighLevelClient(RestClient
                    .builder(httpHosts)
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {

                            return httpClientBuilder
                                    .setDefaultCredentialsProvider(credentialsProvider)
                                    .setMaxConnTotal(maxConnectTotal)
                                    .setMaxConnPerRoute(maxConnectPerRoute)
                                    .setSSLContext(sslContext)
                                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                        }
                    })
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(
                                RequestConfig.Builder requestConfigBuilder) {
                            return requestConfigBuilder.setConnectTimeout(connectTimeoutMillis)
                                    .setSocketTimeout(socketTimeoutMillis)
                                    .setConnectionRequestTimeout(connectionRequestTimeoutMillis);
                        }
                    }));


            return client;
        } catch (Exception e) {

            throw new Exception("Could not create an elasticsearch client!!");
        }

    }


}
