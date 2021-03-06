package org.toobs.framework.pres.component;

import java.util.List;

import org.toobs.framework.exception.PermissionException;


public interface IComponentLayoutManager {

  public abstract RuntimeLayout getLayout(String Id, long deployTime)
      throws ComponentNotFoundException, ComponentInitializationException;

  public abstract RuntimeLayout getLayout(PermissionException permissionException)
    throws ComponentNotFoundException, ComponentInitializationException;

  // Read from config file
  public abstract void init() throws ComponentInitializationException;

  public void addConfigFiles(List configFiles);
  
}