package unicom;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class ZkClient {

    private ZooKeeper zkClient;

    @BeforeTest
    public void init() throws IOException {
        zkClient = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 2000 * 5, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Zookeeper state changes!");
            }
        });
    }




    @Test
    public void create() throws InterruptedException, KeeperException {
        String zkNode = zkClient.create("/xiao", "xiaomianyang".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zkNode);
    }

    @Test
    public void getData () throws InterruptedException, KeeperException {
        byte[] data = zkClient.getData("/xiao", new Watcher() {
            public void process(WatchedEvent event) {

            }
        }, new Stat());
        System.out.println(new String(data));
    }


    @Test
    public void getChildren () throws InterruptedException, KeeperException {
        List<String> list = zkClient.getChildren("/", true);
        System.out.println(list);
    }

    @Test
    public void exits () throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/xiao", false);
        System.out.println(stat);
    }


}
