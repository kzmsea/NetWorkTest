package org.example.chapter_two.proxy_server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author KongZimin
 * @date 2021/2/1 16:24
 */
public class server {
    public static void main(String[] args) throws IOException {

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
                message = message.split("\\n")[0];
                String filename = message.substring(message.indexOf("/") + 1, message.indexOf(" HTTP"));
                System.out.println(filename);
                boolean fileExist = false;

                try {
                    Scanner scanner = new Scanner(new File("E:\\file\\" + filename));
                    fileExist = true;

                    // ProxyServer查找缓存命中并生成响应消息
                    OutputStream outputStream = accept.getOutputStream();
                    outputStream.write("HTTP/1.0 200 OK\r\nContent-Type:text/html\r\n".getBytes());
                    while (scanner.hasNextLine())
                        outputStream.write(scanner.nextLine().getBytes());

                    System.out.println("从缓存中读取");
                } catch (IOException e) {
                    if (fileExist == false){
                        // 在代理服务器上创建一个套接字
                        Socket c = new Socket();
                        filename = filename.replace("www.","");
                        try {
                            // 连接端口为80的套接字
                            c.connect(new InetSocketAddress(filename, 80));

                            // 在此套接字上创建一个临时文件，并向端口80询问客户端请求的文件
                            InputStream inputStream1 = c.getInputStream();
                            byte[] bytes1 = new byte[1024*1024];
                            // 将响应读入缓冲区
                            inputStream1.read(bytes1);
                            System.out.println(new String(bytes1));
                            // 在缓存中为请求的文件创建一个新文件
                            File.createTempFile("", filename, new File("E:\\file"));
                            // 还将缓冲区中的响应发送到客户端套接字以及缓存中的相应文件
                            Scanner scanner = new Scanner(new File("E:\\file\\" + filename));
                            while (scanner.hasNextLine())
                                accept.getOutputStream().write(scanner.nextLine().getBytes());
                        } catch (Exception ex){
                            System.out.println("非法请求");
                        } finally {
                            c.close();
                        }
                    } else {
                        // 找不到文件的HTTP响应消息
                        
                    }
                } finally {
                    // 关闭客户端和服务器套接字
                    accept.close();
                }
            }

    }
}
