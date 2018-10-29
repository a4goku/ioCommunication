package com.io.demo.socketDemo.custom.server;

import com.io.demo.socketDemo.custom.struct.Header;
import com.io.demo.socketDemo.custom.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.err.println("---------通道激活--------");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage)msg;
        System.err.println("Server: " + message.getBody());

        NettyMessage response = new NettyMessage();
        Header header = new Header();
        header.setSessionID(2002L);
        header.setPriority((byte)2);
        header.setType((byte)2);
        message.setHeader(header);
        message.setBody("我是响应数据-----" + response.getBody());
        ctx.writeAndFlush(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.err.println("-----------通道关闭----------");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.err.println("-----------数据异常---------");
        cause.printStackTrace();
    }


}
