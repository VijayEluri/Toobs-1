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
package org.toobsframework.pres.layout;

import org.toobsframework.exception.BaseException;

/**
 * @author stewari
 */
public class ComponentLayoutInitializationException extends BaseException {
    
    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String componentId;
    
    public ComponentLayoutInitializationException(String message, Throwable cause) {
      super(message, cause);
    }

    public ComponentLayoutInitializationException(Throwable cause) {
      super(cause);
    }

    public ComponentLayoutInitializationException() {
        super();
    }
    
    public ComponentLayoutInitializationException(String message) {
        super(message);
    }
    
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
    
    public String getComponentId() {
        return this.componentId;
    }
}
