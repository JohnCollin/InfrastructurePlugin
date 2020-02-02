package org.cjohnson.infrastructure.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class MessageUtilities {
  
  public static final char colorEncodingChar = '&';
  
  public static final String universalPrefix = "&3&lInfrastructure &8» ";
  
  public static final String infoColorModifier = "&7";
  public static final String warningColorModifier = "&6";
  public static final String errorColorModifier = "&c";
  
  public static void displayInfoMessage(CommandSender targetUser, String messageRaw) {
    targetUser.sendMessage(getEncodedMessage(universalPrefix + infoColorModifier + messageRaw));
  }
  
  public static void displayWarningMessage(CommandSender targetUser, String messageRaw) {
    targetUser.sendMessage(getEncodedMessage(universalPrefix + warningColorModifier + messageRaw));
  }
  
  public static void displayErrorMessage(CommandSender targetUser, String messageRaw) {
    targetUser.sendMessage(getEncodedMessage(universalPrefix + errorColorModifier + messageRaw));
  }
  
  private static String getEncodedMessage(String message) {
    return ChatColor.translateAlternateColorCodes(colorEncodingChar, message);
  }
  
}
