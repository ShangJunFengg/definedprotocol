package com.protocol;

import java.util.Arrays;

/**
 * 自定义协议
 */
public class MyProtocol {
    private MyProtocol(){}

    private int head_data = ConstantValue.HEAD_DATA;
    private int contentLength;//长度
    private byte[] conent;

    public MyProtocol(int contentLength, byte[] conent)
    {
            this.contentLength=contentLength;
            this.conent=conent;
    }

    public int getHead_data() {
        return head_data;
    }

    public void setHead_data(int head_data) {
        this.head_data = head_data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getConent() {
        return conent;
    }

    public void setConent(byte[] conent) {
        this.conent = conent;
    }

    @Override
    public String toString() {
        return "MyProtocol{" +
                "head_data=" + head_data +
                ", contentLength=" + contentLength +
                ", conent=" + Arrays.toString(conent) +
                '}';
    }
}

