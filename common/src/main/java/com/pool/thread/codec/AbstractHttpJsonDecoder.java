package com.pool.thread.codec;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.Charset;

/**
 * @Author ZhiTong Tan
 **/
public abstract class AbstractHttpJsonDecoder<T> extends MessageToMessageDecoder<T> {

  private final static Charset UTF_8 = Charset.forName("UTF-8");

  public AbstractHttpJsonDecoder() {
  }

  protected Object decode0(ChannelHandlerContext ctx, ByteBuf body, Class clazz) {
    String content = body.toString(UTF_8);
    Object result = new Gson().fromJson(content, clazz);
    return result;
  }
}
