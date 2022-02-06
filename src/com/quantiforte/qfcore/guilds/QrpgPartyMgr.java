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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QrpgPartyMgr extends QfManager implements CommandExecutor {
   int nextPartyId;
   public String lkgm;

   public void doInit(QfCore newCore) {
      this.configFileName = "config_parties.yml";
      this.mitems = new ArrayList();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      this.lkgm = ChatColor.LIGHT_PURPLE + "L" + ChatColor.GRAY + "&" + ChatColor.LIGHT_PURPLE + "K" + ChatColor.YELLOW + "Party Manager";
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "party";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.DARK_GREEN + "Current parties:\n" + ChatColor.GREEN : ChatColor.DARK_GREEN + "Current " + ChatColor.YELLOW + cat + ChatColor.GREEN + " parties:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      String partyName;
      switch(cmdName.hashCode()) {
      case 106437350:
         if (cmdName.equals("party")) {
            if (args.length >= 1 && "setup".startsWith(args[0])) {
               if (args.length == 2) {
                  partyName = args[1];
                  this.setupParty(pUser, partyName);
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "Please specify a party name");
               }

               return true;
            }

            if (args.length >= 1 && "reset".startsWith(args[0])) {
               if (args.length == 2) {
                  partyName = args[1];
                  this.resetParty(pUser, partyName);
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "Please specify a party name");
               }

               return true;
            }

            if (args.length >= 1 && "list".startsWith(args[0])) {
               if (pUser != null && !pUser.hasPermission("Qrpg.party.list")) {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               } else {
                  String dispStr = this.listItems((String)null);
                  this.msgCaller(pUser, dispStr);
               }

               return true;
            }

            if (args.length == 2 && "info".startsWith(args[0])) {
               if (pUser != null && !pUser.hasPermission("Qrpg.party.info")) {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               } else {
                  this.showInfoParty(pUser, args[1]);
               }

               return true;
            }

            if (args.length == 2 && "members".startsWith(args[0])) {
               if (pUser != null && !pUser.hasPermission("Qrpg.party.members")) {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               } else {
                  this.listMembersParty(pUser, args[1]);
               }

               return true;
            }

            if (args.length == 2 && "invites".startsWith(args[0])) {
               if (pUser != null && !pUser.hasPermission("Qrpg.party.invites")) {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               } else {
                  this.listInvitesParty(pUser, args[1]);
               }

               return true;
            }

            if (args.length == 2 && "requests".startsWith(args[0])) {
               if (pUser != null && !pUser.hasPermission("Qrpg.party.requests")) {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               } else {
                  this.listRequestsParty(pUser, args[1]);
               }

               return true;
            }

            if (args.length == 3 && "invite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.invites")) {
                  this.addInviteParty(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "uninvite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.invites")) {
                  this.removeInviteParty(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "add".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.members")) {
                  this.addMemberParty(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "reset".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.reset")) {
                  this.resetParty(pUser, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "remove".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.members")) {
                  this.removeMemberParty(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 4 && "setrank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.ranks")) {
                  this.setRankParty(pUser, args[3], args[1], args[2]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "rank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.ranks")) {
                  this.showRankParty(pUser, args[2], args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "setmaxmembers".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.setmaxmembers")) {
                  int num = 0;

                  try {
                     num = Integer.parseInt(args[1]);
                  } catch (NumberFormatException var26) {
                     this.msgCaller(pUser, ChatColor.RED + "Could not read the number properly. Please make sure you are using a positive number with no formatting or decimal points.");
                  } finally {
                     this.doSetMaxMembersParty(args[2], num);
                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "pvp".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.party.pvp")) {
                  if ("off".startsWith(args[1])) {
                     this.pvpParty(pUser, args[2], false);
                  } else if ("on".startsWith(args[1])) {
                     this.pvpParty(pUser, args[2], true);
                  } else {
                     this.msgCaller(pUser, ChatColor.RED + "Please use either " + ChatColor.YELLOW + "/party pvp on <partyname>" + ChatColor.RED + " or " + ChatColor.YELLOW + "/myparty pvp off <partyname>");
                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 1) {
               OfflinePlayer pOffTarget = this.core.getServer().getOfflinePlayer(args[0]);
               if (pOffTarget == null) {
                  this.msgCaller(pUser, ChatColor.RED + "Could not locate player " + ChatColor.YELLOW + args[0]);
                  return true;
               }

               partyName = this.getPlayerPartyId(args[0]);
               if (partyName == null) {
                  this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " is not in a party");
               } else {
                  this.msgCaller(pUser, ChatColor.GREEN + "Player " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " is in party " + ChatColor.GOLD + partyName);
               }

               return true;
            }

            this.msgCaller(pUser, ChatColor.RED + "Unknown /party sub-command");
            return true;
         }
         break;
      case 1524218042:
         if (cmdName.equals("myparty")) {
            partyName = this.getPlayerPartyId(pUser);
            if (partyName == null) {
               if (args.length == 0) {
                  this.showInvitesParty(pUser, pUser.getName());
                  this.showRequestsParty(pUser, pUser.getName());
                  this.msgCaller(pUser, ChatColor.DARK_AQUA + "Use: " + ChatColor.GRAY + "/myparty accept <partyname> " + ChatColor.DARK_AQUA + "to accept an invitation");
                  return true;
               }

               if (args.length == 2) {
                  if ("accept".startsWith(args[0].toLowerCase())) {
                     this.acceptInviteParty(pUser, args[1]);
                     return true;
                  }

                  if ("decline".startsWith(args[0].toLowerCase())) {
                     this.declineInviteParty(pUser, args[1]);
                     return true;
                  }

                  if ("request".startsWith(args[0].toLowerCase())) {
                     if ("cancel".equalsIgnoreCase(args[1].toLowerCase())) {
                        this.removeAllRequests(pUser.getName());
                        this.msgCaller(pUser, ChatColor.YELLOW + "You have canceled your request to join a party");
                     } else {
                        String coolId = "party_request";
                        long coolTime = 900L;
                        String coolTimeLeft = this.qfcore.cooldownMgr.checkCooldown(pUser, coolId, false);
                        if (coolTimeLeft == null && this.addRequestParty(pUser, args[1], pUser.getName())) {
                           this.qfcore.cooldownMgr.addCooldown(pUser, coolId, coolTime);
                        }
                     }

                     return true;
                  }
               }

               this.msgCaller(pUser, ChatColor.GRAY + "Could not understand your /myparty command. Please use either: " + ChatColor.DARK_AQUA + "/myparty " + ChatColor.GRAY + "(to see your request/invites), or\n" + ChatColor.DARK_AQUA + "/myparty [accept|decline|request] <partyname>");
               return true;
            }

            if (args.length == 0) {
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "You are in party " + ChatColor.GREEN + this.getPlayerPartyId(pUser) + ChatColor.GRAY + "\nPlease use: " + ChatColor.DARK_AQUA + "/p " + ChatColor.GRAY + "(for party chat), or\n" + ChatColor.DARK_AQUA + "/myparty [members|invites|requests|invite|remove|setrank|leave|pvp]");
               return true;
            }

            if (args.length == 1 && "members".startsWith(args[0])) {
               this.listMembersParty(pUser, partyName);
               return true;
            }

            if (args.length == 1 && "invites".startsWith(args[0])) {
               this.listInvitesParty(pUser, partyName);
               return true;
            }

            if (args.length == 1 && "requests".startsWith(args[0])) {
               this.listRequestsParty(pUser, partyName);
               return true;
            }

            if (args.length == 2 && "invite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myparty.leader")) {
                  this.addInviteParty(pUser, partyName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "accept".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myparty.leader")) {
                  this.acceptRequestParty(pUser, partyName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "pvp".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myparty.leader")) {
                  if ("off".startsWith(args[1])) {
                     this.pvpParty(pUser, partyName, false);
                  } else if ("on".startsWith(args[1])) {
                     this.pvpParty(pUser, partyName, true);
                  } else {
                     this.msgCaller(pUser, ChatColor.RED + "Please use either " + ChatColor.YELLOW + "/myparty pvp on" + ChatColor.RED + " or " + ChatColor.YELLOW + "/myparty pvp off");
                  }
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "remove".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myparty.leader")) {
                  this.removeMemberParty(pUser, partyName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "uninvite".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myparty.leader")) {
                  this.removeInviteParty(pUser, partyName, args[1]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 3 && "setrank".startsWith(args[0])) {
               if (pUser.hasPermission("Qrpg.myparty.leader")) {
                  this.setRankParty(pUser, partyName, args[1], args[2]);
               } else {
                  this.msgCaller(pUser, ChatColor.GRAY + "You do not have permission to use that command");
               }

               return true;
            }

            if (args.length == 2 && "rank".startsWith(args[0])) {
               this.showRankParty(pUser, partyName, args[1]);
               return true;
            }

            if (args.length == 1 && "rank".startsWith(args[0])) {
               this.showRankParty(pUser, partyName, pUser.getName());
               return true;
            }

            if (args.length == 2 && "leave".startsWith(args[0])) {
               this.removeMemberParty(pUser, args[1], pUser.getName());
               return true;
            }

            this.msgCaller(pUser, ChatColor.GRAY + "Could not understand your /myparty command. Please use either: " + ChatColor.DARK_AQUA + "/p " + ChatColor.GRAY + "(for party chat), or\n" + ChatColor.DARK_AQUA + "/myparty [members|invites|requests|invite|remove|setrank|leave]");
            return true;
         }
      }

      return false;
   }

   public void readConfig() {
      this.mitems.clear();
      this.triggerLocs.clear();
      Set keys = this.getConfig().getConfigurationSection("party").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " parties in config_parties.yml");
      String[] names = (String[])keys.toArray(new String[keys.size()]);
      String[] var10 = names;
      int var9 = names.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String name = var10[var8];
         this.core.getLogger().info("adding party: " + name);
         QrpgParty party = new QrpgParty();
         party.mgr = this;
         party.doInit();
         party.name = name;
         party.memberList = new ArrayList();
         party.inviteList = new ArrayList();
         party.requestList = new ArrayList();
         String path = "party." + name + ".name";
         if (this.getConfig().contains(path)) {
            party.partyName = this.getConfig().getString(path);
         }

         path = "party." + name + ".maxmembers";
         if (this.getConfig().contains(path)) {
            party.maxMembers = Integer.parseInt(this.getConfig().getString(path));
         }

         path = "party." + name + ".category";
         if (this.getConfig().contains(path)) {
            party.category = this.getConfig().getString(path);
         }

         path = "party." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            party.subcategory = this.getConfig().getString(path);
         }

         path = "party." + name + ".members";
         List inList;
         String item;
         Iterator var12;
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               item = (String)var12.next();
               party.memberList.add(item);
            }
         }

         path = "party." + name + ".invites";
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               item = (String)var12.next();
               party.inviteList.add(item);
            }
         }

         path = "party." + name + ".requests";
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               item = (String)var12.next();
               party.requestList.add(item);
            }
         }

         this.mitems.add(party);
         this.nextPartyId = Integer.parseInt(party.name) + 1;
      }

   }

   public void saveConfig() {
      try {
         this.getConfig().save(this.ourConfigFile);
         this.core.getServer().getLogger().info("config file written !!!!");
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

   public QrpgParty findParty(String partyName) {
      Iterator var4 = this.mitems.iterator();

      while(var4.hasNext()) {
         QfMItem mitem = (QfMItem)var4.next();
         QrpgParty party = (QrpgParty)mitem;
         if (party.partyName.equalsIgnoreCase(partyName)) {
            return party;
         }
      }

      return null;
   }

   public boolean isPartyName(String partyName) {
      return false;
   }

   public String nextPartyFileName() {
      int id = this.nextPartyId++;
      String nextName = "" + id;
      return nextName;
   }

   public void setupParty(Player pUser, String partyName) {
      if (this.isPartyName(partyName)) {
         this.msgCaller(pUser, ChatColor.RED + "A party with the name " + ChatColor.YELLOW + partyName + ChatColor.RED + " already exists.");
      } else {
         QrpgParty party = new QrpgParty();
         party.mgr = this;
         party.doInit();
         party.category = "Default";
         party.subcategory = "Default";
         party.partyName = partyName;
         party.name = this.nextPartyFileName();
         party.memberList = new ArrayList();
         party.inviteList = new ArrayList();
         party.requestList = new ArrayList();
         String path = "party." + party.name;
         if (this.getConfig().contains(path)) {
            this.msgCaller(pUser, ChatColor.RED + "Critical Error, a party with that name already exists");
         } else {
            this.getConfig().createSection(path);
            path = "party." + party.name + ".name";
            this.getConfig().createSection(path);
            this.getConfig().set(path, party.partyName);
            path = "party." + party.name + ".maxmembers";
            this.getConfig().createSection(path);
            this.getConfig().set(path, party.maxMembers);
            path = "party." + party.name + ".members";
            this.getConfig().createSection(path);
            path = "party." + party.name + ".invites";
            this.getConfig().createSection(path);
            path = "party." + party.name + ".requests";
            this.getConfig().createSection(path);
            this.saveConfig();
            this.mitems.add(party);
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "New party " + ChatColor.GREEN + party.partyName + ChatColor.DARK_GREEN + " has been created.");
         }
      }
   }

   public void resetParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the party " + ChatColor.GREEN + partyName);
      } else {
         this.removeAllMembersForParty(pUser, partyName);
         party.inviteList.clear();
         party.requestList.clear();
         List configList = new ArrayList();
         String path = "party." + party.name + ".members";
         this.getConfig().set(path, configList);
         path = "party." + party.name + ".invites";
         this.getConfig().set(path, configList);
         path = "party." + party.name + ".requests";
         this.getConfig().set(path, configList);
         this.saveConfig();
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully reset the party  " + ChatColor.GREEN + partyName);
      }
   }

   public String getPlayerPartyId(Player pTarget) {
      String playerName = pTarget.getName();
      Iterator var6 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var6.hasNext()) {
               return null;
            }

            QfMItem item = (QfMItem)var6.next();
            party = (QrpgParty)item;
         } while(party.memberList == null);

         Iterator var8 = party.memberList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return party.partyName;
            }
         }
      }
   }

   public String getPlayerPartyId(String playerName) {
      Iterator var5 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var5.hasNext()) {
               return null;
            }

            QfMItem item = (QfMItem)var5.next();
            party = (QrpgParty)item;
         } while(party.memberList == null);

         Iterator var7 = party.memberList.iterator();

         while(var7.hasNext()) {
            String mem = (String)var7.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return party.partyName;
            }
         }
      }
   }

   public String getPlayerPartyRankAbrv(Player pUser) {
      String uStr = pUser.getUniqueId().toString();
      Iterator var6 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var6.hasNext()) {
               return ChatColor.GRAY + "(unk)" + ChatColor.WHITE + ": ";
            }

            QfMItem item = (QfMItem)var6.next();
            party = (QrpgParty)item;
         } while(party.memberList == null);

         Iterator var8 = party.memberList.iterator();

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
               default:
                  return ChatColor.DARK_AQUA + " (mem)" + ChatColor.WHITE + ": ";
               }
            }
         }
      }
   }

   public void removeAllInvites(String playerName) {
      Iterator var7 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var7.hasNext()) {
               return;
            }

            QfMItem item = (QfMItem)var7.next();
            party = (QrpgParty)item;
         } while(party.inviteList == null);

         Iterator var9 = party.inviteList.iterator();

         while(var9.hasNext()) {
            String mem = (String)var9.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               String path = "party." + party.name + ".invites";
               List configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               party.inviteList.remove(mem);
               this.removeAllInvites(playerName);
               return;
            }
         }
      }
   }

   public void removeAllRequests(String playerName) {
      Iterator var7 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var7.hasNext()) {
               return;
            }

            QfMItem item = (QfMItem)var7.next();
            party = (QrpgParty)item;
         } while(party.requestList == null);

         Iterator var9 = party.requestList.iterator();

         while(var9.hasNext()) {
            String mem = (String)var9.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               String path = "party." + party.name + ".requests";
               List configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               party.requestList.remove(mem);
               this.removeAllRequests(playerName);
               return;
            }
         }
      }
   }

   public void showInvitesParty(Player pUser, String playerName) {
      String names = "";
      Iterator var7 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var7.hasNext()) {
               if (names == "") {
                  this.msgCaller(pUser, ChatColor.YELLOW + "You currently have no party invitations");
               } else {
                  this.msgCaller(pUser, ChatColor.YELLOW + "You currently have invitations from these parties: " + names);
               }

               return;
            }

            QfMItem item = (QfMItem)var7.next();
            party = (QrpgParty)item;
         } while(party.inviteList == null);

         Iterator var9 = party.inviteList.iterator();

         while(var9.hasNext()) {
            String mem = (String)var9.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               if (names != "") {
                  names = names + ChatColor.GRAY + ", ";
               }

               names = names + ChatColor.GREEN + party.partyName;
            }
         }
      }
   }

   public void showRequestsParty(Player pUser, String playerName) {
      Iterator var6 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var6.hasNext()) {
               this.msgCaller(pUser, ChatColor.YELLOW + "You have not requested to join a party");
               return;
            }

            QfMItem item = (QfMItem)var6.next();
            party = (QrpgParty)item;
         } while(party.requestList == null);

         Iterator var8 = party.requestList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               this.msgCaller(pUser, ChatColor.YELLOW + "You have requested to join party " + ChatColor.GREEN + party.partyName);
               return;
            }
         }
      }
   }

   public boolean hasInvite(String partyName, String playerName) {
      Iterator var6 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            do {
               if (!var6.hasNext()) {
                  return false;
               }

               QfMItem item = (QfMItem)var6.next();
               party = (QrpgParty)item;
            } while(!party.partyName.equalsIgnoreCase(partyName));
         } while(party.inviteList == null);

         Iterator var8 = party.inviteList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return true;
            }
         }
      }
   }

   public String hasRequestedParty(String playerName) {
      Iterator var5 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var5.hasNext()) {
               return null;
            }

            QfMItem item = (QfMItem)var5.next();
            party = (QrpgParty)item;
         } while(party.requestList == null);

         Iterator var7 = party.requestList.iterator();

         while(var7.hasNext()) {
            String mem = (String)var7.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return party.partyName;
            }
         }
      }
   }

   public boolean isMember(String partyName, String playerName) {
      Iterator var6 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            do {
               if (!var6.hasNext()) {
                  return false;
               }

               QfMItem item = (QfMItem)var6.next();
               party = (QrpgParty)item;
            } while(!party.partyName.equalsIgnoreCase(partyName));
         } while(party.memberList == null);

         Iterator var8 = party.memberList.iterator();

         while(var8.hasNext()) {
            String mem = (String)var8.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return true;
            }
         }
      }
   }

   public boolean isPartyMember(String playerName) {
      Iterator var5 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            if (!var5.hasNext()) {
               return false;
            }

            QfMItem item = (QfMItem)var5.next();
            party = (QrpgParty)item;
         } while(party.memberList == null);

         Iterator var7 = party.memberList.iterator();

         while(var7.hasNext()) {
            String mem = (String)var7.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               return true;
            }
         }
      }
   }

   public void acceptInviteParty(Player pUser, String partyName) {
      if (!this.hasInvite(partyName, pUser.getName())) {
         this.msgCaller(pUser, ChatColor.RED + "You have not been invited to the party " + ChatColor.GREEN + partyName);
      } else {
         this.addMemberParty(pUser, partyName, pUser.getName());
      }
   }

   public boolean declineInviteParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find a party with the name " + ChatColor.GREEN + partyName);
         return false;
      } else if (!this.hasInvite(partyName, pUser.getName())) {
         this.msgCaller(pUser, ChatColor.RED + "You have not been invited to the party " + ChatColor.GREEN + partyName);
         return false;
      } else {
         this.removeInviteParty(pUser, partyName, pUser.getName());
         return true;
      }
   }

   public boolean addRequestParty(Player pUser, String partyName, String playerName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the party " + ChatColor.GREEN + partyName);
         return false;
      } else if (this.isPartyMember(playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of a party");
         return false;
      } else if (this.hasInvite(partyName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " already has been invited");
         return false;
      } else {
         OfflinePlayer pTarget = this.core.getServer().getOfflinePlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
            return false;
         } else {
            String reqPartyName = this.hasRequestedParty(playerName);
            if (reqPartyName != null) {
               this.msgCaller(pUser, ChatColor.GRAY + "Player " + ChatColor.GRAY + playerName + ChatColor.GRAY + " has already requested to join the party " + ChatColor.GREEN + reqPartyName + ChatColor.GRAY + ".\nPlease cancel the request first by using: " + ChatColor.DARK_AQUA + "/myparty request cancel");
               return false;
            } else {
               String mem = pTarget.getUniqueId() + " " + playerName;
               party.requestList.add(mem);
               String path = "party." + party.name + ".requests";
               List configList = this.getConfig().getStringList(path);
               configList.add(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               if (!pUser.getName().equalsIgnoreCase(playerName)) {
                  this.msgCaller(pUser, ChatColor.DARK_GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " has requested to join the party " + ChatColor.GREEN + partyName);
               }

               if (pTarget.isOnline()) {
                  this.msgCaller(pTarget.getPlayer(), ChatColor.DARK_GREEN + "You have requested to join the party " + ChatColor.GREEN + partyName);
               }

               this.core.sendPartyChat((Player)null, partyName, this.lkgm, " Player " + playerName + " has requested to join the party.");
               return true;
            }
         }
      }
   }

   public void addInviteParty(Player pUser, String partyName, String playerName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the party " + ChatColor.GREEN + partyName);
      } else if (this.isPartyMember(playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of a party");
      } else if (this.hasInvite(partyName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " already has been invited");
      } else {
         OfflinePlayer pTarget = this.core.getServer().getOfflinePlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
         } else {
            String mem = pTarget.getUniqueId() + " " + playerName;
            party.inviteList.add(mem);
            String path = "party." + party.name + ".invites";
            List configList = this.getConfig().getStringList(path);
            configList.add(mem);
            this.getConfig().set(path, configList);
            this.saveConfig();
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " has been invited to the party " + ChatColor.GREEN + partyName);
            if (pTarget.isOnline()) {
               this.msgCaller(pTarget.getPlayer(), ChatColor.DARK_GREEN + "You have been invited to join the party " + ChatColor.GREEN + partyName);
            }

         }
      }
   }

   public void acceptRequestParty(Player pUser, String partyName, String playerName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the party " + ChatColor.GREEN + partyName);
      } else {
         OfflinePlayer pTarget = this.core.getServer().getOfflinePlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " could not be located");
         } else if (this.isPartyMember(playerName)) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of a party");
         } else {
            String reqPartyName = this.hasRequestedParty(playerName);
            if (reqPartyName != null && reqPartyName == partyName) {
               this.addMemberParty(pUser, partyName, playerName);
            } else {
               this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " has not requested to join the party " + ChatColor.GREEN + partyName);
            }
         }
      }
   }

   public void removeInviteParty(Player pUser, String partyName, String playerName) {
      Iterator var10 = this.mitems.iterator();

      while(true) {
         QrpgParty party;
         do {
            do {
               if (!var10.hasNext()) {
                  return;
               }

               QfMItem item = (QfMItem)var10.next();
               party = (QrpgParty)item;
            } while(!party.partyName.equalsIgnoreCase(partyName));
         } while(party.inviteList == null);

         Iterator var12 = party.inviteList.iterator();

         while(var12.hasNext()) {
            String mem = (String)var12.next();
            String[] mems = mem.split(" ");
            if (mems != null && mems.length >= 2 && mems[1].equalsIgnoreCase(playerName)) {
               String path = "party." + party.name + ".invites";
               party.inviteList.remove(mem);
               List configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " has had their invite canceled for the party " + ChatColor.GREEN + partyName);
               Player pTarget = this.core.getServer().getPlayer(playerName);
               if (pTarget != null) {
                  this.msgCaller(pTarget, ChatColor.YELLOW + "Your invitation to the party " + ChatColor.GREEN + partyName + ChatColor.YELLOW + " has been canceled.");
               }

               return;
            }
         }
      }
   }

   public void listMembersParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the party " + ChatColor.GREEN + partyName);
      } else {
         party = this.findParty(partyName);
         if (party == null) {
            this.msgCaller(pUser, ChatColor.RED + "Could not find the party " + ChatColor.GREEN + partyName);
         } else if (party.memberList.size() == 0) {
            this.msgCaller(pUser, ChatColor.YELLOW + "There are currently no members for the party " + ChatColor.GREEN + partyName);
         } else {
            String rank2 = "";
            String rank1 = "";
            Iterator var9 = party.memberList.iterator();

            while(var9.hasNext()) {
               String mem = (String)var9.next();
               String[] mems = mem.split(" ");
               if ("Leader".startsWith(mems[2])) {
                  if (rank1 != "") {
                     rank1 = rank1 + ChatColor.GRAY + ", ";
                  }

                  rank1 = rank1 + ChatColor.AQUA + mems[1];
               } else {
                  if (rank2 != "") {
                     rank2 = rank2 + ChatColor.GRAY + ", ";
                  }

                  rank2 = rank2 + ChatColor.DARK_AQUA + mems[1];
               }
            }

            String names = ChatColor.GOLD + " The party " + ChatColor.YELLOW + partyName + ChatColor.GOLD + " currently has the following members: \n";
            if (rank1 == "") {
               rank1 = ChatColor.GRAY + "None";
            }

            if (rank2 == "") {
               rank2 = ChatColor.GRAY + "None";
            }

            names = names + ChatColor.GOLD + "Leaders: " + rank1 + "\n";
            names = names + ChatColor.GOLD + "Members: " + rank2 + "\n";
            this.msgCaller(pUser, names);
         }
      }
   }

   public void listInvitesParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the party " + ChatColor.GREEN + partyName);
      } else if (party.inviteList.size() == 0) {
         this.msgCaller(pUser, ChatColor.YELLOW + "There are currently no invites active for the party " + ChatColor.GREEN + partyName);
      } else {
         String names = "";

         String mem;
         for(Iterator var6 = party.inviteList.iterator(); var6.hasNext(); names = names + ChatColor.DARK_GREEN + mem.split(" ")[1]) {
            mem = (String)var6.next();
            if (names != "") {
               names = names + ChatColor.GRAY + ", " + ChatColor.DARK_GREEN;
            }
         }

         this.msgCaller(pUser, ChatColor.YELLOW + "The party " + ChatColor.GREEN + partyName + ChatColor.YELLOW + " currently has invited these players: " + names);
      }
   }

   public void listRequestsParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the party " + ChatColor.GREEN + partyName);
      } else if (party.requestList.size() == 0) {
         this.msgCaller(pUser, ChatColor.YELLOW + "There are currently no requests for the party " + ChatColor.GREEN + partyName);
      } else {
         String names = "";

         String mem;
         for(Iterator var6 = party.requestList.iterator(); var6.hasNext(); names = names + ChatColor.AQUA + mem.split(" ")[1]) {
            mem = (String)var6.next();
            if (names != "") {
               names = names + ChatColor.GRAY + ", " + ChatColor.AQUA;
            }
         }

         this.msgCaller(pUser, ChatColor.DARK_AQUA + "The party " + ChatColor.GREEN + partyName + ChatColor.DARK_AQUA + " currently has received requests to join from these players: " + names);
      }
   }

   public void addMemberParty(Player pUser, String partyName, String playerName) {
      if (this.isMember(partyName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is already a member of party " + ChatColor.GREEN + partyName);
      } else {
         QrpgParty party = this.findParty(partyName);
         OfflinePlayer pTarget = this.core.getServer().getOfflinePlayer(playerName);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GREEN + playerName + ChatColor.RED + " could not be located");
         } else if (party.memberList.size() >= party.maxMembers) {
            this.msgCaller(pUser, ChatColor.RED + "The party " + ChatColor.GREEN + partyName + ChatColor.RED + " has reached its maximum member size");
         } else {
            this.removeAllInvites(playerName);
            this.removeAllRequests(playerName);
            String mem = pTarget.getUniqueId() + " " + playerName + " Member";
            party.memberList.add(mem);
            String path = "party." + party.name + ".members";
            List configList = this.getConfig().getStringList(path);
            configList.add(mem);
            this.getConfig().set(path, configList);
            this.saveConfig();
            this.setRankParty(pUser, partyName, playerName, "member");
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " is now a member of the party " + ChatColor.GREEN + partyName);
            if (pTarget != null && pTarget.isOnline()) {
               this.msgCaller((Player)pTarget, ChatColor.DARK_GREEN + "You are now a member of the " + ChatColor.GREEN + partyName + ChatColor.DARK_GREEN + " party!");
            }

            this.core.sendPartyChat((Player)null, partyName, this.lkgm, " Player " + playerName + " has joined the party!");
         }
      }
   }

   public void removeMemberParty(Player pUser, String partyName, String playerName) {
      if (!this.isMember(partyName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is not a member of party " + ChatColor.GREEN + partyName);
      } else {
         QrpgParty party = this.findParty(partyName);
         Iterator var10 = party.memberList.iterator();

         while(var10.hasNext()) {
            String mem = (String)var10.next();
            String[] mems = mem.split(" ");
            if (mems[1].equalsIgnoreCase(playerName)) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myparty.leader");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.chat.party");
               party.memberList.remove(mem);
               String path = "party." + party.name + ".members";
               List configList = this.getConfig().getStringList(path);
               configList.remove(mem);
               this.getConfig().set(path, configList);
               this.saveConfig();
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "Player " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " has been removed from the party " + ChatColor.GREEN + partyName);
               Player pTarget = this.core.getServer().getPlayer(playerName);
               if (pTarget != null) {
                  this.msgCaller(pTarget, ChatColor.DARK_GREEN + "You are no longer a member of the party " + ChatColor.GREEN + partyName);
               }

               this.core.sendPartyChat((Player)null, partyName, this.lkgm, " Player " + playerName + " is no longer part of the party!");
               return;
            }
         }

         this.msgCaller(pUser, ChatColor.RED + "Could not locate player " + ChatColor.GRAY + playerName);
      }
   }

   public String getRankParty(String partyName, String playerName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         return null;
      } else {
         Iterator var6 = party.memberList.iterator();

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

   public void showRankParty(Player pUser, String partyName, String playerName) {
      String rank = this.getRankParty(partyName, playerName);
      if (rank == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the information");
      } else {
         this.msgCaller(pUser, ChatColor.GREEN + partyName + ChatColor.DARK_GREEN + " party member " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " is currently rank " + ChatColor.YELLOW + rank);
      }
   }

   public void setRankParty(Player pUser, String partyName, String playerName, String rankName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not find the party " + ChatColor.GOLD + partyName);
      } else if (!this.isMember(partyName, playerName)) {
         this.msgCaller(pUser, ChatColor.RED + "Player " + ChatColor.GRAY + playerName + ChatColor.RED + " is not a member of party " + ChatColor.GOLD + partyName);
      } else {
         String newRank = null;
         if ("leader".startsWith(rankName.toLowerCase())) {
            newRank = "Leader";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.myparty.leader");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.chat.party");
         } else if ("member".startsWith(rankName.toLowerCase())) {
            newRank = "Member";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.myparty.leader");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.chat.party");
         }

         if (newRank == null) {
            this.msgCaller(pUser, ChatColor.RED + "It doesn't appear that " + ChatColor.GRAY + rankName + ChatColor.RED + " is a valid rank");
         } else {
            String path = "party." + party.name + ".members";
            List configList = this.getConfig().getStringList(path);
            Iterator var13 = party.memberList.iterator();

            while(var13.hasNext()) {
               String mem = (String)var13.next();
               String[] mems = mem.split(" ");
               if (mems[1].equalsIgnoreCase(playerName)) {
                  party.memberList.remove(mem);
                  configList.remove(mem);
                  String newMem = mems[0] + " " + mems[1] + " " + newRank;
                  party.memberList.add(newMem);
                  configList.add(newMem);
                  this.getConfig().set(path, configList);
                  this.saveConfig();
                  this.msgCaller(pUser, ChatColor.DARK_GREEN + "Party member " + ChatColor.YELLOW + playerName + ChatColor.DARK_GREEN + " is now rank " + ChatColor.YELLOW + newRank);
                  this.core.sendPartyChat((Player)null, partyName, this.lkgm, " Player " + playerName + " is now rank " + newRank);
                  Player pTarget = this.core.getServer().getPlayer(playerName);
                  if (pTarget != null) {
                     this.msgCaller(pTarget, ChatColor.DARK_GREEN + "Congratulations, you are now rank " + ChatColor.YELLOW + newRank + ChatColor.DARK_GREEN + " in your party " + ChatColor.GREEN + partyName);
                  }

                  return;
               }
            }

         }
      }
   }

   public void showInfoParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the party " + ChatColor.GREEN + partyName);
      } else {
         this.msgCaller(pUser, ChatColor.GREEN + "Party " + partyName + ":\n" + ChatColor.GRAY + "\nMax Members: " + ChatColor.YELLOW + party.maxMembers + ChatColor.GRAY + "\nPvp: " + ChatColor.YELLOW + (party.allowPvp ? "On" : "Off"));
      }

   }

   public void doSetMaxMembersParty(String partyName, int amount) {
      QrpgParty party = this.findParty(partyName);
      party.maxMembers = amount;
      String path = "party." + party.name + ".maxmembers";
      this.getConfig().set(path, party.maxMembers);
      this.saveConfig();
   }

   public void removeAllMembersForParty(Player pUser, String partyName) {
      QrpgParty party = this.findParty(partyName);
      if (party != null) {
         if (party.memberList.size() > 0) {
            String[] mems = ((String)party.memberList.get(0)).split(" ");
            this.removeMemberParty(pUser, partyName, mems[1]);
            this.removeAllMembersForParty(pUser, partyName);
         }

      }
   }

   public void pvpParty(Player pUser, String partyName, boolean allowPvp) {
      QrpgParty party = this.findParty(partyName);
      if (party == null) {
         this.msgCaller(pUser, ChatColor.RED + "Could not locate the party " + ChatColor.GREEN + partyName);
      } else {
         party.allowPvp = allowPvp;
         String path = "party." + party.name + ".allowpvp";
         this.getConfig().set(path, party.allowPvp);
         this.saveConfig();
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Party PvP is now " + ChatColor.GREEN + (allowPvp ? "On" : "Off"));
         this.core.sendPartyChat((Player)null, partyName, this.lkgm, " Party PvP is now " + (allowPvp ? "On" : "Off"));
      }
   }

   public boolean canPvp(String partyName) {
      QrpgParty party = this.findParty(partyName);
      return party == null ? true : party.allowPvp;
   }
}
