package me.yourselvs.qfcore.workorder;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import me.yourselvs.qfcore.movement.QfSite;

public class QrpgWorkOrder extends QfSite {
   String active;
   boolean isActive;
   String descr;
   String coolId;
   String coolTimeStr;
   long coolTime;
   String[] reqMat;
   String[] reqDescr;
   String rewardStr;
   double rewardCash;
   String iconStr;
   ItemStack icon;

   public void doInit() {
      this.category = "unknown";
      this.subcategory = "open";
   }

   public String NameNice() {
      String var1;
      switch((var1 = this.category).hashCode()) {
      case -2030599806:
         if (var1.equals("soldier")) {
            return ChatColor.RED + this.name;
         }
         break;
      case -1357819320:
         if (var1.equals("clergy")) {
            return ChatColor.YELLOW + this.name;
         }
         break;
      case -284840886:
         if (var1.equals("unknown")) {
            return ChatColor.WHITE + this.name;
         }
         break;
      case 753830763:
         if (var1.equals("tradesman")) {
            return ChatColor.DARK_AQUA + this.name;
         }
         break;
      case 1070217866:
         if (var1.equals("nobility")) {
            return ChatColor.LIGHT_PURPLE + this.name;
         }
      }

      return ChatColor.GRAY + this.name;
   }

   public ChatColor CatColor(String cat) {
      switch(cat.hashCode()) {
      case -2030599806:
         if (cat.equals("soldier")) {
            return ChatColor.RED;
         }
         break;
      case -1357819320:
         if (cat.equals("clergy")) {
            return ChatColor.YELLOW;
         }
         break;
      case -284840886:
         if (cat.equals("unknown")) {
            return ChatColor.WHITE;
         }
         break;
      case 753830763:
         if (cat.equals("tradesman")) {
            return ChatColor.DARK_AQUA;
         }
         break;
      case 1070217866:
         if (cat.equals("nobility")) {
            return ChatColor.LIGHT_PURPLE;
         }
      }

      return ChatColor.GRAY;
   }
}
