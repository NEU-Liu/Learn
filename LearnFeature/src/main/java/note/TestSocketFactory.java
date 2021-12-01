package note;

import java.io.IOException;
import java.net.Socket;

public class TestSocketFactory extends javax.net.ssl.SSLSocketFactory {


    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return null;
    }

    public Socket createSocket(String host, int port, java.net.InetAddress addr)
            throws IOException {
        return null;
    }

    @Override
    public Socket createSocket(String host, int port, java.net.InetAddress addr, int port2) throws IOException {
        return null;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return null;
    }

    @Override
    public Socket createSocket(java.net.InetAddress addr, int port, java.net.InetAddress addr2, int port2) {
        return null;
    }

    @Override
    public Socket createSocket(java.net.InetAddress addr, int port) {
        return null;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return null;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return null;
    }
}