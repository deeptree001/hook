package com.pool.thread.codec;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

import com.pool.thread.model.HttpJsonResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import java.util.List;
import java.util.Objects;

/**
 * @Author ZhiTong Tan
 **/
public class HttpResponseJsonEncoder extends AbstractHttpJsonEncoder<HttpJsonResponse> {

  public HttpResponseJsonEncoder() {
  }

  @Override
  protected void encode(ChannelHandlerContext ctx,
      HttpJsonResponse result, List<Object> list) throws Exception {
    ByteBuf body = encode0(ctx, result.getResult());
    FullHttpResponse response = result.getHttpResponse();
    if (Objects.isNull(response)) {
      response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
    } else {
      response = new DefaultFullHttpResponse(result.getHttpResponse().protocolVersion(),
          result.getHttpResponse().status(), body);
    }
    response.headers().set(CONTENT_TYPE, "text/json");
    HttpUtil.setContentLength(response, body.readableBytes());
    list.add(response);
  }
}
