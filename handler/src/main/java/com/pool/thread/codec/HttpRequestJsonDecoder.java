package com.pool.thread.codec;

import com.pool.thread.common.RequestValidateHandler;
import com.pool.thread.common.ResultHandler;
import com.pool.thread.model.HttpJsonRequest;
import com.pool.thread.model.RequestType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ZhiTong Tan
 **/
public class HttpRequestJsonDecoder extends AbstractHttpJsonDecoder<FullHttpRequest> {

  private static final Logger logger = LoggerFactory.getLogger(HttpRequestJsonDecoder.class);

  public HttpRequestJsonDecoder() {
    super();
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, FullHttpRequest request,
      List<Object> list) throws Exception {
    if (!request.decoderResult().isSuccess()) {
      logger.error("hook netty decode error,uri:" + request.uri() + ",body:" + request.content());
      ResultHandler.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
      return;
    }
    Pair<RequestType, Class> pair = RequestValidateHandler.parseUri(request.uri());
    if (Objects.isNull(pair)) {
      logger.error("hook netty decode error,uri:" + request.uri() + ",body:" + request.content());
      ResultHandler.sendError(ctx, HttpResponseStatus.NOT_FOUND);
      return;
    }
    HttpJsonRequest jsonRequest = new HttpJsonRequest(request,
        decode0(ctx, request.content(), pair.getRight()), pair.getLeft());
    list.add(jsonRequest);
  }
}
