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

package org.cjohnson.infrastructure.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Infrastructure Item Utility Methods
 *
 * @since 0.1.0-ALPHA
 */
public abstract class ItemUtilities {
  
  /**
   * Set an item's durability to a desired amount
   *
   * @param itemStack The ItemStack to modify
   * @param damage The new amount of damage
   *
   * @return The modified ItemStack
   *
   * @since 0.1.0-ALPHA
   */
  public static ItemStack setItemDamage(ItemStack itemStack, short damage) {
    // Buffer for items that have no ItemMeta
    if(!itemStack.hasItemMeta()) {
      return itemStack;
    }
    
    // Get the ItemMeta from the ItemStack
    ItemMeta itemMeta = itemStack.getItemMeta();
    
    // Buffer for items that are unbreakable
    if(itemMeta.isUnbreakable()) {
      return itemStack;
    }
    
    // Cast the ItemMeta to a Damageable object
    Damageable damageable = (Damageable) itemMeta;
    
    // Set the damage amount
    damageable.setDamage(damage);
    
    // Recast back to ItemMeta
    itemMeta = (ItemMeta) damageable;
    
    // Set the ItemStack's ItemMeta
    itemStack.setItemMeta(itemMeta);
    
    return itemStack;
  }
  
}
