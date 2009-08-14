package org.toobsframework.pres.layout.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;
import org.toobsframework.pres.component.config.Parameter;
import org.toobsframework.doitref.IDoItRefQueue;
import org.toobsframework.exception.PermissionException;
import org.toobsframework.pres.component.ComponentException;
import org.toobsframework.pres.layout.ComponentLayoutNotFoundException;
import org.toobsframework.pres.layout.RuntimeLayout;
import org.toobsframework.pres.layout.manager.IComponentLayoutManager;
import org.toobsframework.pres.security.IComponentSecurity;
import org.toobsframework.pres.util.ComponentRequestManager;
import org.toobsframework.pres.util.ParameterUtil;
import org.toobsframework.pres.util.PresConstants;
import org.toobsframework.transformpipeline.domain.IXMLTransformerHelper;

@SuppressWarnings("unchecked")
public class ComponentLayoutHandler implements IComponentLayoutHandler {
  protected final Log log = LogFactory.getLog(getClass());

  private UrlPathHelper urlPathHelper = new UrlPathHelper();

  private IComponentLayoutManager componentLayoutManager = null;
  private ComponentRequestManager componentRequestManager = null;
  private IXMLTransformerHelper transformerHelper = null;
  private IDoItRefQueue doItRefQueue = null;
  private IComponentSecurity layoutSecurity;

  /**
   * 
   * Retrieves the URL path to use for lookup and delegates to
   * <code>getViewNameForUrlPath</code>.
   * 
   * @throws Exception Exception fetching or rendering component.
   * @see #getViewNameForUrlPath
   * 
   */
  public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String output = null;
    String urlPath = this.urlPathHelper.getLookupPathForRequest(request);
    request.getSession().setAttribute(PresConstants.SESSION_LAST_VIEW, urlPath);
    String layoutId = ParameterUtil.extractViewNameFromUrlPath(urlPath);
    String contextPath = ParameterUtil.extractContextPathFromUrlPath(urlPath);
    String extension = ParameterUtil.extractExtensionFromUrlPath(urlPath);
    if (log.isDebugEnabled()) {
      log.debug("Rendering component layout '" + layoutId + "' for lookup path: " + urlPath);
    }
    
    Date startTime = null;
    if (log.isDebugEnabled()) {
      startTime = new Date();
    }

    //Write out to the response.
    response.setContentType("text/html; charset=UTF-8");
    response.setHeader("Pragma",        "no-cache");                           // HTTP 1.0
    response.setHeader("Cache-Control", "no-cache, must-revalidate, private"); // HTTP 1.1

    //Get component and render it.
    if(null != layoutId && !layoutId.equals("")) {
      RuntimeLayout layout = null;
      try {
        request.setAttribute("layoutId", layoutId);
        request.setAttribute("contextPath", contextPath + (contextPath.length() > 0 ? "/" : ""));
        request.setAttribute("appContext", contextPath);
        request.setAttribute("outputFormat", extension);

        layout = this.componentLayoutManager.getLayout(layoutId);
      } catch (ComponentLayoutNotFoundException cnfe) {
        // TODO Missing layout handler
        log.warn("Component Layout " + layoutId + " not found.");
        layout = this.componentLayoutManager.getLayout("FeatureNotEnabled");
        //throw cnfe;
      }
      try {
        Map params = ParameterUtil.buildParameterMap(request);
        componentRequestManager.set(request, response, params);

        // TODO Sec check layout handler
        boolean hasAccess = true;
        if (layoutSecurity != null) {
          hasAccess = layoutSecurity.hasAccess(layoutId);
        }
        params.put("pageAccess", String.valueOf(hasAccess));
        if (!hasAccess && layout.getConfig().getNoAccessLayout() != null) {
          try {
            layout = this.componentLayoutManager.getLayout(layout.getConfig().getNoAccessLayout());
            output = layout.render(componentRequestManager.get(), transformerHelper);
          } catch (ComponentLayoutNotFoundException cnfe) {
            log.warn("No Access Component Layout " + layout.getConfig().getNoAccessLayout() + " not found.");
            throw cnfe;
          }
        } else {
          layout.renderStream(response.getOutputStream(), componentRequestManager.get(), extension, transformerHelper);
        }
        
        if (layout.getDoItRef() != null) {
          Map actionParams = new HashMap();
          if (layout.getDoItRef().getParameters() != null) {
            Parameter[] parameters = layout.getDoItRef().getParameters().getParameter();
            ParameterUtil.mapDoItParameters(componentRequestManager.get(), parameters, params, actionParams, true);
          }
          doItRefQueue.put(layout.getDoItRef().getDoItId(), actionParams);
        }
      } catch (ComponentException e) {
        Throwable t = e.rootCause();
        log.info("Root cause " + t.getClass().getName() + " " + t.getMessage());
        if (t instanceof PermissionException
            && (layout = this.componentLayoutManager.getLayout((PermissionException)t)) != null) {
          output = layout.render(componentRequestManager.get(), transformerHelper);
        } else {
          throw e;
        }
      } catch (Exception e) {
        throw e;
      } finally {
        this.componentRequestManager.unset();
      }
      
    } else {
      throw new Exception ("No layoutId specified");
    }

    if (output != null) {
      PrintWriter writer = response.getWriter();
      writer.print(output);
      writer.flush();
    }

    if (log.isDebugEnabled()) {
      Date endTime = new Date();
      log.debug("Time [" + layoutId + "] - " + (endTime.getTime() - startTime.getTime()));
    }
    return null;

  }

  public IComponentLayoutManager getComponentLayoutManager() {
    return componentLayoutManager;
  }

  public void setComponentLayoutManager(
      IComponentLayoutManager componentLayoutManager) {
    this.componentLayoutManager = componentLayoutManager;
  }

  public ComponentRequestManager getComponentRequestManager() {
    return componentRequestManager;
  }

  public void setComponentRequestManager(
      ComponentRequestManager componentRequestManager) {
    this.componentRequestManager = componentRequestManager;
  }

  public IDoItRefQueue getDoItRefQueue() {
    return doItRefQueue;
  }

  public void setDoItRefQueue(IDoItRefQueue doItRefQueue) {
    this.doItRefQueue = doItRefQueue;
  }

  public IComponentSecurity getLayoutSecurity() {
    return layoutSecurity;
  }

  public void setLayoutSecurity(IComponentSecurity layoutSecurity) {
    this.layoutSecurity = layoutSecurity;
  }

  /**
   * @return the transformerHelper
   */
  public IXMLTransformerHelper getTransformerHelper() {
    return transformerHelper;
  }

  /**
   * @param transformerHelper the transformerHelper to set
   */
  public void setTransformerHelper(IXMLTransformerHelper transformerHelper) {
    this.transformerHelper = transformerHelper;
  }

}
