package com.wywhdgg.dzb.wywhdggnetty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author: dongzhb
 * @date: 2019/9/17
 * @Description:
 */
public class AsyncTimerServerHandler implements Runnable {
    private int port;
    CountDownLatch countDownLatch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimerServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("--------asynchronous server is start ,port:" + port + " ----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccepct();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccepct() {
        System.out.println("do accepct.......");
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
