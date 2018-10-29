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
        return 1;
    }

    @Override
    public int read(byte[] var1) throws IOException{
        return 1;
    }

    @Override
    public int read(byte[] var1, int var2, int var3) throws IOException{
        return 1;
    }

    @Override
    public int available() throws IOException{
        return 1;
    }
    @Override
    public long skip(long var1) throws IOException{
        return 1l;
    }

    ByteBuf getBuffer(){
        return buffer;
    }
}
