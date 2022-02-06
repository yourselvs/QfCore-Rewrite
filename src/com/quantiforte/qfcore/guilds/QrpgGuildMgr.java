package com.quantiforte.qfcore.guilds;

import com.quantiforte.qfcore.QfCore;
import com.quantiforte.qfcore.QfMItem;
import com.quantiforte.qfcore.QfManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QrpgGuildMgr extends QfManager implements CommandExecutor {
   int nextGuildId;
   public String lkgm;

   public void doInit(QfCore newCore) {
      this.configFileName = "config_guilds.yml";
      this.mitems = new ArrayList<QfMItem>();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      this.lkgm = ChatColor.LIGHT_PURPLE + "L" + ChatColor.GRAY + "&" + ChatColor.LIGHT_PURPLE + "K" + ChatColor.YELLOW + "Guild Manager";
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "guild";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Current guilds:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Current " + ChatColor.YELLOW + cat + ChatColor.GOLD + " guilds:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  // decompiler artifacts?
      // Player pTarget = null;
      Player pUser = null;
      // String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         // playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         // pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      String guildName;
      double amt;
      switch(cmdName.hashCode()) {
      case 98712563:
         if (cmdName.equals("guild")) {
            if (args.length >= 1 && "setup".startsWith(args[0])) {
               if (args.length == 2) {
                  guildName = args[1];
                  this.setupGuild(pUser, guildName);
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "Please specify a guild name");
               }

               return true;
            }

            if (args.length >= 1 && "reset".startsWith(args[0])) {
               if (args.length == 2) {
                  guildName = args[1];
                  this.resetGuildRegionFlags(pUser, guildName);
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "Please specify a guild name");
               }

               return true;
            }

            if (args.length >= 1 && "list".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.list")) {
                  String dispStr = this.listItems((String)null);
                  this.msgCaller(pUser, dispStr);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "home".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.home")) {
                  this.gotoGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "info".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.info")) {
                  this.showInfoGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "sethome".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.sethome")) {
                  this.sethomeGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "members".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.members")) {
                  this.listMembersGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "invites".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.invites")) {
                  this.listInvitesGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "requests".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.requests")) {
                  this.listRequestsGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "invite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.invites")) {
                  this.addInviteGuild(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "uninvite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.invites")) {
                  this.removeInviteGuild(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "give".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.give")) {
                  this.giveGuild(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "add".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.members")) {
                  this.addMemberGuild(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "remove".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.members")) {
                  this.removeMemberGuild(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 4 && "setrank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.ranks")) {
                  this.setRankGuild(pUser, args[3], args[1], args[2]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "rank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.ranks")) {
                  this.showRankGuild(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "setmaxmembers".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.setmaxmembers")) {
                  int num = 0;

                  try {
                     num = Integer.parseInt(args[1]);
                  } catch (NumberFormatException var78) {
                     this.msgCaller(pUser, ChatColor.RED + "Could not read the number properly. Please make sure you are using a positive number with no formatting or decimal points.");
                  } finally {
                     this.doSetMaxMembersGuild(args[2], num);
                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "resetbank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.bank.reset")) {
                  this.resetBankGuild(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length >= 2 && "motd".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.motd")) {
                  this.setMotdGuild(pUser, args, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "balance".startsWith(args[0])) {
               this.showBalanceBankGuild(pUser, args[1]);
               return true;
            }

            QrpgGuild var13;
            if (args.length == 3 && "withdraw".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.bank.withdraw")) {
                  amt = 0.0D;

                  try {
                     amt = Double.parseDouble(args[1]);
                  } catch (NumberFormatException var80) {
                     this.msgCaller(pUser, ChatColor.RED + "Could not read the amount you want to withdrawl. Please make sure you are using a positive number.");
                  } finally {
                     var13 = this.findGuild(args[2]);
                     if (var13 == null) {
                        this.msgCaller(pUser, ChatColor.RED + "Could not locate guild " + ChatColor.GOLD + args[2]);
                     } else {
                        this.doWithdrawBankGuild(args[2], amt);
                        this.msgCaller(pUser, ChatColor.YELLOW + "$" + amt + ChatColor.GREEN + " has been withdrawn from the bank account of guild " + ChatColor.GOLD + args[2]);
                     }

                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "deposit".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.guild.bank.deposit")) {
                  amt = 0.0D;

                  try {
                     amt = Double.parseDouble(args[1]);
                  } catch (NumberFormatException var81) {
                     this.msgCaller(pUser, ChatColor.RED + "Could not read the amount you want to deposit. Please make sure you are using a positive number.");
                  } finally {
                     var13 = this.findGuild(args[2]);
                     if (var13 == null) {
                        this.msgCaller(pUser, ChatColor.RED + "Could not locate guild " + ChatColor.GOLD + args[2]);
                     } else {
                        this.doDepositBankGuild(args[2], amt);
                        this.msgCaller(pUser, ChatColor.YELLOW + "$" + amt + ChatColor.GREEN + " has been deposited into the bank account of guild " + ChatColor.GOLD + args[2]);
                     }

                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1) {
//            	 getting offline player by name is deprecated. guilds will need a rework to use player GUIDs
//               OfflinePlayer pOffTarget = this.core.getServer().getOfflinePlayer(args[0]);
//               if (pOffTarget == null) {
//                  this.msgCaller(pUser, ChatColor.RED + "Could not locate player " + ChatColor.YELLOW + args[0]);
//                  return true;
//               }

               guildName = this.getPlayerGuildName(args[0]);
               if (guildName == null) {
                  this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " is not in a guild");
               } else {
                  this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " is in guild " + ChatColor.GOLD + guildName);
               }

               return true;
            }

            this.msgCaller(pUser, ChatColor.RED + "Unknown /guild sub-command");
            return true;
         }
         break;
      case 1516493255:
         if (cmdName.equals("myguild")) {
            guildName = this.getPlayerGuildName(pUser);
            if (guildName == null) {
               if (args.length == 0) {
                  this.showInvitesGuild(pUser, pUser.getName());
                  this.showRequestsGuild(pUser, pUser.getName());
                  this.msgCaller(pUser, ChatColor.DARK_AQUA + "Use: " + ChatColor.GRAY + "/myguild accept <guildname> " + ChatColor.DARK_AQUA + "to accept an invitation");
                  return true;
               }

               if (args.length == 2) {
                  if ("accept".startsWith(args[0].toLowerCase())) {
                     this.acceptInviteGuild(pUser, args[1]);
                     return true;
                  }

                  if ("decline".startsWith(args[0].toLowerCase())) {
                     this.declineInviteGuild(pUser, args[1]);
                     return true;
                  }

                  if ("request".startsWith(args[0].toLowerCase())) {
                     if ("cancel".equalsIgnoreCase(args[1].toLowerCase())) {
                        this.removeAllRequests(pUser.getName());
                        this.msgCaller(pUser, ChatColor.YELLOW + "You have canceled your request to join a guild");
                     } else {
                        String coolId = "guild_request";
                        long coolTime = 900L;
                        String coolTimeLeft = this.qfcore.cooldownMgr.checkCooldown(pUser, coolId, false);
                        if (coolTimeLeft == null && this.addRequestGuild(pUser, args[1], pUser.getName())) {
                           this.qfcore.cooldownMgr.addCooldown(pUser, coolId, coolTime);
                        }
                     }

                     return true;
                  }
               }

               this.msgCaller(pUser, ChatColor.RED + "Could not understand your /myguild command. Please use either: " + ChatColor.YELLOW + "/myguild " + ChatColor.GRAY + "(to show current invites), or " + ChatColor.YELLOW + "/myguild [accept|decline|request|]");
               return true;
            }

            if (args.length == 0) {
               this.gotoGuild(pUser, this.getPlayerGuildName(pUser));
               return true;
            }

            if (args.length == 1 && "members".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.members.see")) {
                  this.listMembersGuild(pUser, guildName);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1 && "invites".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.invites.see")) {
                  this.listInvitesGuild(pUser, guildName);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1 && "requests".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.requests.see")) {
                  this.listRequestsGuild(pUser, guildName);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1 && "expand30".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.expand")) {
                  this.expandMaxUsersGuild(pUser, guildName, 30);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1 && "expand60".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.expand")) {
                  this.expandMaxUsersGuild(pUser, guildName, 60);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length >= 2 && "motd".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.motd")) {
                  this.setMotdGuild(pUser, args, (String)null);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1 && "balance".startsWith(args[0])) {
               this.showBalanceBankGuild(pUser, guildName);
               return true;
            }

            if (args.length == 2 && "withdraw".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.bank.withdraw")) {
                  amt = 0.0D;

                  try {
                     amt = Double.parseDouble(args[1]);
                  } catch (NumberFormatException var74) {
                     this.msgCaller(pUser, ChatColor.RED + "Could not read the amount you want to withdrawl. Please make sure you are using a positive number.");
                  } finally {
                     this.withdrawBankGuild(pUser, guildName, amt);
                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "deposit".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.bank.deposit")) {
                  amt = 0.0D;

                  try {
                     amt = Double.parseDouble(args[1]);
                  } catch (NumberFormatException var76) {
                     this.msgCaller(pUser, ChatColor.RED + "Could not read the amount you want to deposit. Please make sure you are using a positive number.");
                  } finally {
                     this.depositBankGuild(pUser, guildName, amt);
                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "invite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.invite")) {
                  this.addInviteGuild(pUser, guildName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "accept".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.accept")) {
                  this.acceptRequestGuild(pUser, guildName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "remove".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.removemember")) {
                  this.removeMemberGuild(pUser, guildName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "uninvite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.uninvite")) {
                  this.removeInviteGuild(pUser, guildName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "setrank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myguild.setrank")) {
                  this.setRankGuild(pUser, guildName, args[1], args[2]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "rank".startsWith(args[0])) {
               this.showRankGuild(pUser, guildName, args[1]);
               return true;
            }

            if (args.length == 1 && "rank".startsWith(args[0])) {
               this.showRankGuild(pUser, guildName, pUser.getName());
               return true;
            }

            if (args.length == 2 && "leave".startsWith(args[0])) {
               this.removeMemberGuild(pUser, args[1], pUser.getName());
               return true;
            }

            this.msgCaller(pUser, ChatColor.BLUE + "Could not understand your /myguild command. Please use either: " + ChatColor.DARK_AQUA + "/myguild " + ChatColor.GRAY + "(to go to your guild house), or\n" + ChatColor.DARK_AQUA + "/myguild [members|invites|requests|invite|remove|setrank|leave]");
            return true;
         }
      }

      return false;
   }

   public void readConfig() {
      this.mitems.clear();
      this.triggerLocs.clear();
      Set<String> keys = this.getConfig().getConfigurationSection("guild").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " guilds in config_guilds.yml");
      String[] names = keys.toArray(new String[keys.size()]);
      String[] var10 = names;
      int var9 = names.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String name = var10[var8];
         QrpgGuild guild = new QrpgGuild();
         guild.mgr = this;
         guild.doInit();
         guild.name = name;
         guild.balance = 0.0D;
         guild.maxMembers = 15;
         guild.motd = "";
         guild.memberList = new ArrayList<String>();
         guild.inviteList = new ArrayList<String>();
         guild.requestList = new ArrayList<String>();
         String path = "guild." + name + ".name";
         if (this.getConfig().contains(path)) {
            guild.guildName = this.getConfig().getString(path);
         }

         path = "guild." + name + ".color";
         if (this.getConfig().contains(path)) {
            guild.color = this.getConfig().getString(path);
         }

         path = "guild." + name + ".motd";
         if (this.getConfig().contains(path)) {
            guild.motd = this.getConfig().getString(path);
         }

         path = "guild." + name + ".balance";
         if (this.getConfig().contains(path)) {
            guild.balance = Double.parseDouble(this.getConfig().getString(path));
         }

         path = "guild." + name + ".maxmembers";
         if (this.getConfig().contains(path)) {
            guild.maxMembers = Integer.parseInt(this.getConfig().getString(path));
         }

         path = "guild." + name + ".houseLoc";
         if (this.getConfig().contains(path)) {
            String[] args = this.getConfig().getString(path).split(" ");
            guild.houseLoc = this.config2Loc(args);
         }

         path = "guild." + name + ".category";
         if (this.getConfig().contains(path)) {
            guild.category = this.getConfig().getString(path);
         }

         path = "guild." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            guild.subcategory = this.getConfig().getString(path);
         }

         path = "guild." + name + ".members";
         List<String> inList;
         String item;
         Iterator<String> var12;
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               item = (String)var12.next();
               guild.memberList.add(item);
            }
         }

         path = "guild." + name + ".invites";
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               item = (String)var12.next();
               guild.inviteList.add(item);
            }
         }

         path = "guild." + name + ".requests";
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               item = (String)var12.next();
               guild.requestList.add(item);
            }
         }

         this.mitems.add(guild);
         this.nextGuildId = Integer.parseInt(guild.name) + 1;
      }

   }

   public void saveConfig() {
      try {
         this.getConfig().save(this.ourConfigFile);
      } catch (IOException var2) {
         var2.printStackTrace();
         this.core.getServer().getLogger().info("error writing config file");
      }

   }

   public Location config2Loc(String[] args) {
      if (args.length != 6) {
         this.core.getLogger().info("improper num args: " + args.length);
         return null;
      } else {
         Location newLoc = new Location(this.core.getServer().getWorld("world"), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
         return newLoc;
      }
   }

   public String loc2config(String worldName, Location loc) {
      String retVal = "world";
      retVal = retVal + " " + Double.toString(loc.getX());
      retVal = retVal + " " + Double.toString(loc.getY());
      retVal = retVal + " " + Double.toString(loc.getZ());
      retVal = retVal + " " + Float.toString(loc.getYaw());
      retVal = retVal + " " + Float.toString(loc.getPitch());
      return retVal;
   }

   public String niceName(String origName) {
      return origName.replace("'", "").replace(" ", "");
   }

   public QrpgGuild findGuild(String guildName) {
      Iterator<QfMItem> var4 = this.mitems.iterator();

      while(var4.hasNext()) {
         QfMItem mitem = (QfMItem)var4.next();
         QrpgGuild guild = (QrpgGuild)mitem;
         if (guild.guildName.equalsIgnoreCase(guildName)) {
            return guild;
         }
      }

      return null;
   }

   public void giveGuild(Player pUser, String guildName, String playerName) {
      this.resetGuildRegionFlags(pUser, guildName);
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else {
         this.addMemberGuild(pUser, guildName, playerName);
         this.setRankGuild(pUser, guildName, playerName, "GrandElder");
         Player pTarget = this.core.getServer().getPlayer(playerName);
         if (pTarget != null) {
            this.msgCaller(pTarget, ChatColor.GREEN + "The guild " + ChatColor.GOLD + guildName + ChatColor.GREEN + " has been founded and you are now its " + ChatColor.LIGHT_PURPLE + "Grand Elder");
         }

      }
   }

   public void gotoGuild(Player pTarget, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pTarget, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else {
         guild.houseLoc.setWorld(this.core.getServer().getWorld("world"));
         this.msgCaller(pTarget, ChatColor.GOLD + (pTarget.hasPermission("QfCore.builder") ? "Going to the guild " : "Traveling to your guild ") + ChatColor.GREEN + guildName);
         pTarget.teleport(guild.houseLoc);
      }
   }

   public void sethomeGuild(Player pTarget, String guildName) {
      Location loc = pTarget.getLocation();
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pTarget, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
      } else if (!loc.getWorld().getName().equalsIgnoreCase("world")) {
         this.msgCaller(pTarget, ChatColor.RED + "Guild homes must be set in the spawn world");
      } else {
         String path = "guild." + guild.name;
         if (this.getConfig().contains(path)) {
            path = "guild." + guild.name + ".houseLoc";
            this.getConfig().set(path, this.loc2config("world", loc));
            guild.houseLoc = loc;
            this.saveConfig();
            this.msgCaller(pTarget, ChatColor.GREEN + "New /myguild home set here for: " + ChatColor.GOLD + guildName);
         } else {
            this.msgCaller(pTarget, ChatColor.RED + "Error - could not find the guild " + ChatColor.GOLD + guildName + " (id: " + guild.name + ")" + ChatColor.RED + " in the config file!");
         }

      }
   }

   public boolean isGuildName(String guildName) {
      return false;
   }

   public String nextGuildFileName() {
      int id = this.nextGuildId++;
      String nextName = "" + id;
      return nextName;
   }

   public void setupGuild(Player pUser, String guildName) {
      if (this.isGuildName(guildName)) {
         this.msgCaller(pUser, ChatColor.RED + "A guild with the name " + ChatColor.YELLOW + guildName + ChatColor.RED + " already exists.");
      } else {
         QrpgGuild guild = new QrpgGuild();
         guild.mgr = this;
         guild.doInit();
         guild.category = "Default";
         guild.subcategory = "Default";
         guild.guildName = guildName;
         guild.name = this.nextGuildFileName();
         guild.color = "f";
         guild.motd = "";
         guild.balance = 0.0D;
         guild.maxMembers = 15;
         guild.houseLoc = new Location(this.core.getServer().getWorld("world"), -2920.5D, 67.0D, -751.5D, -90.0F, -3.0F);
         guild.memberList = new ArrayList<String>();
         guild.inviteList = new ArrayList<String>();
         guild.requestList = new ArrayList<String>();
         String path = "guild." + guild.name;
         if (this.getConfig().contains(path)) {
            this.msgCaller(pUser, ChatColor.RED + "Critical Error, a guild with that name already exists");
         } else {
            this.getConfig().createSection(path);
            path = "guild." + guild.name + ".name";
            this.getConfig().createSection(path);
            this.getConfig().set(path, guild.guildName);
            path = "guild." + guild.name + ".balance";
            this.getConfig().createSection(path);
            this.getConfig().set(path, guild.balance);
            path = "guild." + guild.name + ".maxmembers";
            this.getConfig().createSection(path);
            this.getConfig().set(path, guild.maxMembers);
            path = "guild." + guild.name + ".color";
            this.getConfig().createSection(path);
            this.getConfig().set(path, guild.color);
            path = "guild." + guild.name + ".motd";
            this.getConfig().createSection(path);
            this.getConfig().set(path, guild.motd);
            path = "guild." + guild.name + ".houseLoc";
            this.getConfig().createSection(path);
            this.getConfig().set(path, this.loc2config("world", guild.houseLoc));
            path = "guild." + guild.name + ".members";
            this.getConfig().createSection(path);
            path = "guild." + guild.name + ".invites";
            this.getConfig().createSection(path);
            path = "guild." + guild.name + ".requests";
            this.getConfig().createSection(path);
            this.saveConfig();
            this.mitems.add(guild);
            this.msgCaller(pUser, ChatColor.GREEN + "New guild " + ChatColor.GOLD + guild.guildName + ChatColor.GREEN + " has been created.");
            pUser.performCommand("region define " + guildName + "wrap");
            pUser.performCommand("region define " + guildName + "island");
            pUser.performCommand("region define " + guildName + "house");
            this.msgCaller(pUser, ChatColor.GREEN + "New guild " + ChatColor.GOLD + guild.guildName + ChatColor.GREEN + " regions have been defined.");
         }
      }
   }

   public void resetGuildRegionFlags(Player pUser, String guildName) {
      pUser.performCommand("region setpriority " + guildName + "Wrap 40");
      pUser.performCommand("region setpriority " + guildName + "Island 50");
      pUser.performCommand("region setpriority " + guildName + "House 60");
      pUser.performCommand("region flag " + guildName + "Wrap entry allow");
      pUser.performCommand("region flag " + guildName + "Island entry deny");
      pUser.performCommand("region flag " + guildName + "House entry");
      pUser.performCommand("region flag " + guildName + "Wrap build");
      pUser.performCommand("region flag " + guildName + "Island build");
      pUser.performCommand("region flag " + guildName + "House build");
      pUser.performCommand("region flag " + guildName + "Wrap mob-spawning deny");
      pUser.performCommand("region flag " + guildName + "Island mob-spawning deny");
      pUser.performCommand("region flag " + guildName + "House mob-spawning deny");
      pUser.performCommand("region flag " + guildName + "Island build deny");
      pUser.performCommand("region flag " + guildName + "Wrap pvp deny");
      pUser.performCommand("region flag " + guildName + "Island pvp deny");
      pUser.performCommand("region flag " + guildName + "House pvp deny");
      pUser.performCommand("region flag " + guildName + "Wrap leaf-decay deny");
      pUser.performCommand("region flag " + guildName + "Island leaf-decay deny");
      pUser.performCommand("region flag " + guildName + "House leaf-decay deny");
      pUser.performCommand("region flag " + guildName + "Island use allow");
      pUser.performCommand("region flag " + guildName + "House use allow");
      this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully set safe flags for all guild regions for guild  " + ChatColor.GOLD + guildName);
   }

   public String getPlayerGuildName(Player pTarget) {
      String playerName = pTarget.getName();
      Iterator<QfMItem> var6 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var6.hasNext()) {
               return null;
            }

            QfMItem item = (QfMItem)var6.next();
            guild = (QrpgGuild)item;
         } while(guild.memberList == null);

         Iterator<String> var8 = guild.memberList.iterator();

         while(var8.hasNext()) {
            String mem = var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return guild.guildName;
            }
         }
      }
   }

   public String getPlayerGuildName(String playerName) {
      Iterator<QfMItem> var5 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var5.hasNext()) {
               return null;
            }

            QfMItem item = (QfMItem)var5.next();
            guild = (QrpgGuild)item;
         } while(guild.memberList == null);

         Iterator<String> var7 = guild.memberList.iterator();

         while(var7.hasNext()) {
            String mem = (String)var7.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return guild.guildName;
            }
         }
      }
   }

   public String getPlayerGuildRankAbrv(Player pUser) {
      String uStr = pUser.getUniqueId().toString();
      Iterator<QfMItem> var6 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var6.hasNext()) {
               return ChatColor.GRAY + "(unk)" + ChatColor.WHITE + ": ";
            }

            QfMItem item = (QfMItem)var6.next();
            guild = (QrpgGuild)item;
         } while(guild.memberList == null);

         Iterator<String> var8 = guild.memberList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[0].equalsIgnoreCase(uStr)) {
               String var9;
               switch((var9 = mems[2]).hashCode()) {
               case -2022887127:
                  if (var9.equals("Leader")) {
                     return ChatColor.AQUA + " (ldr)" + ChatColor.WHITE + ": ";
                  }
                  break;
               case -1993902406:
                  if (var9.equals("Member")) {
                     return ChatColor.DARK_AQUA + " (mem)" + ChatColor.WHITE + ": ";
                  }
                  break;
               case -1933912962:
                  if (var9.equals("GrandElder")) {
                     return ChatColor.LIGHT_PURPLE + " (geldr)" + ChatColor.WHITE + ": ";
                  }
                  break;
               case 67039722:
                  if (var9.equals("Elder")) {
                     return ChatColor.DARK_PURPLE + " (eld)" + ChatColor.WHITE + ": ";
                  }
                  break;
               case 333706937:
                  if (var9.equals("Initiate")) {
                     return ChatColor.DARK_GREEN + " (ini)" + ChatColor.WHITE + ": ";
                  }
               }

               return ChatColor.GRAY + " (unk)" + ChatColor.WHITE + ": ";
            }
         }
      }
   }

   public void removeAllInvites(String playerName) {
      Iterator<QfMItem> var7 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var7.hasNext()) {
               return;
            }

            QfMItem item = (QfMItem)var7.next();
            guild = (QrpgGuild)item;
         } while(guild.inviteList == null);

         Iterator<String> var9 = guild.inviteList.iterator();

         while(var9.hasNext()) {
            String mem = (String)var9.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               String path = "guild." + guild.name + ".invites";
               List<String> configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               guild.inviteList.remove(mem);
               this.removeAllInvites(playerName);
               return;
            }
         }
      }
   }

   public void removeAllRequests(String playerName) {
      Iterator<QfMItem> var7 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var7.hasNext()) {
               return;
            }

            QfMItem item = (QfMItem)var7.next();
            guild = (QrpgGuild)item;
         } while(guild.requestList == null);

         Iterator<String> var9 = guild.requestList.iterator();

         while(var9.hasNext()) {
            String mem = (String)var9.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               String path = "guild." + guild.name + ".requests";
               List<String> configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               guild.requestList.remove(mem);
               this.removeAllRequests(playerName);
               return;
            }
         }
      }
   }

   public void showInvitesGuild(Player pUser, String playerName) {
      String names = "";
      Iterator<QfMItem> var7 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var7.hasNext()) {
               if (names == "") {
                  this.msgCaller(pUser, ChatColor.YELLOW + "You currently have no guild invitations");
               } else {
                  this.msgCaller(pUser, ChatColor.YELLOW + "You currently have invitations from these guilds: " + names);
               }

               return;
            }

            QfMItem item = (QfMItem)var7.next();
            guild = (QrpgGuild)item;
         } while(guild.inviteList == null);

         Iterator<String> var9 = guild.inviteList.iterator();

         while(var9.hasNext()) {
            String mem = (String)var9.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               if (names != "") {
                  names = names + ChatColor.GRAY + ", ";
               }

               names = names + ChatColor.GOLD + guild.guildName;
            }
         }
      }
   }

   public void showRequestsGuild(Player pUser, String playerName) {
      Iterator<QfMItem> var6 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var6.hasNext()) {
               this.msgCaller(pUser, ChatColor.YELLOW + "You have not requested to join a guild");
               return;
            }

            QfMItem item = (QfMItem)var6.next();
            guild = (QrpgGuild)item;
         } while(guild.requestList == null);

         Iterator<String> var8 = guild.requestList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               this.msgCaller(pUser, ChatColor.YELLOW + "You have requested to join guild " + ChatColor.GOLD + guild.guildName);
               return;
            }
         }
      }
   }

   public boolean hasInvite(String guildName, String playerName) {
      Iterator<QfMItem> var6 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            do {
               if (!var6.hasNext()) {
                  return false;
               }

               QfMItem item = (QfMItem)var6.next();
               guild = (QrpgGuild)item;
            } while(!guild.guildName.equalsIgnoreCase(guildName));
         } while(guild.inviteList == null);

         Iterator<String> var8 = guild.inviteList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return true;
            }
         }
      }
   }

   public String hasRequestedGuild(String playerName) {
      Iterator<QfMItem> var5 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var5.hasNext()) {
               return null;
            }

            QfMItem item = (QfMItem)var5.next();
            guild = (QrpgGuild)item;
         } while(guild.requestList == null);

         Iterator<String> var7 = guild.requestList.iterator();

         while(var7.hasNext()) {
            String mem = (String)var7.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return guild.guildName;
            }
         }
      }
   }

   public boolean isMember(String guildName, String playerName) {
      Iterator<QfMItem> var6 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            do {
               if (!var6.hasNext()) {
                  return false;
               }

               QfMItem item = (QfMItem)var6.next();
               guild = (QrpgGuild)item;
            } while(!guild.guildName.equalsIgnoreCase(guildName));
         } while(guild.memberList == null);

         Iterator<String> var8 = guild.memberList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return true;
            }
         }
      }
   }

   public boolean isGuildMember(String playerName) {
      Iterator<QfMItem> var5 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            if (!var5.hasNext()) {
               return false;
            }

            QfMItem item = (QfMItem)var5.next();
            guild = (QrpgGuild)item;
         } while(guild.memberList == null);

         Iterator<String> var7 = guild.memberList.iterator();

         while(var7.hasNext()) {
            String mem = (String)var7.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return true;
            }
         }
      }
   }

   public void acceptInviteGuild(Player pUser, String guildName) {
      if (!this.hasInvite(guildName, pUser.getName())) {
         this.msgCaller(pUser, ChatColor.RED + "You have not been invited to the guild " + ChatColor.GOLD + guildName);
      } else {
         this.addMemberGuild(pUser, guildName, pUser.getName());
      }
   }

   public boolean declineInviteGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find a guild with the name " + ChatColor.GOLD + guildName);
         return false;
      } else if (!this.hasInvite(guildName, pUser.getName())) {
         this.msgCaller(pUser, ChatColor.RED + "You have not been invited to the guild " + ChatColor.GOLD + guildName);
         return false;
      } else {
         this.removeInviteGuild(pUser, guildName, pUser.getName());
         return true;
      }
   }

   public boolean addRequestGuild(Player pUser, String guildName, String playerName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
         return false;
      } else if (this.isGuildMember(playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of a guild");
         return false;
      } else if (this.hasInvite(guildName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " already has been invited");
         return false;
      } else {
         Player pTarget = this.core.getServer().getPlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
            return false;
         } else {
            String reqGuildName = this.hasRequestedGuild(playerName);
            if (reqGuildName != null) {
               this.msgCaller(pUser, ChatColor.BLUE + "Player " + ChatColor.GRAY + playerName + ChatColor.BLUE + " has already requested to join the guild " + ChatColor.GOLD + reqGuildName + ChatColor.BLUE + ".\nPlease cancel the request first by using: " + ChatColor.DARK_AQUA + "/myguild request cancel");
               return false;
            } else {
               String mem = pTarget.getUniqueId() + " " + playerName;
               guild.requestList.add(mem);
               String path = "guild." + guild.name + ".requests";
               List<String> configList = this.getConfig().getStringList(path);
               configList.add(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               if (!pUser.getName().equalsIgnoreCase(playerName)) {
                  this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " has requested to join the guild " + ChatColor.GOLD + guildName);
               }

               this.msgCaller(pTarget, ChatColor.GREEN + "You have requested to join the guild " + ChatColor.GOLD + guildName);
               this.core.sendGuildChat((Player)null, guildName, this.lkgm, " Player " + playerName + " has requested to join the guild.");
               return true;
            }
         }
      }
   }

   public void addInviteGuild(Player pUser, String guildName, String playerName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else if (this.isGuildMember(playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of a guild");
      } else if (this.hasInvite(guildName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " already has been invited");
      } else {
         Player pTarget = this.core.getServer().getPlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
         } else {
            String mem = pTarget.getUniqueId() + " " + playerName;
            guild.inviteList.add(mem);
            String path = "guild." + guild.name + ".invites";
            List<String> configList = this.getConfig().getStringList(path);
            configList.add(mem);
            this.getConfig().set(path, configList);
            this.saveConfig();
            this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " has been invited to the guild " + ChatColor.GOLD + guildName);
            if (pTarget != null) {
               this.msgCaller(pTarget, ChatColor.GREEN + "You have been invited to join the guild " + ChatColor.GOLD + guildName);
            }

         }
      }
   }

   public void acceptRequestGuild(Player pUser, String guildName, String playerName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else {
         /* getOfflinePlayer is deprecated, need to use UUID's
          * OfflinePlayer pTarget = this.core.getServer().getOfflinePlayer(playerName);
          * if (pTarget == null) {
          *     this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
          * } else */ 
         if (this.isGuildMember(playerName)) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of a guild");
         } else {
            String reqGuildName = this.hasRequestedGuild(playerName);
            if (reqGuildName != null && reqGuildName == guildName) {
               this.addMemberGuild(pUser, guildName, playerName);
            } else {
               this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " has not requested to join the guild " + ChatColor.GOLD + guildName);
            }
         }
      }
   }

   public void removeInviteGuild(Player pUser, String guildName, String playerName) {
      Iterator<QfMItem> var10 = this.mitems.iterator();

      while(true) {
         QrpgGuild guild;
         do {
            do {
               if (!var10.hasNext()) {
                  return;
               }

               QfMItem item = (QfMItem)var10.next();
               guild = (QrpgGuild)item;
            } while(!guild.guildName.equalsIgnoreCase(guildName));
         } while(guild.inviteList == null);

         Iterator<String> var12 = guild.inviteList.iterator();

         while(var12.hasNext()) {
            String mem = (String)var12.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               String path = "guild." + guild.name + ".invites";
               guild.inviteList.remove(mem);
               List<String> configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " has had their invite canceled for the guild " + ChatColor.GOLD + guildName);
               Player pTarget = this.core.getServer().getPlayer(playerName);
               if (pTarget != null) {
                  this.msgCaller(pTarget, ChatColor.YELLOW + "Your invitation to the guild " + ChatColor.GOLD + guildName + ChatColor.YELLOW + " has been canceled.");
               }

               return;
            }
         }
      }
   }

   public void listMembersGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
      } else {
         guild = this.findGuild(guildName);
         if (guild == null) {
            this.msgCaller(pUser, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
         } else if (guild.memberList.size() == 0) {
            this.msgCaller(pUser, ChatColor.YELLOW + "There are currently no members for the guild " + ChatColor.GOLD + guildName);
         } else {
            String rank5 = "";
            String rank4 = "";
            String rank3 = "";
            String rank2 = "";
            String rank1 = "";
            Iterator<String> var12 = guild.memberList.iterator();

            while(var12.hasNext()) {
               String mem = (String)var12.next();
               String[] mems = mem.split(" ");
               if ("GrandElder".startsWith(mems[2])) {
                  if (rank1 != "") {
                     rank1 = rank1 + ChatColor.GRAY + ", ";
                  }

                  rank1 = rank1 + ChatColor.LIGHT_PURPLE + mems[1];
               } else if ("Elder".startsWith(mems[2])) {
                  if (rank2 != "") {
                     rank2 = rank2 + ChatColor.GRAY + ", ";
                  }

                  rank2 = rank2 + ChatColor.DARK_PURPLE + mems[1];
               } else if ("Leader".startsWith(mems[2])) {
                  if (rank3 != "") {
                     rank3 = rank3 + ChatColor.GRAY + ", ";
                  }

                  rank3 = rank3 + ChatColor.AQUA + mems[1];
               } else if ("Member".startsWith(mems[2])) {
                  if (rank4 != "") {
                     rank4 = rank4 + ChatColor.GRAY + ", ";
                  }

                  rank4 = rank4 + ChatColor.DARK_AQUA + mems[1];
               } else if ("Initiate".startsWith(mems[2])) {
                  if (rank5 != "") {
                     rank5 = rank5 + ChatColor.GRAY + ", ";
                  }

                  rank5 = rank5 + ChatColor.DARK_GREEN + mems[1];
               }
            }

            String names = ChatColor.GOLD + " The guild " + ChatColor.YELLOW + guildName + ChatColor.GOLD + " currently has the following members: \n";
            if (rank1 == "") {
               rank1 = ChatColor.GRAY + "None";
            }

            if (rank2 == "") {
               rank2 = ChatColor.GRAY + "None";
            }

            if (rank3 == "") {
               rank3 = ChatColor.GRAY + "None";
            }

            if (rank4 == "") {
               rank4 = ChatColor.GRAY + "None";
            }

            if (rank5 == "") {
               rank5 = ChatColor.GRAY + "None";
            }

            names = names + ChatColor.GOLD + "GrandElders: " + rank1 + "\n";
            names = names + ChatColor.GOLD + "Elders: " + rank2 + "\n";
            names = names + ChatColor.GOLD + "Leaders: " + rank3 + "\n";
            names = names + ChatColor.GOLD + "Members: " + rank4 + "\n";
            names = names + ChatColor.GOLD + "Initiates: " + rank5 + "\n";
            this.msgCaller(pUser, names);
         }
      }
   }

   public void listInvitesGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
      } else if (guild.inviteList.size() == 0) {
         this.msgCaller(pUser, ChatColor.YELLOW + "There are currently no invites active for the guild " + ChatColor.GOLD + guildName);
      } else {
         String names = "";

         String mem;
         for(Iterator<String> var6 = guild.inviteList.iterator(); var6.hasNext(); names = names + ChatColor.DARK_GREEN + mem.split(" ")[1]) {
            mem = (String)var6.next();
            if (names != "") {
               names = names + ChatColor.GRAY + ", " + ChatColor.DARK_GREEN;
            }
         }

         this.msgCaller(pUser, ChatColor.YELLOW + "The guild " + ChatColor.GOLD + guildName + ChatColor.YELLOW + " currently has invited these players: " + names);
      }
   }

   public void listRequestsGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
      } else if (guild.requestList.size() == 0) {
         this.msgCaller(pUser, ChatColor.YELLOW + "There are currently no requests for the guild " + ChatColor.GOLD + guildName);
      } else {
         String names = "";

         String mem;
         for(Iterator<String> var6 = guild.requestList.iterator(); var6.hasNext(); names = names + ChatColor.AQUA + mem.split(" ")[1]) {
            mem = (String)var6.next();
            if (names != "") {
               names = names + ChatColor.GRAY + ", " + ChatColor.AQUA;
            }
         }

         this.msgCaller(pUser, ChatColor.DARK_AQUA + "The guild " + ChatColor.GOLD + guildName + ChatColor.DARK_AQUA + " currently has received requests to join from these players: " + names);
      }
   }

   public void addMemberGuild(Player pUser, String guildName, String playerName) {
      if (this.isMember(guildName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of guild " + ChatColor.GOLD + guildName);
      } else {
         QrpgGuild guild = this.findGuild(guildName);
         Player pTarget = this.core.getServer().getPlayer(playerName);
         /* deprecated, as i mentioned before
         OfflinePlayer pTarget = this.core.getServer().getOfflinePlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
         } else */
         if (pTarget == null) {
             this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located or is not online");
          } else if (guild.memberList.size() >= guild.maxMembers) {
            this.msgCaller(pUser, ChatColor.RED + "The guild " + ChatColor.GOLD + guildName + ChatColor.RED + " has reached its maximum member size");
         } else {
            this.removeAllInvites(playerName);
            this.removeAllRequests(playerName);
            String mem = pTarget.getUniqueId() + " " + playerName + " Initiate";
            guild.memberList.add(mem);
            String path = "guild." + guild.name + ".members";
            List<String> configList = this.getConfig().getStringList(path);
            configList.add(mem);
            this.getConfig().set(path, configList);
            this.saveConfig();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.chat.guild");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + guildName + "Island -w world " + playerName);
            this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " is now a member of the guild " + ChatColor.GOLD + guildName);
            if (pTarget != null && pTarget.isOnline()) {
               this.msgCaller((Player)pTarget, ChatColor.GREEN + "You are now a member of the " + ChatColor.GOLD + guildName + ChatColor.GREEN + " guild!");
            }

            this.core.sendGuildChat((Player)null, guildName, this.lkgm, " Player " + playerName + " has joined the guild!");
         }
      }
   }

   public void removeMemberGuild(Player pUser, String guildName, String playerName) {
      if (!this.isMember(guildName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is not a member of guild " + ChatColor.GOLD + guildName);
      } else {
         QrpgGuild guild = this.findGuild(guildName);
         Iterator<String> var10 = guild.memberList.iterator();

         while(var10.hasNext()) {
            String mem = (String)var10.next();
            String[] mems = mem.split(" ");
            if (mems[1].equalsIgnoreCase(playerName)) {
               guild.memberList.remove(mem);
               String path = "guild." + guild.name + ".members";
               List<String> configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.chat.guild");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.removemember");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.setrank");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.invite");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.uninvite");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.accept");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.requests.see");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.invites.see");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.members.see");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember " + guildName + "House -w world " + playerName);
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember " + guildName + "Island -w world " + playerName);
               this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " has been removed from the guild " + ChatColor.GOLD + guildName);
               Player pTarget = this.core.getServer().getPlayer(playerName);
               if (pTarget != null) {
                  this.msgCaller(pTarget, ChatColor.GREEN + "You are no longer a member of the guild " + ChatColor.GOLD + guildName);
               }

               this.core.sendGuildChat((Player)null, guildName, this.lkgm, " Player " + playerName + " is no longer part of the guild!");
               return;
            }
         }

         this.msgCaller(pUser, ChatColor.RED + "Could not locate player " + ChatColor.GRAY + playerName);
      }
   }

   public String getRankGuild(String guildName, String playerName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         return null;
      } else {
         Iterator<String> var6 = guild.memberList.iterator();

         while(var6.hasNext()) {
            String mem = (String)var6.next();
            String[] mems = mem.split(" ");
            if (mems[1].equalsIgnoreCase(playerName)) {
               return mems[2];
            }
         }

         return null;
      }
   }

   public void showRankGuild(Player pUser, String guildName, String playerName) {
      String rank = this.getRankGuild(guildName, playerName);
      if (rank == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the information");
      } else {
         this.msgCaller(pUser, ChatColor.GOLD + guildName + ChatColor.GREEN + " guild member " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " is currently rank " + ChatColor.YELLOW + rank);
      }
   }

   public void setRankGuild(Player pUser, String guildName, String playerName, String rankName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
      } else if (!this.isMember(guildName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is not a member of guild " + ChatColor.GOLD + guildName);
      } else {
         String newRank = null;
         if ("grandelder".startsWith(rankName.toLowerCase())) {
            newRank = "GrandElder";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.removemember");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.setrank");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.invite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.uninvite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.accept");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.requests.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.invites.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.members.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.bank.withdraw");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.expand");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.motd");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.balance.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + guildName + "House -w world " + playerName);
         } else if ("elder".startsWith(rankName.toLowerCase())) {
            newRank = "Elder";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.removemember");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.setrank");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.invite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.uninvite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.accept");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.requests.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.invites.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.members.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.bank.withdraw");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.expand");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.motd");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.balance.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + guildName + "House -w world " + playerName);
         } else if ("leader".startsWith(rankName.toLowerCase())) {
            newRank = "Leader";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.removemember");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.setrank");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.invite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.uninvite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.accept");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.requests.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.invites.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.members.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.withdraw");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.expand");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.motd");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.balance.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + guildName + "House -w world " + playerName);
         } else if ("member".startsWith(rankName.toLowerCase())) {
            newRank = "Member";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.removemember");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.setrank");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.invite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.uninvite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.accept");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.invites.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.requests.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.members.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.withdraw");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.expand");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.motd");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.balance.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + guildName + "House -w world " + playerName);
         } else if ("initiate".startsWith(rankName.toLowerCase())) {
            newRank = "Initiate";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.removemember");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.setrank");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.invite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.uninvite");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.accept");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.invites.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.requests.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.members.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.withdraw");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.expand");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.motd");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myguild.bank.balance.see");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember " + guildName + "House -w world " + playerName);
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myguild.bank.deposit");
         if (newRank == null) {
            this.msgCaller(pUser, ChatColor.RED + "It doesn't appear that " + ChatColor.GRAY + rankName + ChatColor.RED + " is a valid rank");
         } else {
            String path = "guild." + guild.name + ".members";
            List<String> configList = this.getConfig().getStringList(path);
            Iterator<String> var13 = guild.memberList.iterator();

            while(var13.hasNext()) {
               String mem = (String)var13.next();
               String[] mems = mem.split(" ");
               if (mems[1].equalsIgnoreCase(playerName)) {
                  guild.memberList.remove(mem);
                  configList.remove(mem);
                  String newMem = mems[0] + " " + mems[1] + " " + newRank;
                  guild.memberList.add(newMem);
                  configList.add(newMem);
                  this.getConfig().set(path, configList);
                  this.saveConfig();
                  this.msgCaller(pUser, ChatColor.GREEN + "Guild member " + ChatColor.YELLOW + playerName + ChatColor.GREEN + " is now rank " + ChatColor.YELLOW + newRank);
                  this.core.sendGuildChat((Player)null, guildName, this.lkgm, " Player " + playerName + " is now rank " + newRank);
                  Player pTarget = this.core.getServer().getPlayer(playerName);
                  if (pTarget != null) {
                     this.msgCaller(pTarget, ChatColor.GREEN + "Congratulations, you are now rank " + ChatColor.YELLOW + newRank + ChatColor.GREEN + " in your guild " + ChatColor.GOLD + guildName);
                  }

                  return;
               }
            }

         }
      }
   }

   public void showInfoGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else {
         this.msgCaller(pUser, ChatColor.GOLD + "Guild " + guildName + ":\n" + ChatColor.GRAY + "Bank Balance: " + ChatColor.GREEN + guild.niceBalance() + ChatColor.GRAY + "\nMax Members: " + ChatColor.YELLOW + guild.maxMembers);
      }

   }

   public void showBalanceBankGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else {
         this.msgCaller(pUser, ChatColor.GREEN + "Guild " + ChatColor.GOLD + guildName + ChatColor.GREEN + " has a bank balance of " + ChatColor.YELLOW + guild.niceBalance());
      }

   }

   public void doSetMaxMembersGuild(String guildName, int amount) {
      QrpgGuild guild = this.findGuild(guildName);
      guild.maxMembers = amount;
      String path = "guild." + guild.name + ".maxmembers";
      this.getConfig().set(path, guild.maxMembers);
      this.saveConfig();
   }

   public boolean doWithdrawBankGuild(String guildName, double amount) {
      QrpgGuild guild = this.findGuild(guildName);
      guild.balance -= amount;
      String path = "guild." + guild.name + ".balance";
      this.getConfig().set(path, guild.balance);
      this.saveConfig();
      return true;
   }

   public boolean doDepositBankGuild(String guildName, double amount) {
      QrpgGuild guild = this.findGuild(guildName);
      guild.balance += amount;
      String path = "guild." + guild.name + ".balance";
      this.getConfig().set(path, guild.balance);
      this.saveConfig();
      return true;
   }

   public void resetBankGuild(Player pUser, String guildName) {
      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate guild " + ChatColor.GOLD + guildName);
      } else {
         guild.balance = 0.0D;
         this.saveConfig();
         this.msgCaller(pUser, ChatColor.GREEN + "Guild + " + ChatColor.GOLD + guildName + ChatColor.GREEN + " now has a bank balance of " + ChatColor.YELLOW + "$0");
      }

   }

   public void expandMaxUsersGuild(Player pUser, String guildName, int maxNum) {
      double cashNeeded = 0.0D;
      String niceCashNeeded;
      switch(maxNum) {
      case 30:
         cashNeeded = 250000.0D;
         niceCashNeeded = "$250,000";
         break;
      case 60:
         cashNeeded = 900000.0D;
         niceCashNeeded = "$900,000";
         break;
      default:
         this.msgCaller(pUser, ChatColor.RED + "Guild expansion to " + maxNum + " members is not available at this time");
         return;
      }

      QrpgGuild guild = this.findGuild(guildName);
      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the guild " + ChatColor.GOLD + guildName);
      } else if (maxNum == 60 && guild.maxMembers < 30) {
         this.msgCaller(pUser, ChatColor.RED + "The guild " + ChatColor.GOLD + guildName + ChatColor.RED + " must expand to 30 members first");
      } else if (maxNum <= guild.maxMembers) {
         this.msgCaller(pUser, ChatColor.RED + "The guild " + ChatColor.GOLD + guildName + ChatColor.RED + " already has the ability to have " + ChatColor.YELLOW + guild.maxMembers + ChatColor.RED + " members");
      } else {
         if (guild.balance >= cashNeeded) {
            if (this.doWithdrawBankGuild(guildName, cashNeeded)) {
               this.doSetMaxMembersGuild(guildName, maxNum);
               this.msgCaller(pUser, ChatColor.GREEN + "Guild " + ChatColor.GOLD + guildName + ChatColor.GREEN + " now has the ability to have " + ChatColor.YELLOW + maxNum + ChatColor.GREEN + " members");
               this.core.sendGuildChat((Player)null, guildName, this.lkgm, " The guild now has the ability to have " + maxNum + " members");
            } else {
               this.msgCaller(pUser, ChatColor.RED + "Could not withdraw the needed funds from the guild " + ChatColor.GOLD + guildName + ChatColor.RED + " bank account");
            }
         } else {
            this.msgCaller(pUser, ChatColor.RED + "Insufficient funds. Could not withdraw the needed " + ChatColor.YELLOW + niceCashNeeded + ChatColor.RED + " from the guild " + ChatColor.GOLD + guildName + ChatColor.RED + " bank account");
         }

      }
   }

   public void withdrawBankGuild(Player pUser, String guildName, double amount) {
      QrpgGuild guild = this.findGuild(guildName);
      String playerName = pUser.getName();
      if (guild.balance < amount) {
         this.msgCaller(pUser, ChatColor.RED + "Insufficient funds in the guild " + ChatColor.GOLD + guild.guildName + ChatColor.RED + " bank account");
      } else {
         if (this.doWithdrawBankGuild(guildName, amount)) {
            if (this.core.payPlayer(pUser, amount, true)) {
               this.msgCaller(pUser, ChatColor.GREEN + "You have withdrawn " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " from the guild " + ChatColor.GOLD + guildName + ChatColor.GREEN + " bank account");
               this.core.sendGuildChat((Player)null, guildName, this.lkgm, " Player " + playerName + " has withdrawn $" + amount + " from the guild bank account");
            } else {
               this.doDepositBankGuild(guildName, amount);
               this.msgCaller(pUser, ChatColor.RED + "Could not deposit into the personal account for player " + ChatColor.YELLOW + playerName);
            }
         } else {
            this.msgCaller(pUser, ChatColor.RED + "Could not withdraw " + ChatColor.YELLOW + "$" + amount + ChatColor.RED + " from the guild " + ChatColor.GOLD + guildName + ChatColor.RED + " bank account");
         }

      }
   }

   public void depositBankGuild(Player pUser, String guildName, double amount) {
      this.findGuild(guildName);
      String playerName = pUser.getName();
      if (this.core.balancePlayer(pUser) < amount) {
         this.msgCaller(pUser, ChatColor.RED + "Insufficient funds in the personal account for player " + ChatColor.YELLOW + playerName);
      } else {
         if (this.doDepositBankGuild(guildName, amount)) {
            if (this.core.unpayPlayer(pUser, amount, true)) {
               this.msgCaller(pUser, ChatColor.GREEN + "You have deposited " + ChatColor.YELLOW + "$" + amount + ChatColor.GREEN + " into the guild " + ChatColor.GOLD + guildName + ChatColor.GREEN + " bank account");
               this.core.sendGuildChat((Player)null, guildName, this.lkgm, " Player " + playerName + " has deposited $" + amount + " into the guild bank account");
            } else {
               this.doWithdrawBankGuild(guildName, amount);
               this.msgCaller(pUser, ChatColor.RED + "Could not withdraw from the personal account for player " + ChatColor.YELLOW + playerName);
            }
         } else {
            this.msgCaller(pUser, ChatColor.RED + "Could not deposit " + ChatColor.YELLOW + "$" + amount + ChatColor.RED + " into the guild " + ChatColor.GOLD + guildName + ChatColor.RED + " bank account");
         }

      }
   }

   public void setMotdGuild(Player pUser, String[] args, String guildInName) {
      int argStart = 1;
      String guildName;
      QrpgGuild guild;
      if (guildInName == null) {
         guildName = this.getPlayerGuildName(pUser.getName());
         guild = this.findGuild(guildName);
      } else {
         guildName = guildInName;
         guild = this.findGuild(guildInName);
         ++argStart;
      }

      if (guild == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the guild " + ChatColor.GOLD + guildName);
      } else {
         String newMotd = "";

         for(int i = argStart; i < args.length; ++i) {
            newMotd = newMotd + args[i] + " ";
         }

         newMotd.trim();
         if (!newMotd.equalsIgnoreCase("clear") && !newMotd.equalsIgnoreCase("clear ")) {
            newMotd = ChatColor.translateAlternateColorCodes('&', newMotd);
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "The motd has been set for the guild " + ChatColor.GOLD + guild.guildName);
         } else {
            newMotd = "";
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "The motd has been cleared for the guild " + ChatColor.GOLD + guild.guildName);
         }

         guild.motd = newMotd;
         String path = "guild." + guild.name + ".motd";
         this.getConfig().set(path, guild.motd);
         this.saveConfig();
      }
   }

   public void sendAllMotd() {
      Iterator<QfMItem> var3 = this.mitems.iterator();

      while(var3.hasNext()) {
         QfMItem mitem = (QfMItem)var3.next();
         QrpgGuild guild = (QrpgGuild)mitem;
         if (guild.motd != null && guild.motd != "") {
            this.core.sendGuildChat((Player)null, guild.guildName, ChatColor.DARK_PURPLE + "From the " + ChatColor.GOLD + guild.guildName + ChatColor.DARK_PURPLE + " elders: " + ChatColor.GOLD + guild.motd, (String)null);
         }
      }

   }
}
