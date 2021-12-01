package interceptor;

import java.util.ArrayList;
import java.util.List;

public class Invocation {
    int index = 0;
    // 调度器管理业务
    private BusinessService bs;
    // 调度器管理拦截器List
    private List<Interceptor> interceptions = new ArrayList<Interceptor>();
    
    public Invocation() {
        interceptions.add(new LogInterceptor());
        interceptions.add(new ExceptionInterceptor());
    }
    
    public void invoke() {
        if (index == interceptions.size()) {
            bs.perform();
        }
        else {
            Interceptor interceptor = interceptions.get(index);
            index++;
            interceptor.intercept(this);
        }
    }
    
    public void setBusinessService(BusinessService bs) {
        this.bs = bs;
    }
}