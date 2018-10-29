package com.io.demo.socketDemo.custom.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

public class MarshallerDecoder {
    private Unmarshaller unmarshaller;

    public MarshallerDecoder() throws IOException{
        this.unmarshaller = MarshlingCodeFactory.buildUnMarshaller();
    }

    public Object decode(ByteBuf in) throws Exception{
        try {
            int bodySize = in.readInt();
            ByteBuf buf = in.slice(in.readerIndex(), bodySize);
            ChannelBufferByteInput input = new ChannelBufferByteInput(buf);
            this.unmarshaller.start(input);
            Object ret = this.unmarshaller.readObject();
            this.unmarshaller.finish();
            in.readerIndex(in.readerIndex() + bodySize);

            return ret;

        } finally {
            this.unmarshaller.close();
        }
    }
}
