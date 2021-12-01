package example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String connectString = "127.0.0.1:2181";
    private int sessionTimeout = 2000*5;
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception{
        Client client = new Client();
        client.connect();
        client.getServerList();
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void connect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getServerList() throws InterruptedException, KeeperException {
        List<String> children = zk.getChildren("/servers", true);
        ArrayList<String> servers = new ArrayList<String>();
        for (String child:children){
            byte[] data = zk.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }

        for (String item:servers){
            System.out.println(item);
        }
    }

}
