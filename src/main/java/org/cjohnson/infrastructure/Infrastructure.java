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

package org.cjohnson.infrastructure;

import org.bukkit.plugin.java.JavaPlugin;
import org.cjohnson.infrastructure.api.InfrastructureAPI;
import org.cjohnson.infrastructure.command.CommandFramework;
import org.cjohnson.infrastructure.command.player.chat.CommandMsgToggle;
import org.cjohnson.infrastructure.command.player.item.CommandRepair;
import org.cjohnson.infrastructure.player.InfraPlayerHandler;

import java.text.MessageFormat;

/**
 * The Infrastructure Plugin Class, extending the JavaPlugin class from the Bukkit API
 *
 * @since 0.1.0-ALPHA
 */
public class Infrastructure extends JavaPlugin {
  
  /**
   * The official raw plugin name, used in console or official messaging.
   *
   * @since 0.1.0-ALPHA
   */
  public static final String PLUGIN_NAME = "Infrastructure";
  /**
   * The official raw plugin version, used in console or official messaging.
   *
   * @since 0.1.0-ALPHA
   */
  public static final String PLUGIN_VERSION = "0.1.0-ALPHA";
  
  @Override
  public void onEnable() {
    // Log General Enabling Message to Console
    getLogger().info(MessageFormat.format("Enabling {0} {1}", Infrastructure.PLUGIN_NAME, Infrastructure.PLUGIN_VERSION));
    
    // Check if the API is already initialized to act
    // as a prevention from multiple instances.
    if(InfrastructureAPI.getInstance().isInitialized()) {
      getServer().getPluginManager().disablePlugin(this);
      throw new IllegalStateException("InfrastructureAPI is already initialized...");
    }
    
    // Initialize CommandFramework
    CommandFramework commandFramework = new CommandFramework();
    
    // Initialize Player Registry
    InfraPlayerHandler playerHandler = new InfraPlayerHandler();
    
    // Initialize API
    InfrastructureAPI.getInstance().initialize(this, commandFramework, playerHandler);
    
    // Command Setup
    InfrastructureAPI.getInstance().getCommandFramework().addCommand(new CommandRepair(), "repair");
    InfrastructureAPI.getInstance().getCommandFramework().addCommand(new CommandMsgToggle(), "msgtoggle");
    
    // Call method to handle any reloads
    // if there had been a reload.
    handleReloads();
  }
  
  /**
   * The method that will handle any lost data on reloads
   *
   * @since 0.1.0-ALPHA
   */
  private void handleReloads() { }
  
  @Override
  public void onDisable() {
    getLogger().info(MessageFormat.format("Disabling {0} {1}", Infrastructure.PLUGIN_NAME, Infrastructure.PLUGIN_VERSION));
  }
  
}
