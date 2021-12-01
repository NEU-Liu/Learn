package ssl;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.*;

/*
 * This example demostrates how to use a SSLSocket as client to
 * send a HTTP request and get response from an HTTPS server.
 * It assumes that the client is not behind a firewall
 */

public class SSLSocketClient {

    public static void main(String[] args) throws Exception {
        try {
           // SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
           // SSLSocket socket = (SSLSocket)factory.createSocket("www.baidu.com", 443);

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("www.baidu.com",443));

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            Socket sslSocket = factory.createSocket(socket, "www.baidu.com", 443, true);

            ((SSLSocket)sslSocket).startHandshake();

             socket = sslSocket;



            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())));

            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();

            /*
             * Make sure there were no surprises
             */
            if (out.checkError()) {
                System.out.println("SSLSocketClient:  java.io.PrintWriter error");
            }


            /* read response */
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String inputLine;
            System.out.println("response:");
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }


            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}