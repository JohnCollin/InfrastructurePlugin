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
