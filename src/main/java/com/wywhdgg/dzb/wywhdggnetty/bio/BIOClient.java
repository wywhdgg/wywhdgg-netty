package com.wywhdgg.dzb.wywhdggnetty.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class BIOClient {
    private static final Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8080);
        System.out.println("client status:" + socket.isClosed());
        OutputStream out = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        String msg = scanner.nextLine() + "\n";
        out.write(msg.getBytes(charset));
        scanner.close();
        out.write("bye\n".getBytes());
        InputStream inputStream = socket.getInputStream();
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(data)) > 0) {
            System.out.println(new String(data));
        }
        socket.close();
    }
}
