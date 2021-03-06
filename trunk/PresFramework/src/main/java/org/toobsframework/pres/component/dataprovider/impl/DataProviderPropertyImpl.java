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
package org.toobsframework.pres.component.dataprovider.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;

import org.toobsframework.pres.component.dataprovider.api.IDataProviderObjectProperty;
import org.toobsframework.pres.component.dataprovider.api.PropertyType;


public class DataProviderPropertyImpl implements IDataProviderObjectProperty {

  private String parentId;
  private String propertyName;
  private PropertyType propertyType;
  private Class valueType;
  private Object propertyValue;
  
  public DataProviderPropertyImpl(PropertyDescriptor propertyDescriptor) {
   
    this.propertyType = null;
    this.valueType = propertyDescriptor.getPropertyType();
    if (this.propertyType.getName().equals(PropertyType.INDEXED)) {
      this.propertyValue = new ArrayList();
    } else if (this.propertyType.getName().equals(PropertyType.MAPPED)) {
      this.propertyValue = new HashMap();
    }
  }

  public String getParentId() {
    return parentId;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public PropertyType getPropertyType() {
    return propertyType;
  }

  public Class getValueType() {
    return valueType;
  }

  public Object getPropertyValue() {
    return propertyValue;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public void setPropertyType(PropertyType propertyType) {
    this.propertyType = propertyType;
  }

  public void setPropertyValue(Object propertyValue) {
    this.propertyValue = propertyValue;
  }

  public void setValueType(Class valueType) {
    this.valueType = valueType;
  }

  /**
   * Check if this is a mapped property. A mapped property is one that
   * represents a map, i.e., name-value pairs
   * @return true if this is a mapped property, false otherwise
   */
  public boolean isMapped() {
      return this.propertyType.getName().equals(PropertyType.MAPPED.getName()); 
  }
  
  /**
   * Check if this is an indexed property. An indexed property is one that
   * represents a collection, i.e., list, array
   * @return true if this is an indexed property, false otherwise
   */
  public boolean isIndexed() {
      return this.propertyType.getName().equals(PropertyType.INDEXED.getName()); 
  }
  
  /**
   * Check if this is a simple property.
   * @return true if this is a simple property, false otherwise
   */
  public boolean isSimple() {
      return this.propertyType.getName().equals(PropertyType.SIMPLE.getName()); 
  }

}
