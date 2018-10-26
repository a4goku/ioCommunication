package com.io.demo.socketDemo.custom.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

public class MarshallerEncoder {
    //空白占位，用于预留设置body的数据包长度
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    private Marshaller marshaller;

    public MarshallerEncoder() throws IOException{
        this.marshaller = MarshlingCodeFactory.buildMarshaller();
    }

    public void encode(Object body, ByteBuf out) throws IOException{
        int pos = out.writerIndex();
        //占位
        out.writeBytes(LENGTH_PLACEHOLDER);
        ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
        marshaller.start(output);
        marshaller.writeObject(body);
        marshaller.finish();;

        out.setInt(pos, out.writerIndex() - pos - 4);
    }
}
