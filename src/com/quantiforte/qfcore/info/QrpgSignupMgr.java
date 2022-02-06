package com.quantiforte.qfcore.info;

import com.quantiforte.qfcore.QfCore;
import com.quantiforte.qfcore.QfGeneral;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QrpgSignupMgr extends QfGeneral implements CommandExecutor {
   public HashMap<String, String> suUuid;

   public void doInit(QfCore newCore) {
      this.suUuid = new HashMap<String, String>();
      super.doInit(newCore);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      this.qfcore.getLogger().info("signupmgr cmd");
      // decompiler artifact
      // String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         // playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      switch(cmdName.hashCode()) {
      case -902467304:
         if (cmdName.equals("signup")) {
            if (args.length == 0) {
               if (pUser == null) {
                  this.msgCaller(pUser, "Not sure which player to use");
                  return true;
               }

               this.doSignup(pUser, pUser);
               return true;
            } else {
               if (pUser == null || pUser.hasPermission("Qrpg.signup.admin")) {
                  if (args.length == 1) {
                     if ("list".startsWith(args[0])) {
                        this.listSignup(pUser);
                        return true;
                     }

                     if ("clear".startsWith(args[0])) {
                        this.clearAllSignup(pUser);
                        return true;
                     }

                     if ("spin".startsWith(args[0])) {
                        this.spinSignup(pUser);
                        return true;
                     }

                     pTarget = this.qfcore.getServer().getPlayer(args[0]);
                     if (pTarget == null) {
                        this.msgCaller(pUser, ChatColor.RED + "Not sure which player to use");
                        return true;
                     }

                     this.doSignup(pUser, pTarget);
                     return true;
                  }

                  if (args.length == 2) {
                     if ("remove".startsWith(args[0])) {
                        this.removeSignup(pUser, args[1]);
                        return true;
                     }

                     return false;
                  }
               }

               return false;
            }
         }
      default:
         return false;
      }
   }

   public boolean isSignedUp(Player pUser) {
      String key = pUser.getUniqueId().toString();
      return this.suUuid.containsKey(key);
   }

   public void doSignup(Player pUser, Player pTarget) {
      String suName = pTarget.getName();
      if (this.isSignedUp(pTarget)) {
         this.msgCaller(pUser, ChatColor.RED + "You are already on the signup list");
      } else {
         this.suUuid.put(pUser.getUniqueId().toString(), suName);
         this.msgCaller(pTarget, ChatColor.GREEN + "You are now signed up!");
      }
   }

   public void removeSignup(Player pUser, String playerName) {
	  // artifact
      // boolean isFirst = true;
      Iterator<String> it = this.suUuid.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         String listName = (String)this.suUuid.get(key);
         this.qfcore.getLogger().info("remove: " + listName + " ?= " + playerName);
         if (playerName.equalsIgnoreCase(listName)) {
            this.suUuid.remove(key);
            this.msgCaller(pUser, ChatColor.GREEN + "Plyaer " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " has been removed from the signup list");
            return;
         }
      }

      this.msgCaller(pUser, ChatColor.RED + "Could not locate the player " + ChatColor.YELLOW + playerName + ChatColor.RED + " on the signup list");
   }

   public void clearAllSignup(Player pUser) {
      this.suUuid.clear();
      this.msgCaller(pUser, ChatColor.GREEN + "The signup list has been " + ChatColor.YELLOW + "completely cleared");
   }

   public void listSignup(Player pUser) {
      String playerList = "";
      boolean isFirst = true;
      Iterator<String> it = this.suUuid.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         String playerName = (String)this.suUuid.get(key);
         if (isFirst) {
            isFirst = false;
            playerList = ChatColor.GOLD + playerName;
         } else {
            playerList = playerList + ChatColor.GRAY + ", " + ChatColor.GOLD + playerName;
         }
      }

      this.msgCaller(pUser, ChatColor.GREEN + "List of players that have signed-up: " + playerList);
   }

   public void spinSignup(Player pUser) {
      int rnum = (int)(Math.random() * (double)this.suUuid.size());
      this.qfcore.getLogger().info("spin: " + rnum + " / " + this.suUuid.size());
      Iterator<String> it = this.suUuid.keySet().iterator();
      int idx = 0;
      String playerName = "<<Error>>";

      while(it.hasNext()) {
         String key = (String)it.next();
         if (idx++ >= rnum) {
            playerName = (String)this.suUuid.get(key);
            break;
         }
      }

      this.msgCaller(pUser, ChatColor.DARK_GREEN + "Random Player: " + ChatColor.GREEN + playerName);
   }
}
