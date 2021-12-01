package ismtps;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;



/**
 * @author 86175
 */
public class SMTPSClient {

    public static void main(String[] args) throws Exception {
        try {
            SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket)factory.createSocket("smtp.163.com", 465);


            socket.startHandshake();

            PrintWriter out = new PrintWriter(
                                  new BufferedWriter(
                                  new OutputStreamWriter(
                                  socket.getOutputStream())));

            out.println("helo fs");
            out.flush();

            /*
             * Make sure there were no surprises
             */
            if (out.checkError()){
                System.out.println("SSLSocketClient:  java.io.PrintWriter error");
            }


            /* read response */
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null){
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