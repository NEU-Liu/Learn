package unicom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperBase {
    
    private static Logger logger=Logger.getLogger(ZookeeperBase.class);

    private ZooKeeper zookeeper;
    private String connectString;//连接zk服务端的ip:port,如果多个格式为 ip:port,ip:port......
    private int sessionTimeout;//客户端连接超时时间  单位 ms
    /** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    private CountDownLatch countDown=new CountDownLatch(1);
    
    public ZookeeperBase(String connectString,int sessionTimeout) throws IOException{
        this.connectString=connectString;
        this.sessionTimeout=sessionTimeout;
        
        zookeeper=new ZooKeeper(this.connectString, this.sessionTimeout, new Watcher() {
            
            public void process(WatchedEvent event) {
                //影响的路径
                String path = event.getPath();
                //获取事件的状态
                KeeperState state = event.getState();
                //获取事件的类型
                EventType type = event.getType();
                if(KeeperState.SyncConnected.equals(state)){
                    if(EventType.None.equals(type)){
                        //连接建立成功，则释放信号量，让阻塞的程序继续向下执行
                        countDown.countDown();
                        logger.info("zk建立连接成功========");
                    }
                }
            }
        });
    }
    
    public ZooKeeper getZkClient() throws InterruptedException{
        //计数器到达0之前，一直阻塞，只有当信号量被释放，才会继续向下执行
        countDown.await();
        return zookeeper;
    }
    
    public void closeClient() throws InterruptedException{
        if(zookeeper!=null){
            zookeeper.close();
            logger.info("zk关闭连接成功=======");
        }
    }
    
    /**
     * 创建节点
     * @param path 节点path
     * @param data 节点数据
     * @return
     * @throws InterruptedException
     * @throws KeeperException
     */
    public String createNode(String path,String data) throws InterruptedException, KeeperException{
        ZooKeeper zk = this.getZkClient();
        String str = zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        return str;
    }
    
    /**
     * 更新节点数据
     * @param path 节点path
     * @param data 节点数据
     * @return
     * @throws InterruptedException
     * @throws KeeperException
     */
    public Stat updateNode(String path,String data) throws InterruptedException, KeeperException{
        ZooKeeper zk = this.getZkClient();
        Stat stat = zk.setData(path, data.getBytes(),-1);
        return stat;
    }
    
    /**
     * 获得节点数据
     * @param path 节点path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException{
        ZooKeeper zk = this.getZkClient();
        byte[] data = zk.getData(path, false, null);
        return new String(data);
    }
    
    /**
     * 获取当前节点的子节点(不包含孙子节点)
     * @param path 父节点path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException{
        ZooKeeper zk = this.getZkClient();
        List<String> list = zk.getChildren(path, false);
        return list;
    }
    
    /**
     * 判断节点是否存在
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat exists(String path) throws KeeperException, InterruptedException{
        ZooKeeper zk = this.getZkClient();
        Stat stat = zk.exists("/", false);
        return stat;
    }
    
    /**
     * 删除节点
     * @param path
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path) throws InterruptedException, KeeperException{
        ZooKeeper zk = this.getZkClient();
        zk.delete(path, -1);
        logger.info("删除 "+path+" 节点成功");
    }
    
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperBase base = new ZookeeperBase("127.0.0.1:2181", 20000);
        ZooKeeper zkClient = base.getZkClient();
        System.out.println("sessionId:"+zkClient.getSessionId()+",sessionTimeOut:"+zkClient.getSessionTimeout());
        base.closeClient();
    }
}
