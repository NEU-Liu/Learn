package opnc;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @desc:
 * @fileName: Authentication
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 14:43:43
 * @modifier:
 */
public class Authentication implements Serializable {

    /**
     * 应用ID
     */
    @JSONField(name = "APP_ID",ordinal = 0)
    private String APP_ID;

    /**
     * 时间戳 2016-04-12 15:06:06 100
     */
    @JSONField(name = "TIMESTAMP", ordinal = 1)
    private String TIMESTAMP;

    /**
     * 业务ID
     */
    @JSONField(name = "TRANS_ID", ordinal = 2)
    private String TRANS_ID;

    /**
     * 业务APP_TOKEN
     */
    @JSONField(name = "APPTOKEN", ordinal = 3)
    private String APPTOKEN;

    /**
     * 签名
     */
    @JSONField(name = "TOKEN", ordinal = 4)
    private String TOKEN;

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getTRANS_ID() {
        return TRANS_ID;
    }

    public void setTRANS_ID(String TRANS_ID) {
        this.TRANS_ID = TRANS_ID;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getAPPTOKEN() {
        return APPTOKEN;
    }

    public void setAPPTOKEN(String APPTOKEN) {
        this.APPTOKEN = APPTOKEN;
    }
}
