package interceptor;

/**
 * @author 86175
 */
public class LogInterceptor implements Interceptor {
 
    @Override
    public void before(Invocation invocation) {
        System.out.println("LogInterceptor before...");
    }
 
    @Override
    public String intercept(Invocation invocation) {
        this.before(invocation);
        invocation.invoke();
        this.after(invocation);
        return null;
    }
 
    @Override
    public void after(Invocation invocation) {
        System.out.println("LogInterceptor after...");
    }
}