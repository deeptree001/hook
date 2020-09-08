package vo;

import java.io.Serializable;

/**
 * @Author ZhiTong Tan
 **/
public class ReqHookThreadPool implements Serializable {

  private Integer threadPoolId;
  private String threadPoolName;
  private int begin;
  private int size;

  public ReqHookThreadPool() {
    this.begin = 1;
    this.size = 10;
  }

  public Integer getThreadPoolId() {
    return threadPoolId;
  }

  public void setThreadPoolId(Integer threadPoolId) {
    this.threadPoolId = threadPoolId;
  }

  public String getThreadPoolName() {
    return threadPoolName;
  }

  public void setThreadPoolName(String threadPoolName) {
    this.threadPoolName = threadPoolName;
  }

  public int getBegin() {
    return begin;
  }

  public void setBegin(int begin) {
    this.begin = begin;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
