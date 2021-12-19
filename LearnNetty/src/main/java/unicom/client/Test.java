package unicom.client;



import java.util.List;
import java.util.concurrent.*;

/**
 * @author caoyouyuan
 * @version 1.0
 * @date 2021/11/22 13:43
 */
public class Test {

    public static void main(String[] args) throws Exception{



        WSClient client = new WSClient();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        executor.execute(client);
        Thread.sleep(5_000);
        client.send("Hello!");

        //client.disConnect();

    }
}
