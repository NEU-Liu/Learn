package unicom.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 功能描述: 通道初始化器器
 * 用来加载通道处理器(channelhandler)
 * @Author: Zyh
 * @Date: 2020/1/22 20:31
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    //初始化通道
    //在这个方法中加载对应的ChannelHandler
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        /* 固定写法部分*/
        //获取管道,将一个个ChannelHandler添加到管道中
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //可以将channelpipeline理解为拦截器
        //当我们的socketChannel数据进来时候会依次调用我们的ChannelHandler

        //添加一个http的编解码器
        channelPipeline.addLast(new HttpServerCodec());
        //添加大数据流支持
        channelPipeline.addLast(new ChunkedWriteHandler());
        //添加聚合器 ,可以将我们的httpmaessage聚合成Fullhttprequest/respond ---想拿到请求和响应就要添加聚合器
        channelPipeline.addLast(new HttpObjectAggregator(1024*24));//指定缓存大小
        /* 固定写法部分*/

        //指定接收请求的路由
        //指定必须使用ws为结尾的url才能访问
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

       // channelPipeline.addLast(new TaskHandler());

        //添加自定义的handler进行业务处理
        channelPipeline.addLast(new ChatHandler());


    }
}
