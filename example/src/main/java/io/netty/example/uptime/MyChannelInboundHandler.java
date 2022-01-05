package io.netty.example.uptime;

import com.sun.org.apache.xpath.internal.operations.String;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyChannelInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

       int len = msg.readInt();
       ByteBuf my = msg.readBytes(len);

       System.out.println(my.toString(CharsetUtil.UTF_8));

    }
}
