package interceptor;

/**
 * @author liujd65
 * @date 2021/11/11 14:17
 **/
public class Main {
    public static void main(String[] args) {
        Invocation invocation = new Invocation();
        invocation.setBusinessService(new BusinessService());
        invocation.invoke();
    }
}
