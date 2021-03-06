package com.io.demo.socketDemo.netty.wrap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


public class ServerHandler extends ChannelInboundHandlerAdapter {
     @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
         System.err.println("-----------通道激活----------");
     }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf;
        buf = (ByteBuf)msg;
        byte[] request = new byte[buf.readableBytes()];
        buf.readBytes(request);
        String body = new String(request, "utf-8");
        System.out.println("服务器： " + body);

        String response = "我是返回的数据$_";
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        System.err.println("-----------通道关闭----------");
    }
}
