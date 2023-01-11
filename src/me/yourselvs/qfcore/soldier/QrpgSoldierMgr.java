package me.yourselvs.qfcore.soldier;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.yourselvs.qfcore.QfGeneral;

public class QrpgSoldierMgr extends QfGeneral implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
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
      case 94103840:
         if (cmdName.equals("burst")) {
            if (args.length == 0) {
               if (pUser == null) {
                  this.msgCaller(pUser, "Not sure which player to use");
                  return true;
               }

               this.doBurst(pUser, pUser);
               return true;
            }

            if (args.length == 1 && (pUser == null || pUser.hasPermission("Qrpg.leap.others"))) {
               pTarget = this.qfcore.getServer().getPlayer(args[0]);
               if (pTarget == null) {
                  this.msgCaller(pUser, "Not sure which player to use");
                  return true;
               }

               this.doBurst(pUser, pTarget);
               return true;
            }

            return false;
         }
         break;
      case 99457571:
         if (cmdName.equals("hoist")) {
            if (args.length == 0) {
               if (pUser == null) {
                  this.msgCaller(pUser, "Not sure which player to use");
                  return true;
               }

               this.doHoist(pUser, pUser);
               return true;
            }

            if (args.length == 1 && (pUser == null || pUser.hasPermission("Qrpg.hoist.others"))) {
               pTarget = this.qfcore.getServer().getPlayer(args[0]);
               if (pTarget == null) {
                  this.msgCaller(pUser, "Not sure which player to use");
                  return true;
               }

               this.doHoist(pUser, pTarget);
               return true;
            }

            return false;
         }
      }

      return false;
   }

   public void doBurst(Player pUser, Player pTarget) {
      String coolId = "soldier_burst";
      long coolTime = 25L;
      String coolTimeLeft = this.qfcore.cooldownMgr.checkCooldown(pUser, coolId, false);
      if (coolTimeLeft == null) {
         int ticks = 15;
         if (pTarget.hasPermission("Qrpg.soldier.commander")) {
            ticks += 10;
         }

         PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, ticks, 2);
         pTarget.addPotionEffect(pe);
         this.msgCaller(pUser, ChatColor.LIGHT_PURPLE + "You have a " + ChatColor.AQUA + "burst of speed" + ChatColor.LIGHT_PURPLE + " for a brief moment");
         this.qfcore.cooldownMgr.addCooldown(pUser, coolId, coolTime);
      } else {
         this.msgCaller(pUser, ChatColor.RED + "You cannot burst for another " + coolTimeLeft);
      }
   }

   public void doHoist(Player pUser, Player pTarget) {
      PotionEffect pe = new PotionEffect(PotionEffectType.JUMP, 20, 2);
      pTarget.addPotionEffect(pe);
      if (pUser != null) {
         this.msgCaller(pUser, ChatColor.LIGHT_PURPLE + "You can " + ChatColor.AQUA + "hoist " + ChatColor.LIGHT_PURPLE + "yourself for a brief moment");
      } else {
         this.msgCaller(pUser, ChatColor.AQUA + pTarget.getName() + " hoists " + ChatColor.LIGHT_PURPLE + " themselves for a brief moment");
      }

   }
}
