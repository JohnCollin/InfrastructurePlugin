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

package org.cjohnson.infrastructure.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cjohnson.infrastructure.api.InfrastructureAPI;
import org.cjohnson.infrastructure.command.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandFramework implements CommandExecutor {
  
  private ArrayList<Object> commandObjects;
  
  private boolean checkFail;
  
  public CommandFramework() {
    commandObjects = new ArrayList<Object>();
    
    checkFail = true;
  }
  
  public void addCommand(Object commandObject, String baseAlias) {
    //initial checks
    performInitialChecks(commandObject);
    
    //add to list
    commandObjects.add(commandObject);
    
    //add to registry
    InfrastructureAPI.getInstance().getInfrastructurePlugin().getCommand(baseAlias).setExecutor(this);
  }
  
  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
    checkFail = true;
    
    Object selectedCommand = null;
    
    for(Object commandObject : commandObjects) {
      Class<?> commandClass = commandObject.getClass();
      
      selectedCommand = performObjectClassCheck(command, selectedCommand, commandObject, commandClass);
      
      if(selectedCommand == null) {
        continue;
      }
      
      SubCommand selectedSubCommand = null;
      Method selectedMethod = null;
      
      if(args.length == 0) {
        for(Method method : commandClass.getMethods()) {
          for(Annotation annotation : method.getAnnotations()) {
            if(annotation instanceof SubCommand) {
              if(method.isAnnotationPresent(DefaultCommand.class)) {
                selectedMethod = method;
                selectedSubCommand = (SubCommand) annotation;
              }
            }
          }
        }
      } else {
        for(Method method : commandClass.getMethods()) {
          for(Annotation annotation : method.getAnnotations()) {
            if(annotation instanceof SubCommand) {
              SubCommand subCommand = (SubCommand) annotation;
              for(String alias : subCommand.aliases()) {
                if(args[0].equalsIgnoreCase(alias)) {
                  selectedMethod = method;
                  selectedSubCommand = subCommand;
                }
              }
            }
          }
        }
      }
      
      if(selectedMethod == null) {
        throw new IllegalArgumentException("Default Command Case thrown, no methods with annotation instance of \"org.cjohnson.infrastructure.command.annotation.DefaultCommand\"");
      }
      
      if(selectedSubCommand == null) {
        throw new IllegalArgumentException("Default Command Case thrown, method found, no annotation instance of \"org.cjohnson.infrastructure.command.annotation.DefaultCommand\"");
      }
      
      if(!(sender instanceof Player)) {
        for(Method method : commandClass.getMethods()) {
          for(Annotation annotation : method.getAnnotations()) {
            if(annotation instanceof NotPlayer) {
              try {
                method.invoke(selectedCommand, selectedSubCommand, sender, getTrimmedArguments(args));
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              } catch (InvocationTargetException e) {
                e.printStackTrace();
              }
              
              return false;
            }
          }
        }
      }
      
      if(!sender.hasPermission(selectedSubCommand.permission())) {
        for(Method method : commandClass.getMethods()) {
          for(Annotation annotation : method.getAnnotations()) {
            if(annotation instanceof NoPermission) {
              try {
                method.invoke(selectedCommand, selectedSubCommand, sender, getTrimmedArguments(args));
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              } catch (InvocationTargetException e) {
                e.printStackTrace();
              }
              
              return false;
            }
          }
        }
      }
      
      try {
        selectedMethod.invoke(selectedCommand, selectedSubCommand, sender, getTrimmedArguments(args));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    
    return false;
  }
  
  private Object performObjectClassCheck(org.bukkit.command.Command command, Object selectedCommand, Object commandObject, Class<?> commandClass) {
    for(Annotation annotation : commandClass.getAnnotations()) {
      if(annotation instanceof Command) {
        Command commandAnno = (Command) annotation;
        
        for(String alias : commandAnno.aliases()) {
          if(command.getName().equalsIgnoreCase(alias)) {
            selectedCommand = commandObject;
          }
        }
      }
    }
    return selectedCommand;
  }
  
  private void performInitialChecks(Object commandObject) {
    checkFail = true;
    
    Class<?> commandObjectClass = commandObject.getClass();
    
    //Check for the Command annotation.
    if(commandObjectClass.isAnnotationPresent(Command.class)) {
      checkFail = false;
    }
    
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.Command\"");
    }
    
    checkFail = true;
    
    for(Method method : commandObjectClass.getMethods()) {
      for(Annotation annotation : method.getAnnotations()) {
        if(annotation instanceof DefaultCommand) {
          checkFail = false;
        }
      }
    }
    
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.DefaultCommand\"");
    }
    
    checkFail = true;
    
    for(Method method : commandObjectClass.getMethods()) {
      for(Annotation annotation : method.getAnnotations()) {
        if(annotation instanceof NoPermission) {
          checkFail = false;
        }
      }
    }
    
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.NoPermission\"");
    }
    
    checkFail = true;
    
    for(Method method : commandObjectClass.getMethods()) {
      for(Annotation annotation : method.getAnnotations()) {
        if(annotation instanceof NotPlayer) {
          checkFail = false;
        }
      }
    }
    
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.NotPlayer\"");
    }
  }
  
  private String[] getTrimmedArguments(String[] args) {
    if(args.length < 2) {
      return null;
    }
    
    int n = args.length - 1;
    String[] newArgs = new String[n];
    System.arraycopy(args, 1, newArgs, 0, n);
    
    return newArgs;
  }
  
}
