package unicom.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;


import java.net.URI;
import java.net.URISyntaxException;



/**
 * @author caoyouyuan
 * @version 1.0
 * @date 2021/11/17 17:27
 */
public class WSClient implements Runnable{






    private final String wsUrl = "ws://localhost:9090";


    private Channel serviceChannel;




    public WSClient(){

    }



    @Override
    public void run() {
        webSocketConnect(wsUrl);
    }


    public void webSocketConnect(String url){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            try {
            URI uri = new URI(url);
                System.out.println(uri);
            Bootstrap bootstrap = new Bootstrap();
            WebSocketClientHandler webSocketClientHandler = new WebSocketClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(uri
                            , WebSocketVersion.V13
                            , null
                            , false
                            , new DefaultHttpHeaders()));

            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).
                    handler(new MockClientInitializer(webSocketClientHandler));

            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();

            this.serviceChannel = channel;
            channel.closeFuture().sync();

        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }




    public void disConnect(){
        if (serviceChannel == null) {
            return;
        }
        if (serviceChannel.isOpen()) {
            serviceChannel.write(new CloseWebSocketFrame());
            serviceChannel.close();
        }
        serviceChannel = null;
    }
}
