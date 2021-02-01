package org.example.chapter_two.ping;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author KongZimin
 * @date 2021/2/1 9:42
 */
public class PingClient {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            // 设置超时时长为1秒
            socket.setSoTimeout(1000);
            // 配置数据报包
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024, InetAddress.getByName("127.0.0.1"), 7777);
            // start,end用于计算连接时常
            long start, end;
            List<Long> list = new ArrayList<>();
            for (int i = 1; i < 11; i++) {
                try{
                    packet.setData("ping".getBytes());
                    start = System.currentTimeMillis();
                    socket.send(packet);
                    socket.receive(packet);
                    end = System.currentTimeMillis();
                    long time = end - start;
                    System.out.println("Ping " + i + " " + (time) + "ms");
                    list.add(time);
                } catch (SocketTimeoutException e) {
                    System.out.println("请求超时");
                }
            }
            System.out.println("ping统计信息：\n\t数据包：" + (100 - list.size() * 10) + "%丢失");
            System.out.println("往返行程的估计时间(以毫秒为单位)：\n\t最短 = " + Collections.min(list) + "ms, 最长 = " + Collections.max(list) + "ms, " +
                    "平均" + list.stream().mapToDouble(Number::doubleValue).average().getAsDouble() + "ms");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
