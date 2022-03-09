package com.quantiforte.qfcore.movement;

import com.quantiforte.qfcore.QfCore;
import com.quantiforte.qfcore.QfMItem;
import com.quantiforte.qfcore.QfManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QfSiteMgr extends QfManager implements CommandExecutor {
   public void doInit(QfCore newCore) {
      this.configFileName = "config_sites.yml";
      this.mitems = new ArrayList<QfMItem>();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      super.doInit(newCore);
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Current sites:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Current " + ChatColor.YELLOW + cat + ChatColor.GOLD + " sites:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      // decompiler artifact
      // String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         pTarget = pUser;
         // playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      switch(cmdName.hashCode()) {
      case 3530567:
         if (cmdName.equals("site")) {
            String dispStr;
            if (args.length >= 1 && "list".startsWith(args[0])) {
               String cat;
               if (args.length == 2) {
                  cat = args[1];
               } else {
                  cat = null;
               }

               dispStr = this.listItems2(cat);
               this.msgCaller(pUser, dispStr);
               return true;
            }

            if (args.length == 0) {
               dispStr = this.listItems2((String)null);
               this.msgCaller(pUser, dispStr);
               return true;
            }

            this.goSite(pTarget, args[0]);
            return true;
         }
         break;
      case 1550869970:
         if (cmdName.equals("delsite")) {
            this.delSite(pTarget, args[0]);
            return true;
         }
         break;
      case 1985911465:
         if (cmdName.equals("setsite")) {
            this.setSite(pTarget, args);
            return true;
         }
      }

      return false;
   }

   public String listItems2(String cat) {
      if (this.mitems == null) {
         return ChatColor.GRAY + "No Items.";
      } else {
         boolean found = false;
         String retVal = this.listItemHeader(cat);
         QfMItem mitem;
         Iterator<QfMItem> var6;
         if (cat == null) {
            for(var6 = this.mitems.iterator(); var6.hasNext(); found = true) {
               mitem = (QfMItem)var6.next();
               retVal = retVal + mitem.NameNice() + ChatColor.GRAY + ", ";
            }

            if (found) {
               retVal = retVal.substring(0, retVal.length() - 2);
            }
         } else {
            var6 = this.mitems.iterator();

            while(var6.hasNext()) {
               mitem = (QfMItem)var6.next();
               if (!found) {
                  retVal = retVal + mitem.CatColor(cat);
                  found = true;
               }

               String itemName = mitem.NameCat(cat);
               if (itemName != null) {
                  retVal = retVal + " " + itemName;
               }
            }
         }

         if (!found) {
            retVal = retVal + "\n" + ChatColor.RED + "NONE.";
         }

         return retVal;
      }
   }

   public void readConfig() {
      this.mitems.clear();
      this.triggerLocs.clear();
      Set<String> keys = this.getConfig().getConfigurationSection("site").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " build sites sites in config_sites.yml");
      String[] names = (String[])keys.toArray(new String[keys.size()]);
      String[] var9 = names;
      int var8 = names.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String name = var9[var7];
         QfSite site = new QfSite();
         site.mgr = this;
         site.doInit();
         site.siteName = name;
         site.name = name;
         String path = "site." + name + ".name";
         if (this.getConfig().contains(path)) {
            site.name = this.getConfig().getString(path);
         }

         path = "site." + name + ".loc";
         if (this.getConfig().contains(path)) {
            String[] args = this.getConfig().getString(path).split(" ");
            site.loc = this.config2Loc(args);
            site.worldName = args[0];
         }

         path = "site." + name + ".category";
         if (this.getConfig().contains(path)) {
            site.category = this.getConfig().getString(path);
         }

         path = "site." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            site.subcategory = this.getConfig().getString(path);
         }

         this.mitems.add(site);
      }

   }

   public void saveConfig() {
      try {
         this.getConfig().save(this.ourConfigFile);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public Location config2Loc(String[] args) {
      if (args.length != 6) {
         this.core.getLogger().info("improper num args: " + args.length);
         return null;
      } else {
         Location newLoc = new Location((World)null, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
         return newLoc;
      }
   }

   public String loc2config(String worldName, Location loc) {
      String retVal = worldName + " " + Double.toString(loc.getX());
      retVal = retVal + " " + Double.toString(loc.getY());
      retVal = retVal + " " + Double.toString(loc.getZ());
      retVal = retVal + " " + Float.toString(loc.getYaw());
      retVal = retVal + " " + Float.toString(loc.getPitch());
      return retVal;
   }

   public String niceName(String origName) {
      return origName.replace("'", "").replace(" ", "");
   }

   public QfSite findSite(String name) {
      Iterator<QfMItem> var3 = this.mitems.iterator();

      while(var3.hasNext()) {
         QfMItem mitem = (QfMItem)var3.next();
         if (mitem.name.equalsIgnoreCase(name)) {
            return (QfSite)mitem;
         }
      }

      return null;
   }

   public void goSite(Player pTarget, String name) {
      Iterator<QfMItem> var5 = this.mitems.iterator();

      while(var5.hasNext()) {
         QfMItem mitem = (QfMItem)var5.next();
         if (mitem.name.equalsIgnoreCase(name)) {
            QfSite site = (QfSite)mitem;
            World world = this.core.getServer().getWorld(site.worldName);
            site.loc.setWorld(world);
            if(world != null) {
               this.msgCaller(pTarget, ChatColor.GOLD + (pTarget.hasPermission("QfCore.builder") ? "Going to build site " : "Traveling to ") + ChatColor.GREEN + name);
               pTarget.teleport(((QfSite)mitem).loc);
            }
            return;
         }
      }

      this.msgCaller(pTarget, ChatColor.RED + "The build site " + ChatColor.GRAY + name + ChatColor.RED + " is invalid or does not exist");
   }

   public void delSite(Player pTarget, String inName) {
      Location loc = pTarget.getLocation();
      // decompiler artifact
      // String worldName = loc.getWorld().getName();
      String name = this.niceName(inName);
      if (loc.getWorld().getName().equalsIgnoreCase("survival")) {
         this.msgCaller(pTarget, ChatColor.RED + "Building Sites are not permitted in the survival world");
      } else {
         String path = "site." + name;
         if (this.getConfig().contains(path)) {
            QfSite site = this.findSite(name);
            if (site != null) {
               this.mitems.remove(site);
            }

            this.getConfig().getConfigurationSection(path).getKeys(false).remove((Object)null);
            this.msgCaller(pTarget, ChatColor.RED + "Deleted the build site");
         } else {
            this.msgCaller(pTarget, ChatColor.RED + "That build site does not exist");
         }

      }
   }

   public void setSite(Player pTarget, String[] args) {
      String inName = args[0];
      Location loc = pTarget.getLocation();
      String worldName = loc.getWorld().getName();
      String name = this.niceName(inName);
      if (loc.getWorld().getName().equalsIgnoreCase("survival")) {
         this.msgCaller(pTarget, ChatColor.RED + "Building Sites are not permitted in the survival world");
      } else {
         String cat;
         if (args.length > 1) {
            cat = args[1];
         } else {
            cat = "build";
         }

         String path = "site." + name;
         QfSite site;
         if (this.getConfig().contains(path)) {
            path = "site." + name + ".loc";
            this.getConfig().set(path, this.loc2config(worldName, loc));
            site = this.findSite(name);
            if (site != null) {
               site.siteName = name;
               site.name = inName;
               site.worldName = worldName;
               site.loc = loc;
               site.category = cat;
               site.subcategory = "build";
            }

            this.saveConfig();
         } else {
            this.getConfig().createSection(path);
            path = "site." + name + ".name";
            this.getConfig().createSection(path);
            this.getConfig().set(path, inName);
            path = "site." + name + ".loc";
            this.getConfig().createSection(path);
            this.getConfig().set(path, this.loc2config(worldName, loc));
            site = new QfSite();
            site.mgr = this;
            site.doInit();
            site.siteName = name;
            site.name = inName;
            site.worldName = worldName;
            site.loc = loc;
            site.category = cat;
            site.subcategory = "build";
            this.saveConfig();
            this.mitems.add(site);
         }

         this.msgCaller(pTarget, ChatColor.GOLD + "New build site set here for: " + ChatColor.GREEN + "/site " + inName);
      }
   }
}
