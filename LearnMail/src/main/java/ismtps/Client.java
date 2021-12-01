package ismtps;

import lombok.SneakyThrows;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;


/**
 * @author 86175
 */
public class Client {

    public static void main(String[] args) throws Exception {
        try {
            SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket)factory.createSocket("smtp.163.com", 465);


            socket.startHandshake();

            Thread send = new Thread(new Runnable() {

                void send(String data,BufferedWriter bw){

                    try {
                        bw.write(data);
                        bw.newLine();
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                @SneakyThrows
                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String line = null;
                    System.out.println("----Start----");
                    send("helo fs",bw);
                    send("auth login",bw);
                    send("bGl1ZGQwMTA5QDE2My5jb20=",bw);
                    send("TkNGQURSUkpLTElCVE1NWA==",bw);
                    send("mail from: <liudd0109@163.com>",bw);
                    send("rcpt to: <1531653614@qq.com>",bw);
                    send("data",bw);
                    send("from: <liudd0109@163.com>",bw);
                    send("to: <1531653614@qq.com>",bw);
                    send("subject:Nice to See You!",bw);
                    send("World",bw);
                    send(".",bw);

                    System.out.println("end send!");
                }
            });

            send.start();

            Thread recv = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line ="";

                    while ((line =br.readLine())!=null){

                        System.out.println(line);
                    }
                    System.out.println("end recv!");
                }
            });

            recv.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}