package com.wywhdgg.dzb.wywhdggnetty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: dongzhb
 * @date: 2019/9/16
 * @Description:
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //创建服务端对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//非阻塞

        //selector
        Selector selector = Selector.open();

        //服务端channel注册到selector,注册accept事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0);//0 代表无事件
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);//服务端只有accept reader
        //绑定端口，启动服务

        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        System.out.println("nio server start ......");
        while (true) {
            //启动selector管家
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    //打开通过，注册selector选择事件
                    SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                // 客户端连接有数据可以读时触发
                else if (key.isReadable()) {
                    try {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
                            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
                            if (requestBuffer.position() > 0) {
                                break;
                            }
                        }
                        if (requestBuffer.position() == 0) {
                            continue; // 如果没数据了, 则不继续后面的处理
                        }
                        requestBuffer.flip();
                        byte[] content = new byte[requestBuffer.remaining()];
                        requestBuffer.get(content);
                        System.out.println(new String(content));
                        System.out.println("收到数据,来自：" + socketChannel.getRemoteAddress());
                        // TODO 业务操作 数据库 接口调用等等

                        // 响应结果 200
                        String response = "HTTP/1.1 200 OK\r\n" + "Content-Length: 11\r\n\r\n" + "Hello World";
                        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                        while (buffer.hasRemaining()) {
                            socketChannel.write(buffer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //有异常要取消通道连接，否则服务端一直会报错
                        key.cancel();
                    }
                }
            }
        }
    }
}
