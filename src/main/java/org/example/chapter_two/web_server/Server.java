package org.example.chapter_two.web_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kzm on 2021/1/19 20:20
 */
public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9999)){
            while (true){
                Socket incoming = server.accept();
                TcpThread tcpThread = new TcpThread();
                tcpThread.setIncoming(incoming);
                new Thread(tcpThread).start();
            }
        }
    }


}
class TcpThread implements Runnable {
    private Socket incoming;

    public void setIncoming(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = incoming.getInputStream();
            OutputStream outputStream = incoming.getOutputStream();
            byte[] bytes = new byte[1024];
            try {
                inputStream.read(bytes);
                String request = new String(bytes);
                System.out.println(request);
                String fileName = request.substring(5, request.indexOf(" HTTP"));
                String fileValue = new String(Files.readAllBytes(Paths.get("src/main/java/org/kzm/second/webServer/" + fileName)));
                String response = "HTTP/1.1 200 OK\r\nConnection-Length:" + fileValue.getBytes().length + "Content-Type: text/plain\r\n\r\n" + fileValue;
                outputStream.write(response.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                String response = "HTTP/1.1 404 NOT FOUND\r\nContent-Type: text/plain\r\n\r\n";
                outputStream.write(response.getBytes());
                outputStream.flush();
            }finally {
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
