package com.io.demo.socketDemo.custom.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength){
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
        super.decode(ctx, in);
        return null;
    }
}

