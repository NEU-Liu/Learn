package note;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.Security;

/**
 * @author liujd65
 * @date 2021/11/29 9:44
 **/
public class ISecurity {
    public static void main(String[] args) throws Exception {
        // reserve the security properties
        String reservedSSFacProvider =
                Security.getProperty("ssl.SocketFactory.provider");
        System.out.println(reservedSSFacProvider);
        try {
            Security.setProperty("ssl.SocketFactory.provider", "oops");
            ServerSocketFactory ssocketFactory =
                    SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket =
                    (SSLServerSocket)ssocketFactory.createServerSocket();
        } catch (Exception e) {
            if (!(e.getCause() instanceof ClassNotFoundException)) {
                throw e;
            }
            // get the expected exception
        } finally {
            // restore the security properties
            if (reservedSSFacProvider == null) {
                reservedSSFacProvider = "";
            }
            Security.setProperty("ssl.ServerSocketFactory.provider",
                    reservedSSFacProvider);
        }
    }
}
