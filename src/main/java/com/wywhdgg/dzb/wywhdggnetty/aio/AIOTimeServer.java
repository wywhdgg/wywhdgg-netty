package com.wywhdgg.dzb.wywhdggnetty.aio;

/**
 * @author: dongzhb
 * @date: 2019/9/17
 * @Description:
 */
public class AIOTimeServer {
    static final int PORT = Integer.valueOf(System.getProperty("port", "8080"));

    public static void main(String[] args) {
        AsyncTimerServerHandler handler = new AsyncTimerServerHandler(PORT);
        new Thread(handler, "AIO-AsyncTimerServerHandler-001").start();
    }
}
