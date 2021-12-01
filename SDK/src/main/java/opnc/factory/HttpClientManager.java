package opnc.factory;


import opnc.config.ConfigUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @desc:
 * @fileName: HttpClientManager
 * @author: tangjiaxiang
 * @createTime: 2020/12/2 9:13:13
 * @modifier:
 */
public class HttpClientManager {

    private final static Log log = LogFactory.getLog(HttpClientManager.class);

    /**
     * HttpClient实现类类名 property
     */
    private final static String HTTP_CLIENT_CLASS_NAME = "sdk.HttpClientClassName";

    public static HttpClientFactory getHttpClient() {

        String httpClientClassName = ConfigUtils.INSTANCE.getProperty(HTTP_CLIENT_CLASS_NAME);
        log.debug("httpClientClassName: " + httpClientClassName);

        Object o;
        try {
            Class<?> defaultHttpClientImpl = Class.forName(httpClientClassName);
            o = defaultHttpClientImpl.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return (HttpClientFactory) o;
    }
}
