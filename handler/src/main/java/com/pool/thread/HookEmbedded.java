package com.pool.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.pool.thread.exception.HookException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ZhiTong Tan
 **/
public class HookEmbedded extends AbstractHookServer implements HookService, HookEmbeddedService {

  private static final Logger logger = LoggerFactory.getLogger(HookEmbedded.class);

  private static Map<Integer, ThreadPoolExecutor> threadPoolStore = new HashMap<>();
  private AtomicInteger incrNumber = new AtomicInteger(0);
  private static long allMaximumPoolSize = 0L;
  private static long currentMaximumPoolSize = 0L;
  private static final String THREAD_POOL_NAME = "hook-thread-pool-";

  private static HookEmbedded INSTANCE = new HookEmbedded();

  public HookEmbedded() {
    allMaximumPoolSize = Long.MAX_VALUE;
  }

  public HookEmbedded(long allMaximumPoolSize) {
    if (allMaximumPoolSize >= HookEmbedded.allMaximumPoolSize) {
      HookEmbedded.allMaximumPoolSize = allMaximumPoolSize;
    } else {
      throw new HookException(
          "the value of allMaximumPoolSize can not less than current allMaximumPoolSize");
    }
  }

  public static HookEmbedded instance() {
    return INSTANCE;
  }

  @Override
  public ThreadPoolExecutor get(int threadPoolId) {
    return threadPoolStore.get(threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateFixedThreadPool(int nThreads) {
    int threadPoolId = incrNumber.incrementAndGet();
    String threadPoolName = THREAD_POOL_NAME + threadPoolId;
    return generateThreadPool(nThreads, nThreads, 0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(),
        buildFactory(threadPoolName, true), new AbortPolicy(), threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService singleThreadPool() {
    return null;
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
    int threadPoolId = incrNumber.incrementAndGet();
    String threadPoolName = THREAD_POOL_NAME + threadPoolId;
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        buildFactory(threadPoolName, true), new AbortPolicy(), threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String threadPoolName) {
    int threadPoolId = incrNumber.incrementAndGet();
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        buildFactory(threadPoolName, true), new AbortPolicy(), threadPoolName, threadPoolId);

  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory) {
    int threadPoolId = incrNumber.incrementAndGet();
    String threadPoolName = THREAD_POOL_NAME + threadPoolId;
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        threadFactory, new AbortPolicy(), threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory, String threadPoolName) {
    int threadPoolId = incrNumber.incrementAndGet();
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        threadFactory, new AbortPolicy(), threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      RejectedExecutionHandler handler) {
    int threadPoolId = incrNumber.incrementAndGet();
    String threadPoolName = THREAD_POOL_NAME + threadPoolId;
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        buildFactory(threadPoolName, true), handler, threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      RejectedExecutionHandler handler, String threadPoolName) {
    int threadPoolId = incrNumber.incrementAndGet();
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        buildFactory(threadPoolName, true), handler, threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory, RejectedExecutionHandler handler) {
    int threadPoolId = incrNumber.incrementAndGet();
    String threadPoolName = THREAD_POOL_NAME + threadPoolId;
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        threadFactory, handler, threadPoolName, threadPoolId);
  }

  @Override
  public synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadPoolName) {
    int threadPoolId = incrNumber.incrementAndGet();
    return generateThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
        threadFactory, handler, threadPoolName, threadPoolId);
  }

  @Override
  public List<ThreadPoolExecutor> pageList(int begin, int size) {
    return threadPoolStore.entrySet().stream()
        .skip(begin).limit(size).map(Entry::getValue).collect(Collectors.toList());
  }

  @Override
  public List<ThreadPoolExecutor> all() {
    return (List<ThreadPoolExecutor>) threadPoolStore.values();
  }

  @Override
  public void setThreadPoolName(int threadPoolId, String threadPoolName) {
    ThreadPoolExecutor threadPoolExecutor = threadPoolStore.get(threadPoolId);
    if (Objects.isNull(threadPoolExecutor)) {
      throw new HookException("hook thread pool id not exist,threadPoolId:" + threadPoolId);
    }
    if (threadPoolExecutor instanceof HookThreadPool) {
      ((HookThreadPool) threadPoolExecutor).setThreadPoolName(threadPoolName);
    }
  }

  @Override
  public void setThreadCorePoolSize(int threadPoolId, int threadCorePoolSize) {
    ThreadPoolExecutor threadPoolExecutor = threadPoolStore.get(threadPoolId);
    if (Objects.isNull(threadPoolExecutor)) {
      throw new HookException("hook thread pool id not exist,threadPoolId:" + threadPoolId);
    }
    threadPoolExecutor.setCorePoolSize(threadCorePoolSize);
  }

  @Override
  public void setMaximumPoolSize(int threadPoolId, int maximumPoolSize) {
    ThreadPoolExecutor threadPoolExecutor = threadPoolStore.get(threadPoolId);
    if (Objects.isNull(threadPoolExecutor)) {
      throw new HookException("hook thread pool id not exist,threadPoolId:" + threadPoolId);
    }
    threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
  }

  @Override
  public void setRejectHandler(int threadPoolId, int rejectType) {
    ThreadPoolExecutor threadPoolExecutor = threadPoolStore.get(threadPoolId);
    if (Objects.isNull(threadPoolExecutor)) {
      throw new HookException("hook thread pool id not exist,threadPoolId:" + threadPoolId);
    }
    threadPoolExecutor.setRejectedExecutionHandler(RejectHandlerType.valueOf(rejectType));
  }

  private synchronized ExecutorService generateThreadPool(int corePoolSize, int maximumPoolSize,
      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadPoolName,
      int threadPoolId) {
    if (!validateMaximumPoolSize(maximumPoolSize)) {
      logger.error(threadPoolName + " hook thread pool creat fail");
      throw new HookException(
          "hook thread pool create fail,current all max thread size already more than allMaximumPoolSize");
    }
    HookThreadPool hookThreadPool = new HookThreadPool(corePoolSize, maximumPoolSize, keepAliveTime,
        unit, workQueue,
        threadFactory, handler, threadPoolId, threadPoolName);
    poolStore(hookThreadPool.getThreadPoolId(), hookThreadPool);
    return hookThreadPool;
  }

  @Override
  public int getCurrentWorkerCount() {
    int count = 0;
    for (Entry<Integer, ThreadPoolExecutor> entry : threadPoolStore.entrySet()) {
      if (entry.getValue() instanceof ThreadPoolExecutor) {
        count = +entry.getValue().getActiveCount();
      }
    }
    return count;
  }

  @Override
  public int getCurrentThreadPoolCount() {
    return threadPoolStore.size();
  }


  private boolean validateMaximumPoolSize(long maximumPoolSize) {
    if (currentMaximumPoolSize + maximumPoolSize > allMaximumPoolSize) {
      return false;
    }
    return true;
  }

  private ThreadFactory buildFactory(String threadPoolName, boolean daemon) {
    return new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-").setDaemon(daemon).build();
  }

  private void poolStore(int threadPoolId, ThreadPoolExecutor threadPool) {
    threadPoolStore.put(threadPoolId, threadPool);
  }

  @Override
  public void stop() {
    //线程池中任务执行完成再进行关闭
  }

  @Override
  protected void addShutdownHook() {

  }

  @Override
  public void start() {

  }

  @Override
  public boolean isRunning() {
    return super.isRunning();
  }

  private enum RejectHandlerType {
    ABORTPOLICY(1),
    CALLERRUNSPOLICY(2),
    DISCARDOLDESTPOLICY(3),
    DISCARDPOLICY(4),
    ;
    private int type;
    private Class clazz;

    RejectHandlerType(int type) {
      this.type = type;
    }

    public int getType() {
      return type;
    }

    public static RejectedExecutionHandler valueOf(int type) {
      switch (type) {
        case 1:
          return new AbortPolicy();
        case 2:
          return new CallerRunsPolicy();
        case 3:
          return new DiscardOldestPolicy();
        case 4:
          return new DiscardPolicy();
        default:
          throw new HookException("reject type parse error");
      }
    }
  }

}
