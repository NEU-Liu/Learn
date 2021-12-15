package unicom.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

class PingTask implements Runnable {

    private final Channel channel;

    public PingTask(Channel channel) {
        this.channel= channel;
    }

    @Override
    public void run() {
        //发送的内容，是一个文本格式的内容
        final String putMessage="你好，我是客户端";
        TextWebSocketFrame frame = new TextWebSocketFrame(putMessage);

        channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("消息发送成功，发送的消息是："+ putMessage);
                } else {
                    System.out.println("消息发送失败 " + channelFuture.cause().getMessage());
                }
            }
        });

    }
}