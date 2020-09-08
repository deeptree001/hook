package com.pool.thread.container;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @Author ZhiTong Tan
 **/
public class HookScheduleThreadPool extends ScheduledThreadPoolExecutor {

  protected long threadPoolId;
  protected String threadName;
  protected static final String THREAD_POOL_NAME = "hook-thread-pool-";

  public HookScheduleThreadPool(int corePoolSize) {
    super(corePoolSize);

  }

  public HookScheduleThreadPool(int corePoolSize,
      ThreadFactory threadFactory) {
    super(corePoolSize, threadFactory);
  }

  public HookScheduleThreadPool(int corePoolSize,
      RejectedExecutionHandler handler) {
    super(corePoolSize, handler);
  }

  public HookScheduleThreadPool(int corePoolSize,
      ThreadFactory threadFactory, RejectedExecutionHandler handler) {
    super(corePoolSize, threadFactory, handler);
  }
}
