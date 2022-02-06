package com.quantiforte.qfcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QfAuraBlock extends QfMItem {
   public String loc;
   public List<QfAura> auras;

   public void doInit() {
      this.auras = new ArrayList<QfAura>();
      this.category = "general";
      this.subcategory = "general";
   }

   public boolean addAuraConfig(String strCon) {
      QfAura aura = (QfAura)this.mgr.core.auraMgr.getMItemByName(strCon);
      if (aura != null) {
         this.auras.add(aura);
         return true;
      } else {
         return false;
      }
   }

   public String NameNice() {
      return ChatColor.YELLOW + this.name + " " + this.CatColor(this.subcategory) + "(" + this.subcategory + ") - " + ChatColor.GRAY + this.category + " @ " + this.triggerLoc.worldName + " " + this.triggerLoc.trigLoc.getX() + "/" + this.triggerLoc.trigLoc.getZ() + "/" + this.triggerLoc.trigLoc.getY() + " radius: " + this.triggerLoc.trigRadius;
   }

   public ChatColor CatColor(String cat) {
      switch(cat.hashCode()) {
      case -1600582850:
         if (cat.equals("survival")) {
            return ChatColor.WHITE;
         }
         break;
      case -1221262756:
         if (cat.equals("health")) {
            return ChatColor.LIGHT_PURPLE;
         }
         break;
      case -80148248:
         if (cat.equals("general")) {
            return ChatColor.WHITE;
         }
         break;
      case 97285:
         if (cat.equals("bad")) {
            return ChatColor.DARK_RED;
         }
         break;
      case 101759:
         if (cat.equals("fun")) {
            return ChatColor.GOLD;
         }
         break;
      case 3029869:
         if (cat.equals("boss")) {
            return ChatColor.DARK_PURPLE;
         }
         break;
      case 3178685:
         if (cat.equals("good")) {
            return ChatColor.DARK_GREEN;
         }
         break;
      case 3556498:
         if (cat.equals("test")) {
            return ChatColor.AQUA;
         }
      }

      return ChatColor.GRAY;
   }

   public void doTriggerAction(Player pTarget, Double distanceFromPlayer) {
      Iterator<QfAura> var4 = this.auras.iterator();

      while(var4.hasNext()) {
         QfAura aura = (QfAura)var4.next();
         aura.applyAuraToPlayer(pTarget, distanceFromPlayer);
      }

   }
}
