package com.pool.thread.model;

/**
 * @Author ZhiTong Tan
 **/
public enum RequestType {
  INFO(0),
  LIST(1),
  MOD_THREAD_POOL_NAME(3),
  MOD_THREAD_POOL_CORE_SIZE(3),
  MOD_THREAD_POOL_MAX_SIZE(4),
  MOD_THREAD_POOL_REJECT_HANDLER(5);

  private int type;

  RequestType(int type) {
    this.type = type;
  }
}
