package com.wywhdgg.dzb.wywhdggnetty.aio;

/**
 * @author: dongzhb
 * @date: 2019/9/17
 * @Description:
 */
public class AIOTimeClient {
    static final int PORT = Integer.valueOf(System.getProperty("port", "8080"));

    public static void main(String[] args) {
        AsyncTimeClientHandler asyncTimerClientHandler = new AsyncTimeClientHandler("127.0.0.1", PORT);
        new Thread(asyncTimerClientHandler, "AIO asyncTimerClientHandler-001").start();
    }
}
