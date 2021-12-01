package ai;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author liujd65
 * @date 2021/11/24 14:55
 **/
public class TT {
    public static void main(String[] args) {
        String dir = null;
        try {
            dir = AccessController.doPrivileged(
                    new PrivilegedAction<String>() {
                        @Override
                        public String run() {
                            String home = System.getProperty("java.home");
                            String newdir = home + File.separator + "conf";
                            File conf = new File(newdir);
                            if (conf.exists())
                                return newdir + File.separator;
                            else
                                return home + File.separator +
                                        "lib" + File.separator;
                        }
                    });
        } catch (Exception ex) {
            // ignore any exceptions
        }


        System.out.println(dir);
    }


}
