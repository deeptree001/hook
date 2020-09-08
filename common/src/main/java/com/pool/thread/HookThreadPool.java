package com.pool.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZhiTong Tan
 **/
public class HookThreadPool extends ThreadPoolExecutor {

  private int threadPoolId;
  private String threadPoolName;

  public HookThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
      TimeUnit unit, BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory, RejectedExecutionHandler handler, int threadPoolId,
      String threadPoolName) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    this.threadPoolName = threadPoolName;
    this.threadPoolId = threadPoolId;
  }

  public int getThreadPoolId() {
    return threadPoolId;
  }

  public String getThreadPoolName() {
    return threadPoolName;
  }

  public void setThreadPoolName(String threadPoolName) {
    this.threadPoolName = threadPoolName;
  }
}
