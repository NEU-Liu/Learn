package opnc;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @desc:
 * @fileName: ResponseHeader
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 17:33:27
 * @modifier:
 */
public class ResponseHeader {

    @JSONField(name = "RESP_CODE")
    private String RESP_CODE;

    @JSONField(name = "RESP_DESC")
    private String RESP_DESC;

    @JSONField(name = "TIMESTAMP")
    private String TIMESTAMP;

    public String getRESP_CODE() {
        return RESP_CODE;
    }

    public void setRESP_CODE(String RESP_CODE) {
        this.RESP_CODE = RESP_CODE;
    }

    public String getRESP_DESC() {
        return RESP_DESC;
    }

    public void setRESP_DESC(String RESP_DESC) {
        this.RESP_DESC = RESP_DESC;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }
}
