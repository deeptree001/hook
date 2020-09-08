package com.pool.thread.model;

/**
 * @Author ZhiTong Tan
 **/

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014年3月1日
 */
public class HttpJsonRequest {

  private FullHttpRequest request;
  private Object body;
  private RequestType requestType;

  public HttpJsonRequest(FullHttpRequest request, Object body, RequestType requestType) {
    this.request = request;
    this.body = body;
    this.requestType = requestType;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestType requestType) {
    this.requestType = requestType;
  }

  public HttpJsonRequest() {
  }

  public FullHttpRequest getRequest() {
    return request;
  }

  public void setRequest(FullHttpRequest request) {
    this.request = request;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }
}