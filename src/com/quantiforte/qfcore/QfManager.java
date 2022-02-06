package com.quantiforte.qfcore;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class QfManager extends QfGeneral {
   public QfCore core;
   protected String configFileName;
   private FileConfiguration ourConfig = null;
   protected File ourConfigFile = null;
   protected List mitems;
   public boolean hasLocationTriggers = false;
   public boolean hasDynLocationTriggers = false;
   protected List triggerLocs;

   public void loadMgr(QfCore newCore) {
      this.doInit(newCore);
      this.saveDefaultConfig();
      this.readConfig();
   }

   public void doInit(QfCore newCore) {
      this.qfcore = newCore;
      this.core = newCore;
      if (this.triggerLocs == null) {
         this.triggerLocs = new ArrayList();
      }

   }

   public String cmdPrefix() {
      return null;
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      return false;
   }

   public void readConfig() {
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GRAY + "Available items:" : ChatColor.GRAY + "Available " + cat + " items:";
   }

   public String listItems(String cat) {
      if (this.mitems == null) {
         return ChatColor.GRAY + "No Items.";
      } else {
         boolean found = false;
         String retVal = this.listItemHeader(cat);
         QfMItem mitem;
         Iterator var6;
         if (cat == null) {
            for(var6 = this.mitems.iterator(); var6.hasNext(); found = true) {
               mitem = (QfMItem)var6.next();
               retVal = retVal + mitem.NameNice() + "\n";
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
                  retVal = retVal + "\n" + itemName;
               }
            }
         }

         if (!found) {
            retVal = retVal + "\n" + ChatColor.RED + "NONE.";
         }

         return retVal;
      }
   }

   public void reloadConfig() {
      if (this.configFileName != null) {
         if (this.ourConfigFile == null) {
            this.ourConfigFile = new File(this.core.getDataFolder(), this.configFileName);
         }

         this.core.getLogger().info("reloading: " + this.configFileName);
         this.ourConfig = YamlConfiguration.loadConfiguration(this.ourConfigFile);

         try {
            Reader reader = new InputStreamReader(this.core.getResource(this.configFileName), "UTF-8");
            if (reader != null) {
               YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);
               this.ourConfig.setDefaults(defaultConfig);
            }
         } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
         }

      }
   }

   public FileConfiguration getConfig() {
      if (this.ourConfig == null) {
         this.reloadConfig();
      }

      return this.ourConfig;
   }

   public void saveConfig() {
      if (this.configFileName != null) {
         if (this.ourConfig != null && this.ourConfigFile != null) {
            try {
               this.getConfig().save(this.configFileName);
            } catch (IOException var2) {
               this.core.getLogger().info("Error saving config file '" + this.configFileName + "': <" + var2.getMessage() + ">");
            }

         }
      }
   }

   public void saveDefaultConfig() {
      if (this.configFileName != null) {
         if (this.ourConfigFile == null) {
            this.ourConfigFile = new File(this.core.getDataFolder(), this.configFileName);
         }

         if (!this.ourConfigFile.exists()) {
            this.core.saveResource(this.configFileName, false);
         }

      }
   }

   public void deleteConfig() {
      if (this.configFileName != null) {
         if (this.ourConfig != null && this.ourConfigFile != null) {
            try {
               this.ourConfigFile.delete();
               this.ourConfigFile = null;
               this.saveDefaultConfig();
               if (this.ourConfigFile == null) {
                  this.ourConfigFile = new File(this.core.getDataFolder(), this.configFileName);
               }
            } catch (SecurityException var2) {
               this.core.getLogger().info("Error re-genning config file '" + this.configFileName + "': <" + var2.getMessage() + ">");
            }

         }
      }
   }

   public void UpdateDynTriggerLocs() {
      Iterator var2 = this.triggerLocs.iterator();

      while(var2.hasNext()) {
         QfTriggerLoc trig = (QfTriggerLoc)var2.next();
         if (trig.mitem != null && trig.mitem.triggerIsDynamic) {
            trig.trigLoc = trig.mitem.getCurrentTriggerLoc();
         }
      }

   }

   public List getTriggerLocs() {
      if (!this.hasLocationTriggers) {
         return null;
      } else if (!this.hasDynLocationTriggers) {
         return this.triggerLocs;
      } else {
         this.UpdateDynTriggerLocs();
         return this.triggerLocs;
      }
   }

   public void addTriggerLoc(QfMItem mi) {
      QfTriggerLoc tLoc = new QfTriggerLoc();
      tLoc.mitem = mi;
      tLoc.trigRadius = mi.triggerLoc.trigRadius;
      tLoc.trigLoc = mi.triggerLoc.trigLoc;
      tLoc.worldName = mi.triggerLoc.worldName;
      tLoc.trigLoc.setWorld(mi.triggerLoc.trigLoc.getWorld());
      this.triggerLocs.add(tLoc);
   }

   public void removeTriggerLoc(QfMItem mi) {
   }

   public QfMItem getMItemByName(String mname) {
      Iterator var3 = this.mitems.iterator();

      while(var3.hasNext()) {
         QfMItem mi = (QfMItem)var3.next();
         if (mi.name.equalsIgnoreCase(mname)) {
            return mi;
         }
      }

      return null;
   }

   public QfMItem getMItem(List itemList, String mName) {
      Iterator var4 = itemList.iterator();

      while(var4.hasNext()) {
         QfMItem mi = (QfMItem)var4.next();
         if (mi.name.equalsIgnoreCase(mName)) {
            return mi;
         }
      }

      return null;
   }

   public QfMItem getMItem(String mName) {
      return this.getMItem(this.mitems, mName);
   }

   public QfMItem getMItemUuid(UUID itemUuid) {
      Iterator var3 = this.mitems.iterator();

      while(var3.hasNext()) {
         QfMItem mi = (QfMItem)var3.next();
         if (mi.mUuid.equals(itemUuid)) {
            return mi;
         }
      }

      return null;
   }

   public QfMItem getMItem(String propName, String itemName) {
      return null;
   }
}
