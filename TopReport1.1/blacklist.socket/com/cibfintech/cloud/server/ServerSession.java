package com.cibfintech.cloud.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 自定义服务端读写流
 * @author administrator
 *
 */
public class ServerSession {
	public void startServer() {
        ServerSocket serverSocket;
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;
        try {
            // new一个socket对象并且设置端口号
            serverSocket = new ServerSocket(8888);
            System.out.println("服务器打开了");
            socket = serverSocket.accept();
            System.out.println("client" + socket.hashCode() + "connect");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String recevierMsg;
            while ((recevierMsg = reader.readLine()) != null) {
                System.out.println(recevierMsg + "11");
                writer.write(recevierMsg+"response");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
