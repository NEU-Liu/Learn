package opnc.multipart;

/**
 * @desc:
 * @fileName: TextMultipartParam
 * @author: tangjiaxiang
 * @createTime: 2020/12/1 17:33:33
 * @modifier:
 */
public class TextMultipartParam implements MultipartParam {

    /**
     * 参数名
     */
    private String name;
    /**
     * 参数值
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
