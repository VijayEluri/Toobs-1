/*
 * This file is licensed to the Toobs Framework Group under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Toobs Framework Group licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License.  You may obtain a copy of the 
 * License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.toobsframework.servlet.filters.export;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.MimeConstants;
import org.toobsframework.servlet.filters.compression.FilterResponseWrapper;

public class PDFExportFilter extends BaseExportFilter implements Filter {

  private static final long serialVersionUID = -968249206640558155L;

  /** The logger instance */
  private static Log log = LogFactory.getLog(PDFExportFilter.class);

  /** The Filter config */
  private FilterConfig config = null;

  private String contextName;

  public void init(FilterConfig config) throws ServletException {
    this.config = config;
    this.config.getServletContext().log("PDFFilter - init()");
    contextName = this.config.getServletContext().getServletContextName();
    if (contextName == null) {
      contextName = "/";
    }
  }

  public void destroy() {
    this.config.getServletContext().log("PDFFilter - destroy()");
    this.config = null;
  }

  /**
   * Pipe the fetched *.pdf <fo> xml output from the ComponentLayoutManager
   * through the FO transformer in order to get a viable pdf file. Set the
   * response headers, response stream, and the browser should pick the pdf up
   * and handle it properly.
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
  throws IOException, ServletException
  {
    this.config.getServletContext().log("PDFFilter - doFilter(...)");
    log.debug("ENTER doFilter(...)");

    HttpServletResponse httpResponse = (HttpServletResponse) response;
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    
    // set headers so browser knows it's looking at a pdf
    //httpResponse.setHeader("Content-Disposition", "attachment");
    
    //first chain the filters... ie do everything else first... this filter
    //will work on the data that comes back after the transform has already been run
    FilterResponseWrapper wrapper = new FilterResponseWrapper(httpResponse);
    chain.doFilter(request, wrapper);

    String fileName;
    String exportMode = httpRequest.getParameter("exportMode");
    if ("table".equals(exportMode)) {
      fileName = httpRequest.getParameter("component") + ".pdf";
    } else {
      fileName = httpRequest.getRequestURI().substring(contextName.length() + 1).replace("xpdf", "pdf");
    }

    // set headers so browser knows it's looking at a pdf
    httpResponse.setContentType(MimeConstants.MIME_PDF);
    httpResponse.setHeader("Pragma", "public");
    httpResponse.setDateHeader("Expires", 0);
    httpResponse.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
    httpResponse.setHeader("Cache-Control","public");
    httpResponse.setHeader("Content-Description","File Transfer");
    httpResponse.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
    
    //now fetch the output stream and response data
    ServletOutputStream httpOutputStream = httpResponse.getOutputStream();
    byte[] responseData = wrapper.getData();

    //dump the output stream to the log, just for verification purposes in debugging mode
    if (log.isDebugEnabled()) {
      log.debug("url *.pdf initial response output data: " + new String(responseData));
    }
    
    try {
      convertFO2Mime(new ByteArrayInputStream(responseData), httpOutputStream, MimeConstants.MIME_PDF);
    } catch(FOPException fope) {
      log.error("Encountered FOPException: " + fope.getMessage());
      fope.printStackTrace();
      throw new ServletException(fope);      
    } 

    if (log.isDebugEnabled()) {
      log.debug("EXIT doFilter(...)");
    }
  }
}
