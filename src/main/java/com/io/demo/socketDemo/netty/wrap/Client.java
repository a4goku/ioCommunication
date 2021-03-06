package com.io.demo.socketDemo.netty.wrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class Client {
    public static void main(String[] args) throws Exception{
        //2.用于对接受客户端连续读写操作的线程工作组
        EventLoopGroup work = new NioEventLoopGroup();
        //3.辅助类，用于创建netty服务
        Bootstrap b = new Bootstrap();
        b.group(work)
         .channel(NioSocketChannel.class)
         .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception{
                //定义特殊字符进行分割
                ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

                sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));

                sc.pipeline().addLast(new ClientHandler());
            }
         });

        ChannelFuture cf = b.connect("127.0.0.1", 8765).syncUninterruptibly();


        cf.channel().write(Unpooled.copiedBuffer("Hello, netty-1$_".getBytes()));

        cf.channel().write(Unpooled.copiedBuffer("Hello, netty-2$_".getBytes()));

        cf.channel().write(Unpooled.copiedBuffer("Hello, netty-3$_".getBytes()));

        cf.channel().writeAndFlush(Unpooled.copiedBuffer("Hello, netty-4$_".getBytes()));

        cf.channel().closeFuture().sync();
        work.shutdownGracefully();
    }
}
