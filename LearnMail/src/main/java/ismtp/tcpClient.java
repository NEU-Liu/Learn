package ismtp;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class tcpClient {


    public void toServer(String ip,int port,String data){
        try {

            Socket socket=new Socket(ip,port);

            Thread send = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String line = null;
                    System.out.println("----Start----");
                    while ((line = br.readLine()) != null) {
                        if ("88".equals(line)) {
                            break;
                        }
                        bw.write(line);
                        bw.newLine();
                        bw.flush();
                    }

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


            //接收服务器的消息

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new tcpClient().toServer("smtp.163.com",465,"我是柚西");
    }
}