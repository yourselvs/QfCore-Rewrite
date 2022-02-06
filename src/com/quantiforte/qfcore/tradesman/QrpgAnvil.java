package com.quantiforte.qfcore.tradesman;

import com.quantiforte.qfcore.QfGeneral;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.AnvilInventory;

public abstract class QrpgAnvil extends QfGeneral implements AnvilInventory {
   @EventHandler(
      priority = EventPriority.LOW,
      ignoreCancelled = false
   )
   public void onInventoryClose(InventoryCloseEvent event) {
      this.msgCaller((Player)null, "inv close");
   }
}
