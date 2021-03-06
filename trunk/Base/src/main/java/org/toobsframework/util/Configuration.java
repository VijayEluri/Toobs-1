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
package org.toobsframework.util;

import java.util.Date;
import java.util.Properties;

public class Configuration {

  public static final String USE_TRANSLETS_PROPERTY = "toobs.useTranslets";
  public static final String USE_CHAIN_PROPERTY     = "toobs.useChain";
  public static final String RELOAD_PROPERTY = "toobs.doReload";
  public static final String DEBUG_PROPERTY = "toobs.debug";
  public static final String SHOW_STACK_PROPERTY = "toobs.showStackTrace";
  public static final String USE_PARALLEL_PROPERTY = "toobs.enableParallel";
  public static final String ERROR_COMPONENT_PROPERTY = "toobs.errorComponent";

  public static final String LAYOUT_EXT_PROPERTY = "toobs.layout.ext";
  public static final String COMPONENT_EXT_PROPERTY = "toobs.component.ext";
  public static final String CHART_EXT_PROPERTY = "toobs.chart.ext";

  public static final String LAYOUT_EXT_DEFAULT = ".html";
  public static final String COMPONENT_EXT_DEFAULT = ".comp";
  public static final String CHART_EXT_DEFAULT = ".chart";

  public static final String ERROR_COMPONENT_DEFAULT = "error";

  public static final String UPLOAD_DIR_PROPERTY = "toobs.uploadDir";

  protected Properties properties;

  public long deployTime = new Date().getTime();

  private Configuration() {
  }

  public String getProperty(String key) {
    return properties.getProperty(key, null);
  }
  
  public String getProperty(String key, String defaultValue) {
    if (properties.containsKey(key)) {
      return properties.getProperty(key);
    } else {
      return defaultValue;
    }
  }

  public boolean useTranslets() {
    String prop = getProperty(USE_TRANSLETS_PROPERTY);
    if (prop != null && prop.equalsIgnoreCase("true")) {
      return true;
    } else {
      return false;
    }
  }

  public boolean useChain() {
    String prop = getProperty(USE_CHAIN_PROPERTY);
    if (prop != null && prop.equalsIgnoreCase("true")) {
      return true;
    } else {
      return false;
    }
  }

  public boolean doReload() {
    String prop = getProperty(RELOAD_PROPERTY);
    if ((prop != null && prop.equalsIgnoreCase("true")) || prop == null) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isDebug() {
    String prop = getProperty(DEBUG_PROPERTY);
    if ((prop != null && prop.equalsIgnoreCase("false")) || prop == null) {
      return false;
    } else {
      return true;
    }
  }

  public long getDeployTime() {
    return this.deployTime;
  }

  public String getLayoutExtension() {
    String prop = getProperty(LAYOUT_EXT_PROPERTY);
    if (prop != null && prop.length() > 0) {
      return prop;
    } else {
      return LAYOUT_EXT_DEFAULT;
    }
  }

  public String getComponentExtension() {
    String prop = getProperty(COMPONENT_EXT_PROPERTY);
    if (prop != null && prop.length() > 0) {
      return prop;
    } else {
      return COMPONENT_EXT_DEFAULT;
    }
  }

  public String getChartExtension() {
    String prop = getProperty(CHART_EXT_PROPERTY);
    if (prop != null && prop.length() > 0) {
      return prop;
    } else {
      return CHART_EXT_DEFAULT;
    }
  }

  public String getUploadDir() {
    return getProperty(UPLOAD_DIR_PROPERTY);
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public boolean showStackTrace() {
    String prop = getProperty(SHOW_STACK_PROPERTY);
    if ((prop != null && prop.equalsIgnoreCase("true")) || prop == null) {
      return true;
    } else {
      return false;
    }
  }

  public boolean enableParallel() {
    String prop = getProperty(USE_PARALLEL_PROPERTY);
    if ((prop != null && prop.equalsIgnoreCase("true")) || prop == null) {
      return true;
    } else {
      return false;
    }
  }

  public String getErrorComponentName() {
    String prop = getProperty(ERROR_COMPONENT_PROPERTY);
    if (prop != null && prop.length() > 0) {
      return prop;
    } else {
      return ERROR_COMPONENT_DEFAULT;
    }
  }

}
