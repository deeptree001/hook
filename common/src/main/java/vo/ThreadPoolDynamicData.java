package vo;

import com.pool.thread.HookThreadPool;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author ZhiTong Tan
 **/
public class ThreadPoolDynamicData implements Serializable {

  private static final long serialVersionUID = 2332798099928474975L;

  private boolean isRunning;
  private int poolSize;
  private int runningCount;
  private int maximumPoolSize;
  private String queue;
  private long waitTaskCount;
  private String threadPoolType;
  private int corePooSize;
  private long completeTaskCount;
  private int queueCapacity;
  private int rejectTaskCount;
  private String rejectHandler;
  private long threadPoolId;
  private String threadPoolName;
  private int loadFactor;
  private int queueRemainingCapacity;
  private String host;

  public ThreadPoolDynamicData(ThreadPoolExecutor poolExecutor) {
    this.isRunning = !poolExecutor.isShutdown();
    this.poolSize = poolExecutor.getPoolSize();
    this.runningCount = poolExecutor.getActiveCount();
    this.maximumPoolSize = poolExecutor.getMaximumPoolSize();
    this.queue = poolExecutor.getQueue().getClass().getSimpleName();
    this.waitTaskCount = poolExecutor.getTaskCount();
    this.threadPoolType = poolExecutor.getClass().getSimpleName();
    this.corePooSize = poolExecutor.getCorePoolSize();
    this.completeTaskCount = poolExecutor.getCompletedTaskCount();
    this.queueCapacity = poolExecutor.getQueue().size();
    this.rejectHandler = poolExecutor.getRejectedExecutionHandler().getClass().getSimpleName();
    if (poolExecutor instanceof HookThreadPool) {
      this.threadPoolId = ((HookThreadPool) poolExecutor).getThreadPoolId();
      this.threadPoolName = ((HookThreadPool) poolExecutor).getThreadPoolName();
    }
    this.loadFactor = runningCount / maximumPoolSize;
    this.queueRemainingCapacity = poolExecutor.getQueue().remainingCapacity();
    try {
      this.host = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
    }
  }

  public ThreadPoolDynamicData() {
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public void setRunning(boolean running) {
    isRunning = running;
  }

  public int getRunningCount() {
    return runningCount;
  }

  public void setRunningCount(int runningCount) {
    this.runningCount = runningCount;
  }

  public int getMaximumPoolSize() {
    return maximumPoolSize;
  }

  public void setMaximumPoolSize(int maximumPoolSize) {
    this.maximumPoolSize = maximumPoolSize;
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public long getWaitTaskCount() {
    return waitTaskCount;
  }

  public void setWaitTaskCount(long waitTaskCount) {
    this.waitTaskCount = waitTaskCount;
  }

  public String getThreadPoolType() {
    return threadPoolType;
  }

  public void setThreadPoolType(String threadPoolType) {
    this.threadPoolType = threadPoolType;
  }

  public int getCorePooSize() {
    return corePooSize;
  }

  public void setCorePooSize(int corePooSize) {
    this.corePooSize = corePooSize;
  }

  public long getCompleteTaskCount() {
    return completeTaskCount;
  }

  public void setCompleteTaskCount(long completeTaskCount) {
    this.completeTaskCount = completeTaskCount;
  }

  public int getQueueCapacity() {
    return queueCapacity;
  }

  public void setQueueCapacity(int queueCapacity) {
    this.queueCapacity = queueCapacity;
  }

  public int getRejectTaskCount() {
    return rejectTaskCount;
  }

  public void setRejectTaskCount(int rejectTaskCount) {
    this.rejectTaskCount = rejectTaskCount;
  }

  public String getRejectHandler() {
    return rejectHandler;
  }

  public void setRejectHandler(String rejectHandler) {
    this.rejectHandler = rejectHandler;
  }

  public long getThreadPoolId() {
    return threadPoolId;
  }

  public void setThreadPoolId(long threadPoolId) {
    this.threadPoolId = threadPoolId;
  }

  public String getThreadPoolName() {
    return threadPoolName;
  }

  public void setThreadPoolName(String threadPoolName) {
    this.threadPoolName = threadPoolName;
  }

  public int getLoadFactor() {
    return loadFactor;
  }

  public void setLoadFactor(int loadFactor) {
    this.loadFactor = loadFactor;
  }
}
