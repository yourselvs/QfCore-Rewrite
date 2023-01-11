package me.yourselvs.qfcore.movement;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.yourselvs.qfcore.QfCore;
import me.yourselvs.qfcore.QfGeneral;

public final class QfDynHomeMgr extends QfGeneral implements CommandExecutor, TabCompleter {
   protected String cfNameDhRpg;
   private FileConfiguration configDhRpg = null;
   private File configFileDhRpg = null;
   protected List<QfDynHome> dynHomeList;
   public Location locRpg;
   public Location locAnvil;
   public Location locEtable;

   public void doInit(QfCore plugin) {
      super.doInit(plugin);
      this.cfNameDhRpg = plugin.getDataFolder() + File.separator + "dynhomes" + File.separator + "dynhomes_rpg.yml";
      this.dynHomeList = new ArrayList<QfDynHome>();
   }

   public void loadMgr(QfCore plugin) {
      this.doInit(plugin);
      this.saveDefaultConfig(this.configDhRpg, this.configFileDhRpg, this.cfNameDhRpg);
      this.readConfig();
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Available auras:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Available " + ChatColor.YELLOW + cat + ChatColor.GOLD + " auras:";
   }

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
      if (cmdName.equalsIgnoreCase("rpg")) {
         return true;
      } else if (cmdName.equalsIgnoreCase("setrpg")) {
         this.DoSetRpg(pUser, sender, cmd, label, args);
         return true;
      } else if (cmdName.equalsIgnoreCase("warp2")) {
         this.DoWarp(pUser, sender, cmd, label, args);
         return true;
      } else if (cmdName.equalsIgnoreCase("setwarp2")) {
         this.DoSetWarp(pUser, sender, cmd, label, args);
         return true;
      } else {
         return false;
      }
   }

   public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
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
               this.msgCaller((Player)null, "tabcomplete: 0");
               tabOut.add("list");
               tabOut.add("off");
               return tabOut;
            } else if (args.length == 1) {
               String partial = ChatColor.stripColor(args[0]).toLowerCase();
               this.msgCaller((Player)null, "tabcomplete: 1 =" + args[0] + "=");
               String txt = "list";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "off";
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

   public void readConfig() {
      this.readConfigRpg();
   }

   public void readConfigRpg() {
      FileConfiguration config = this.getConfig(this.configDhRpg, this.configFileDhRpg, this.cfNameDhRpg);
      this.dynHomeList.clear();
      Set<String> keys = config.getConfigurationSection("rpg").getKeys(false);
      this.qfcore.getLogger().info("found " + keys.size() + " rpg warps in config file");
      String[] names = keys.toArray(new String[keys.size()]);
      String[] var10 = names;
      int var9 = names.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String name = var10[var8];
         this.qfcore.getLogger().info("registering rpg warp: " + name);
         QfDynHome dynHome = new QfDynHome();
         dynHome.mgr = this;
         dynHome.configName = name;
         String path = "rpg." + name + ".active";
         if (config.contains(path)) {
            dynHome.active = config.getString(path);
         }

         path = "rpg." + name + ".name";
         if (config.contains(path)) {
            dynHome.name = config.getString(path);
         }

         path = "rpg." + name + ".locInMsg";
         if (config.contains(path)) {
            dynHome.locInMsg = config.getString(path);
         }

         path = "rpg." + name + ".descr";
         if (config.contains(path)) {
            dynHome.descr = config.getString(path);
         }

         path = "rpg." + name + ".locIn";
         if (config.contains(path)) {
            String strLoc = config.getString(path);
            if (!dynHome.addLocIn(strLoc)) {
               this.qfcore.getLogger().info("problem processing rpg warp " + name + ", locIn: " + strLoc);
            }
         }

         this.dynHomeList.add(dynHome);
      }

   }

   public void reloadConfig(FileConfiguration config, File configFile, String fileName) {
      if (fileName != null) {
         if (configFile == null) {
            configFile = new File(this.qfcore.getDataFolder(), fileName);
         }

         this.qfcore.getLogger().info("reloading: " + fileName);
         config = YamlConfiguration.loadConfiguration(configFile);

         try {
            Reader reader = new InputStreamReader(this.qfcore.getResource(fileName), "UTF-8");
            if (reader != null) {
               YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);
               config.setDefaults(defaultConfig);
            }
         } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
         }

      }
   }

   public FileConfiguration getConfig(FileConfiguration config, File configFile, String fileName) {
      if (config == null) {
         this.reloadConfig(config, configFile, fileName);
      }

      return config;
   }

   public void saveConfig(FileConfiguration config, File configFile, String fileName) {
      if (fileName != null) {
         if (config != null && configFile != null) {
            try {
               this.getConfig(config, configFile, fileName).save(fileName);
            } catch (IOException var5) {
               this.qfcore.getLogger().info("Error saving config file '" + fileName + "': <" + var5.getMessage() + ">");
            }

         }
      }
   }

   public void saveDefaultConfig(FileConfiguration config, File configFile, String fileName) {
      if (this.configFileDhRpg != null && this.cfNameDhRpg != null) {
         if (this.configFileDhRpg == null) {
            this.configFileDhRpg = new File(this.qfcore.getDataFolder(), this.cfNameDhRpg);
         }

         if (!this.configFileDhRpg.exists()) {
            this.qfcore.saveResource(this.cfNameDhRpg, false);
         }

      }
   }

   public void DoRpg(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
	  // decompiler artifact
      // Player pTarget = null;
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      this.DynamicPlace(pUser, "rpg");
      if (args != null && args.length == 1) {
         if (args[0] != "home") {
            this.msgCaller(pUser, ChatColor.GRAY + "Please use either " + ChatColor.WHITE + "/rpg" + ChatColor.GRAY + " or " + ChatColor.WHITE + "/rpg home");
            return;
         }

         this.msgCaller(pUser, "go rpg home");
         new Location(this.qfcore.getServer().getWorld("Adventure"), 740.5D, 73.0D, -2101.5D, -256.0F, -2.8F);
      }

      if (args != null) {
    	 // wack decompiler artifact
         // int var10000 = args.length;
      }

      if (pUser == null) {
         this.msgCaller(pUser, "Could not locate player");
      }

   }

   public void DoSetRpg(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

   }

   public void DoWarp(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

   }

   public void DoSetWarp(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

   }

   public void DynamicPlace(Player pUser, String cmdName) {
	  // decompiler artifact
      // Player pTarget = null;
      if (cmdName == "anvil") {
         if (pUser.getName().equalsIgnoreCase("WizardWil")) {
            pUser.teleport(this.locAnvil);
            this.msgCaller(pUser, "WW DYN Anvil");
            return;
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp tmanvil " + pUser.getName());
      }

      if (cmdName == "rpg") {
         if (pUser.getName().equalsIgnoreCase("WizardWil")) {
            pUser.teleport(this.locRpg);
            this.msgCaller(pUser, "WW DYN rpg");
            return;
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp adventure " + pUser.getName());
      }

      if (cmdName == "etable") {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp tmenchant " + pUser.getName());
      }

   }

   public void recordMove(Player pTarget, String cmdName, String playerName) {
      this.msgCaller((Player)null, "rm start ===================");
      if (pTarget != null) {
         Location loc = pTarget.getLocation();
         String worldName = pTarget.getLocation().getWorld().getName();
         this.msgCaller((Player)null, "record move: " + playerName + ", " + worldName + " " + loc.getX());
         switch(cmdName.hashCode()) {
         case -2104352421:
            if (!cmdName.equals("/tphere")) {
            }
            break;
         case -816802874:
            if (!cmdName.equals("/tpahere")) {
            }
            break;
         case 48875:
            if (!cmdName.equals("/tp")) {
            }
            break;
         case 48954:
            if (!cmdName.equals("/wb")) {
            }
            break;
         case 1501574:
            if (!cmdName.equals("/fix")) {
            }
            break;
         case 1513306:
            if (!cmdName.equals("/rpg")) {
            }
            break;
         case 1515206:
            if (!cmdName.equals("/top")) {
            }
            break;
         case 1515222:
            if (!cmdName.equals("/tpa")) {
            }
            break;
         case 46421398:
            if (!cmdName.equals("/back")) {
            }
            break;
         case 46613902:
            if (!cmdName.equals("/home")) {
            }
            break;
         case 46679261:
            if (!cmdName.equals("/jump")) {
            }
            break;
         case 46964838:
            if (!cmdName.equals("/thru")) {
            }
            break;
         case 47047479:
            if (!cmdName.equals("/warp")) {
            }
            break;
         case 444225459:
            if (!cmdName.equals("/tpaccept")) {
            }
            break;
         case 1438545405:
            if (!cmdName.equals("/anvil")) {
            }
            break;
         case 1440491345:
            if (!cmdName.equals("/craft")) {
            }
            break;
         case 1444282660:
            if (!cmdName.equals("/guild")) {
            }
            break;
         case 1455208620:
            if (!cmdName.equals("/spawn")) {
            }
            break;
         case 1764660088:
            if (!cmdName.equals("/etable")) {
            }
            break;
         case 1976661291:
            if (!cmdName.equals("/market")) {
            }
            break;
         case 2072831676:
            if (!cmdName.equals("/marketplace")) {
            }
         }

         switch(worldName.hashCode()) {
         case 113318802:
            if (worldName.equals("world")) {
               this.locAnvil = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
               this.msgCaller(pTarget, ChatColor.RED + ">>Updated " + ChatColor.YELLOW + "Anvil " + ChatColor.RED + "location" + this.locAnvil.getX());
            }
            break;
         case 1309873904:
            if (worldName.equals("Adventure")) {
               this.locRpg = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
               this.msgCaller(pTarget, ChatColor.RED + ">>Updated " + ChatColor.YELLOW + "rpg " + ChatColor.RED + "location" + this.locRpg.getX());
            }
         }

      }
   }
}
