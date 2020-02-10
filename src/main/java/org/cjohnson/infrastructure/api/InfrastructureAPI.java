/*
 * MIT License
 *
 * Copyright (c) 2020 Collin Johnson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cjohnson.infrastructure.api;

import org.cjohnson.infrastructure.Infrastructure;
import org.cjohnson.infrastructure.command.CommandFramework;
import org.cjohnson.infrastructure.player.InfraPlayerHandler;

import java.util.logging.Logger;

/**
 * The InfrastructureAPI is a singleton class that functions as a way for the plugin to share important data.
 *
 * @since 0.1.0-ALPHA
 */
public class InfrastructureAPI {
  
  /**
   * Boolean flag that shows if the API has been initialized or not.
   *
   * @since 0.1.0-ALPHA
   */
  private static boolean initialized;
  
  /**
   * Singleton instance of the InfrastructureAPI.
   *
   * @since 0.1.0-ALPHA
   */
  private static InfrastructureAPI instance;
  
  /**
   * The Infrastructure Plugin Class.
   *
   * @since 0.1.0-ALPHA
   */
  private static Infrastructure infrastructurePlugin;
  
  /**
   * Infrastructure's Command Framework and Command Registry
   *
   * @since 0.1.0-ALPHA
   */
  private static CommandFramework commandFramework;
  
  /**
   * Infrastructure's Player Handler and Player Registry
   */
  private static InfraPlayerHandler playerHandler;
  
  /**
   * InfrastructureAPI Singleton Constructor
   *
   * @since 0.1.0-ALPHA
   */
  private InfrastructureAPI() {
    initialized = false;
  }
  
  /**
   * Singleton getInstance() method with first-time initialization.
   *
   * @return Singleton instance of InfrastructureAPI.
   *
   * @since 0.1.0-ALPHA
   */
  public static InfrastructureAPI getInstance() {
    if(instance == null) {
      instance = new InfrastructureAPI();
    }
    
    return instance;
  }
  
  /**
   * Initialization method called by the Infrastructure Plugin Class on Initialization of the Plugin.
   *
   * @param infrastructure The Plugin Object
   * @param framework The CommandFramework and Command Registry
   *
   * @return Boolean flag to signal whether the API was initialized correctly.
   *
   * @since 0.1.0-ALPHA
   */
  public boolean initialize(Infrastructure infrastructure, CommandFramework framework, InfraPlayerHandler infraPlayerHandler) {
    if(initialized) {
      return false;
    }
    
    // Define Class-Level Variables
    infrastructurePlugin = infrastructure;
    commandFramework = framework;
    playerHandler = infraPlayerHandler;
    
    return true;
  }
  
  /**
   * Getter for the boolean initialized flag.
   *
   * @return The boolean initialized flag
   *
   * @since 0.1.0-ALPHA
   */
  public boolean isInitialized() {
    return initialized;
  }
  
  /**
   * Getter for the Infrastructrue Plugin Object
   *
   * @return The Infrastructure Plugin Object
   *
   * @since 0.1.0-ALPHA
   */
  public Infrastructure getInfrastructurePlugin() {
    return infrastructurePlugin;
  }
  
  /**
   * Getter for the Bukkit Plugin Logger.
   *
   * @return Bukkit Plugin Logger.
   *
   * @since 0.1.0-ALPHA
   */
  public Logger getPluginLogger() {
    return infrastructurePlugin.getLogger();
  }
  
  /**
   * Getter for the Infrastructure Command Framework and Registry
   *
   * @return Infrastructure Command Framework and Registry
   *
   * @since 0.1.0-ALPHA
   */
  public CommandFramework getCommandFramework() {
    return commandFramework;
  }
  
  /**
   * Getter for the Infrastructure Player Handler and Registry
   *
   * @return Infrastructure Player Handler and Registry
   *
   * @since 0.1.0-ALPHA
   */
  public InfraPlayerHandler getPlayerHandler() {
    return playerHandler;
  }
}
