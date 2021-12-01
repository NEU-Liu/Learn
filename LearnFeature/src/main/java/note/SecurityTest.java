package note;


import java.security.Security;

public class SecurityTest {
    public static void main(String[] argv) {

        java.security.Security.setProperty("ssfdas", "TestSocketFactory");
        String property = Security.getProperty("ssfdas");
        System.out.println(property);

    }
}