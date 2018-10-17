package com.io.demo.socketDemo.basicSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    final static int PORT = 9264;

    public static void main(String[] args){
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server Start ...");

            //进行阻塞
            Socket socket = server.accept();
            //创建一个线程执行客户端的任务
            new Thread(new ServerHandler(socket)).start();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(server != null){
                try {
                    server.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            server = null;
        }
    }
}
