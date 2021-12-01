package opnc.invoke;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import opnc.AbilityResponse;
import opnc.Authentication;
import opnc.factory.HttpClientManager;
import opnc.multipart.FileMultipartParam;
import opnc.multipart.InputStreamMultipartParam;
import opnc.multipart.MultipartParam;
import opnc.multipart.TextMultipartParam;
import opnc.util.AuthenticationType;
import opnc.util.TokenUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @desc:
 * @fileName: AbilityInvokeManager
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 11:08:08
 * @modifier:
 */

public class AbilityInvokeManager implements InvokeManager {

    private final static Log log = LogFactory.getLog(AbilityInvokeManager.class);

    /**
     * 应用ID
     */
    private final String appId;
    /**
     * 应用密钥
     */
    private final String secret;


    /**
     * 类型
     */
    private static AuthenticationType type;


    public AbilityInvokeManager(String appId, String secret, AuthenticationType type) {
        this.appId = appId;
        this.secret = secret;
        AbilityInvokeManager.type = type;
    }


//    public AbilityInvokeManager(String appId, String secret) {
//        log.debug("appId: " + appId + ",secret: " + secret);
//        if (appId == null || "".equals(appId) || secret == null || "".equals(secret)) {
//            throw new RuntimeException("appId or secret is empty");
//        }
//        this.appId = appId;
//        this.secret = secret;
//    }

    /**
     * 认证请求头key
     */
    public static final String AUTH_HEADER = "authentication";

    /**
     * application/json
     */
    public static final String CONTENT_TYPE_JSON_UTF8 = "application/json; charset=utf-8";

    /**
     * 发送 GET请求
     *
     * @param url 请求地址
     * @return
     */
    @Override
    public AbilityResponse get(String url) {
        log.debug("appId: " + appId + ",secret: " + secret + ",url: " + url);
        return get(url, null);
    }


    /**
     * 发送 GET请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     */
    @Override
    public AbilityResponse get(String url, List<Header> headers) {
        log.debug("appId: " + appId + ",secret: " + secret + ",url: " + url);

        if (headers == null) {
            headers = new ArrayList<>();
        }
        // 设置认证请求头
        addAuthHeader(headers, appId, secret);
        return baseGet(url, headers);
    }

    /**
     * 发送 POST: JSON请求
     *
     * @param url 请求地址
     * @return
     */
    @Override
    public AbilityResponse post(String url) {
        return post(url, null, (String) null);
    }

    /**
     * 发送 POST: JSON请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     */
    @Override
    public AbilityResponse post(String url, List<Header> headers) {
        return post(url, headers, (String) null);
    }

    /**
     * 发送 POST: JSON请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param body    请求体
     * @return
     */
    @Override
    public AbilityResponse post(String url, List<Header> headers, String body) {
        log.debug("body ==> appId: " + appId + ",secret: " + secret + ",url: " + url + ",body: " + body);
        if (headers == null) {
            headers = new ArrayList<>();
        }

        // CONTENT_TYPE JSON UTF_8
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_UTF8));
        // 设置认证请求头
        addAuthHeader(headers, appId, secret, body);

        // 设置json请求体
        StringEntity stringEntity = null;
        if (body != null) {
            stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
        }
        return basePost(url, headers, stringEntity);
    }


    /**
     * 发送 POST: multipart/form-data请求
     *
     * @param url             请求地址
     * @param headers         请求头
     * @param multipartParams 复合参数列表
     * @return
     */
    @Override
    public AbilityResponse post(String url, List<Header> headers, List<MultipartParam> multipartParams) throws FileNotFoundException {
        log.debug("multipartParams ==> appId: " + appId + ",secret: " + secret + ",url: " + url);

        List<InputStream> closeList = new ArrayList<>();

        //  将 multipartParams 解析为 MultipartEntityBuilder
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);

        for (MultipartParam multipartParam : multipartParams) {
            // FileMultipartParam
            if (multipartParam instanceof FileMultipartParam) {
                FileMultipartParam param = (FileMultipartParam) multipartParam;
                String name = param.getName();
                File file = param.getFile();
                String filename = param.getFilename();
                log.debug("name: " + name + ",filename: " + filename);

                FileInputStream fileInputStream = new FileInputStream(file);
                closeList.add(fileInputStream);
                builder.addBinaryBody(name, fileInputStream, ContentType.DEFAULT_BINARY, filename != null ? filename : file.getName());
            }
            // InputStreamMultipartParam
            if (multipartParam instanceof InputStreamMultipartParam) {
                InputStreamMultipartParam param = (InputStreamMultipartParam) multipartParam;
                String name = param.getName();
                InputStream inputStream = param.getInputStream();
                String filename = param.getFilename();
                log.debug("name: " + name + ",filename: " + filename);

                closeList.add(inputStream);
                builder.addBinaryBody(name, inputStream, ContentType.DEFAULT_BINARY, filename != null ? filename : "");
            }
            // TextMultipartParam
            if (multipartParam instanceof TextMultipartParam) {
                TextMultipartParam param = (TextMultipartParam) multipartParam;
                String name = param.getName();
                String value = param.getValue();
                log.debug("name: " + name + ",value: " + value);

                builder.addTextBody(name, value);
            }
        }

        // 发送post请求
        AbilityResponse response = basePost(url, headers, builder.build());
        // 关闭资源
        closeInputStreamList(closeList);
        return response;
    }


    /**
     * 自定义 HttpEntity POST请求
     *
     * @param url             请求地址
     * @param headers         请求头
     * @param multipartEntity 自定义HttpEntity (能力调用场景应为 multipartEntity)
     * @return
     */
    @Override
    public AbilityResponse post(String url, List<Header> headers, HttpEntity multipartEntity) {
        log.debug("multipartEntity ==> appId: " + appId + ",secret: " + secret + ",url: " + url);

        if (headers == null) {
            headers = new ArrayList<>();
        }
        // 设置认证请求头
        addAuthHeader(headers, appId, secret);
        return basePost(url, headers, multipartEntity);
    }


    /**
     * 基础 POST请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param entity  请求体
     * @return
     */
    private static AbilityResponse basePost(String url, List<Header> headers, HttpEntity entity) {

        CloseableHttpClient httpClient = HttpClientManager.getHttpClient().create();

        HttpPost httpPost = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            httpPost.setHeaders(headers.stream().filter(Objects::nonNull).toArray(Header[]::new));
        }

        if (entity != null) {
            httpPost.setEntity(entity);
        }

        return execute(httpClient, httpPost);
    }


    /**
     * 基础 GET请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     */
    private static AbilityResponse baseGet(String url, List<Header> headers) {
        CloseableHttpClient httpClient = HttpClientManager.getHttpClient().create();

        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            httpGet.setHeaders(headers.stream().filter(Objects::nonNull).toArray(Header[]::new));
        }

        return execute(httpClient, httpGet);
    }


    /**
     * 设置认证请求头
     *
     * @param headers 请求头列表
     * @param appId   应用ID
     * @param secret  应用密钥
     */
    private static void addAuthHeader(List<Header> headers, String appId, String secret) {
        addAuthHeader(headers, appId, secret, null);
    }


    /**
     * 设置认证请求头
     *
     * @param headers 请求头列表
     * @param body    请求体
     */
    private static void addAuthHeader(List<Header> headers, String appId, String secret, String body) {
        log.debug("headers: " + JSON.toJSONString(headers) + ",appId: " + appId + ",secret: " + secret + ",body: " + body);
        //有改动
        Authentication authentication = TokenUtil.generateAuth(appId, secret, body, type);

        String base64 = new Base64().encodeToString(JSONObject.toJSONString(authentication).getBytes());
        log.debug("base64: " + base64);
        BasicHeader authHeader = new BasicHeader(AUTH_HEADER, base64);
        headers.add(authHeader);
    }

    /**
     * 执行http请求
     *
     * @param httpClient 客户端
     * @param request    请求信息
     * @return
     */
    private static AbilityResponse execute(CloseableHttpClient httpClient, HttpUriRequest request) {
        AbilityResponse response = new AbilityResponse();
        response.setHttpClient(httpClient);
        try {
            response.setResponse(httpClient.execute(request));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                httpClient.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        log.debug("responseCode: " + response.getResponse().getStatusLine().getStatusCode());
        return response;
    }

    /**
     * 关闭输入流列表
     *
     * @param closeList 输入流列表
     */
    private void closeInputStreamList(List<InputStream> closeList) {
        log.debug("closeList: " + JSON.toJSONString(closeList));
        if (closeList.isEmpty()) {
            return;
        }

        for (InputStream inputStream : closeList) {
            if (inputStream == null) {
                continue;
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {


        AbilityInvokeManager abilityInvokeManager = new AbilityInvokeManager("0161157AA9D3", "7c554c13-9732-46a4-88f6-0d7038b46457", AuthenticationType.MD5);
//        AbilityResponse responseBody = abilityInvokeManager.post("http://localhost:8000/api/system/northInterfaceId_md5/v1.0", null, "{\"orderId\":\"123\"}");
//        System.out.println(responseBody.getRespStr());
//
//
//        AbilityResponse responseNoBody = abilityInvokeManager.post("http://localhost:8000/api/system/northInterfaceId_md5/v1.0", null);
//        System.out.println(responseNoBody.getRespStr());



        AbilityResponse responseBody = abilityInvokeManager.post("https://www.baidu.com", null, "{\"orderId\":\"123\"}");
        System.out.println(responseBody.getRespStr());
    }

}
