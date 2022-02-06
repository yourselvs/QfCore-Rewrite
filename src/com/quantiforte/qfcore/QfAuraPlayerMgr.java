package com.quantiforte.qfcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class QfAuraPlayerMgr extends QfManager implements CommandExecutor, TabCompleter {
   public void doInit(QfCore newCore) {
      this.configFileName = "config_auraplayers.yml";
      this.configFileName = null;
      this.mitems = new ArrayList();
      this.hasLocationTriggers = true;
      this.hasDynLocationTriggers = true;
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "auraplayer";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Active player auras:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Active " + ChatColor.YELLOW + cat + ChatColor.GOLD + " player auras:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      // decompiler artifact?
      //int nextarg = false;
      String playerName = "";
      String arg0 = null;
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         pTarget = pUser;
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      if (args.length < 1) {
         this.msgCaller(pUser, ChatColor.GRAY + "You must specify the name of the aura you would like to use.");
         return true;
      } else if (args.length > 2) {
         this.msgCaller(pUser, ChatColor.GRAY + "You must specify the name of the aura you would like to use and the player name.");
         return true;
      } else {
         if (args.length == 2) {
            arg0 = args[0].toLowerCase();
            if (pUser != null) {
               if ("list".startsWith(arg0) && !pUser.hasPermission("QfCore.aura.others.see")) {
                  this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have the ability to see other player's aura information");
                  return true;
               }

               if ("off".startsWith(arg0) && !pUser.hasPermission("QfCore.aura.others.off")) {
                  this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have the ability to turn off other player's auras");
                  return true;
               }

               if ("show".startsWith(arg0) && !pUser.hasPermission("QfCore.aura.others.show")) {
                  this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have the ability to show other player's auras");
                  return true;
               }

               if (!"list".startsWith(arg0) && !"off".startsWith(arg0) && !"show".startsWith(arg0) && !pUser.hasPermission("QfCore.aura.others.give")) {
                  this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have the ability to give other players an aura");
                  return true;
               }
            }

            playerName = args[1];
            pTarget = this.core.getServer().getPlayer(playerName);
         }

         if (pTarget == null && playerName != "*") {
            this.msgCaller(pUser, ChatColor.DARK_RED + "Not sure which player's aura to work with");
            return true;
         } else {
            label272: {
               String auraName;
               label273: {
                  label274: {
                     label275: {
                        String dispStr;
                        QfAuraPlayer auraPlayer;
                        label276: {
                           label277: {
                              label278: {
                                 label279: {
                                    auraName = args[0].toLowerCase();
                                    switch(auraName.hashCode()) {
                                    case -1849290568:
                                       if (!auraName.equals("darkbuild")) {
                                          break label272;
                                       }
                                       break label277;
                                    case -1383205240:
                                       if (!auraName.equals("bounce")) {
                                          break label272;
                                       }
                                       break label273;
                                    case -1321333613:
                                       if (!auraName.equals("flamedamp")) {
                                          break label272;
                                       }
                                       break label279;
                                    case -1221262756:
                                       if (auraName.equals("health")) {
                                          if (isPlayer && pUser.hasPermission("Qrpg.class.grandmaster")) {
                                             this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                                          }

                                          return true;
                                       }
                                       break label272;
                                    case -270580530:
                                       if (!auraName.equals("fireproof")) {
                                          break label272;
                                       }
                                       break label275;
                                    case 108:
                                       if (!auraName.equals("l")) {
                                          break label272;
                                       }
                                       break;
                                    case 111:
                                       if (!auraName.equals("o")) {
                                          break label272;
                                       }
                                       break label276;
                                    case 109935:
                                       if (!auraName.equals("off")) {
                                          break label272;
                                       }
                                       break label276;
                                    case 115734:
                                       if (!auraName.equals("ugh")) {
                                          break label272;
                                       }
                                       break label274;
                                    case 3143256:
                                       if (!auraName.equals("fish")) {
                                          break label272;
                                       }
                                       break label275;
                                    case 3322014:
                                       if (!auraName.equals("list")) {
                                          break label272;
                                       }
                                       break;
                                    case 98357969:
                                       if (!auraName.equals("gills")) {
                                          break label272;
                                       }
                                       break label279;
                                    case 109578318:
                                       if (!auraName.equals("snack")) {
                                          break label272;
                                       }
                                       break label278;
                                    case 182619929:
                                       if (!auraName.equals("nighteye")) {
                                          break label272;
                                       }
                                       break label278;
                                    case 365177999:
                                       if (!auraName.equals("underwaterbuild")) {
                                          break label272;
                                       }
                                       break label277;
                                    case 1266452202:
                                       if (!auraName.equals("swiftness")) {
                                          break label272;
                                       }
                                       break label273;
                                    case 1439257549:
                                       if (!auraName.equals("autofeed")) {
                                          break label272;
                                       }
                                       break label274;
                                    case 1439317015:
                                       if (!auraName.equals("autoheal")) {
                                          break label272;
                                       }
                                       break label274;
                                    case 1791316033:
                                       if (auraName.equals("strength")) {
                                          if (isPlayer && pUser.hasPermission("Qrpg.class.paladin")) {
                                             this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                                          }

                                          return true;
                                       }
                                       break label272;
                                    case 1991257093:
                                       if (!auraName.equals("ugh-lite")) {
                                          break label272;
                                       }
                                       break label274;
                                    default:
                                       break label272;
                                    }

                                    if (args.length == 1) {
                                       dispStr = this.listAvailPlayerAuras(pTarget, args.length == 1);
                                       auraPlayer = (QfAuraPlayer)this.getMItemUuid(pTarget.getUniqueId());
                                       dispStr = dispStr + ChatColor.GOLD + "You have the following aura active: " + this.dispActiveAuras(auraPlayer);
                                       this.msgCaller(pUser, dispStr);
                                    }

                                    if (args.length == 2) {
                                       if (playerName == "*") {
                                          this.msgCaller(pUser, ChatColor.DARK_RED + "Not sure which player's aura to work with");
                                          return true;
                                       }

                                       dispStr = this.listAvailPlayerAuras(pTarget, args.length == 1);
                                       auraPlayer = (QfAuraPlayer)this.getMItemUuid(pTarget.getUniqueId());
                                       dispStr = dispStr + ChatColor.RED + pTarget.getName() + ChatColor.GOLD + " has the following aura(s) active: " + this.dispActiveAuras(auraPlayer);
                                       this.msgCaller(pUser, dispStr);
                                    }

                                    return true;
                                 }

                                 if (isPlayer && pUser.hasPermission("Qrpg.class.cleric")) {
                                    this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                                 }

                                 return true;
                              }

                              if (isPlayer && pUser.hasPermission("Qrpg.class.monk")) {
                                 this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                              }

                              return true;
                           }

                           if (isPlayer && pUser.hasPermission("Qrpg.class.builder")) {
                              this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                           }

                           return true;
                        }

                        auraPlayer = (QfAuraPlayer)this.getMItemUuid(pTarget.getUniqueId());
                        dispStr = this.deactivatePlayerAuras(auraPlayer, pTarget.getName(), args.length == 1);
                        this.msgCaller(pUser, dispStr);
                        return true;
                     }

                     if (isPlayer && pUser.hasPermission("Qrpg.class.bishop")) {
                        this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                     }

                     return true;
                  }

                  if (isPlayer && pUser.hasPermission("Qrpg.class.exec")) {
                     this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
                  }

                  return true;
               }

               if (isPlayer && pUser.hasPermission("Qrpg.class.acolyte")) {
                  this.ActivateAura(pUser, pTarget, auraName, args.length != 2);
               }

               return true;
            }

            this.msgCaller(pUser, ChatColor.DARK_RED + "You have not yet learned that aura");
            return true;
         }
      }
   }

   public void deactivatePlayerAuraNow(Player pUser, Player pTarget) {
      QfAuraPlayer auraPlayer = (QfAuraPlayer)this.getMItemUuid(pTarget.getUniqueId());
      String dispStr = this.deactivatePlayerAuras(auraPlayer, pTarget.getName(), false);
      this.msgCaller(pUser, dispStr);
   }

   public void deactivatePlayerAurasQuit(Player pPlayer) {
      QfAuraPlayer auraPlayer = (QfAuraPlayer)this.getMItemUuid(pPlayer.getUniqueId());
      if (auraPlayer != null) {
         String dispStr = this.deactivatePlayerAuras(auraPlayer, auraPlayer.mPlayer.getName(), false);
         this.msgCaller((Player)null, dispStr);
      }

   }

   public void deacvitatePlayerAura(Player pUser, Player pTarget) {
      String dispStr;
      if (pTarget == null) {
         dispStr = ChatColor.RED + "Could not locate player ";
         this.msgCaller(pUser, dispStr);
      } else {
         String playerName = pTarget.getName();
         QfAuraPlayer auraPlayer = (QfAuraPlayer)this.getMItemUuid(pTarget.getUniqueId());
         if (auraPlayer == null) {
            dispStr = ChatColor.YELLOW + playerName + ChatColor.GRAY + " does not have any active auras";
            this.msgCaller(pUser, dispStr);
         } else {
            dispStr = this.deactivatePlayerAuras(auraPlayer, playerName, false);
            this.msgCaller(pUser, dispStr);
         }
      }
   }

   public boolean ActivateAura(Player pUser, Player pTarget, String auraName, boolean isSelf) {
      QfAuraPlayer auraPlayer = (QfAuraPlayer)this.getMItemUuid(pTarget.getUniqueId());
      String playerName = pTarget.getName();
      if (auraPlayer != null) {
         this.deactivatePlayerAuras(auraPlayer, playerName, false);
      }

      auraPlayer = new QfAuraPlayer();
      auraPlayer.mgr = this;
      auraPlayer.mPlayer = pTarget;
      auraPlayer.mUuid = pTarget.getUniqueId();
      auraPlayer.name = playerName;
      auraPlayer.category = "player";
      auraPlayer.subcategory = "player";
      auraPlayer.doInit();
      this.mitems.add(auraPlayer);
      QfAura aura = (QfAura)this.core.auraMgr.getMItem(auraName);
      String dispStr;
      if (aura == null) {
         this.mitems.remove(auraPlayer);
         dispStr = ChatColor.YELLOW + auraName + ChatColor.RED + " is not an available aura";
         this.msgCaller(pUser, dispStr);
         return true;
      } else {
         auraPlayer.auras.add(aura);
         this.addTriggerLoc(auraPlayer);
         pTarget.playEffect(pTarget.getLocation(), Effect.SMOKE, null);
         dispStr = ChatColor.RED + playerName + ChatColor.GRAY + " activated aura " + ChatColor.YELLOW + auraName;
         this.msgCaller(pUser, dispStr);
         return true;
      }
   }

   public List onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      boolean isPlayer = sender instanceof Player;
      if (!isPlayer) {
         return null;
      } else {
         pUser = (Player)sender;
         String var8 = cmd.getName().toLowerCase();
         if (!var8.equalsIgnoreCase("aura")) {
            return null;
         } else {
            List tabOut = new ArrayList();
            List avail;
            String txt;
            String one;
            Iterator var15;
            if (args.length != 0) {
               if (args.length == 1) {
                  String partial = ChatColor.stripColor(args[0]).toLowerCase();
                  txt = "list";
                  if (txt.toLowerCase().startsWith(partial)) {
                     tabOut.add(txt);
                  }

                  txt = "off";
                  if (txt.toLowerCase().startsWith(partial)) {
                     tabOut.add(txt);
                  }

                  txt = "show";
                  if (txt.toLowerCase().startsWith(partial)) {
                     tabOut.add(txt);
                  }

                  avail = this.AvailableAuras(pUser);
                  var15 = avail.iterator();

                  while(var15.hasNext()) {
                     one = (String)var15.next();
                     txt = ChatColor.stripColor(one).toLowerCase();
                     if (txt.startsWith(partial)) {
                        tabOut.add(txt);
                     }
                  }

                  return tabOut;
               } else {
                  return null;
               }
            } else {
               tabOut.add("list");
               tabOut.add("off");
               tabOut.add("show");
               avail = this.AvailableAuras(pUser);
               var15 = avail.iterator();

               while(var15.hasNext()) {
                  one = (String)var15.next();
                  txt = ChatColor.stripColor(one).toLowerCase();
                  tabOut.add(txt);
               }

               return tabOut;
            }
         }
      }
   }

   public String deactivateAllAuras() {
      Iterator var4 = this.mitems.iterator();

      while(var4.hasNext()) {
         QfMItem mitem = (QfMItem)var4.next();
         QfAuraPlayer auraPlayer = (QfAuraPlayer)mitem;
         this.deactivatePlayerAuras(auraPlayer, auraPlayer.mPlayer.getName(), true);
      }

      this.mitems.clear();
      String dispStr = ChatColor.WHITE + "All player auras have been deactivated";
      return dispStr;
   }

   public String deactivatePlayerAuras(QfAuraPlayer auraPlayer, String playerName, boolean isSelf) {
      if (auraPlayer != null) {
         auraPlayer.auras.clear();
         this.mitems.remove(auraPlayer);
         this.removeTriggerLoc(auraPlayer);
      }

      String dispStr;
      if (isSelf) {
         dispStr = ChatColor.GRAY + "Your aura is now " + ChatColor.DARK_RED + "off";
      } else {
         dispStr = ChatColor.YELLOW + playerName + ChatColor.GRAY + " - deactivated all auras";
      }

      return dispStr;
   }

   public List AvailableAuras(Player pPlayer) {
      List avail = new ArrayList();
      if (pPlayer.hasPermission("Qrpg.class.builder")) {
         avail.add(ChatColor.GREEN + "DarkBuild");
         avail.add(ChatColor.GREEN + "UnderwaterBuild");
      }

      if (pPlayer.hasPermission("Qrpg.class.exec")) {
         avail.add(ChatColor.GREEN + "AutoFeed");
         avail.add(ChatColor.GREEN + "AutoHeal");
         avail.add(ChatColor.LIGHT_PURPLE + "Ugh");
         avail.add(ChatColor.LIGHT_PURPLE + "Ugh-Lite");
      }

      pPlayer.hasPermission("Qrpg.class.admin");
      if (pPlayer.hasPermission("Qrpg.class.acolyte")) {
         avail.add(ChatColor.YELLOW + "Swiftness");
         avail.add(ChatColor.YELLOW + "Bounce");
      }

      if (pPlayer.hasPermission("Qrpg.class.cleric")) {
         avail.add(ChatColor.BLUE + "Gills");
         avail.add(ChatColor.RED + "Flamedamp");
      }

      if (pPlayer.hasPermission("Qrpg.class.monk")) {
         avail.add(ChatColor.GRAY + "Nighteye");
         avail.add(ChatColor.GREEN + "Snack");
      }

      if (pPlayer.hasPermission("Qrpg.class.bishop")) {
         avail.add(ChatColor.RED + "Fireproof");
         avail.add(ChatColor.BLUE + "Fish");
      }

      if (pPlayer.hasPermission("Qrpg.class.crusader")) {
         avail.add(ChatColor.LIGHT_PURPLE + "Strength");
      }

      if (pPlayer.hasPermission("Qrpg.class.grandmaster")) {
         avail.add(ChatColor.GREEN + "Health");
      }

      return avail;
   }

   public String listAvailPlayerAuras(Player pPlayer, boolean isSelf) {
      String dispStr;
      if (isSelf) {
         dispStr = ChatColor.GOLD + "You can use these auras:\n";
      } else {
         dispStr = ChatColor.RED + pPlayer.getName() + ChatColor.GOLD + " can use these auras:\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.builder")) {
         dispStr = dispStr + ChatColor.GREEN + "B" + ChatColor.DARK_GREEN + "uilder: " + ChatColor.AQUA + "DarkBuild" + ChatColor.GRAY + ", " + ChatColor.AQUA + "UnderwaterBuild\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.exec")) {
         dispStr = dispStr + ChatColor.WHITE + "E" + ChatColor.GRAY + "xecutive: " + ChatColor.GREEN + "autofeed" + ChatColor.GRAY + ", " + ChatColor.GREEN + "autoheal" + ChatColor.GRAY + ", " + ChatColor.LIGHT_PURPLE + "ugh-lite" + ChatColor.GRAY + ", " + ChatColor.LIGHT_PURPLE + "ugh\n";
      }

      pPlayer.hasPermission("Qrpg.class.admin");
      if (pPlayer.hasPermission("Qrpg.class.acolyte")) {
         dispStr = dispStr + ChatColor.GOLD + "Acolyte: " + ChatColor.YELLOW + "Swiftness I" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "Bounce I\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.cleric")) {
         dispStr = dispStr + ChatColor.GOLD + "Cleric: " + ChatColor.BLUE + "Gills" + ChatColor.GRAY + ", " + ChatColor.RED + "Flamedamp I\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.monk")) {
         dispStr = dispStr + ChatColor.GOLD + "Monk: " + ChatColor.BLUE + "Nighteye" + ChatColor.GRAY + ", " + ChatColor.GREEN + "Snack I\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.bishop")) {
         dispStr = dispStr + ChatColor.GOLD + "Bishop: " + ChatColor.BLUE + "Fish" + ChatColor.GRAY + ", " + ChatColor.RED + "Fireproof\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.crusader")) {
         dispStr = dispStr + ChatColor.GOLD + "Crusader: " + ChatColor.GREEN + "Snack II\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.paladin")) {
         dispStr = dispStr + ChatColor.GOLD + "Paladin: " + ChatColor.LIGHT_PURPLE + "Strength I" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "Swiftness II\n";
      }

      if (pPlayer.hasPermission("Qrpg.class.grandmaster")) {
         dispStr = dispStr + ChatColor.GOLD + "Grand Master: " + ChatColor.LIGHT_PURPLE + "Strength II" + ChatColor.GRAY + ", " + ChatColor.GREEN + "Health\n";
      }

      return dispStr;
   }

   public void readConfig() {
   }

   public QfAura getMItemAura(List itemList, String mName) {
      Iterator var4 = itemList.iterator();

      while(var4.hasNext()) {
         QfAura mi = (QfAura)var4.next();
         if (mi.name.equalsIgnoreCase(mName)) {
            return mi;
         }
      }

      return null;
   }

   public String listItems(String cat) {
      if (this.mitems == null) {
         return ChatColor.GRAY + "No Items.";
      } else {
         String retVal = "";
         QfMItem mitem;
         Iterator var4;
         if (cat == null) {
            for(var4 = this.mitems.iterator(); var4.hasNext(); retVal = retVal + mitem.NameNice() + ChatColor.GRAY + " - " + this.dispActiveAuras((QfAuraPlayer)mitem) + "\n") {
               mitem = (QfMItem)var4.next();
            }
         } else {
            for(var4 = this.mitems.iterator(); var4.hasNext(); retVal = retVal + this.dispActiveAurasCat((QfAuraPlayer)mitem, cat) + "\n") {
               mitem = (QfMItem)var4.next();
            }
         }

         if (retVal.length() == 0) {
            retVal = this.listItemHeader(cat) + "\n" + ChatColor.RED + "NONE";
         } else {
            retVal = this.listItemHeader(cat) + "\n" + retVal;
         }

         return retVal;
      }
   }

   public String dispActiveAurasCat(QfAuraPlayer auraPlayer, String cat) {
      String retVal = "";
      Iterator var5 = auraPlayer.auras.iterator();

      while(true) {
         QfAura aura;
         do {
            if (!var5.hasNext()) {
               if (retVal != "") {
                  retVal = auraPlayer.NameNice() + ChatColor.GRAY + " - " + retVal;
               }

               return retVal;
            }

            aura = (QfAura)var5.next();
         } while(!aura.category.contains(cat) && !aura.subcategory.contains(cat));

         retVal = retVal + aura.NameNice() + " ";
      }
   }

   public String dispActiveAuras(QfAuraPlayer auraPlayer) {
      String retVal = "";
      if (auraPlayer != null && auraPlayer.auras != null) {
         QfAura aura;
         for(Iterator var4 = auraPlayer.auras.iterator(); var4.hasNext(); retVal = retVal + aura.NameNice() + " ") {
            aura = (QfAura)var4.next();
         }

         if (retVal == "") {
            retVal = ChatColor.RED + "None";
         }

         return retVal;
      } else {
         return ChatColor.RED + "None";
      }
   }

   public void removeTriggerLoc(QfMItem mi) {
      QfAuraPlayer auraPlayer = (QfAuraPlayer)mi;
      if (auraPlayer.auras.size() <= 0) {
         Iterator var5 = this.triggerLocs.iterator();

         while(var5.hasNext()) {
            QfTriggerLoc mtl = (QfTriggerLoc)var5.next();
            if (mtl.mitem.mUuid.equals(auraPlayer.mUuid)) {
               this.triggerLocs.remove(mtl);
               return;
            }
         }

      }
   }
}
