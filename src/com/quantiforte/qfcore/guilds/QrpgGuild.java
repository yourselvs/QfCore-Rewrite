package com.quantiforte.qfcore.guilds;

import com.quantiforte.qfcore.QfMItem;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class QrpgGuild extends QfMItem {
   public Location houseLoc;
   public String guildName;
   public String color;
   public double balance;
   public int maxMembers;
   public String worldName;
   public String motd;
   public List<String> memberList;
   public List<String> inviteList;
   public List<String> requestList;

   public void doInit() {
      this.category = "general";
      this.subcategory = "general";
      this.maxMembers = 15;
   }

   public String niceBalance() {
      String bal = "$" + this.balance;
      if (bal.endsWith(".0")) {
         bal.substring(0, bal.length() - 2);
      }

      return "$" + this.balance;
   }

   public String NameNice() {
      String var1;
      switch((var1 = this.category).hashCode()) {
      case -1600582850:
         if (var1.equals("survival")) {
            return ChatColor.RED + this.guildName;
         }
         break;
      case -1422950650:
         if (var1.equals("active")) {
            return ChatColor.YELLOW + this.guildName;
         }
         break;
      case 94094958:
         if (var1.equals("build")) {
            return ChatColor.GREEN + this.guildName;
         }
      }

      return ChatColor.GOLD + this.guildName;
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
            return ChatColor.GOLD;
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
