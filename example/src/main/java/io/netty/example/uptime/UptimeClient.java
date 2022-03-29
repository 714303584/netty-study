/*
 * Copyright 2012 The Netty Project
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
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.Buffer;
import java.util.zip.Deflater;


/**
 * Connects to a server periodically to measure and print the uptime of the
 * server.  This example demonstrates how to implement reliable reconnection
 * mechanism in Netty.
 */
public final class UptimeClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8888"));
    // Sleep 5 seconds before a reconnection attempt.
    static final int RECONNECT_DELAY = Integer.parseInt(System.getProperty("reconnectDelay", "5"));
    // Reconnect when the server sends nothing for 10 seconds.
    private static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("readTimeout", "10"));

    private static final UptimeClientHandler handler = new UptimeClientHandler();
    private static final Bootstrap bs = new Bootstrap();

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        bs.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST, PORT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        // ch.pipeline().addLast(new IdleStateHandler(READ_TIMEOUT, 0, 0), handler);

                    }
                });
        ChannelFuture channelFuture = bs.connect();
        int i = 0;
        Thread.sleep(1000);

        while (true) {


            User user = new User();
            user.setUserId(i + "");
            user.setUserName("小猪猪" + i);
            user.setDesc("d11111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                    "111111111111111111111111aaaaa" +
                    "11111111aaaaaaaaaaaaaaaaaaa" + i);
            String userString = JSON.toJSONString(user);


            byte[] bytes = userString.getBytes("UTF-8");
            System.out.println("压缩前：" + bytes.length);

            byte[] comBytes = Com.compress(bytes);
            System.out.println("加密前：" + comBytes.length);

            System.out.println("加密后：" + comBytes.length);
//
            ByteBuf byteBuf = Unpooled.buffer();

            channelFuture.channel().writeAndFlush(
                    byteBuf
                            .writeInt(comBytes.length)
                            .writeBytes(comBytes)
            );
            //Thread.sleep(110);
            i++;
            if( i >1 ){
                return;
            }
        }
//        channelFuture.channel().flush();

    }

    static void connect() {
        bs.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.cause() != null) {
                    handler.startTime = -1;
                    handler.println("Failed to connect: " + future.cause());
                }
            }
        });
    }
}
