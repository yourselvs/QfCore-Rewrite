package me.yourselvs.qfcore.inventory;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.yourselvs.qfcore.QfGeneral;

public class QfBuildInvMgr extends QfGeneral implements CommandExecutor, TabCompleter {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  // decompiler artifact
      // Player pTarget = null;
      Player pUser = null;
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
      } else {
         pUser = null;
         // pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      if (cmdName.equalsIgnoreCase("binv")) {
         if (isPlayer) {
            this.DoBuilderInventory(pUser, args);
            return true;
         } else {
            this.msgCaller(pUser, "Cannot access this command from console");
            return true;
         }
      } else {
         return false;
      }
   }

   public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
	  // decompiler artifact
	  // Player pTarget = null;
      // Player pUser = null;
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         // pUser = (Player)sender;
         String var8 = cmd.getName().toLowerCase();
         if (!var8.equalsIgnoreCase("aura")) {
            return null;
         } else {
            List<String> tabOut = new ArrayList<String>();
            if (args.length == 0) {
               tabOut.add("binv");
               tabOut.add("bkit");
               return tabOut;
            } else if (args.length == 1) {
               String partial = ChatColor.stripColor(args[0]).toLowerCase();
               String txt = "binv";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "bkit";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               return tabOut;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public void DoBuilderInventory(Player pUser, String[] args) {
      this.msgCaller(pUser, ChatColor.RED + "Builder Inventory coming soon...");
   }
}
