package com.pool.thread.netty;

import com.pool.thread.AbstractHookServer;
import com.pool.thread.HookEmbedded;
import com.pool.thread.HookService;
import com.pool.thread.codec.HttpRequestJsonDecoder;
import com.pool.thread.codec.HttpResponseJsonEncoder;
import com.pool.thread.exception.HookException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ZhiTong Tan
 **/
public class HookNettyServer extends AbstractHookServer implements HookService {

  private static final Logger logger = LoggerFactory.getLogger(HookNettyServer.class);
  private HookEmbedded hookEmbedded = new HookEmbedded();
  private String ip;
  private int port;
  private ServerBootstrap bootstrap = null;
  private EventLoopGroup bossGroup = new NioEventLoopGroup();
  private EventLoopGroup workerGroup = new NioEventLoopGroup();
  private HookNettyServer hookNettyServer;


  public HookNettyServer(String ip, int port) {
    if (Objects.nonNull(hookNettyServer)) {
      throw new HookException("hook netty server has started");
    }
    this.ip = ip;
    this.port = port;
    start();
    hookNettyServer = this;
  }

  public HookNettyServer(int port) {
    this(null, port);
  }

  @Override
  public void start() {
    super.start();
    startServer();
    addShutdownHook();
  }

  @Override
  public void stop() {
    super.stop();
    stopServer();
    hookEmbedded.stop();
  }

  private void stopServer() {
    logger.info("hook netty server is stop");
    hookEmbedded.stop();
    if (!bossGroup.isShutdown()) {
      bossGroup.shutdownGracefully();
    }
    if (!workerGroup.isShutdown()) {
      workerGroup.shutdownGracefully();
    }
  }

  @Override
  protected void addShutdownHook() {
    Thread shutdownThread = new Thread(this::stop);
    shutdownThread.setName("shutdown@thread");
    Runtime.getRuntime().addShutdownHook(shutdownThread);
  }

  protected void startServer() {
    logger.info("hook netty server is starting");
    try {
      this.bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
              socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
              socketChannel.pipeline().addLast("json-decoder", new HttpRequestJsonDecoder());
              socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
              socketChannel.pipeline().addLast("json-encoder", new HttpResponseJsonEncoder());
              socketChannel.pipeline().addLast("requestHandler",
                  new RequestHandler(hookNettyServer, hookEmbedded));
            }
          });
      ChannelFuture future;
      if (StringUtils.isNotBlank(this.ip)) {
        future = bootstrap.bind(new InetSocketAddress(ip, port)).sync();
      } else {
        future = bootstrap.bind(new InetSocketAddress(port)).sync();
      }
      future.channel().closeFuture().sync();
      logger.info("hook netty server start success");
    } catch (Throwable e) {
      throw new HookException("hook netty server start fail", e);
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }


  @Override
  public ExecutorService get(int threadPoolId) {
    return hookEmbedded.get(threadPoolId);
  }

  @Override
  public int getCurrentWorkerCount() {
    return hookEmbedded.getCurrentWorkerCount();
  }

  @Override
  public int getCurrentThreadPoolCount() {
    return hookEmbedded.getCurrentThreadPoolCount();
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
