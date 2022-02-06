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
import org.bukkit.entity.Player;

public class QfLandHomeMgr extends QfManager {
   public void doInit(QfCore newCore) {
      this.configFileName = "config_landhomes.yml";
      this.mitems = new ArrayList<QfMItem>();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "landhome";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Current land homes:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Current " + cat + ChatColor.GOLD + " homes:";
   }

   public String listItems2(String cat) {
      if (this.mitems == null) {
         return ChatColor.GRAY + "No land homes.";
      } else {
         boolean found = false;
         String retVal = this.listItemHeader(cat);
         QfMItem mitem;
         Iterator<QfMItem> var6;
         if (cat == null) {
            for(var6 = this.mitems.iterator(); var6.hasNext(); found = true) {
               mitem = var6.next();
               retVal = retVal + mitem.NameNice() + ChatColor.GRAY + ", ";
            }

            if (found) {
               retVal = retVal.substring(0, retVal.length() - 2);
            }
         } else {
            var6 = this.mitems.iterator();

            while(var6.hasNext()) {
               mitem = var6.next();
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
      Set<String> keys = this.getConfig().getConfigurationSection("landhome").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " landhomes in config_landhomes.yml");
      String[] names = keys.toArray(new String[keys.size()]);
      String[] var9 = names;
      int var8 = names.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String name = var9[var7];
         QfLandHome landHome = new QfLandHome();
         landHome.mgr = this;
         landHome.doInit();
         landHome.siteName = name;
         landHome.name = name;
         String path = "landhome." + name + ".name";
         if (this.getConfig().contains(path)) {
            landHome.name = this.getConfig().getString(path);
         }

         path = "landhome." + name + ".loc";
         if (this.getConfig().contains(path)) {
            String[] args = this.getConfig().getString(path).split(" ");
            landHome.loc = this.config2Loc(args);
            landHome.worldName = args[0];
         }

         path = "landhome." + name + ".category";
         if (this.getConfig().contains(path)) {
            landHome.category = this.getConfig().getString(path);
         }

         path = "landhome." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            landHome.subcategory = this.getConfig().getString(path);
         }

         this.mitems.add(landHome);
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

   public QfLandHome findLandHome(String name) {
      Iterator<QfMItem> var3 = this.mitems.iterator();

      while(var3.hasNext()) {
         QfMItem mitem = (QfMItem)var3.next();
         if (mitem.name.equalsIgnoreCase(name)) {
            return (QfLandHome)mitem;
         }
      }

      return null;
   }

   public void goLandHome(Player pTarget, String name, boolean isShop) {
      String lhName = name;
      if (isShop) {
         lhName = name + "shop";
      }

      Iterator<QfMItem> var7 = this.mitems.iterator();

      while(var7.hasNext()) {
         QfMItem mitem = (QfMItem)var7.next();
         if (mitem.name.equalsIgnoreCase(lhName)) {
            QfLandHome landHome = (QfLandHome)mitem;
            if (landHome.subcategory.equalsIgnoreCase("shop")) {
               landHome.loc.setWorld(this.core.getServer().getWorld("world"));
            } else {
               landHome.loc.setWorld(this.core.getServer().getWorld("Survival"));
            }

            this.msgCaller(pTarget, ChatColor.GOLD + "Traveling to your " + ChatColor.RED + landHome.subcategory);
            pTarget.teleport(((QfLandHome)mitem).loc);
            return;
         }
      }

      this.msgCaller(pTarget, ChatColor.RED + "Cannot travel to " + ChatColor.GOLD + name + "'s " + (isShop ? "shop" : "") + ChatColor.RED + " area. Please ask a staff member to set your home");
   }

   public void delLandHome(Player pUser, String lhName) {
      String path = "landhome." + lhName;
      if (this.getConfig().contains(path)) {
         QfLandHome landHome = this.findLandHome(lhName);
         if (landHome != null) {
            this.mitems.remove(landHome);
         }

         this.getConfig().getConfigurationSection(path).getKeys(false).remove((Object)null);
         this.msgCaller(pUser, ChatColor.RED + "Deleted the home for " + ChatColor.GRAY + lhName);
      } else {
         this.msgCaller(pUser, ChatColor.RED + "The home for " + ChatColor.GRAY + lhName + ChatColor.RED + " does not exist");
      }

   }

   public void setShopHome(Player pUser, Player pTarget) {
      String inName = pTarget.getName() + "shop";
      Location loc;
      if (pUser == null) {
         loc = pTarget.getLocation();
      } else {
         loc = pUser.getLocation();
      }

      String worldName = loc.getWorld().getName();
      String name = this.niceName(inName);
      if (!loc.getWorld().getName().equalsIgnoreCase("world")) {
         this.msgCaller(pTarget, ChatColor.RED + "Shop homes are only permitted in the marketplace");
      } else {
         String cat = "shopkeeper";
         String land = "shop";
         String path = "landhome." + name;
         QfLandHome landHome;
         if (this.getConfig().contains(path)) {
            path = "landhome." + name + ".loc";
            this.getConfig().set(path, this.loc2config(worldName, loc));
            landHome = this.findLandHome(name);
            if (landHome != null) {
               landHome.siteName = name;
               landHome.name = inName;
               landHome.worldName = worldName;
               landHome.loc = loc;
               landHome.category = cat;
               landHome.subcategory = land;
            }

            this.saveConfig();
         } else {
            this.getConfig().createSection(path);
            path = "landhome." + name + ".name";
            this.getConfig().createSection(path);
            this.getConfig().set(path, inName);
            path = "landhome." + name + ".loc";
            this.getConfig().createSection(path);
            this.getConfig().set(path, this.loc2config(worldName, loc));
            path = "landhome." + name + ".category";
            this.getConfig().createSection(path);
            this.getConfig().set(path, cat);
            path = "landhome." + name + ".subcategory";
            this.getConfig().createSection(path);
            this.getConfig().set(path, land);
            landHome = new QfLandHome();
            landHome.mgr = this;
            landHome.doInit();
            landHome.siteName = name;
            landHome.name = inName;
            landHome.worldName = worldName;
            landHome.loc = loc;
            landHome.category = cat;
            landHome.subcategory = land;
            this.saveConfig();
            this.mitems.add(landHome);
         }

         this.msgCaller(pUser, ChatColor.BLUE + "New " + ChatColor.GOLD + land + ChatColor.BLUE + " home set here.");
         this.msgCaller(pTarget, ChatColor.BLUE + "Your " + ChatColor.GOLD + "marketplace shop's " + ChatColor.BLUE + "home has been set.");
      }
   }

   public void setLandHome(Player pUser, Player pTarget) {
      String inName = pTarget.getName();
      Location loc;
      if (pUser == null) {
         loc = pTarget.getLocation();
      } else {
         loc = pUser.getLocation();
      }

      String worldName = loc.getWorld().getName();
      String name = this.niceName(inName);
      if (!loc.getWorld().getName().equalsIgnoreCase("survival")) {
         this.msgCaller(pTarget, ChatColor.RED + "Land homes are only permitted in the survival world");
      } else {
         String cat;
         String land;
         if (pTarget.hasPermission("Qrpg.class.primary.nobility")) {
            cat = "nobility";
            land = "town";
         } else if (pTarget.hasPermission("Qrpg.class.primary.tradesman")) {
            cat = "tradesman";
            land = "land";
         } else if (pTarget.hasPermission("Qrpg.class.primary.clergy")) {
            cat = "clergy";
            land = "land";
         } else if (pTarget.hasPermission("Qrpg.class.primary.soldier")) {
            cat = "soldier";
            land = "land";
         } else {
            cat = "unknown";
            land = "land";
         }

         String path = "landhome." + name;
         QfLandHome landHome;
         if (this.getConfig().contains(path)) {
            path = "landhome." + name + ".loc";
            this.getConfig().set(path, this.loc2config(worldName, loc));
            landHome = this.findLandHome(name);
            if (landHome != null) {
               landHome.siteName = name;
               landHome.name = inName;
               landHome.worldName = worldName;
               landHome.loc = loc;
               landHome.category = cat;
               landHome.subcategory = land;
            }

            this.saveConfig();
         } else {
            this.getConfig().createSection(path);
            path = "landhome." + name + ".name";
            this.getConfig().createSection(path);
            this.getConfig().set(path, inName);
            path = "landhome." + name + ".loc";
            this.getConfig().createSection(path);
            this.getConfig().set(path, this.loc2config(worldName, loc));
            path = "landhome." + name + ".category";
            this.getConfig().createSection(path);
            this.getConfig().set(path, cat);
            path = "landhome." + name + ".subcategory";
            this.getConfig().createSection(path);
            this.getConfig().set(path, land);
            landHome = new QfLandHome();
            landHome.mgr = this;
            landHome.doInit();
            landHome.siteName = name;
            landHome.name = inName;
            landHome.worldName = worldName;
            landHome.loc = loc;
            landHome.category = cat;
            landHome.subcategory = land;
            this.saveConfig();
            this.mitems.add(landHome);
         }

         this.msgCaller(pUser, ChatColor.BLUE + "New " + ChatColor.GOLD + land + ChatColor.BLUE + " home set here.");
         this.msgCaller(pTarget, ChatColor.BLUE + "Your " + ChatColor.GOLD + land + "'s " + ChatColor.BLUE + "home has been set.");
      }
   }
}
