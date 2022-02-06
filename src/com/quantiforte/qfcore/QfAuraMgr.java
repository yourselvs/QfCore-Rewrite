package com.quantiforte.qfcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class QfAuraMgr extends QfManager {
   public void doInit(QfCore newCore) {
      this.configFileName = "config_auras.yml";
      this.mitems = new ArrayList();
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "aura";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Available auras:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Available " + ChatColor.YELLOW + cat + ChatColor.GOLD + " auras:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      // decompiler artifact?
      // int nextarg = false;
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
      }

      String cmdName = cmd.getName().toLowerCase();
      String dispStr;
      if (args.length == 0) {
         dispStr = ChatColor.GOLD + "qf aura running";
         this.msgCaller(pUser, dispStr);
         return true;
      } else {
         String var31;
         switch((var31 = args[0]).hashCode()) {
         case 108:
            if (!var31.equals("l")) {
               return false;
            }
            break;
         case 3463:
            if (!var31.equals("ls")) {
               return false;
            }
            break;
         case 3322014:
            if (!var31.equals("list")) {
               return false;
            }
            break;
         default:
            return false;
         }

         String cat;
         if (args.length == 2) {
            cat = args[1];
         } else {
            cat = null;
         }

         dispStr = this.listItems(cat);
         this.msgCaller(pUser, dispStr);
         return true;
      }
   }

   public void readConfig() {
      this.mitems.clear();
      Set keys = this.getConfig().getConfigurationSection("aura").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " auras in config file");
      String[] names = (String[])keys.toArray(new String[keys.size()]);
      String[] var10 = names;
      int var9 = names.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String name = var10[var8];
         QfAura aura = new QfAura();
         aura.mgr = this;
         aura.doInit();
         aura.name = name;
         String path = "aura." + name + ".active";
         if (this.getConfig().contains(path)) {
            aura.active = this.getConfig().getString(path);
         }

         path = "aura." + name + ".self";
         if (this.getConfig().contains(path)) {
            aura.self = this.getConfig().getString(path);
         }

         path = "aura." + name + ".category";
         if (this.getConfig().contains(path)) {
            aura.category = this.getConfig().getString(path);
         }

         path = "aura." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            aura.subcategory = this.getConfig().getString(path);
         }

         path = "aura." + name + ".sneak";
         if (this.getConfig().contains(path)) {
            aura.triggerLoc.hasSpecial = aura.triggerLoc.specialSneak = this.getConfig().getString(path) == "sneak";
         }

         path = "aura." + name + ".effects";
         if (this.getConfig().contains(path)) {
            List strCons = this.getConfig().getStringList(path);
            Iterator var12 = strCons.iterator();

            while(var12.hasNext()) {
               String strCon = (String)var12.next();
               if (!aura.addEffectConfig(strCon)) {
                  this.core.getLogger().info("problem processing aura " + name + ", effect: " + strCon);
               }
            }
         }

         this.mitems.add(aura);
      }

   }
}
