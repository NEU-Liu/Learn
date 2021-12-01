package opnc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import opnc.Authentication;
import org.apache.commons.codec.binary.Base64;

/**
 * @author liujd65
 * @date 2021/11/14 21:32
 **/
public class MainToken {
    public static void main(String[] args) {
        //System.out.println(TokenUtil.generateAuth1());

        Authentication authentication = TokenUtil.generateAuth("MD5APP3948371", "37a25b67b3ddeac9", "{\"orderId\":\"123\"}", AuthenticationType.AUTH2);
        //authentication.setAPP_TOKEN("fdas");
        System.out.println(JSON.toJSONString(authentication));
        String base64 = new Base64().encodeToString(JSONObject.toJSONString(authentication).getBytes());
        System.out.println("base64: " + base64);
    }
}


class IEntry{
    int id;
    String name;

    public IEntry(int id) {
        this.id = id;
        this.name = name;
    }

    public IEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}