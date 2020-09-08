package com.pool.thread.netty;

import com.pool.thread.HookEmbedded;
import com.pool.thread.common.ResultHandler;
import com.pool.thread.model.HttpJsonRequest;
import com.pool.thread.model.HttpJsonResponse;
import com.pool.thread.model.RequestType;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.ReqHookThreadPool;
import vo.ThreadPoolDynamicData;

/**
 * @Author ZhiTong Tan
 **/
public class RequestHandler extends
    SimpleChannelInboundHandler<HttpJsonRequest> {

  private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

  private HookNettyServer hookNettyServer;

  private HookEmbedded hookEmbedded;

  public RequestHandler(HookNettyServer hookNettyServer, HookEmbedded hookEmbedded) {
    this.hookNettyServer = hookNettyServer;
    this.hookEmbedded = hookEmbedded;
  }


  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
      HttpJsonRequest msg) throws Exception {
    FullHttpRequest request = msg.getRequest();
    RequestType requestType = msg.getRequestType();
    ChannelFuture channelFuture;
    ReqHookThreadPool req;
    try {
      switch (requestType) {
        case INFO:
          req = (ReqHookThreadPool) msg.getBody();
          if (Objects.isNull(req.getThreadPoolId())) {
            ResultHandler.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
          }
          channelFuture = ctx
              .writeAndFlush(new HttpJsonResponse(null,
                  new ThreadPoolDynamicData(hookEmbedded.get(req.getThreadPoolId()))));
          if (!HttpUtil.isKeepAlive(request)) {
            channelFuture.addListener(
                future -> ctx.close());
          }
          break;
        case LIST:
          req = (ReqHookThreadPool) msg.getBody();
          channelFuture = ctx
              .writeAndFlush(new HttpJsonResponse(null,
                  hookEmbedded.pageList(req.getBegin(), req.getSize()).stream()
                      .map(ThreadPoolDynamicData::new).collect(Collectors.toList())));
          if (!HttpUtil.isKeepAlive(request)) {
            channelFuture.addListener(
                future -> ctx.close());
          }
          break;
        case MOD_THREAD_POOL_NAME:
          req = (ReqHookThreadPool) msg.getBody();
          if (StringUtils.isEmpty(req.getThreadPoolName()) || Objects
              .isNull(req.getThreadPoolId())) {
            ResultHandler.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
          }
          hookEmbedded.setThreadPoolName(req.getThreadPoolId(), req.getThreadPoolName());
          channelFuture = ctx
              .writeAndFlush(new HttpJsonResponse(null, "succcess"));
          if (!HttpUtil.isKeepAlive(request)) {
            channelFuture.addListener(
                future -> ctx.close());
          }

      }
    } catch (Throwable th) {
      logger.error("hook request handler error");
      ResultHandler.sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, th);
    }
  }
}
