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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.cjohnson.infrastructure.command.annotation.*;
import org.cjohnson.infrastructure.item.ItemUtilities;
import org.cjohnson.infrastructure.message.MessageUtilities;

@Command(aliases = {"repair", "fix", "efix", "erepair"}, permission = "infrastructure.repair")
public class CommandRepair {
  
  public CommandRepair() {}
  
  @DefaultCommand
  @SubCommand(aliases = {"hand"}, permission = "infrastructure.repair")
  public void handRepairCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    //Get necessary objects
    Player player = (Player) sender;
    PlayerInventory playerInventory = player.getInventory();
    ItemStack itemStack = playerInventory.getItemInMainHand();
    
    if(itemStack.getType() == Material.AIR) {
      MessageUtilities.displayInfoMessage(player, "You cannot repair air.");
      
      return;
    }
    
    //Repair
    playerInventory.setItemInMainHand(ItemUtilities.setItemDamage(itemStack, (short) 0));
    
    //Log to Player
    MessageUtilities.displayInfoMessage(player, "Your " + itemStack.getType().toString() + " has been repaired.");
  }
  
  @SubCommand(aliases = {"all"}, permission = "infrastructure.repair.all")
  public void allRepairCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    //Get necessary objects
    Player player = (Player) sender;
    PlayerInventory playerInventory = player.getInventory();
    ItemStack[] itemStacks = playerInventory.getContents();
    
    //Repair
    for(int slot = 0; slot < itemStacks.length; slot++) {
      if(itemStacks[slot] == null) {
        continue;
      }
      
      itemStacks[slot] = ItemUtilities.setItemDamage(itemStacks[slot], (short) 0);
    }
    playerInventory.setContents(itemStacks);
    
    //Log to Player
    MessageUtilities.displayInfoMessage(player, "All of your items have been repaired.");
  }
  
  @NoPermission
  public void noPermission(SubCommand subCommand, CommandSender sender, String[] args) {
    MessageUtilities.displayErrorMessage(sender, "You do not have permission to perform this command.");
  }
  
  @NotPlayer
  public void notPlayer(SubCommand subCommand, CommandSender sender, String[] args) {
    MessageUtilities.displayErrorMessage(sender, "You must be a player to perform this command.");
  }
  
}
