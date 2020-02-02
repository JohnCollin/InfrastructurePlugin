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

/**
 * The Infrastructure Player container class that contains more Infrastructrue Data.
 *
 * @since 0.1.0-ALPHA
 */
public class InfraPlayer {
  
  /**
   * The Bukkit API Player Object
   *
   * @since 0.1.0-ALPHA
   */
  private Player player;
  
  /**
   * The flag that determines if players can receive private messages
   *
   * @since 0.1.0-ALPHA
   */
  private boolean messageToggled;
  
  /**
   * InfraPlayer standard on-join constructor
   *
   * @param player The Bukkit API Player Object
   *
   * @since 0.1.0-ALPHA
   */
  public InfraPlayer(Player player) {
  
  }
  
}
