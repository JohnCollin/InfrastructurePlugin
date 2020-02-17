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

package org.cjohnson.infrastructure.command.player.chat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cjohnson.infrastructure.api.InfrastructureAPI;
import org.cjohnson.infrastructure.command.annotation.*;
import org.cjohnson.infrastructure.message.MessageUtilities;
import org.cjohnson.infrastructure.player.InfraPlayer;

/**
 * The CommandMsgToggle command class is used as a command class for the
 * in-game /msgtoggle command.
 *
 * @since 0.1.0-ALPHA
 */
@Command(aliases = {"msgtoggle", "emsgtoggle"}, permission = "infrastructure.chat.msgtoggle")
public class CommandMsgToggle {
  
  public CommandMsgToggle() {}
  
  /**
   * The defaultMsgToggleCommand() method is the default command case for
   * the commmand.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @DefaultCommand
  public void defaultMsgToggleCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    // Get the BukkitAPI Player object that ran the command
    Player player = (Player) sender;
    
    // Get the Corresponding Player Wrapper (InfraPlayer) object
    // from the PlayerHandler.
    InfraPlayer infraPlayer = InfrastructureAPI.getInstance().getPlayerHandler().getPlayer(player);
    
    // Get the current toggle setting
    boolean currentMsgToggleSetting = infraPlayer.isMessageToggled();
    
    // Set it to the opposite of the toggle setting. (Hence Toggle)
    infraPlayer.setMessageToggled(!currentMsgToggleSetting);
  }
  
  /**
   * The onMsgToggleCommand() method is the case when the
   * argument [on] is used.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @SubCommand(aliases = {"on"}, permission = "infrastructure.chat.msgtoggle")
  public void onMsgToggleCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    // Check if player wants to change another player's msg toggle.
    // In this case there are 2 args, so we need to check if there's
    // more than one as opposed to more than 0.
    if(args.length > 1) {
      // Get the Player Wrapper (InfraPlayer) object
      // from the Player Name.
      InfraPlayer infraPlayer = InfrastructureAPI.getInstance().getPlayerHandler().getPlayer(args[1]);
      
      infraPlayer.setMessageToggled(true);
      
      return;
    }
    
    // Get the BukkitAPI Player object that ran the command
    Player player = (Player) sender;
    
    // Get the Corresponding Player Wrapper (InfraPlayer) object
    // from the PlayerHandler.
    InfraPlayer infraPlayer = InfrastructureAPI.getInstance().getPlayerHandler().getPlayer(player);
    
    // Set it to the opposite of the toggle setting. (Hence Toggle)
    infraPlayer.setMessageToggled(true);
  }
  
  /**
   * The offMsgToggleCommand() method is the case when the
   * argument [off] is used.
   *
   * @param subCommand The subcommand annotation passed by the CommandFramework
   * @param sender The CommandSender passed by the Bukkit API
   * @param args The command arguments passed by the Bukkit API
   *
   * @since 0.1.0-ALPHA
   */
  @SubCommand(aliases = {"off"}, permission = "infrastructure.chat.msgtoggle")
  public void offMsgToggleCommand(SubCommand subCommand, CommandSender sender, String[] args) {
    // Check if player wants to change another player's msg toggle.
    // In this case there are 2 args, so we need to check if there's
    // more than one as opposed to more than 0.
    if(args.length > 1) {
      // Get the Player Wrapper (InfraPlayer) object
      // from the Player Name.
      InfraPlayer infraPlayer = InfrastructureAPI.getInstance().getPlayerHandler().getPlayer(args[1]);
    
      infraPlayer.setMessageToggled(false);
    
      return;
    }
    
    // Get the BukkitAPI Player object that ran the command
    Player player = (Player) sender;
    
    // Get the Corresponding Player Wrapper (InfraPlayer) object
    // from the PlayerHandler.
    InfraPlayer infraPlayer = InfrastructureAPI.getInstance().getPlayerHandler().getPlayer(player);
    
    // Set it to the opposite of the toggle setting. (Hence Toggle)
    infraPlayer.setMessageToggled(false);
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