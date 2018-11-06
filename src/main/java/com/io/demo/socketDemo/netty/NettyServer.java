package com.io.demo.socketDemo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws Exception{
        //1.用于对接受客户端连接的线程工作组
        EventLoopGroup boss = new NioEventLoopGroup();
        //2.用于对接受客户端连续读写操作的线程工作组
        EventLoopGroup work = new NioEventLoopGroup();
        //3.辅助类，用于创建netty服务
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, work)
         .channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 1024)
         //.option(ChannelOption.SO_SNDBUF, 32*1024)
         .option(ChannelOption.SO_RCVBUF, 32*1024)
         .childOption(ChannelOption.SO_KEEPALIVE, true)
         .childOption(ChannelOption.SO_SNDBUF, 32*1024)
         //初始化绑定服务通道
         .childHandler(new ChannelInitializer<SocketChannel>() {
             @Override
             protected void initChannel(SocketChannel sc) throws Exception{
                 sc.pipeline().addLast(new NettyServerHandler());

             }
         });

        ChannelFuture cf = b.bind(8765).sync();

        cf.channel().closeFuture().sync();
        work.shutdownGracefully();
        boss.shutdownGracefully();
    }
}
