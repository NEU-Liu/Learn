package unicom.client;



import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author caoyouyuan
 * @version 1.0
 * @date 2021/11/17 12:04
 */
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {



    private final WebSocketClientHandshaker webSocketClientHandshaker;



    public WebSocketClientHandler(WebSocketClientHandshaker webSocketClientHandshaker) {

        this.webSocketClientHandshaker = webSocketClientHandshaker;

    }

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {

        Channel channel = channelHandlerContext.channel();
        webSocketClientHandshaker.handshake(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已关闭!");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object data) throws Exception {
        final Channel ch = ctx.channel();
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            webSocketClientHandshaker.finishHandshake(ch, (FullHttpResponse) data);
            return;
        }

        if (data instanceof FullHttpResponse) {
            final FullHttpResponse response = (FullHttpResponse) data;
            throw new Exception("Unexpected FullHttpResponse (content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        final WebSocketFrame frame = (WebSocketFrame) data;
        if (frame instanceof TextWebSocketFrame) {
            final TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;

            String content = textFrame.text();



            if(content == null || content.length() <= 0){

            }

        } else if (frame instanceof PongWebSocketFrame) {
        } else if (frame instanceof CloseWebSocketFrame) {
            ch.close();
        } else if (frame instanceof BinaryWebSocketFrame) {
            // uncomment to print request
            // logger.info(frame.content().toString());
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }


}
