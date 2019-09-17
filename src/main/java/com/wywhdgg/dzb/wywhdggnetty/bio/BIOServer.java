package com.wywhdgg.dzb.wywhdggnetty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class BIOServer {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("bio server start.......");
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();//阻塞
            System.out.println("receive new connect:" + socket.toString());
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream in = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        String msg;
                        while ((msg = reader.readLine()) != null) {
                            if (msg.length() == 0 || "bye".equals(msg)) {
                                break;
                            }
                            System.out.println("receive client msg:" + msg);
                        }
                        System.out.println("msg:" + msg);
                        OutputStream out = socket.getOutputStream();
                        //字节转换处理
                        out.write("hello word!".getBytes());
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (socket != null) {
                                socket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
