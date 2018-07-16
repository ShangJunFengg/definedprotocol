package com.server;

import com.coder.MyProtocolDecoder;
import com.coder.MyProtocolEncoder;
import com.protocol.MyProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServerBootstrap {
    public static void main(String[] args) {
        int port=6666;//端口
        ServerBootstrap serverBootstrap = new ServerBootstrap();//服务端入口
        serverBootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup());//两个线程池
        serverBootstrap.channel(NioServerSocketChannel.class);//Channel的类型
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MyProtocolEncoder());//解码方式
                socketChannel.pipeline().addLast(new MyProtocolDecoder());//编码方式
                socketChannel.pipeline().addLast(new NettyServerHandler());
            }
        });
        serverBootstrap.bind(port);//开服务
    }
}


class NettyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol myProtocol) throws Exception {
        System.out.println("Server接受的客户端的信息 :" + myProtocol.toString());
        // 会写数据给客户端
        String str = "Hi I am Server ...";
        MyProtocol response = new MyProtocol(str.getBytes().length,str.getBytes());
        // 当服务端完成写操作后，关闭与客户端的连接
        ctx.writeAndFlush(response);
        // .addListener(ChannelFutureListener.CLOSE);
        // 当有写操作时，不需要手动释放msg的引用
        // 当只有读操作时，才需要手动释放msg的引用

    }


}