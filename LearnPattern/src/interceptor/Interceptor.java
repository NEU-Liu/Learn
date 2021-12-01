package interceptor;

/**
 * @author 86175
 */
public interface Interceptor {
    void before(Invocation invocation);
 
    String intercept(Invocation invocation);
 
    void after(Invocation invocation);
}