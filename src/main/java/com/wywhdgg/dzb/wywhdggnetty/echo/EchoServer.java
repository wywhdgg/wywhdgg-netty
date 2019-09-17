package com.wywhdgg.dzb.wywhdggnetty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class EchoServer {
//    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) {
        EventLoopGroup bossGrop = new NioEventLoopGroup(1);
        EventLoopGroup workGrop=new NioEventLoopGroup();
        final  EchoServerHandler serverHandler = new EchoServerHandler();
        try {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGrop,workGrop)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG,100)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline c=ch.pipeline();
                    c.addLast(serverHandler);
                }
            });
            ChannelFuture c = b.bind(PORT).sync();
            c.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGrop.shutdownGracefully();
            workGrop.shutdownGracefully();
        }
        System.out.println("服务启动.........");
    }
}
