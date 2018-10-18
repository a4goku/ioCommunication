package com.io.demo.socketDemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args){
        //1.描述套接字的ip，端口
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 9642);
        //2.缓冲区
        ByteBuffer readBuf = ByteBuffer.allocate(1024);

        ByteBuffer writeBuf = ByteBuffer.allocate(1024);
        //3.声明一下客户端通道
        try {
            //4.打开客户端通道
            SocketChannel sc = SocketChannel.open();
            //5.与服务器地址进行连接
            sc.connect(addr);

            while (true) {
                byte[] data = new byte[1024];
                System.in.read(data);

                writeBuf.put(data);
                writeBuf.flip();
                sc.write(writeBuf);

                writeBuf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
