package org.example.chapter_two.web_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kzm on 2021/1/26 7:50
 */
public class Client {
    public static void main(String[] args) throws IOException {
        try (Socket client = new Socket("127.0.0.1", 9999)){
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            outputStream.write("GET /HelloWorlds.html HTTP/1.1".getBytes());
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            if (bytes.length > 0)
                System.out.println(new String(bytes));
        }
    }
}
