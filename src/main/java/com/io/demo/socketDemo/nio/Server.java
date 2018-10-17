package com.io.demo.socketDemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{
    //1.缓冲区
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    //2.多路复用器
    private Selector selector;

    public Server(int port){
        try {
            //1.打开多路复用器
            this.selector = Selector.open();
            //2.打开服务器端的通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //3.设置阻塞模式
            ssc.configureBlocking(false);
            //4.绑定地址
            ssc.bind(new InetSocketAddress(port));
            //5.把服务器通道注册到多路复用器上，并且监听阻塞事件
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (true) {
            try {
                //1.必须让多路复用器开始监听各个通道
                this.selector.select();
                //2.返回多路复用器里所有注册的通道key
                Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
                //3.遍历获取的key
                while (it.hasNext()){
                    //4.接收key值
                    SelectionKey key = it.next();
                    //5.从容器中移除已经被选中的key
                    it.remove();
                    //6.验证操作：判断key是否有效
                    if(key.isValid()){
                        //7.如果为阻塞状态
                        if(key.isAcceptable()){
                            this.accept(key);
                        }
                        //8.如果为可读状态
                        if(key.isReadable()){
                            this.read(key);
                        }
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }


    /*
     *
     */
    private void accept(SelectionKey key){
        try {
            //1.由于目前是server端，那么一定是server端启动，并且处于阻塞状态，所以获取阻塞状态的key，一定是ServerSocketChannel
            ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
            //2.通过调用accept方法，返回一个具体的客户端连接句柄
            SocketChannel sc = ssc.accept();
            //3.设置客户端通道为非阻塞
            sc.configureBlocking(false);
            //4.设置当前获取的客户端连接句柄为可读状态
            sc.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /*
     *
     */
    private void read(SelectionKey key){

    }
}
