package unicom.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author caoyouyuan
 * @version 1.0
 * @date 2021/11/17 13:34
 */
public class MockClientInitializer extends ChannelInitializer<SocketChannel> {

    private WebSocketClientHandler mockClientHandler;

    public MockClientInitializer(WebSocketClientHandler mockClientHandler) {
        this.mockClientHandler = mockClientHandler;
    }


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 将请求与应答消息编码或者解码为HTTP消息
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        // 客户端Handler
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new IdleStateHandler(0,3,0, TimeUnit.SECONDS));
        pipeline.addLast(new HeartCheckClientHandler());
        pipeline.addLast("handler", mockClientHandler);
    }
}
