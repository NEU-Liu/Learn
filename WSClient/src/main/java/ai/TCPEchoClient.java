package ai;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class TCPEchoClient {

    public static void main(String[] args) throws Exception {

        String ip = "127.0.0.1";
        int port = 8088;


        Http http = new Http("/hi");


        Socket socket = new Socket(ip, port);
        //socket.bind(new InetSocketAddress(9999));
        http.addHeader("Host", "localhost:" + socket.getLocalPort());
        //http.addHeader("Accept","*/*");
        // System.out.println(socket.getLocalPort());
        http.endHeader();
        byte[] data = http.toBytes();

        System.out.println("Connected to server... sending echo string");

        System.out.println(new String(data));


        socket.isClosed();


        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(data);

        int totalBytesRcvd = 0;
        int bytesRcvd;




        byte[] recvData = new byte[1024];


        System.out.println("====================");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        System.out.print(br.readLine());
        System.out.print(br.readLine());
        System.out.print(br.readLine());
        System.out.print(br.readLine());
        System.out.print(br.readLine());
        System.out.println("-------");


        char[] content = new char[5];
        System.out.println(br.read(content));
        System.out.println(new String(content));

        System.out.println("-------------");


        //int read = in.read(recvData);
        //System.out.println("Receved: " + new String(recvData,0,read));


        socket.close();
    }
}  