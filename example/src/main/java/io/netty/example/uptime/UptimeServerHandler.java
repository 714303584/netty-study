/*
 * Copyright 2017 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.uptime;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

@Sharable
public class UptimeServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        byte[] unCompress = Com.uncompress(bytes);
        String stringMsg = new String(unCompress, "UTF-8");
        System.out.println("message:" + msg);
        User user = JSON.toJavaObject(JSON.parseObject(stringMsg), User.class);
        System.out.print(user.toString()+"\n");
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
