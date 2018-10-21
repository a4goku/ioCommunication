package com.io.demo.socketDemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                sc.pipeline().addLast(new ClientHandler());

            }
         });

        ChannelFuture cf = b.connect("127.0.0.1", 8765).syncUninterruptibly();

        cf.channel().writeAndFlush(Unpooled.copiedBuffer("胡二狗天天刷抖音".getBytes()));
    }
}
