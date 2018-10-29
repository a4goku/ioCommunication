package com.io.demo.socketDemo.custom.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

public class ChannelBufferByteInput implements ByteInput {
    private ByteBuf buffer;

    public ChannelBufferByteInput(ByteBuf buffer){
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException{

    }

    @Override
    public int read() throws IOException{
        if (buffer.isReadable()) {
            return buffer.readByte() & 0xff;
        }
        return -1;
    }

    @Override
    public int read(byte[] var1) throws IOException{
        return read(var1, 0, var1.length);
    }

    @Override
    public int read(byte[] dst, int dstIndex, int length) throws IOException{
        int available = available();
        if (available == 0) {
            return -1;
        }

        length = Math.min(available, length);
        buffer.readBytes(dst, dstIndex, length);
        return length;
    }

    @Override
    public int available() throws IOException{
        return buffer.readableBytes();
    }
    @Override
    public long skip(long bytes) throws IOException{
        int readable = buffer.readableBytes();
        if (readable < bytes) {
            bytes = readable;
        }
        buffer.readerIndex((int) (buffer.readerIndex() + bytes));
        return bytes;
    }

}
