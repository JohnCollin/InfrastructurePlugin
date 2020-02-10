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

import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * The Player Handling Class that manages all instances of InfraPlayer during runtime.
 *
 * @since 0.1.0-ALPHA
 */
public class InfraPlayerHandler {
  
  /**
   * The list of online InfraPlayers, managed in a java.util.ArrayList
   *
   * @since 0.1.0-ALPHA
   */
  private ArrayList<InfraPlayer> infraPlayerArrayList;
  
  /**
   * Default empty parameter constructor for InfraPlayerHandler
   *
   * @since 0.1.0-ALPHA
   */
  public InfraPlayerHandler() {
    infraPlayerArrayList = new ArrayList<InfraPlayer>();
  }
  
  /**
   * Gets the InfraPlayer object from the Bukkit API player object
   *
   * @param player The Bukkit API player object
   * @return The InfraPlayer object
   *
   * @since 0.1.0-ALPHA
   */
  public InfraPlayer getPlayer(Player player) {
    for(InfraPlayer searchInfraPlayer : infraPlayerArrayList) {
      if(searchInfraPlayer.getPlayer().getName().equalsIgnoreCase(player.getName())) {
        return searchInfraPlayer;
      }
    }
    
    return null;
  }
  
  /**
   * Gets the InfraPlayer object from the player's username
   *
   * @param playerName The Player's username
   * @return The InfraPlayer Object
   *
   * @since 0.1.0-ALPHA
   */
  public InfraPlayer getPlayer(String playerName) {
    for(InfraPlayer searchInfraPlayer : infraPlayerArrayList) {
      if(searchInfraPlayer.getPlayer().getName().equalsIgnoreCase(playerName)) {
        return searchInfraPlayer;
      }
    }
    
    return null;
  }
  
  /**
   * Adds a new InfraPlayer to the ArrayList player registry.
   *
   * @param infraPlayer
   */
  public void addPlayer(InfraPlayer infraPlayer) {
    infraPlayerArrayList.add(infraPlayer);
  }
  
  /**
   * Finds the target player and removes them from the list.
   *
   * @param infraPlayer The InfraPlayer object
   *
   * @since 0.1.0-ALPHA
   */
  public void removePlayer(InfraPlayer infraPlayer) {
    for(InfraPlayer searchInfraPlayer : infraPlayerArrayList) {
      if(searchInfraPlayer.getPlayer().getName().equalsIgnoreCase(infraPlayer.getPlayer().getName())) {
        infraPlayerArrayList.remove(searchInfraPlayer);
        
        break;
      }
    }
  }
  
  /**
   * Finds the target player and removes them from the list.
   *
   * @param player The Bukkit API Player object
   *
   * @since 0.1.0-ALPHA
   */
  public void removePlayer(Player player) {
    for(InfraPlayer searchInfraPlayer : infraPlayerArrayList) {
      if(searchInfraPlayer.getPlayer().getName().equalsIgnoreCase(player.getName())) {
        infraPlayerArrayList.remove(searchInfraPlayer);
      
        break;
      }
    }
  }
  
  /**
   * Finds the target player and removes them from the list.
   *
   * @param playerName The Player's name
   *
   * @since 0.1.0-ALPHA
   */
  public void removePlayer(String playerName) {
    for(InfraPlayer searchInfraPlayer : infraPlayerArrayList) {
      if(searchInfraPlayer.getPlayer().getName().equalsIgnoreCase(playerName)) {
        infraPlayerArrayList.remove(searchInfraPlayer);
      
        break;
      }
    }
  }
  
  /**
   * Gets the entire list of players.
   *
   * @return The InfraPlayer ArrayList object.
   *
   * @since 0.1.0-ALPHA
   */
  public ArrayList<InfraPlayer> getInfraPlayerArrayList() {
    return infraPlayerArrayList;
  }
  
}
