package org.toobsframework.pres.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.task.TaskExecutor;
import org.toobsframework.pres.component.ParallelComponent;
import org.toobsframework.pres.layout.ComponentRef;
import org.toobsframework.pres.url.UrlDispatchInfo;
import org.toobsframework.transformpipeline.domain.IXMLTransformerHelper;
import org.toobsframework.util.BaseRequestManager;

public class ComponentRequestManager extends BaseRequestManager {

  private TaskExecutor parallelTaskExecutor;

  public IComponentRequest set(UrlDispatchInfo dispatchInfo, HttpServletRequest httpRequest, HttpServletResponse httpResponse, Map<String,Object> params, boolean expectResponse) {
    IComponentRequest request = new ComponentRequest(dispatchInfo, httpRequest, httpResponse, params, expectResponse);
    if (get() != null) {
      log.warn("REQUEST ALREADY SET");
    }
    requestHolder.set(request);
    return request;
  }

  public void execParallelComponent(IComponentRequest componentRequest, ComponentRef componentRef, IXMLTransformerHelper transformerHelper) {
    ParallelComponent parallelComponent = new ParallelComponent(componentRequest, componentRef, transformerHelper);
    parallelTaskExecutor.execute(parallelComponent);
    componentRequest.addParallelComponent(parallelComponent);
  }

  public void setParallelTaskExecutor(TaskExecutor parallelTaskExecutor) {
    this.parallelTaskExecutor = parallelTaskExecutor;
  }

  public TaskExecutor getParallelTaskExecutor() {
    return parallelTaskExecutor;
  } 

//  private static Log log = LogFactory.getLog(ComponentRequestManager.class);

  /*
  public void cacheObject(String oper, String type, String ident, Object obj) {
    String cacheKey = oper + "-" + type + "-" + ident;
    IRequest cr = get();
    if (cr != null && cr.getHttpRequest() != null) {
      cr.getHttpRequest().setAttribute(cacheKey, obj);
    }
  }
  
  public IDataProviderObject checkRequestCache(String oper, String type, String ident) {
    String cacheKey = oper + "-" + type + "-" + ident;
    //log.info("Looking for instance of " + type + " with guid " + ident + " in request cache");
    Object obj = checkRequestCache(cacheKey);
    if (obj instanceof IDataProviderObject) {
      if (log.isDebugEnabled()) {
        log.debug("Found instance of " + type + " with guid " + ident + " in request cache");
      }
      return (IDataProviderObject)obj;
    } else if (obj != null) {
      log.warn("Object cached with key " + cacheKey + " is not a valid datasource object [" + obj + "]");
    }
    return null;
  }

  public Object checkRequestCache(String key) {
    Object cachedObj = null;
    IRequest cr = get();
    if (cr != null && cr.getHttpRequest() != null) {
      cachedObj = cr.getHttpRequest().getAttribute(key);
    }
    return cachedObj;
  }
  */
}
