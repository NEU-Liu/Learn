package example;

import org.apache.zookeeper.*;

import java.io.IOException;

public class Server {


    private String connectString = "127.0.0.1:2181";
    private int sessionTimeout = 2000*5;
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception{
        Server server = new Server();
        server.connect();
        String hostName = "server4";
        String hostNameData = "server4Data";
        server.regist(hostName,hostNameData);
    }

    private void connect() throws IOException{
        zk = new ZooKeeper("127.0.0.1:2181", 2000 * 5, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Zookeeper state changes!");
            }
        });
    }

    private void regist(String hostName, String hostNameData) throws InterruptedException, KeeperException {
        String string = zk.create("/servers/" + hostName, hostNameData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

}
