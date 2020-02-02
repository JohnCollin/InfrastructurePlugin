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

package org.cjohnson.infrastructure.command.player.item;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.cjohnson.infrastructure.command.annotation.*;
import org.cjohnson.infrastructure.item.ItemUtilities;
import org.cjohnson.infrastructure.message.MessageUtilities;

/**
 * The CommandRepair command class is used as a command class for the
 * in-game /repair command.
 *
 * @since 0.1.0-ALPHA
 */
@Command(aliases = {"repair", "fix", "efix", "erepair"}, permission = "infrastructure.repair")
public class CommandRepair {
  
  /**
   * Default Constructor for CommandRepair
   *
   * @since 0.1.0-ALPHA
   */
  public CommandRepair() {}
  
  /**
   * The handRepairCommand() method is the default command case and the case when the
   * argument [hand] is used.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @DefaultCommand
  @SubCommand(aliases = {"hand"}, permission = "infrastructure.repair")
  public void handRepairCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    // Get the ItemStack in the player's hand,
    // through getting the PlayerInventory first.
    Player player = (Player) sender;
    PlayerInventory playerInventory = player.getInventory();
    ItemStack itemStack = playerInventory.getItemInMainHand();
    
    // Air buffer (You cannot repair air)
    if(itemStack.getType() == Material.AIR) {
      MessageUtilities.displayInfoMessage(player, "You cannot repair air.");
      
      return;
    }
    
    // Repair the Item in Main Hand using the ItemUtilties
    playerInventory.setItemInMainHand(ItemUtilities.setItemDamage(itemStack, (short) 0));
    
    // Tell the player that their item has been repaired.
    MessageUtilities.displayInfoMessage(player, "Your " + itemStack.getType().toString() + " has been repaired.");
  }
  
  /**
   * The allRepairCommand() method is the case when the
   * argument [all] is used.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @SubCommand(aliases = {"all"}, permission = "infrastructure.repair.all")
  public void allRepairCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    // Get the contents of all the items in an inventory
    // through the PlayerInventory object, as an array
    // of ItemStacks.
    Player player = (Player) sender;
    PlayerInventory playerInventory = player.getInventory();
    ItemStack[] itemStacks = playerInventory.getContents();
    
    // Iterate through the contents of the inventory
    for(int slot = 0; slot < itemStacks.length; slot++) {
      // Air buffer (You cannot repair air)
      if(itemStacks[slot] == null) {
        continue;
      }
      
      // Repair the item in the inventory using the ItemUtilities
      itemStacks[slot] = ItemUtilities.setItemDamage(itemStacks[slot], (short) 0);
    }
    
    // Set the player's inventory contents as the new array.
    playerInventory.setContents(itemStacks);
  
    // Tell the player that their items have been repaired.
    MessageUtilities.displayInfoMessage(player, "All of your items have been repaired.");
  }
  
  /**
   * The noPermssion() method is the case when the sender has
   * no permission to run the command.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @NoPermission
  public void noPermission(SubCommand subCommand, CommandSender sender, String[] args) {
    MessageUtilities.displayErrorMessage(sender, "You do not have permission to perform this command.");
  }
  
  /**
   * The notPlayer() method is the case when the sender is
   * not an instanceof player.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @NotPlayer
  public void notPlayer(SubCommand subCommand, CommandSender sender, String[] args) {
    MessageUtilities.displayErrorMessage(sender, "You must be a player to perform this command.");
  }
  
}
