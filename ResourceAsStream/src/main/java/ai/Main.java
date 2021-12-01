package ai;

import sun.misc.Launcher;
import sun.misc.Resource;
import sun.misc.URLClassPath;

import javax.management.openmbean.TabularType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author liujd65
 * @date 2021/11/15 21:12
 **/
public class Main {
    public static void main(String[] args) {
        Properties properties = new Main().properties("conf.properties");
        System.out.println(properties);

        new Main().TT();

    }


    private void TT(){

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream fdas = classLoader.getResourceAsStream("fdas");
        System.out.println(classLoader);



        URL[] url = sun.misc.Launcher.getBootstrapClassPath().getURLs();

        for (URL value : url) {
            System.out.println(value.toExternalForm());
        }

        URLClassPath urlClassPath = Launcher.getBootstrapClassPath();

        Resource resource = urlClassPath.getResource("conf.properties");
        System.out.println(resource);

    }



    private Properties properties(String FILE_NAME) {
        Properties properties = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        if (in == null) {
            return null;
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
