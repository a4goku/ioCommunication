package com.io.demo.socketDemo.custom.codec;

import com.io.demo.socketDemo.custom.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    private MarshallerEncoder marshallerEncoder;

    public NettyMessageEncoder() throws Exception{
        this.marshallerEncoder = new MarshallerEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage message, ByteBuf sendBuf) throws Exception {
        if(message == null || message.getHeader() == null){
            throw new Exception("编码失败");
        }

        //Head
        sendBuf.writeInt(message.getHeader().getCrcCode());
        sendBuf.writeInt(message.getHeader().getLength());
        sendBuf.writeLong(message.getHeader().getSessionID());
        sendBuf.writeByte(message.getHeader().getType());
        sendBuf.writeByte(message.getHeader().getPriority());

        Object body = message.getBody();
        if(body != null){
            //使用MarshalingEncoder
            this.marshallerEncoder.encode(body, sendBuf);
        } else{
            //如果没有数据 进行补位 为了方便后续的decoder操作
            sendBuf.writeInt(0);
        }

        //最后要获取整个数据包的总长度，header+body进行对header length的设置
        sendBuf.setIndex(4, sendBuf.readableBytes() - 8);
    }
}
