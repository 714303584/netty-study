package io.netty.example.uptime;

import io.netty.buffer.ByteBuf;

public class MyPackage {

    private int len;
    private ByteBuf byteBuf;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public void setByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }
}
