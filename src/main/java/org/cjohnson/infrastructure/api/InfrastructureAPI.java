package org.cjohnson.infrastructure.api;

import org.cjohnson.infrastructure.Infrastructure;
import org.cjohnson.infrastructure.command.CommandFramework;

import java.util.logging.Logger;

public class InfrastructureAPI {
  
  private static boolean initialized;
  
  private static InfrastructureAPI instance;
  
  private static Infrastructure infrastructurePlugin;
  
  private static CommandFramework commandFramework;
  
  private InfrastructureAPI() {
    initialized = false;
  }
  
  public static InfrastructureAPI getInstance() {
    if(instance == null) {
      instance = new InfrastructureAPI();
    }
    
    return instance;
  }
  
  public boolean initialize(Infrastructure infrastructure, CommandFramework framework) {
    if(initialized) {
      return false;
    }
    
    infrastructurePlugin = infrastructure;
    commandFramework = framework;
    
    return true;
  }
  
  public boolean isInitialized() {
    return initialized;
  }
  
  public Infrastructure getInfrastructurePlugin() {
    return infrastructurePlugin;
  }
  
  public Logger getPluginLogger() {
    return infrastructurePlugin.getLogger();
  }
  
  public CommandFramework getCommandFramework() {
    return commandFramework;
  }
}
