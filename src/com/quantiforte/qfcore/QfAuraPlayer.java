package com.quantiforte.qfcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QfAuraPlayer extends QfMItem {
   public List auras;

   public void doInit() {
      this.auras = new ArrayList();
      this.triggerIsDynamic = true;
      this.initLocation();
   }

   public String NameNice() {
      return ChatColor.YELLOW + this.name;
   }

   public void recalcTriggerLoc() {
      if (this.triggerLoc != null) {
         this.triggerLoc.trigLoc = this.mPlayer.getLocation();
         this.triggerLoc.trigRadius = 10.0D;
      }
   }

   public void doTriggerAction(Player pTarget, Double distanceFromPlayer) {
      Iterator var4 = this.auras.iterator();

      while(var4.hasNext()) {
         QfAura aura = (QfAura)var4.next();
         aura.applyAuraToPlayer(pTarget, distanceFromPlayer);
      }

   }
}
