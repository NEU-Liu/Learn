package opnc;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @desc:
 * @fileName: AbilityResponse
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 13:50:50
 * @modifier:
 */
public class AbilityResponse {

    private final static Log log = LogFactory.getLog(AbilityResponse.class);

    /**
     * 本次调用使用的 HttpClient 实例
     */
    CloseableHttpClient httpClient;
    /**
     * 本次调用响应的 HttpResponse 实例
     */
    CloseableHttpResponse response;

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CloseableHttpResponse getResponse() {
        return response;
    }

    public void setResponse(CloseableHttpResponse response) {
        this.response = response;
    }

    public ResponseBody getRespObj() throws IOException {
        if (response == null) {
            log.debug("response is null");
            return null;
        }
        HttpEntity entity = response.getEntity();
        String responseStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return JSONObject.parseObject(responseStr, ResponseBody.class);
    }

    public String getRespStr() throws IOException {
        if (response == null) {
            log.debug("response is null");
            return null;
        }
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity,StandardCharsets.UTF_8);
    }

    public InputStream getInputStream() throws IOException {
        if (response == null) {
            log.debug("response is null");
            return null;
        }
        HttpEntity entity = response.getEntity();
        return entity.getContent();
    }

    public void close() throws IOException {
        if (response == null) {
            log.debug("response is null");
            return;
        }
        response.close();
        if (httpClient == null) {
            log.debug("httpClient is null");
            return;
        }
        httpClient.close();
    }


}
