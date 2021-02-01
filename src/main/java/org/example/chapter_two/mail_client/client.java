package org.example.chapter_two.mail_client;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author KongZimin
 * @date 2021/2/1 15:02
 */
public class client {
    public static void main(String[] args) {
        String msg = "\r\n I love computer networks!";
        String endmsg = "\r\n.\r\n";
        // 选择一个邮件服务器（例如Google邮件服务器）并将其命名为mailserver
        String mailserver = "mail.163.com";
        // 创建名为clientSocket的套接字，并与mailserver建立TCP连接
        Socket client = new Socket();
        byte[] bytes = new byte[1024];
        try {
            client.connect(new InetSocketAddress(mailserver, 25));
            InputStream inputStream = client.getInputStream();
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(bytes));
        if (!new String(bytes).startsWith("220"))
            System.out.println("未从服务器收到220回复。");
        
        // 发送HELO命令并打印服务器响应。
        String heloCommand = "HELO Alice\r\n";
            
    }
}
