package opnc.factory.impl;

import opnc.config.ConfigUtils;
import opnc.factory.HttpClientFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @desc:
 * @fileName: IgnoreSslHttpClientImpl
 * @author: tangjiaxiang
 * @createTime: 2020/12/2 9:18:18
 * @modifier:
 */
public class IgnoreSslHttpClientImpl implements HttpClientFactory {

    private final Log log = LogFactory.getLog(IgnoreSslHttpClientImpl.class);

    /**
     * 连接超时时间 property
     */
    private final static String CONNECT_TIMEOUT = "sdk.connectTimeout";
    /**
     * 数据交互超时时间 property
     */
    private final static String SOCKET_TIMEOUT = "sdk.socketTimeout";
    /**
     * 代理hostname property
     */
    private final static String PROXY_HOSTNAME = "sdk.proxyHostname";
    /**
     * 代理端口 property
     */
    private final static String PROXY_PORT = "sdk.proxyPort";

    @Override
    public CloseableHttpClient create() {
        SSLConnectionSocketFactory scsf;
        try {
            scsf = new SSLConnectionSocketFactory(SSLContexts
                    .custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build(), NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            log.debug("createIgnoreVerifySsl error");
            throw new RuntimeException(e);
        }
        HttpClientBuilder builder = HttpClients.custom().setSSLSocketFactory(scsf);
        RequestConfig.Builder custom = RequestConfig.custom();

        // 设置连接超时时间
        String connectTimeout = ConfigUtils.INSTANCE.getProperty(CONNECT_TIMEOUT);
        log.debug("connectTimeout: " + connectTimeout);

        if (!"".equals(connectTimeout)) {
            custom.setConnectTimeout(Integer.parseInt(connectTimeout));
        }

        // 设置读取超时时间
        String socketTimeout = ConfigUtils.INSTANCE.getProperty(SOCKET_TIMEOUT);
        log.debug("socketTimeout: " + socketTimeout);

        if (!"".equals(socketTimeout)) {
            custom.setSocketTimeout(Integer.parseInt(socketTimeout));
        }

        // 设置代理
        String proxyHostname = ConfigUtils.INSTANCE.getProperty(PROXY_HOSTNAME);
        log.debug("proxyHostname: " + proxyHostname);

        if (!"".equals(proxyHostname)) {
            String proxyPort = ConfigUtils.INSTANCE.getProperty(PROXY_PORT);
            log.debug("proxyPort: " + proxyPort);

            custom.setProxy("".equals(proxyPort)
                    ? new HttpHost(proxyHostname)
                    : new HttpHost(proxyHostname, Integer.parseInt(proxyPort)));
        }

        builder.setDefaultRequestConfig(custom.build());
        return builder.build();
    }
}
