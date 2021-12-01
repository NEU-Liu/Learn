package opnc;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @desc:
 * @fileName: ResponseBody
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 17:33:30
 * @modifier:
 */
public class ResponseBody {

    /**
     * 响应头
     */
    @JSONField(name = "UNI_NET_HEAD")
    private ResponseHeader UNI_NET_HEAD;

    /**
     * 返回内容
     */
    @JSONField(name = "UNI_NET_BODY")
    private Object UNI_NET_BODY;

    public ResponseHeader getUNI_NET_HEAD() {
        return UNI_NET_HEAD;
    }

    public void setUNI_NET_HEAD(ResponseHeader UNI_NET_HEAD) {
        this.UNI_NET_HEAD = UNI_NET_HEAD;
    }

    public Object getUNI_NET_BODY() {
        return UNI_NET_BODY;
    }

    public void setUNI_NET_BODY(Object UNI_NET_BODY) {
        this.UNI_NET_BODY = UNI_NET_BODY;
    }
}
