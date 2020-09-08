package com.pool.thread.model;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @Author ZhiTong Tan
 **/
public class HttpJsonResponse {

  private FullHttpResponse httpResponse;
  private Object result;

  public HttpJsonResponse(FullHttpResponse httpResponse, Object result) {
    this.httpResponse = httpResponse;
    this.result = result;
  }

  public HttpJsonResponse() {

  }

  public FullHttpResponse getHttpResponse() {
    return httpResponse;
  }

  public void setHttpResponse(FullHttpResponse httpResponse) {
    this.httpResponse = httpResponse;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }
}
