package com.quantiforte.qfcore.movement;

import com.quantiforte.qfcore.QfGeneral;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class QfMoveMgr extends QfGeneral implements CommandExecutor, TabCompleter {
   public Location locRpg;
   public Location locAnvil;
   public Location locEtable;

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         pTarget = pUser;
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      if (cmdName.equalsIgnoreCase("rpg")) {
         return true;
      } else if (cmdName.equalsIgnoreCase("go")) {
         this.DoGo(pUser, sender, cmd, label, args);
         return true;
      } else if (cmdName.equalsIgnoreCase("arena")) {
         this.DoArena(pUser, sender, cmd, label, args);
         return true;
      } else if (cmdName.equalsIgnoreCase("tour")) {
         this.DoTour(pUser, sender, cmd, label, args);
         return true;
      } else if (cmdName.equalsIgnoreCase("staff")) {
         this.DoStaff(pUser, sender, cmd, label, args);
         return true;
      } else if (!cmdName.equalsIgnoreCase("market") && !cmdName.equalsIgnoreCase("marketplace")) {
         return false;
      } else {
         if (args.length == 0) {
            this.qfcore.siteMgr.goSite(pUser, "marketplace");
         } else if (pTarget != null) {
            Location loc;
            if (args.length == 1 && args[0].equalsIgnoreCase("wolf")) {
               loc = new Location(this.qfcore.getServer().getWorld("world"), -8887.5D, 76.0D, -2670.5D, 166.0F, 13.0F);
               pTarget.teleport(loc);
               return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("bear")) {
               loc = new Location(this.qfcore.getServer().getWorld("world"), -9018.5D, 73.0D, -2475.5D, 70.0F, 1.0F);
               pTarget.teleport(loc);
               return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("snake")) {
               loc = new Location(this.qfcore.getServer().getWorld("world"), -8901.5D, 71.0D, -2443.5D, 55.0F, -7.0F);
               pTarget.teleport(loc);
               return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("eagle")) {
               loc = new Location(this.qfcore.getServer().getWorld("world"), -8843.5D, 77.0D, -2323.5D, 173.0F, -2.0F);
               pTarget.teleport(loc);
               return true;
            }

            this.msgCaller(pTarget, ChatColor.RED + "Unknown market hub " + ChatColor.GOLD + args[0]);
         }

         return true;
      }
   }

   public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
	  // decompiler artifacts
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

   public void DoGo(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      Inventory goInv = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.DARK_PURPLE + "Legends and Kings Destinations");
      int idx = 0;
      int var9 = idx + 1;
      goInv.setItem(idx, this.QuickItem(Material.BEACON, "Spawn", ChatColor.AQUA + "/warp spawn"));
      goInv.setItem(var9++, this.QuickItem(Material.LEATHER_CHESTPLATE, "Adventure World", ChatColor.RED + "Future RPG World"));
      goInv.setItem(var9++, this.QuickItem(Material.BREAD, "Survival Spawn", ChatColor.RED + "/warp survival"));
      idx = 9;
      var9 = idx + 1;
      goInv.setItem(idx, this.QuickItem(Material.GOLD_INGOT, "$erver $hop", ChatColor.YELLOW + "/warp sshop"));
      goInv.setItem(var9++, this.QuickItem(Material.GOLD_NUGGET, "Marketplace", ChatColor.YELLOW + "/marketplace"));
      ++var9;
      goInv.setItem(var9++, this.QuickItem(Material.BLAZE_POWDER, "Portals Hub", ChatColor.GREEN + "/warp portals"));
      goInv.setItem(var9++, this.QuickItem(Material.ENDER_EYE, "Staff Hub", ChatColor.BLUE + "/warp staff"));
      goInv.setItem(var9++, this.QuickItem(Material.ARMOR_STAND, "Class Hub", ChatColor.GOLD + "/warp classhub"));
      goInv.setItem(var9++, this.QuickItem(Material.OAK_SIGN, "Info Hub", ChatColor.WHITE + "/warp info"));
      goInv.setItem(var9++, this.QuickItem(Material.IRON_BARS, "Jail", ChatColor.GRAY + "/warp jail"));
      ++var9;
      idx = 18;
      if (pUser.hasPermission("Qrpg.mytown.given")) {
         var9 = idx + 1;
         goInv.setItem(idx, this.QuickItem(Material.MAP, "My Town", ChatColor.YELLOW + "/mytown"));
      } else if (pUser.hasPermission("Qrpg.myland.given")) {
         var9 = idx + 1;
         goInv.setItem(idx, this.QuickItem(Material.MAP, "My Land", ChatColor.YELLOW + "/myland"));
      } else {
         var9 = idx + 1;
      }

      if (pUser.hasPermission("Qrpg.playerrpg.has_home")) {
         goInv.setItem(var9++, this.QuickItem(Material.RED_BED, "My Adventure Home", ChatColor.GREEN + "/rpg home"));
      } else {
         ++var9;
      }

      if (pUser.hasPermission("Qrpg.guild.member")) {
         goInv.setItem(var9++, this.QuickItem(Material.WHITE_BANNER, "My Guild", ChatColor.GOLD + "/guild home"));
      } else {
         ++var9;
      }

      ++var9;
      if (pUser.hasPermission("Qrpg.tradesman.shop.given")) {
         goInv.setItem(var9++, this.QuickItem(Material.GOLD_NUGGET, "My Shop", ChatColor.GREEN + "/myshop"));
      } else {
         ++var9;
      }

      if (pUser.hasPermission("Qrpg.tradesman.shop.available")) {
         goInv.setItem(var9++, this.QuickItem(Material.OAK_DOOR, "MyShop Decision", ChatColor.LIGHT_PURPLE + "MyShop decision point"));
      } else if (pUser.hasPermission("Qrpg.tradesman.shop.requested")) {
         goInv.setItem(var9++, this.QuickItem(Material.GOLD_NUGGET, "MyShop Pending", ChatColor.YELLOW + "Your shop has been requested"));
      } else if (pUser.hasPermission("Qrpg.tradesman.shop.closed")) {
         goInv.setItem(var9++, this.QuickItem(Material.IRON_DOOR, "MyShop Closed", ChatColor.RED + "Your shop has been closed down"));
      } else {
         ++var9;
      }

      if (pUser.hasPermission("Qrpg.class.peddler")) {
         goInv.setItem(var9++, this.QuickItem(Material.CRAFTING_TABLE, "Tradesman Recipes", ChatColor.GOLD + "/tlibrary"));
      }

      if (pUser.hasPermission("Qrpg.class.trader")) {
         goInv.setItem(var9++, this.QuickItem(Material.ENCHANTING_TABLE, "Tradesman Enchanting", ChatColor.GOLD + "/etable"));
      }

      if (pUser.hasPermission("Qrpg.class.blacksmith")) {
         goInv.setItem(var9++, this.QuickItem(Material.ANVIL, "Tradesman Anvil", ChatColor.GOLD + "/anvil"));
      }

      // decompiler artifact?
      // int idx = true;
      pUser.openInventory(goInv);
   }

   public void DoTour(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      if (args.length > 0 && !pUser.hasPermission("Qrpg.tour.claim")) {
         this.msgCaller(pUser, ChatColor.RED + "Please use '/tour' to open the menu of choices for tour destinations.");
      } else if (args.length == 0) {
         Inventory goInv = Bukkit.createInventory((InventoryHolder)null, 9, ChatColor.DARK_GREEN + "Legends and Kings Tour");
         int idx = 0;
         int var14 = idx + 1;
         goInv.setItem(idx, this.QuickItem(Material.BEACON, "Spawn", ChatColor.AQUA + "/warp spawn"));
         goInv.setItem(var14++, this.QuickItem(Material.ARMOR_STAND, "Class Hub", ChatColor.GOLD + "/warp classhub"));
         goInv.setItem(var14++, this.QuickItem(Material.OAK_SIGN, "Info Hub", ChatColor.WHITE + "/warp info"));
         goInv.setItem(var14++, this.QuickItem(Material.BLAZE_POWDER, "Portals Hub", ChatColor.GREEN + "/warp portals"));
         goInv.setItem(var14++, this.QuickItem(Material.GOLD_INGOT, "$erver $hop", ChatColor.YELLOW + "/warp sshop"));
         goInv.setItem(var14++, this.QuickItem(Material.GOLD_NUGGET, "Marketplace", ChatColor.YELLOW + "/marketplace"));
         ++var14;
         ++var14;
         goInv.setItem(var14++, this.QuickItem(Material.BREAD, "Survival Spawn", ChatColor.RED + "/warp survival"));
         pUser.openInventory(goInv);
      } else {
         String playerName = args[0];
         Player pTarget = this.qfcore.getServer().getPlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Not sure which player to work with");
         } else if (args.length == 1) {
            String coolId = "stafftour";
            long coolTime = 25L;
            String coolTimeLeft = this.qfcore.cooldownMgr.checkCooldown(pTarget, coolId, false);
            if (coolTimeLeft == null) {
               this.qfcore.sendChat(pUser, "m", ChatColor.DARK_PURPLE + "Tour Manager: ", pUser.getDisplayName() + ChatColor.LIGHT_PURPLE + " will be giving a tour to " + pTarget.getDisplayName());
               this.msgCaller(pTarget, pUser.getDisplayName() + ChatColor.RESET + ChatColor.LIGHT_PURPLE + " will be giving you a tour!");
               this.qfcore.cooldownMgr.addCooldown(pUser, coolId, coolTime);
            } else {
               this.msgCaller(pUser, ChatColor.RED + "Another staff member has already claimed that player for a tour");
            }

         }
      }
   }

   public void DoStaff(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      Inventory goInv = Bukkit.createInventory((InventoryHolder)null, 9, ChatColor.DARK_BLUE + "Legends and Kings Staff");
      int idx = 0;
      int var8 = idx + 1;
      goInv.setItem(idx, this.QuickItem(Material.GOLD_NUGGET, "Interns", ChatColor.YELLOW + "Fantastic Intern Team"));
      goInv.setItem(var8++, this.QuickItem(Material.GOLD_INGOT, "Moderators", ChatColor.GOLD + "Excellent Moderators"));
      goInv.setItem(var8++, this.QuickItem(Material.GOLD_BLOCK, "Sr Mods", ChatColor.RED + "Amazing Sr Mods"));
      goInv.setItem(var8++, this.QuickItem(Material.DIAMOND, "Head Mod", ChatColor.BLUE + "Stellar Head Mod"));
      goInv.setItem(var8++, this.QuickItem(Material.DIAMOND_BLOCK, "Admins", ChatColor.AQUA + "Incredible Admins"));
      goInv.setItem(var8++, this.QuickItem(Material.NETHER_STAR, "Executives", ChatColor.LIGHT_PURPLE + "Superb Executives and Owner"));
      pUser.openInventory(goInv);
   }

   public void DoArena(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      Inventory goInv = Bukkit.createInventory((InventoryHolder)null, 9, ChatColor.DARK_RED + "Soldier Arenas");
      int idx = 0;
      int var8 = idx + 1;
      goInv.setItem(idx, this.QuickItem(Material.GRASS, "Green Fields", ChatColor.AQUA + "Plains with natural traps"));
      goInv.setItem(var8++, this.QuickItem(Material.OAK_WOOD, "Tree Glen", ChatColor.GREEN + "Forest battle"));
      goInv.setItem(var8++, this.QuickItem(Material.SAND, "High Noon", ChatColor.YELLOW + "Several small arenas for duels"));
      goInv.setItem(var8++, this.QuickItem(Material.OAK_DOOR, "Street Fight", ChatColor.GOLD + "Narrow alleyway"));
      goInv.setItem(var8++, this.QuickItem(Material.STONE, "Sumo Mountain", ChatColor.WHITE + "Beautiful high mountain top"));
      goInv.setItem(var8++, this.QuickItem(Material.SOUL_SAND, "No Man's Land", ChatColor.DARK_RED + "Burning battlefield and old castle"));
      goInv.setItem(var8++, this.QuickItem(Material.NETHER_BRICK, "Dark Dungeon", ChatColor.DARK_PURPLE + "Medieval dungeon"));
      goInv.setItem(var8++, this.QuickItem(Material.LAVA_BUCKET, "Torture Chamber", ChatColor.RED + "Not for the meek"));
      pUser.openInventory(goInv);
   }

   public void goDispatch(Player pUser, String itemName) {
      label160: {
         switch(itemName.hashCode()) {
         case -1761981013:
            if (itemName.equals("Executives")) {
               this.qfcore.siteMgr.goSite(pUser, "staff_execs");
               break label160;
            }
            break;
         case -1482928583:
            if (itemName.equals("MyShop Pending")) {
               this.msgCaller(pUser, ChatColor.RED + "Your shop has been requested and is waiting for an admin to set it up for you");
               return;
            }
            break;
         case -1360316503:
            if (itemName.equals("Sumo Mountain")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_sumomountain");
               break label160;
            }
            break;
         case -1180619809:
            if (itemName.equals("My Land")) {
               if (!pUser.hasPermission("Qrpg.myland.given")) {
                  this.msgCaller(pUser, ChatColor.RED + "You must have " + ChatColor.GOLD + "a tract of land" + ChatColor.RED + " to use this command");
               } else {
                  pUser.performCommand("myland");
               }

               return;
            }
            break;
         case -1180404502:
            if (itemName.equals("My Shop")) {
               pUser.performCommand("myshop");
               break label160;
            }
            break;
         case -1180367738:
            if (itemName.equals("My Town")) {
               if (!pUser.hasPermission("Qrpg.mytown.given")) {
                  this.msgCaller(pUser, ChatColor.RED + "You must have " + ChatColor.GOLD + "a town" + ChatColor.RED + " to use this command");
               } else {
                  pUser.performCommand("mytown");
               }

               return;
            }
            break;
         case -1110505447:
            if (itemName.equals("Survival Spawn")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp survival " + pUser.getName());
               break label160;
            }
            break;
         case -1098468956:
            if (itemName.equals("No Man's Land")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_nomansland");
               break label160;
            }
            break;
         case -1052705598:
            if (itemName.equals("Head Mod")) {
               this.qfcore.siteMgr.goSite(pUser, "staff_headmod");
               break label160;
            }
            break;
         case -990577363:
            if (itemName.equals("My Survival Home")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo " + pUser.getName() + " home home");
               break label160;
            }
            break;
         case -799317438:
            if (itemName.equals("Adventure World")) {
               this.msgCaller(pUser, ChatColor.RED + "The RPG world is not yet open.");
               return;
            }
            break;
         case -672251007:
            if (itemName.equals("Interns")) {
               this.msgCaller(pUser, ChatColor.RED + "staff_interns.");
               this.qfcore.siteMgr.goSite(pUser, "staff_interns");
               break label160;
            }
            break;
         case -593913133:
            if (itemName.equals("$erver $hop")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp sshop " + pUser.getName());
               break label160;
            }
            break;
         case -569686285:
            if (itemName.equals("Coliseum")) {
               this.msgCaller(pUser, ChatColor.RED + "Not yet available. Warping to Spawn");
               break label160;
            }
            break;
         case -483386507:
            if (itemName.equals("Staff Hub")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp staff " + pUser.getName());
               break label160;
            }
            break;
         case -350926126:
            if (itemName.equals("Sr Mods")) {
               this.qfcore.siteMgr.goSite(pUser, "staff_srmods");
               break label160;
            }
            break;
         case -39581047:
            if (itemName.equals("Torture Chamber")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_torturechamber");
               break label160;
            }
            break;
         case -22816722:
            if (itemName.equals("Moderators")) {
               this.qfcore.siteMgr.goSite(pUser, "staff_mods");
               break label160;
            }
            break;
         case 2301114:
            if (itemName.equals("Jail")) {
               this.qfcore.siteMgr.goSite(pUser, "jail");
               break label160;
            }
            break;
         case 80085851:
            if (itemName.equals("Spawn")) {
               pUser.performCommand("/warp spawn");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp spawn " + pUser.getName());
               break label160;
            }
            break;
         case 175423664:
            if (itemName.equals("Tradesman Recipes")) {
               this.qfcore.siteMgr.goSite(pUser, "tlibrary");
               break label160;
            }
            break;
         case 218693302:
            if (itemName.equals("Green Fields")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_greenfields");
               break label160;
            }
            break;
         case 240729104:
            if (itemName.equals("Dark Dungeon")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_darkdungeon");
               break label160;
            }
            break;
         case 241003139:
            if (itemName.equals("Info Hub")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp info " + pUser.getName());
               break label160;
            }
            break;
         case 288255178:
            if (itemName.equals("My RPG Home")) {
               break label160;
            }
            break;
         case 1230134526:
            if (itemName.equals("Tradesman Enchanting")) {
               this.qfcore.siteMgr.goSite(pUser, "tmenchant");
               break label160;
            }
            break;
         case 1251773214:
            if (itemName.equals("High Noon")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_highnoon");
               break label160;
            }
            break;
         case 1438497436:
            if (itemName.equals("Portals Hub")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp portals " + pUser.getName());
               break label160;
            }
            break;
         case 1524957050:
            if (itemName.equals("MyShop Decision")) {
               this.qfcore.siteMgr.goSite(pUser, "myshopdecision");
               break label160;
            }
            break;
         case 1607113623:
            if (itemName.equals("Tradesman Anvil")) {
               this.qfcore.siteMgr.goSite(pUser, "tmanvil");
               break label160;
            }
            break;
         case 1824399795:
            if (itemName.equals("Street Fight")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_streetfight");
               break label160;
            }
            break;
         case 1956598564:
            if (itemName.equals("Admins")) {
               this.qfcore.siteMgr.goSite(pUser, "staff_admins");
               break label160;
            }
            break;
         case 1968779819:
            if (itemName.equals("Marketplace")) {
               this.qfcore.siteMgr.goSite(pUser, "marketplace");
               break label160;
            }
            break;
         case 1976440720:
            if (itemName.equals("Tree Glen")) {
               this.qfcore.siteMgr.goSite(pUser, "pvp_treeglen");
               break label160;
            }
            break;
         case 1992685069:
            if (itemName.equals("Class Hub")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp classhub " + pUser.getName());
               break label160;
            }
         }

         this.msgCaller(pUser, ChatColor.RED + "Not yet available. Warping to Spawn");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warp spawn " + pUser.getName());
      }

      pUser.playSound(pUser.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
   }

   public void DynamicPlace(Player pUser, String cmdName) {
	  // decompiler artifact
      // Player pTarget = null;
      if (cmdName == "anvil") {
         this.qfcore.siteMgr.goSite(pUser, "tmanvil");
      }

      if (cmdName == "tlibrary") {
         this.qfcore.siteMgr.goSite(pUser, "tlibrary");
      }

      if (cmdName == "etable") {
         this.qfcore.siteMgr.goSite(pUser, "tmenchant");
      }

   }

   public void DoRpgWarp(Player pUser, String[] args) {
	  // decompiler artifact
      // Player pTarget = null;
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
    	 // decompiler artifacts
         // int var10000 = args.length;
      }

      if (pUser == null) {
         this.msgCaller(pUser, "Could not locate player");
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
               this.msgCaller(pTarget, ChatColor.RED + "Updated " + ChatColor.YELLOW + "Anvil " + ChatColor.RED + "location" + this.locAnvil.getX());
            }
            break;
         case 1309873904:
            if (worldName.equals("Adventure")) {
               this.locRpg = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
               this.msgCaller(pTarget, ChatColor.RED + "Updated " + ChatColor.YELLOW + "rpg " + ChatColor.RED + "location" + this.locRpg.getX());
            }
         }

      }
   }
}
