package org.example.chapter_two.ping;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

/**
 * @author KongZimin
 * @date 2021/2/1 9:42
 */
public class PingServer {
    public static void main(String[] args) {
        try {
            // 创建一个UDP套接字
            DatagramSocket serverSocket = new DatagramSocket(7777);
            while (true){
                // 产生0到10范围内的随机数
                int rand = new Random().nextInt(11);
                // 创建数据包
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                // 读取客户端数据到数据包
                serverSocket.receive(packet);
                String data = new String(packet.getData());
                data = data.toUpperCase();
                // 如果rand小于4，则我们认为数据包丢失并且不响应
                if (rand < 4)
                    continue;
                packet.setData(data.getBytes());
                // 否则，服务器响应
                serverSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
