package com.pool.thread.exception;

/**
 * @Author ZhiTong Tan
 **/
public class HookException extends RuntimeException {

  private static final long serialVersionUID = -654893533794556357L;

  public HookException(String message) {
    super(message);
  }

  public HookException(String message, Throwable ex) {
    super(message, ex);
  }

  public HookException(Throwable ex) {
    super(ex);
  }

  public Throwable callStackTrace() {
    return this;
  }
}
