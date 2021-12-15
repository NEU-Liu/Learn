package singleton;

public class Singleton1 {
    private static Singleton1 instance = new Singleton1();
    private Singleton1(){
        System.out.println("-----------");
    }
    public static Singleton1 getInstance() {
    return instance;  
    }  
}