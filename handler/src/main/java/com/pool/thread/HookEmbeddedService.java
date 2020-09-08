package com.pool.thread;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZhiTong Tan
 **/
public interface HookEmbeddedService {

  ExecutorService generateFixedThreadPool(int nThreads);

  ExecutorService singleThreadPool();

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue, String threadPoolName);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory, String threadPoolName);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      RejectedExecutionHandler handler);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      RejectedExecutionHandler handler, String threadPoolName);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory,
      RejectedExecutionHandler handler);

  ExecutorService generateThreadPool(int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory,
      RejectedExecutionHandler handler, String threadPoolName);

  List<ThreadPoolExecutor> pageList(int begin, int size);

  List<ThreadPoolExecutor> all();

  void setThreadPoolName(int threadPoolId, String threadPoolName);

  void setThreadCorePoolSize(int threadPoolId, int threadCorePoolSize);

  void setMaximumPoolSize(int threadPoolId, int maximumPoolSize);

  void setRejectHandler(int threadPoolId, int rejectType);


}
