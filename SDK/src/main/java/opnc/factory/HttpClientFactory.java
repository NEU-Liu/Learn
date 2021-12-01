package opnc.factory;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @desc:
 * @fileName: HttpClientFactory
 * @author: tangjiaxiang
 * @createTime: 2020/12/2 9:16:16
 * @modifier:
 */
public interface HttpClientFactory {

    /**
     * 创建 CloseableHttpClient
     *
     * @return
     */
    CloseableHttpClient create();
}
