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

package org.cjohnson.infrastructure.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.cjohnson.infrastructure.api.InfrastructureAPI;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * The Data Manager and Player Configuration Manager Class
 *
 * @since @since 0.1.0-ALPHA
 */
public class InfraPlayerDataHandler {
  
  /**
   * The Player Unique ID
   *
   * @since 0.1.0-ALPHA
   */
  private UUID uuid;
  
  /**
   * The file of the configuration on the server's os file system
   *
   * @since 0.1.0-ALPHA
   */
  private File file;
  
  /**
   * Bukkit API Configuration Object
   *
   * @since 0.1.0-ALPHA
   */
  private FileConfiguration fileConfiguration;
  
  /**
   * Default Constructor for InfraPlayerDataHandler
   *
   * @param uuid The Player Unique ID
   *
   * @since 0.1.0-ALPHA
   */
  public InfraPlayerDataHandler(UUID uuid) {
    this.uuid = uuid;
    
    this.file = new File(InfrastructureAPI.getInstance().getInfrastructurePlugin().getDataFolder(), uuid + ".yml");
  }
  
  /**
   * Method that creates a new user file and configuration if a player first-joins.
   *
   * @param player The BukkitAPI player object
   * @throws IOException
   *
   * @since 0.1.0-ALPHA
   */
  public void createUser(final InfraPlayer player) throws IOException {
    if(!(file.exists())) {
      YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
      
      yamlConfiguration.set("user.info.recentalias", player.getPlayer().getName());
      yamlConfiguration.set("user.info.uniqueid", player.getPlayer().getUniqueId());
      yamlConfiguration.set("user.info.internetprotocoladdress", player.getPlayer().getAddress().getAddress().getHostAddress());
      
      yamlConfiguration.set("infrastructure.message.toggled", true);
  
      yamlConfiguration.save(file);
    }
  }
  
  /**
   * Method that saves the user configuration when editing said configuration.
   *
   * @throws IOException
   *
   * @since 0.1.0-ALPHA
   */
  public void saveUserFile() throws IOException {
    fileConfiguration.save(file);
  }
  
  /**
   * Getter for the Bukkit File Configuration Object
   *
   * @return The Bukkit File Configuration Object
   *
   * @since 0.1.0-ALPHA
   */
  public FileConfiguration getUserFileConfiguration() {
    return fileConfiguration;
  }
  
}
