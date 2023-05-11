package io.netty.example.uptime;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

@Sharable
public class UptimeServerHandler2 extends SimpleChannelInboundHandler<User> {

    String name = "";

    public UptimeServerHandler2(String name) {
        this.name = name;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, User user) throws Exception {
        System.out.print(name + "---"+user.toString()+"\n");
//        ReferenceCountUtil.release(msg);
        // discard
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
