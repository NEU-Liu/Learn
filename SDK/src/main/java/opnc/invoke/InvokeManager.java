package opnc.invoke;

import opnc.AbilityResponse;
import opnc.multipart.MultipartParam;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @desc:
 * @fileName: InvokeManager
 * @author: tangjiaxiang
 * @createTime: 2020/12/2 13:37:37
 * @modifier:
 */
public interface InvokeManager {

    /**
     * 发送 GET请求
     *
     * @param url    请求地址
     * @return
     */
    AbilityResponse get(String url);

    /**
     * 发送 GET请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     */
    AbilityResponse get(String url, List<Header> headers);

    /**
     * 发送 POST: JSON请求
     *
     * @param url    请求地址
     * @return
     */
    AbilityResponse post(String url);

    /**
     * 发送 POST: JSON请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     */
    AbilityResponse post(String url, List<Header> headers);

    /**
     * 发送 POST: JSON请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param body    请求体
     * @return
     */
    AbilityResponse post(String url, List<Header> headers, String body);

    /**
     * 发送 POST: multipart/form-data请求
     *
     * @param url             请求地址
     * @param headers         请求头
     * @param multipartParams 复合参数列表
     * @return
     * @throws FileNotFoundException
     */
    AbilityResponse post(String url, List<Header> headers, List<MultipartParam> multipartParams) throws FileNotFoundException;

    /**
     * 自定义 HttpEntity POST请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param entity  HttpEntity
     * @return
     */
    AbilityResponse post(String url, List<Header> headers, HttpEntity entity);
}
