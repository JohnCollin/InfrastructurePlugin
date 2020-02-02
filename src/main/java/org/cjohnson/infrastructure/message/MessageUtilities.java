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
