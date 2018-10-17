package com.io.demo.socketDemo.buffer;

import java.nio.IntBuffer;

public class TestBuffer {

    public static void main(String[] args){
        //1.基本操作
        /*
         *创建指定长度的缓冲区
         */
        IntBuffer buf = IntBuffer.allocate(10);
        buf.put(13);
        buf.put(21);
        buf.put(35);

        buf.flip();
        System.out.println("使用flip复位： " + buf);
        System.out.println("容量为： " + buf.capacity());
        System.out.println("限制为： " + buf.limit());

        System.out.println("获取下标为1的元素： " + buf.get(1));
        System.out.println("使用flip复位： " + buf);
        buf.put(1,4);

        for(int i = 0; i < buf.limit(); i++){
            System.out.println(buf.get());
        }
        System.out.println("buf对象遍历之后为： " + buf);

    }
}
