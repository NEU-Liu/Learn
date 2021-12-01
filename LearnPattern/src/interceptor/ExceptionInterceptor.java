package interceptor;

/**
 * @author 86175
 */
public class ExceptionInterceptor implements Interceptor {
 
    @Override
    public void before(Invocation invocation) {
        System.out.println("ExceptionInterceptor before...");
    }
 
    @Override
    public String intercept(Invocation invocation) {
        this.before(invocation);
        // 实际代码应该是try catch，在catch中做事情
        invocation.invoke();
        this.after(invocation);
        return null;
    }
 
    @Override
    public void after(Invocation invocation) {
        System.out.println("ExceptionInterceptor after...");
    }
}