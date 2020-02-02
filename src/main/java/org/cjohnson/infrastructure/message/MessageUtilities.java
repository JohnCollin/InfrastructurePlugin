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

/**
 * Infrastructure Message Utilties
 *
 * @since 0.1.0-ALPHA
 */
public abstract class MessageUtilities {
  
  /**
   * The Color Encoding Character that Infrastructure uses
   *
   * @since 0.1.0-ALPHA
   */
  public static final char colorEncodingChar = '&';
  
  /**
   * The universal prefix for plugin messages
   *
   * @since 0.1.0-ALPHA
   */
  public static final String universalPrefix = "&3&lInfrastructure &8» ";
  
  /**
   * The color modifier for info messages.
   *
   * @since 0.1.0-ALPHA
   */
  public static final String infoColorModifier = "&7";
  /**
   * The color modifier for warning messages.
   *
   * @since 0.1.0-ALPHA
   */
  public static final String warningColorModifier = "&6";
  /**
   * The color modifier for error messages.
   *
   * @since 0.1.0-ALPHA
   */
  public static final String errorColorModifier = "&c";
  
  /**
   * The method that displays an info message to a target player
   *
   * @param targetUser The user that receives the message
   * @param messageRaw The raw message to be sent
   *
   * @since 0.1.0-ALPHA
   */
  public static void displayInfoMessage(CommandSender targetUser, String messageRaw) {
    targetUser.sendMessage(getEncodedMessage(universalPrefix + infoColorModifier + messageRaw));
  }
  
  /**
   * The method that displays an info message to a target player
   *
   * @param targetUser The user that receives the message
   * @param messageRaw The raw message to be sent
   *
   * @since 0.1.0-ALPHA
   */
  public static void displayWarningMessage(CommandSender targetUser, String messageRaw) {
    targetUser.sendMessage(getEncodedMessage(universalPrefix + warningColorModifier + messageRaw));
  }
  
  /**
   * The method that displays an info message to a target player
   *
   * @param targetUser The user that receives the message
   * @param messageRaw The raw message to be sent
   *
   * @since 0.1.0-ALPHA
   */
  public static void displayErrorMessage(CommandSender targetUser, String messageRaw) {
    targetUser.sendMessage(getEncodedMessage(universalPrefix + errorColorModifier + messageRaw));
  }
  
  /**
   * The method takes a raw string and adds Color Encoding
   *
   * @return The Color Encoded Message
   *
   * @since 0.1.0-ALPHA
   */
  private static String getEncodedMessage(String message) {
    return ChatColor.translateAlternateColorCodes(colorEncodingChar, message);
  }
  
}
