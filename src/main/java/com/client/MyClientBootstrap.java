package com.client;

import com.coder.MyProtocolDecoder;
import com.coder.MyProtocolEncoder;
import com.protocol.MyProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

public class MyClientBootstrap {


    public static void main(String[] args) {
        int port=6666;//端口
        Bootstrap bootstrap = new Bootstrap();//客户端入口
        bootstrap.group(new NioEventLoopGroup());//开个线程
        bootstrap.channel(NioSocketChannel.class);//Channel的类型
        bootstrap.remoteAddress("127.0.0.1",port);//往哪连
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MyProtocolDecoder());//解码方式
                socketChannel.pipeline().addLast(new MyProtocolEncoder());//编码方式
                socketChannel.pipeline().addLast(new MyClientHandler());
            }
        });

        ChannelFuture future = bootstrap.connect();//开始连接
        SocketChannel channel = (SocketChannel)future.channel();//拿到channel

        //疯狂发消息
        while (true)
        {
            try {
                TimeUnit.SECONDS.sleep(3);
                String data = "I am client ...";
                // 获得要发送信息的字节数组
                byte[] content = data.getBytes();
                // 要发送信息的长度
                int contentLength = content.length;
                MyProtocol protocol = new MyProtocol(contentLength, content);
                channel.writeAndFlush(protocol);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    // 客户端与服务端，连接成功的售后
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送SmartCar协议的消息
        // 要发送的信息
        String data = "I am client ...";
        // 获得要发送信息的字节数组
        byte[] content = data.getBytes();
        // 要发送信息的长度
        int contentLength = content.length;
        MyProtocol protocol = new MyProtocol(contentLength, content);
        ctx.writeAndFlush(protocol);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
            try {
                System.out.println("Client接受的客户端的信息 :" + msg.toString());
            } finally {
                //只有读操作需要手动释放
                ReferenceCountUtil.release(msg);
            }

        }

}