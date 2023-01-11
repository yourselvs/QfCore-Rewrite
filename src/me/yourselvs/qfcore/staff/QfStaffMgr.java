package me.yourselvs.qfcore.staff;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.yourselvs.qfcore.QfGeneral;

public class QfStaffMgr extends QfGeneral implements CommandExecutor, TabCompleter {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pOffTarget = null;
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

      label84: {
         String cmdName = cmd.getName().toLowerCase();
         switch(cmdName.hashCode()) {
         case -1183693858:
            if (cmdName.equals("invoff")) {
               if (args.length < 1) {
                  this.msgCaller(pUser, ChatColor.RED + "Please specify a player name");
                  return true;
               }

               pOffTarget = this.qfcore.getServer().getPlayer(args[0]);
               if (pOffTarget == null) {
                  this.msgCaller(pUser, ChatColor.RED + "Could not locate the player");
                  return true;
               }

               pUser.openInventory(pOffTarget.getPlayer().getInventory());
               break label84;
            }
            break;
         case -980110702:
            if (cmdName.equals("prefix")) {
               if (args.length < 2) {
                  this.msgCaller(pUser, "Please specify a player and a new prefix");
                  return true;
               }

               pTarget = this.qfcore.getServer().getPlayer(args[0]);
               if (pTarget == null) {
                  this.msgCaller(pUser, "Could not locate the player");
                  return true;
               }

               this.DoSetPrefix(pUser, pTarget, args);
               return true;
            }
            break;
         case 104433:
            if (cmdName.equals("inv")) {
               break label84;
            }
            break;
         case 3242771:
            if (cmdName.equals("item")) {
               this.DoDesignItem(pUser, args);
               return true;
            }
            break;
         case 3641990:
            if (cmdName.equals("warn")) {
               if (args.length < 2) {
                  this.msgCaller(pUser, "Please specify a violation type and player name");
                  return true;
               }

               pTarget = this.qfcore.getServer().getPlayer(args[1]);
               if (pTarget == null) {
                  this.msgCaller(pUser, "Could not locate the player");
                  return true;
               }

               this.DoModWarn(pUser, pTarget, args[0]);
               return true;
            }
            break;
         case 104229765:
            if (cmdName.equals("mtool")) {
               if (args.length < 1) {
                  this.msgCaller(pUser, "Please specify a tool to use");
                  return true;
               }

               if ("invisible".startsWith(args[0].toLowerCase())) {
                  this.msgCaller(pUser, ChatColor.GREEN + "Invisibility effect will be active for 10 minutes. Don't forget to have an " + ChatColor.YELLOW + "empty hand and no armor or other visibile itmes\n" + ChatColor.RED + "DO NOT ABUSE this ability");
                  pUser.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 12000, 1, false));
                  return true;
               }

               return false;
            }
         }

         return false;
      }

      if (args.length < 1) {
         this.msgCaller(pUser, ChatColor.RED + "Please specify a player name");
         return true;
      } else {
         pTarget = this.qfcore.getServer().getPlayer(args[0]);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Could not locate the player");
            return true;
         } else {
            pUser.openInventory(pTarget.getInventory());
            return false;
         }
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
         if (!var8.equalsIgnoreCase("warn")) {
            return null;
         } else {
            List<String> tabOut = new ArrayList<String>();
            if (args.length == 0) {
               tabOut.add("advertising");
               tabOut.add("begging");
               tabOut.add("behavior");
               tabOut.add("caps");
               tabOut.add("disrespect");
               tabOut.add("language");
               tabOut.add("scamming");
               tabOut.add("spamming");
               return tabOut;
            } else if (args.length == 1) {
               String partial = ChatColor.stripColor(args[0]).toLowerCase();
               String txt = "advertising";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "begging";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "behavior";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "caps";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "disrespect";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "language";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "scamming";
               if (txt.toLowerCase().startsWith(partial)) {
                  tabOut.add(txt);
               }

               txt = "spamming";
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

   public void clearPrefix(Player pTarget) {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " prefix \"\"");
   }

   public void DoSetPrefix(Player pUser, Player pTarget, String[] args) {
      if (!args[1].equalsIgnoreCase("reset") && !args[1].equalsIgnoreCase("clear")) {
         String prefix = "";

         for(int i = 1; i < args.length; ++i) {
            prefix = prefix + args[i] + " ";
         }

         prefix = prefix.replace("\"", "").trim();
         prefix = "\"" + prefix + " \"";
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " prefix " + prefix);
         this.msgCaller(pUser, ChatColor.GRAY + "Prefix for player " + ChatColor.YELLOW + pTarget.getDisplayName() + ChatColor.GRAY + " is now: " + ChatColor.RESET + prefix);
      } else {
         this.clearPrefix(pTarget);
         this.msgCaller(pUser, ChatColor.GRAY + "Prefix for player " + ChatColor.YELLOW + pTarget.getDisplayName() + ChatColor.GRAY + " has been reset to default.");
      }
   }

   public void DoModWarn(Player pUser, Player pTarget, String warnType) {
      String warnTypeLc = warnType.toLowerCase();
      String warnKey = null;
      String warnLang = null;
      long warnTime = 20L;
      if ("advertising".startsWith(warnTypeLc)) {
         warnKey = "warn-adv";
         warnTime = 20L;
         warnLang = "Advertising other servers or web sites";
      } else if ("begging".startsWith(warnTypeLc)) {
         warnKey = "warn-beg";
         warnTime = 20L;
         warnLang = "Begging for money/food/items";
      } else if ("behavior".startsWith(warnTypeLc)) {
         warnKey = "warn-tpkill";
         warnTime = 20L;
         warnLang = "Inappropriate behavior";
      } else if ("caps".startsWith(warnTypeLc)) {
         warnKey = "warn-caps";
         warnTime = 20L;
         warnLang = "Using TOO MANY CAPS";
      } else if ("disrespect".startsWith(warnTypeLc)) {
         warnKey = "warn-disr";
         warnTime = 20L;
         warnLang = "Disrespect towards Staff";
      } else if ("language".startsWith(warnTypeLc)) {
         warnKey = "warn-lang";
         warnTime = 20L;
         warnLang = "Inappropriate Language";
      } else if ("scamming".startsWith(warnTypeLc)) {
         warnKey = "warn-scam";
         warnTime = 20L;
         warnLang = "Scamming other players";
      } else if ("spamming".startsWith(warnTypeLc)) {
         warnKey = "warn-spam";
         warnTime = 20L;
         warnLang = "Spamming";
      } else if (warnKey == null) {
         this.msgCaller(pUser, ChatColor.RED + "Unrecognized reason for warning. No warning sent to player");
         return;
      }

      String cool = this.qfcore.cooldownMgr.checkCooldown(pTarget, warnKey, false);
      if (cool != null) {
         this.msgCaller(pUser, ChatColor.RED + "Player was recently warned, please wait " + ChatColor.YELLOW + cool + ChatColor.RED + " before warning that player again");
      } else {
         this.msgCaller(pTarget, ChatColor.RED + "You have received a warning from staff for: " + ChatColor.YELLOW + warnLang);
         this.qfcore.cooldownMgr.addCooldown(pTarget, warnKey, warnTime);
         String dispStr = ">>> Sent " + warnKey + " to player " + pTarget.getName();
         if (pUser == null) {
            this.qfcore.sendChat(pUser, "m", ChatColor.RED + "[Console]", dispStr);
            this.qfcore.getLogger().info(ChatColor.stripColor(dispStr));
         } else {
            this.qfcore.sendChat(pUser, "m", pUser.getName(), dispStr);
         }

      }
   }

   public void DoDesignItem(Player pUser, String[] args) {
      if (args.length < 1) {
         this.msgCaller(pUser, ChatColor.RED + "Please specificy: name, lore, lore clear");
      } else {
         String arg0 = args[0].toLowerCase();
         String name;
         ItemStack item;
         ItemMeta meta;
         int i;
         switch(arg0.hashCode()) {
         case 3173137:
            if (arg0.equals("give")) {
               Player pTarget = this.qfcore.getServer().getPlayer(args[1]);
               if (pTarget == null) {
                  this.msgCaller(pUser, "Could not locate the player " + args[1]);
                  return;
               }

               if (pTarget.getInventory().firstEmpty() > -1) {
                  item = pUser.getItemInUse();
                  if (item == null) {
                     this.msgCaller(pUser, ChatColor.RED + "You must be holding the item you want to give");
                     return;
                  }

                  pTarget.getInventory().addItem(new ItemStack[]{item.clone()});
                  this.msgCaller(pUser, ChatColor.GOLD + "You have given " + pTarget.getDisplayName() + ChatColor.GOLD + " the item");
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "The player does not have room in their inventory to receive the item.");
               }
            }
            break;
         case 3327734:
            if (arg0.equals("lore")) {
               if (args.length < 2) {
                  this.msgCaller(pUser, ChatColor.RED + "You must specify the lore or 'clear' to clear all lore for this item");
                  return;
               }

               name = null;
               boolean doClear;
               if (args[1].equalsIgnoreCase("clear")) {
                  doClear = true;
               } else {
                  doClear = false;
                  name = "";

                  for(i = 1; i < args.length; ++i) {
                     name = name + args[i] + " ";
                  }

                  name = name.trim();
                  name = ChatColor.translateAlternateColorCodes('&', name);
               }

               item = pUser.getItemInUse();
               if (item == null) {
                  this.msgCaller(pUser, ChatColor.RED + "You must be holding the item you want to change the lore on");
                  return;
               }

               meta = item.getItemMeta();
               if (doClear) {
                  meta.setLore((List<String>)null);
                  this.msgCaller(pUser, ChatColor.GOLD + "You have " + ChatColor.YELLOW + "cleared " + ChatColor.GOLD + "the lore for the item");
               } else {
                  List<String> lore = meta.getLore();
                  if (lore == null) {
                     lore = new ArrayList<String>();
                     ((List<String>)lore).add(name);
                  } else {
                     ((List<String>)lore).add(name);
                  }

                  meta.setLore((List<String>)lore);
                  this.msgCaller(pUser, ChatColor.GOLD + "You have " + ChatColor.YELLOW + "edited " + ChatColor.GOLD + "the lore for the item");
               }

               item.setItemMeta(meta);
            }
            break;
         case 3373707:
            if (arg0.equals("name")) {
               name = "";

               for(i = 1; i < args.length; ++i) {
                  name = name + args[i] + " ";
               }

               name = name.trim();
               name = ChatColor.translateAlternateColorCodes('&', name);
               item = pUser.getItemInUse();
               if (item == null) {
                  this.msgCaller(pUser, ChatColor.RED + "You must be holding the item you want to rename");
                  return;
               }

               meta = item.getItemMeta();
               meta.setDisplayName(name);
               item.setItemMeta(meta);
               this.msgCaller(pUser, ChatColor.GOLD + "You have renamed the item: " + name);
            }
         }

      }
   }

   public void anvilRename(Player pPlayer, String[] args) {
      String name = "";

      for(int i = 0; i < args.length; ++i) {
         name = name + args[i] + " ";
      }

      name = name.trim();
      name = ChatColor.stripColor(name);
      ItemStack item = pPlayer.getItemInUse();
      if (item == null) {
         this.msgCaller(pPlayer, ChatColor.DARK_RED + "You must be holding the item you want to rename to use this ability");
      } else {
         ItemMeta meta = item.getItemMeta();
         meta.setDisplayName(ChatColor.stripColor(name));
         item.setItemMeta(meta);
         this.msgCaller(pPlayer, ChatColor.GOLD + "You have used your tradesman skills to rename the item.");
      }
   }
}
