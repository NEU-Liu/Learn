package singleton;

/**
 * @author liujd65
 * @date 2021/12/13 9:37
 **/
public class Test02 {
    public static void main(String[] args) {
        Singleton0 instance1 = Singleton0.getInstance("123");
        Singleton0 instance2 = Singleton0.getInstance("1234");
        Singleton0 instance3 = Singleton0.getInstance("123");
        for (;;){}
    }
}
