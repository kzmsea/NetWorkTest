package org.example.chapter_two.proxy_server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author KongZimin
 * @date 2021/2/1 16:24
 */
public class server {
    public static void main(String[] args) {
        try {
            // 缓冲区
            Map<String, String> map = new HashMap<>();
            // 创建服务器套接字，将其绑定到端口并开始侦听
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true){
                System.out.println("Ready to serve...");
                Socket accept = serverSocket.accept();
                System.out.println("收到来自以下方面的连接：" + accept.getInetAddress());

                InputStream inputStream = accept.getInputStream();
                byte[] bytes = new byte[1024];
                inputStream.read(bytes);
                String message = new String(bytes);
                System.out.println(message);
                
                // 从给定的消息中提取文件名
                String filename = message.split("\\n")[1].substring(message.lastIndexOf("/") + 1, message.length());
                System.out.println(filename);
                boolean fileExist = false;

                Scanner scanner = new Scanner(new File(filename));
                String line = scanner.nextLine();
                fileExist = true;
                
                // ProxyServer查找缓存命中并生成响应消息
                OutputStream outputStream = accept.getOutputStream();
                outputStream.write("HTTP/1.0 200 OK\r\nContent-Type:text/html\r\n".getBytes());
                map.forEach((k, v) -> {
                    
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
