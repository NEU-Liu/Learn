package unicom.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


class TestTask implements Runnable {

    private final ChannelHandlerContext ctx;

    public TestTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        //do something
        ctx.writeAndFlush(Unpooled.copiedBuffer(String.valueOf(System.currentTimeMillis()), CharsetUtil.UTF_8));
    }
}


public class TaskHandler extends ChannelHandlerAdapter {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private java.util.concurrent.ScheduledFuture<?> heartBeat;



    @Override
    public void channelActive(ChannelHandlerContext ctx) {

      //  this.heartBeat = this.scheduler.scheduleWithFixedDelay(new TestTask(ctx),0,2, TimeUnit.SECONDS);

     this.heartBeat = this.scheduler.scheduleWithFixedDelay(new TestTask(ctx),0,2,TimeUnit.SECONDS);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive   连接断开了");
    }


}
