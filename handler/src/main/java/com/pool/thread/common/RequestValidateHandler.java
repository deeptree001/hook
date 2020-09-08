package com.pool.thread.common;

import com.pool.thread.HookConstants;
import com.pool.thread.model.RequestType;
import org.apache.commons.lang3.tuple.Pair;
import vo.ReqHookThreadPool;

/**
 * @Author ZhiTong Tan
 **/
public class RequestValidateHandler {

  public static Pair<RequestType, Class> parseUri(String uri) {
    if (uri.contains(HookConstants.HOOK_THREAD_POOL_INFO)) {
      return Pair.of(RequestType.INFO, ReqHookThreadPool.class);
    }
    if (uri.contains(HookConstants.HOOK_THREAD_POOL_LIST)) {
      return Pair.of(RequestType.LIST, ReqHookThreadPool.class);
    }
    return null;
  }
}
