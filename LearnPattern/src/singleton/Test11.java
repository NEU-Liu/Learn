package singleton;

/**
 * @author liujd65
 * @date 2021/12/13 9:42
 **/
public class Test11 {
    public static void main(String[] args) {
        String appId = "id";
        String appSecret = "secret";

        String identity = String.format("%s" + "%s", appId, appSecret);
        System.out.println(identity);

    }
}
