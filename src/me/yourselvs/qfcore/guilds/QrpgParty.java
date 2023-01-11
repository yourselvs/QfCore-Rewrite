package me.yourselvs.qfcore.guilds;

import java.util.List;
import org.bukkit.ChatColor;

import me.yourselvs.qfcore.QfMItem;

public class QrpgParty extends QfMItem {
   public String partyName;
   public int maxMembers;
   public boolean allowPvp;
   public List<String> memberList;
   public List<String> inviteList;
   public List<String> requestList;

   public void doInit() {
      this.category = "general";
      this.subcategory = "general";
      this.maxMembers = 6;
      this.allowPvp = false;
   }

   public String NameNice() {
      String var1;
      switch((var1 = this.category).hashCode()) {
      case -1600582850:
         if (var1.equals("survival")) {
            return ChatColor.RED + this.partyName;
         }
         break;
      case -1422950650:
         if (var1.equals("active")) {
            return ChatColor.YELLOW + this.partyName;
         }
         break;
      case 94094958:
         if (var1.equals("build")) {
            return ChatColor.GREEN + this.partyName;
         }
      }

      return ChatColor.GREEN + this.partyName;
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
            return ChatColor.GREEN;
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
