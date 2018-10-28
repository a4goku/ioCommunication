package com.io.demo.socketDemo.custom.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

public class ChannelBufferByteInput implements ByteOutput {
    private ByteBuf buffer;

    public ChannelBufferByteInput(ByteBuf buffer){
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException{

    }

    @Override
    public void flush() throws IOException{

    }

    @Override
    public void write(int b) throws IOException{
        buffer.writeByte(b);
    }

    @Override
    public void write(byte[] bytes) throws IOException{
        buffer.writeBytes(bytes);
    }

    @Override
    public void write(byte[] bytes, int srcIndex, int length) throws IOException{
        buffer.writeBytes(bytes, srcIndex, length);
    }

    ByteBuf getBuffer(){
        return buffer;
    }
}
