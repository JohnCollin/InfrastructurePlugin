package org.cjohnson.infrastructure.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemUtilities {
  
  public static ItemStack setItemDamage(ItemStack itemStack, short damage) {
    if(!itemStack.hasItemMeta()) {
      return itemStack;
    }
    
    ItemMeta itemMeta = itemStack.getItemMeta();
    
    if(itemMeta.isUnbreakable()) {
      return itemStack;
    }
    
    Damageable damageable = (Damageable) itemMeta;
    
    damageable.setDamage(damage);
    
    itemMeta = (ItemMeta) damageable;
    
    itemStack.setItemMeta(itemMeta);
    
    return itemStack;
  }
  
}
