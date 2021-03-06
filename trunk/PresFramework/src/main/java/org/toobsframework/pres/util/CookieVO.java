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
package org.toobsframework.pres.util;

public class CookieVO {
  private String cookieName;
  private String cookieValue;
  
  public String getCookieName() {
    return cookieName;
  }
  public void setCookieName(String cookieName) {
    this.cookieName = cookieName;
  }
  public String getCookieValue() {
    return cookieValue;
  }
  public void setCookieValue(String cookieValue) {
    this.cookieValue = cookieValue;
  }
  public CookieVO(String cookieName, String cookieValue) {
    super();
    this.cookieName = cookieName;
    this.cookieValue = cookieValue;
  }
}
