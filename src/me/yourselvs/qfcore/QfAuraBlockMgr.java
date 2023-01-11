package me.yourselvs.qfcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QfAuraBlockMgr extends QfManager {
   public void doInit(QfCore newCore) {
      this.configFileName = "config_aurablocks.yml";
      this.mitems = new ArrayList<QfMItem>();
      this.hasLocationTriggers = true;
      this.hasDynLocationTriggers = false;
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "aurablock";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Current aurablocks:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Current " + ChatColor.YELLOW + cat + ChatColor.GOLD + " aurablocks:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  // decompiler artifacts?
      // Player pTarget = null;
      Player pUser = null;
      // int nextarg = false;
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
      }

      // String cmdName = cmd.getName().toLowerCase();
      String dispStr;
      if (args.length == 0) {
         dispStr = ChatColor.GOLD + "qf aurablock running";
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
      this.triggerLocs.clear();
      Set<String> keys = this.getConfig().getConfigurationSection("aurablock").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " aurablocks in config file");
      String[] names = keys.toArray(new String[keys.size()]);
      String[] var10 = names;
      int var9 = names.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String name = var10[var8];
         QfAuraBlock auraBlock = new QfAuraBlock();
         auraBlock.mgr = this;
         auraBlock.doInit();
         auraBlock.name = name;
         String path = "aurablock." + name + ".active";
         if (this.getConfig().contains(path)) {
            auraBlock.active = this.getConfig().getString(path);
         }

         path = "aurablock." + name + ".loc";
         if (this.getConfig().contains(path)) {
            auraBlock.setLocation(this.getConfig().getString(path));
         }

         path = "aurablock." + name + ".category";
         if (this.getConfig().contains(path)) {
            auraBlock.category = this.getConfig().getString(path);
         }

         path = "aurablock." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            auraBlock.subcategory = this.getConfig().getString(path);
         }

         path = "aurablock." + name + ".auras";
         if (this.getConfig().contains(path)) {
            List<String> strCons = this.getConfig().getStringList(path);
            Iterator<String> var12 = strCons.iterator();

            while(var12.hasNext()) {
               String strCon = (String)var12.next();
               if (!auraBlock.addAuraConfig(strCon)) {
                  this.core.getLogger().info("problem processing aurablock " + name + ", aura: " + strCon);
               }
            }
         }

         this.mitems.add(auraBlock);
         this.addTriggerLoc(auraBlock);
      }

   }
}
