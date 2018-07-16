package com.coder;

import com.protocol.MyProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 */
public class MyProtocolEncoder extends MessageToByteEncoder<MyProtocol> {

    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getHead_data());//自定义消息开头
        out.writeInt(msg.getContentLength());//消息长度
        out.writeBytes(msg.getConent());//消息内容
    }
}
