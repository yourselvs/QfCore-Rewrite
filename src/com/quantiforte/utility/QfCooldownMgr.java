package com.quantiforte.utility;

import com.quantiforte.qfcore.QfCore;
import com.quantiforte.qfcore.QfManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QfCooldownMgr extends QfManager {
   public HashMap mapUuid;

   public void doInit(QfCore newCore) {
      this.configFileName = "config_cooldowns.yml";
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      this.mapUuid = new HashMap();
      super.doInit(newCore);
   }

   public void addCooldown(Player pUser, String coolType, Long coolSeconds) {
      this.mapUuid.put(pUser.getUniqueId() + " " + coolType, coolSeconds);
   }

   public String checkCooldown(Player pUser, String coolType, boolean shouldMessage) {
      String key = pUser.getUniqueId() + " " + coolType;
      if (!this.mapUuid.containsKey(key)) {
         return null;
      } else {
         Long keyTime = (Long)this.mapUuid.get(key);
         String cool = this.niceTime(keyTime);
         if (shouldMessage) {
            this.msgCaller(pUser, ChatColor.RED + "You must wait " + ChatColor.YELLOW + cool + ChatColor.RED + " before using this command again.");
         }

         return cool;
      }
   }

   public String niceTime(Long sec) {
      String outStr = "";
      Long m = sec % 60L;
      Long d = sec / 60L;
      outStr = m + "s";
      if (d == 0L) {
         return outStr;
      } else {
         m = d % 60L;
         d = d / 60L;
         outStr = m + "m " + outStr;
         if (d == 0L) {
            return outStr;
         } else {
            m = d % 24L;
            d = d / 24L;
            outStr = m + "h " + outStr;
            if (d == 0L) {
               return outStr;
            } else {
               outStr = d + "d " + outStr;
               return outStr;
            }
         }
      }
   }

   public void taskCooldownCheck() {
      Iterator it = this.mapUuid.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         Long keyTime = (Long)this.mapUuid.get(key);
         if (keyTime <= 1L) {
            it.remove();
            this.core.getLogger().info("Cooldown removed " + key);
         } else {
            this.mapUuid.put(key, keyTime - 1L);
         }
      }

   }

   public void readConfig() {
      Set keys = this.getConfig().getConfigurationSection("cooldown").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " pending in config_cooldowns.yml");
      String[] names = (String[])keys.toArray(new String[keys.size()]);
      String[] var9 = names;
      int var8 = names.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String name = var9[var7];
         String path = "cooldown." + name + ".base";
         if (this.getConfig().contains(path)) {
            String coolBase = this.getConfig().getString(path);
            path = "cooldown." + name + ".secs";
            if (this.getConfig().contains(path)) {
               Long coolSeconds = Long.parseLong(this.getConfig().getString(path));
               this.mapUuid.put(coolBase, coolSeconds);
            }
         }
      }

   }

   public void saveConfig() {
      try {
         this.getConfig().save(this.ourConfigFile);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public void genCooldownConfig() {
      this.deleteConfig();
      Iterator it = this.mapUuid.keySet().iterator();
      int idx = 0;

      while(it.hasNext()) {
         String key = (String)it.next();
         Long keyTime = (Long)this.mapUuid.get(key);
         if (keyTime <= 1L) {
            it.remove();
         } else {
            String name = "a" + idx++;
            String path = "cooldown." + name;
            this.getConfig().createSection(path);
            path = "cooldown." + name + ".base";
            this.getConfig().createSection(path);
            this.getConfig().set(path, key);
            path = "cooldown." + name + ".secs";
            this.getConfig().createSection(path);
            this.getConfig().set(path, keyTime);
         }
      }

      if (idx > 0) {
         this.saveConfig();
      }

   }
}
