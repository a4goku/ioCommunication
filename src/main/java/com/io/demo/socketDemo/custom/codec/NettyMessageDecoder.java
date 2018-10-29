package com.io.demo.socketDemo.custom.codec;

import com.io.demo.socketDemo.custom.struct.Header;
import com.io.demo.socketDemo.custom.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MarshallerDecoder marshallerDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        this.marshallerDecoder = new MarshallerDecoder();
    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);

        if(frame == null){
            return null;
        }

        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        message.setHeader(header);

        if(frame.readableBytes() > 4){
            message.setBody(marshallerDecoder.decode(frame));
        }
        return message;
    }
}

