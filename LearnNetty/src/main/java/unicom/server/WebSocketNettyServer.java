package unicom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketNettyServer {
    public static void main(String[] args){  
        //创建netty的主从两个线程池
        NioEventLoopGroup master = new NioEventLoopGroup();//主线程池
        NioEventLoopGroup worker = new NioEventLoopGroup();//从线程池

        try {
            //创建Netty服务器启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //初始化服务器启动对象
            serverBootstrap
                    //为netty服务器指定和配备主从线程池
                    .group(master,worker)
                    //指定netty通道类型
                    .channel(NioServerSocketChannel.class)
                    //指定通道初始化器用来加载当channel收到消息后
                    //如何进行业务处理
                    .childHandler(new WebSocketChannelInitializer());

            //绑定服务器端口, 以同步的方式启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(9090).sync();
            //等待服务器关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //使用优雅的方式关闭服务器
            //主要是关闭两个线程池
            master.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
