package singleton;

/**
 * @author liujd65
 * @date 2021/12/13 9:37
 **/
public class Test01 {
    public static void main(String[] args) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                Singleton0 instance1 = Singleton0.getInstance("1234");
                instance1.put("123","345");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Singleton0 instance1 = Singleton0.getInstance("1234");
                System.out.println(instance1.get("123"));
            }
        }).start();




    }
}
