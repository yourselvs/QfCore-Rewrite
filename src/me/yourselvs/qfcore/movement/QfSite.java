package me.yourselvs.qfcore.movement;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.yourselvs.qfcore.QfMItem;

public class QfSite extends QfMItem {
   public Location loc;
   public String siteName;
   public String worldName;

   public void doInit() {
      this.category = "build";
      this.subcategory = "build";
   }

   public String NameNice() {
      String var1;
      switch((var1 = this.category).hashCode()) {
      case -1600582850:
         if (var1.equals("survival")) {
            return ChatColor.RED + this.name;
         }
         break;
      case -1422950650:
         if (var1.equals("active")) {
            return ChatColor.YELLOW + this.name;
         }
         break;
      case 94094958:
         if (var1.equals("build")) {
            return ChatColor.GREEN + this.name;
         }
      }

      return ChatColor.AQUA + this.name;
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
         break;
      case 94094958:
         if (cat.equals("build")) {
            return ChatColor.DARK_GREEN;
         }
      }

      return ChatColor.GRAY;
   }
}
