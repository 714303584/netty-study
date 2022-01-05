package io.netty.example.uptime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {
    int start;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){
            return;
        }
        in.markReaderIndex();
        int len = in.readInt();
        System.out.println("read len:"+len);
        int canread = in.readableBytes();
        if(canread > len){
            System.out.println("can read len:"+canread);
            out.add(in.readBytes(len));
        }else{
            in.resetReaderIndex();
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }


}
