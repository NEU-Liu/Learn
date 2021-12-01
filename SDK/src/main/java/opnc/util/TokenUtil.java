package opnc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import opnc.Authentication;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @desc:
 * @fileName: TokenUtil
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 11:40:40
 * @modifier:
 */
public class TokenUtil {

    private final static Log log = LogFactory.getLog(TokenUtil.class);

    private final static DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    private final static DateFormat RANDOM_FORMAT = new SimpleDateFormat("yyMMddHHmmssSSS");

    private static int JVM_SEQ = 9000;

    private final static String APP_ID = "APP_ID";
    private final static String TIMESTAMP = "TIMESTAMP";
    private final static String TRANS_ID = "TRANS_ID";


    /**
     * 生成认证实体
     *
     * @param appId  应用ID
     * @param secret 应用密钥
     * @return
     */
    public static Authentication generateAuth(String appId, String secret) {
        return generateAuth(appId, secret, null);
    }


    /**
     * 生成认证实体
     *
     * @param appId  应用ID
     * @param secret 应用密钥
     * @param body   请求体
     * @return
     */
    public static Authentication generateAuth(String appId, String secret, String body) {
        log.debug("appId: " + appId + ",secret: " + secret + ",body: " + body);

        String timestamp = FORMAT.format(new Date());
        String transId = random();
        String token = generateToken(appId, secret, timestamp, transId, body);
        Authentication authentication = new Authentication();
        authentication.setAPP_ID(appId);
        authentication.setTIMESTAMP(timestamp);
        authentication.setTRANS_ID(transId);
        authentication.setTOKEN(token);

        log.debug("authenticationJson: " + JSON.toJSONString(authentication));
        return authentication;
    }



    public static Authentication generateAuth(String appId, String secret, String body, AuthenticationType type) {

        switch (type) {
            case MD5:
                return md5Authentication(appId, secret, body);
            case AUTH2:
                return auth2Authentication(appId, secret, body);
            case SHA256:
                return sha256Authentication(appId, secret, body);
            case RSA:
                return rsaAuthentication(appId, secret, body);
            case JWT:
                return jwtAuthentication(appId, secret, body);
            default:
                break;
        }
        return null;
    }

    private static Authentication md5Authentication(String appId, String secret, String body) {
        return authentication("md5", appId, secret, body);
    }

    private static Authentication auth2Authentication(String appId, String secret, String body) {
        return authentication("auth2", appId, secret, body);
    }

    private static Authentication sha256Authentication(String appId, String secret, String body) {
        return authentication("sha256", appId, secret, body);
    }


    private static Authentication rsaAuthentication(String appId, String secret, String body) {
        return authentication("rsa", appId, secret, body);
    }

    private static Authentication jwtAuthentication(String appId, String secret, String body) {
        return authentication("jwt", appId, secret, body);
    }


    private static Authentication authentication(String type, String appId, String appSecret, String body) {

        Authentication authentication = new Authentication();
        authentication.setAPP_ID(appId);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -8);
        date = calendar.getTime();
        String timestamp = FORMAT.format(date);
        authentication.setTIMESTAMP(timestamp);
        String transId = random();
        authentication.setTRANS_ID(transId);
        String token = null;
        if (type.equals("md5")) {
            token = md5Hex(appId, timestamp, transId, body, appSecret);
        } else if (type.equals("auth2")) {
            token = md5Hex(appId, timestamp, transId, body, appSecret);
            String responseString = postAuthentication(appId, appSecret, "/auth2.0/authentication");
            JSONObject responseJson = JSON.parseObject(responseString);
            String appToken = null;
            if (responseJson.get("code") !=null && (int)responseJson.get("code") == 200){
                appToken = (String)(((JSONObject) responseJson.get("data")).get("token"));
            }else {
                log.error("Can not get auth2 appToken, " + "the response is: " + responseString + "!");

                appToken = "1ffd0a970a5604b9b7a9b3eff6c18b24";
            }
            authentication.setAPPTOKEN(appToken);
        } else if (type.equals("sha256")) {
            token = sha256Hex(appId, timestamp, transId, body, appSecret);
        } else if (type.equals("rsa")) {
            token = rsaSign(appId, timestamp, transId, body, appSecret);
        } else if (type.equals("jwt")) {
            token = md5Hex(appId,timestamp,transId,body,appSecret);

            String responseString = postAuthentication(appId, appSecret, "/jwt/apptoken");
            JSONObject responseJson = JSON.parseObject(responseString);
            String appToken = null;
            if (responseJson.get("code") !=null && (int)responseJson.get("code") == 200){
                appToken = (String)(((JSONObject) responseJson.get("data")).get("token"));
            }else {
                log.error("Can not get jwt appToken, " + "the response is: " + responseString + "!");
                //appToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTVUJKRUNUIiwiYXVkIjoiQVVESUVOQ0UiLCJuYmYiOjE2MzQ4NzYxODMsImFwcElkIjoiMDE2MTE1N0FBOUQzIiwiaXNzIjoiSVNTVVNFUiIsImFwcFNlY3JldCI6IjdjNTU0YzEzLTk3MzItNDZhNC04OGY2LTBkNzAzOGI0NjQ1NyIsImV4cCI6MTYzNDg3NzA4MywiaWF0IjoxNjM0ODc2MTgzfQ.DPSVUn1tPDIHQPLrVHbYGWO2lvFwNd6qSHsxDZkKgB96ELSRu6EgbDbv9kjn5L5MuOwkEBnVyeF3mmbzUm0ebn5DoraOZTxRppwScz_5iWdjvLB8xt0Y3LHkQogaGfisExD9Rdqo6j7--e1W8nS6sZErShgvnv3XcDm8Wh_5Rr0";
            }
            authentication.setAPPTOKEN(appToken);
        } else {
            log.error("未知的鉴权方式!");
        }
        authentication.setTOKEN(token);
        return authentication;
    }


    /**
     * 生成Token
     *
     * @param appId     应用ID
     * @param secret    应用密钥
     * @param timestamp 时间戳
     * @param transId   业务ID
     * @return
     */
    public static String generateToken(String appId, String secret, String timestamp, String transId) {
        String concatStr = APP_ID + appId + TIMESTAMP + timestamp + TRANS_ID + transId + secret;
        String md5Hex = DigestUtils.md5Hex(concatStr);
        log.debug("concatStr: " + concatStr + ",md5Hex: " + md5Hex);
        return md5Hex;
    }


    /**
     * 生成Token
     *
     * @param appId     应用ID
     * @param secret    应用密钥
     * @param timestamp 时间戳
     * @param transId   业务ID
     * @param body      请求体
     * @return
     */
    public static String generateToken(String appId, String secret, String timestamp, String transId, String body) {
        log.debug("appId: " + appId + ",secret: " + secret + ",timestamp: " + timestamp + ",transId: " + transId + ",body: " + body);

        if (body != null) {
            String concatStr = APP_ID + appId + TIMESTAMP + timestamp + TRANS_ID + transId + body + secret;
            String md5Hex = DigestUtils.md5Hex(concatStr);
            log.debug("concatStr: " + concatStr + ",md5Hex: " + md5Hex);
            return md5Hex;
        }
        return generateToken(appId, secret, timestamp, transId);
    }


    private static String random() {
        StringBuilder sb = new StringBuilder();
        sb.append(RANDOM_FORMAT.format(new Date()));
        ++JVM_SEQ;
        if (JVM_SEQ >= 10000) {
            JVM_SEQ = 0;
        }
        if (JVM_SEQ < 10) {
            sb.append("000");
        } else if (JVM_SEQ < 100) {
            sb.append("00");
        } else if (JVM_SEQ < 1000) {
            sb.append("0");
        }
        sb.append(JVM_SEQ);
        return sb.toString();
    }


    private static String sha256Hex(String APP_ID, String TIMESTAMP, String TRANS_ID, String Body, String APP_SECRET) {
        String plainText = readyToToken(APP_ID, TIMESTAMP, TRANS_ID, Body, APP_SECRET);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(bytes);
    }


    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    private static String md5Hex(String APP_ID, String TIMESTAMP, String TRANS_ID, String Body, String APP_SECRET) {
        String plainText = readyToToken(APP_ID, TIMESTAMP, TRANS_ID, Body, APP_SECRET);
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(plainText.getBytes());
        return new BigInteger(1, md5.digest()).toString(16);
    }


    private static String readyToToken(String APP_ID, String TIMESTAMP, String TRANS_ID, String Body, String APP_SECRET) {
        String value = "APP_ID" + APP_ID + "TIMESTAMP" + TIMESTAMP + "TRANS_ID" + TRANS_ID;

        if (Body != null) {
            value += Body;
        }
        if (APP_SECRET != null) {
            value += APP_SECRET;
        }
        return value;
    }

    private static String rsaSign(String appId, String timestamp, String transId, String body, String appSecret) {
        KeyPair keyPair = keyPair();
        //网络获取
       // String privateKeyPkcs8NoFlag = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));

        String privateKeyPkcs8NoFlag = null;
        String responseString = privateKeyPkcs8NoFlag(appId);
        JSONObject responseJson = JSON.parseObject(responseString);
        if (responseJson.get("code") !=null && (int)responseJson.get("code") == 200){
            privateKeyPkcs8NoFlag = (String)responseJson.get("data") ;
        }else {
            log.error("Can not get rsa private key, " + "the response is: " + responseString + "!");
        }



        String readyToSign = readyToToken(appId, timestamp, transId, body, appSecret);
        String token = null;
        try {
            token = sign(readyToSign, getPrivateKey(privateKeyPkcs8NoFlag));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    private static KeyPair keyPair() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generator.initialize(1024);
        return generator.generateKeyPair();
    }


    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }


    private static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signature.sign()));
    }

    private static String postAuthentication(String appId, String appSecret, String url) {

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

    private static String privateKeyPkcs8NoFlag(String appId) {

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = null;
        try {
            String url = "http://172.24.131.104:30000/unify-authentication/SDKHelper/queryPrivateKey";
            HttpPost req = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<>();

            list.add(new BasicNameValuePair("appId", appId));

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
