package com.pool.thread;

import com.pool.thread.exception.HookException;

/**
 * @Author ZhiTong Tan
 **/
public abstract class AbstractHookServer implements HookServer {

  protected volatile boolean running = false;

  @Override
  public boolean isRunning() {
    return running;
  }

  @Override
  public void start() {
    if (isRunning()) {
      throw new HookException("hook netty server has startup");
    }
    running = true;
  }

  @Override
  public void stop() {
    if (!isRunning()) {
      throw new HookException("hook netty server has stop");
    }
    running = false;
  }

  protected abstract void addShutdownHook();

}
