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

/**
 * The CommandFramework Class is an overall handler for the Infrastructure Command Registry
 * <p>
 * The Infrastructure Command Registry is a custom system for handling commands with
 * the Bukkit API platform, with the developers in mind. It is now easy to develop command
 * functionality in a more readable and easy-to-understand way.
 *
 * @since 0.1.0-ALPHA
 */
public class CommandFramework implements CommandExecutor {
  
  /**
   * The Infrastrucutre Command Registry ArrayList
   *
   * @since 0.1.0-ALPHA
   */
  private ArrayList<Object> commandObjects;
  
  /**
   * Boolean used by checking methods to use less memory and less garbage collection utilities.
   *
   * @since 0.1.0-ALPHA
   */
  private boolean checkFail;
  
  /**
   * Default CommandFramework Constructor
   *
   * @since 0.1.0-ALPHA
   */
  public CommandFramework() {
    commandObjects = new ArrayList<Object>();
    
    checkFail = true;
  }
  
  /**
   * The addCommand() method is used to add a new class/object to the Infrastructure Command Registry
   *
   * @param commandObject Object to be tested
   * @param baseAlias The alias that is used under the BukkitAPI
   *
   * @since 0.1.0-ALPHA
   */
  public void addCommand(Object commandObject, String baseAlias) {
    // Perform the Initial Checks to Rule out any
    // obviously offending classes that are not
    // able to be properly added to the Infrastructure
    // Command Registry.
    performInitialChecks(commandObject);
    
    // Add the Objects to the Class-Level list.
    commandObjects.add(commandObject);
    
    // Add the command to the Bukkit command registry,
    // and set this class (object at runtime) as the
    // CommandExecutor.
    InfrastructureAPI.getInstance().getInfrastructurePlugin().getCommand(baseAlias).setExecutor(this);
  }
  
  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
    checkFail = true;
    
    // Null initialization is required here, as we
    // do not yet have the first object in the loop.
    Object selectedCommand = null;
    
    for(Object commandObject : commandObjects) {
      // Get the Java Relection Class Object
      Class<?> commandClass = commandObject.getClass();
      
      // Perform the class check to rule out any obvious
      // offenders that cannot properly be parsed.
      selectedCommand = performObjectClassCheck(command, selectedCommand, commandObject, commandClass);
      
      // Another null buffer to make sure that errors are
      // not thrown.
      if(selectedCommand == null) {
        continue;
      }
      
      // Null initializations are required here,
      // for the reflexive loops to follow.
      SubCommand selectedSubCommand = null;
      Method selectedMethod = null;
      
      // These large, intimidating blocks are necessary due to the
      // structure and layering of Java Reflection. Each layer from
      // Class to Method to Annotation is just a simple iterative
      // search.
      
      // Process the current command and find the correct
      // Command and invoke the proper method using Java
      // Reflection Utilities.
      if(args.length == 0) {
        
        // Iterate through the class methods
        for(Method method : commandClass.getMethods()) {
          
          // Loop through the annotations of the method
          for(Annotation annotation : method.getAnnotations()) {
            
            // Check if the annotation is an instance
            if(annotation instanceof SubCommand) {
              
              // Check if the method is a default command, because this case
              // is looking for the default command case.
              if(method.isAnnotationPresent(DefaultCommand.class)) {
                // When found, set the selected objects to the current
                // Annotation for later processing and invocation.
                selectedMethod = method;
                selectedSubCommand = (SubCommand) annotation;
              }
            }
          }
        }
      } else {
  
        // Iterate through the class methods
        for(Method method : commandClass.getMethods()) {
  
          // Loop through the annotations of the method
          for(Annotation annotation : method.getAnnotations()) {
  
            // Check if the annotation is an instance
            if(annotation instanceof SubCommand) {
              
              // Cast the annotation to that of type in order
              // To retrieve values from it.
              SubCommand subCommand = (SubCommand) annotation;
              
              // Iterate through the aliases to check if we've found the
              // correct alias.
              for(String alias : subCommand.aliases()) {
                if(args[0].equalsIgnoreCase(alias)) {
                  // When found, set the selected objects to the current
                  // Annotation for later processing and invocation.
                  selectedMethod = method;
                  selectedSubCommand = subCommand;
                }
              }
            }
          }
        }
      }
      
      // Null Buffers to ensure correctly constructed
      // arguments are passed.
      if(selectedMethod == null) {
        throw new IllegalArgumentException("Default Command Case thrown, no methods with annotation instance of \"org.cjohnson.infrastructure.command.annotation.DefaultCommand\"");
      }
      
      if(selectedSubCommand == null) {
        throw new IllegalArgumentException("Default Command Case thrown, method found, no annotation instance of \"org.cjohnson.infrastructure.command.annotation.DefaultCommand\"");
      }
      
      // Again, due to the nature of Java Reflection,
      // this type of search is necessary.
      
      // Check if the sender is not a player,
      // Since all Infrastructure commands
      // are player-run this then finds the
      // custom error case and invokes the
      // custom method.
      if(!(sender instanceof Player)) {
        
        // Iterate through Class Methods.
        for(Method method : commandClass.getMethods()) {
          
          // Iterate through Method Annotations.
          for(Annotation annotation : method.getAnnotations()) {
            
            // Check if the annotation is an instance of
            // the NotPlayer method annotation.
            if(annotation instanceof NotPlayer) {
              
              // Invoke the method using Java Reflection.
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
      
      // Check if the sender does not have permission,
      // if so, finds the custom error case and invokes
      // it using Java Reflection.
      if(!sender.hasPermission(selectedSubCommand.permission())) {
        
        // Iterate through Class Methods.
        for(Method method : commandClass.getMethods()) {
          
          // Iterate through Method Annotations.
          for(Annotation annotation : method.getAnnotations()) {
            
            // Check if the given annotation is an instance of
            // the NoPermission command method annotation.
            if(annotation instanceof NoPermission) {
              
              // Invoke the case method using Java Reflection.
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
      
      // After all quick tests are done,
      // Invoke the proper command case method
      // using Java Reflection.
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
  
  /**
   * The performObjectClassCheck() method is used to check the Class-Level Annotations for the correct parameters and Annotations.
   * <p>
   * If a proper alias or annotation is not found by the logic presented, the return value becomes null, which signifies an
   * error, and will then throw an IllegalArgumentException.
   *
   * @param command Command Object passed by the Bukkit API.
   * @param selectedCommand The selected command that will be defined by the commandObject if the commandObject is constructed correctly.
   * @param commandObject Command Object passed based on current iteration of the command search.
   * @param commandClass The class object that is used to reference its annotations.
   *
   * @return The selected command object.
   *
   * @since 0.1.0-ALPHA
   */
  private Object performObjectClassCheck(org.bukkit.command.Command command, Object selectedCommand, Object commandObject, Class<?> commandClass) {
    // Iterate through Class Annotations
    for(Annotation annotation : commandClass.getAnnotations()) {
      
      // Check if the annotation is an instance of
      // Command.
      if(annotation instanceof Command) {
        // Cast the annotation to the Command type
        // to perform Annotation-Specific functions.
        Command commandAnno = (Command) annotation;
        
        // Loop through aliases and find the matching
        // alias.
        for(String alias : commandAnno.aliases()) {
          if(command.getName().equalsIgnoreCase(alias)) {
            // Select the object as the new selected command.
            selectedCommand = commandObject;
          }
        }
      }
    }
    return selectedCommand;
  }
  
  /**
   * The performInitialChecks() method is used to check the method-level possible problems.
   * <p>
   * If the proper instances of commands are not found, an IllegalArgumentException will be thrown.
   *
   * @param commandObject The Object passed as a Command Class.
   *
   * @since 0.1.0-ALPHA
   */
  private void performInitialChecks(Object commandObject) {
    checkFail = true;
    
    // Get Class Object from Argument Object
    Class<?> commandObjectClass = commandObject.getClass();
    
    // Check for the Command annotation.
    // If found, there is no error found.
    if(commandObjectClass.isAnnotationPresent(Command.class)) {
      checkFail = false;
    }
    
    // When the class has no annotation member of
    // Command, throw an IllegalArgumentException.
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.Command\"");
    }
    
    checkFail = true;
    
    // Iterate through Class Methods.
    for(Method method : commandObjectClass.getMethods()) {
      
      // Iterate through Method Annotations.
      for(Annotation annotation : method.getAnnotations()) {
        
        // If the class has a method with an annotation
        // instance of Default Command, failure check is passed.
        // (no failure)
        if(annotation instanceof DefaultCommand) {
          checkFail = false;
        }
      }
    }
    
    // When there is no method with annotation member
    // instance of DefaultCommand, throw an
    // IllegalArgumentException.
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.DefaultCommand\"");
    }
    
    checkFail = true;
    
    // Iterate through Class Methods
    for(Method method : commandObjectClass.getMethods()) {
      
      // Iterate through Method Annotations.
      for(Annotation annotation : method.getAnnotations()) {
        
        // If instance of NoPermission found
        // in any method, no error is thrown.
        if(annotation instanceof NoPermission) {
          checkFail = false;
        }
      }
    }
    
    // If no instance of annotation NoPermission is found,
    // throw an IllegalArgumentException.
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.NoPermission\"");
    }
    
    checkFail = true;
  
    // Iterate through Class Methods
    for(Method method : commandObjectClass.getMethods()) {
  
      // Iterate through Method Annotations.
      for(Annotation annotation : method.getAnnotations()) {
  
        // If instance of NotPlayer found
        // in any method, no error is thrown.
        if(annotation instanceof NotPlayer) {
          checkFail = false;
        }
      }
    }
  
    // If no instance of annotation NotPlayer is found,
    // throw an IllegalArgumentException.
    if(checkFail) {
      throw new IllegalArgumentException("Given CommandObject has no annotation with instance of \"org.cjohnson.infrastructure.command.annotation.NotPlayer\"");
    }
  }
  
  /**
   * The getTrimmedArguments() method is used to trim a standard Bukkit Command into an array of only its arguments.
   *
   * @param args The Bukkit API given arguments
   *
   * @return Trimmed Arguments of String Array (original command)
   *
   * @since 0.1.0-ALPHA
   */
  private String[] getTrimmedArguments(String[] args) {
    // Prevent Errors with small commands by only allowing
    // Argument lists of a certain size.
    if(args.length < 2) {
      return null;
    }
    
    // Grabs length of new String Array
    int n = args.length - 1;
    
    // Initialize new String Array
    String[] newArgs = new String[n];
    
    // Use the System Arraycopy function to copy
    // the contents over to the new array.
    System.arraycopy(args, 1, newArgs, 0, n);
    
    return newArgs;
  }
  
}