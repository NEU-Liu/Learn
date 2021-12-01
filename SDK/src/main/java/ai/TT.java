package ai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @author liujd65
 * @date 2021/11/15 12:13
 **/
public class TT {
    public static void main(String[] args) throws Exception {


//        String auth2 = postAuthentication("0161157AA9D3", "7c554c13-9732-46a4-88f6-0d7038b46457", "/auth2.0/authentication");
//        System.out.println(auth2);
//        JSONObject json = JSON.parseObject(auth2);
//        if ((int)json.get("code") == 200){
//            System.out.println(((JSONObject) json.get("data")).get("token"));
//        }


        //System.out.println(jsonObjectAuth2.get("msg"));

        //privateKeyPkcs8NoFlag();



        String responseString = postAuthentication("0161157AA9D3", "7c554c13-9732-46a4-88f6-0d7038b46457", "/jwt/apptoken");
        System.out.println(responseString);
        JSONObject responseJson = JSON.parseObject(responseString);
        if ((int)responseJson.get("code") == 200){
            System.out.println(responseJson.get("data"));
        }else {
            System.out.println(responseJson.get("msg"));
        }

    }

    public static String postAuthentication(String appId, String appSecret, String url) {

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = null;
        try {
            String baseUrl = "http://172.24.131.104:30000/unify-authentication";
            url = baseUrl + url;
            HttpPost req = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<>();

            list.add(new BasicNameValuePair("appId", appId));
            list.add(new BasicNameValuePair("appSecret", appSecret));

            entity = new UrlEncodedFormEntity(list, Consts.UTF_8);

            RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10_000).setConnectTimeout(10_000).build();
            req.setConfig(reqConfig);
            req.setEntity(entity);
            response = client.execute(req);
            entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String privateKeyPkcs8NoFlag(String appId, String appSecret, String url) {

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = null;
        try {
            String baseUrl = "http://172.24.131.104:30000/unify-authentication";
            url = baseUrl + url;
            HttpPost req = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<>();

            list.add(new BasicNameValuePair("appId", appId));
            list.add(new BasicNameValuePair("appSecret", appSecret));

            entity = new UrlEncodedFormEntity(list, Consts.UTF_8);

            RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10_000).setConnectTimeout(10_000).build();
            req.setConfig(reqConfig);
            req.setEntity(entity);
            response = client.execute(req);
            entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
