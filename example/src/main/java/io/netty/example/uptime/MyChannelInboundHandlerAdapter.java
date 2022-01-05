package io.netty.example.uptime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.codec.CodecOutputList;

public class MyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {


    /**
     * 实现自己的解码器
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        if (msg instanceof ByteBuf) {
//            CodecOutputList out = CodecOutputList.newInstance();
//
//        }



    }



}
