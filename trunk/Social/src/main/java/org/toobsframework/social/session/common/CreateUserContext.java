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
package org.toobsframework.social.session.common;

import org.apache.camel.Handler;
import org.toobsframework.pres.component.dataprovider.api.DispatchContext;
import org.toobsframework.social.persistence.model.User;
import org.toobsframework.social.session.SessionBase;

public class CreateUserContext extends SessionBase {
  
  
  @Handler
  public DispatchContext createUserObject(DispatchContext context) {
    User user = new User();
    user.setFirstName(getParameter(context.getInputParameters(), "firstName"));
    user.setLastName(getParameter(context.getInputParameters(), "lastName"));
    user.setUserId(getParameter(context.getInputParameters(), "email"));
    user.setPassword(getParameter(context.getInputParameters(), "password"));
    
    context.setContextObject(user);
    
    return context;
  }

}
