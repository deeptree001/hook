package com.pool.thread;

import java.util.concurrent.ExecutorService;

/**
 * @Author ZhiTong Tan
 **/
public interface HookService {

  ExecutorService get(int threadPoolId);

  int getCurrentWorkerCount();

  int getCurrentThreadPoolCount();

}
