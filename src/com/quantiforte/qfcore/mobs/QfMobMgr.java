package com.quantiforte.qfcore.mobs;

import com.quantiforte.qfcore.QfCore;
import com.quantiforte.qfcore.QfManager;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class QfMobMgr extends QfManager implements CommandExecutor {
   public int msrWitherSkelly;
   public int msrGuardian;

   public void doInit(QfCore newCore) {
      this.configFileName = "config_mobs.yml";
      this.mitems = new ArrayList();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      super.doInit(newCore);
      this.msrWitherSkelly = 10;
      this.msrGuardian = 10;
      this.core.getLogger().info("mob init ====================================== mob control");
   }

   public String cmdPrefix() {
      return "mobcontrol";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      this.msgCaller(pUser, "mob called: " + args[0]);
      switch(cmdName.hashCode()) {
      case 108288:
         if (cmdName.equals("mob")) {
            if (args.length == 1 && "spawn".startsWith(args[0].toLowerCase())) {
               this.msgCaller(pUser, ChatColor.GOLD + "Wither Skelly spawn rate is " + ChatColor.BLUE + this.msrWitherSkelly + "% " + ChatColor.GOLD + "of vanilla");
               this.msgCaller(pUser, ChatColor.GOLD + "Guardian spawn rate now at " + ChatColor.BLUE + this.msrGuardian + "% " + ChatColor.GOLD + "of vanilla");
               return true;
            } else {
               if (args.length == 3 && "spawn".startsWith(args[0].toLowerCase())) {
                  if ("witherskelly".startsWith(args[1].toLowerCase())) {
                     try {
                        this.msrWitherSkelly = Integer.parseInt(args[2]);
                        this.msgCaller(pUser, ChatColor.GOLD + "Wither Skelly spawn rate is " + ChatColor.BLUE + this.msrWitherSkelly + "% " + ChatColor.GOLD + "of vanilla");
                     } catch (NumberFormatException var19) {
                        this.msgCaller(pUser, ChatColor.RED + "Could not read the spawn rate, please use 1-100");
                     }

                     return true;
                  }

                  if ("guardian".startsWith(args[1].toLowerCase())) {
                     try {
                        this.msrGuardian = Integer.parseInt(args[2]);
                        this.msgCaller(pUser, ChatColor.GOLD + "Guardian spawn rate now at " + ChatColor.BLUE + this.msrGuardian + "% " + ChatColor.GOLD + "of vanilla");
                     } catch (NumberFormatException var20) {
                        this.msgCaller(pUser, ChatColor.RED + "Could not read the spawn rate, please use 1-100");
                     }

                     return true;
                  }
               }

               return true;
            }
         }
      default:
         return false;
      }
   }

   public void HandleMobSpawn(CreatureSpawnEvent event) {
      LivingEntity entity = event.getEntity();
      int rnum;
      if (event.getEntityType() == EntityType.SKELETON && event.getLocation().getWorld().getName().equalsIgnoreCase("Survival_nether")) {
         rnum = (int)(Math.random() * 100.0D) + 1;
         if (rnum > this.msrWitherSkelly) {
            this.core.getLogger().info("WWWWWWWSKEL!! BLOCKED ======== " + rnum + " ===============");
            event.setCancelled(true);
            return;
         }

         this.core.getLogger().info("WWWWWWWSKEL!! ********** " + rnum + " ******** allowed");
      }

      if (event.getEntityType() == EntityType.GUARDIAN) {
         Guardian guardian = (Guardian)entity;
         if (!guardian.isElder()) {
            rnum = (int)(Math.random() * 100.0D) + 1;
            if (rnum > this.msrGuardian) {
               this.core.getLogger().info("Guardian!! BLOCKED ======== " + rnum + " ===============");
               event.setCancelled(true);
               return;
            }

            this.core.getLogger().info("Guardian!! ********** " + rnum + " ******** allowed");
         }
      }

   }

   public void readConfig() {
   }

   public void saveConfig() {
      try {
         this.getConfig().save(this.ourConfigFile);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }
}
