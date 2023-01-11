package me.yourselvs.qfcore.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.yourselvs.qfcore.QfGeneral;

public class QrpgClassMgr extends QfGeneral implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      // value not used: decompiler artifact?
      // String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         // playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      String arg0 = args[0];
      switch(cmdName.hashCode()) {
      case 476625199:
         if (cmdName.equals("rpgclass")) {
            this.qfcore.getLogger().info("rpgclass " + arg0);
            if (args.length != 2) {
               this.msgCaller(pUser, "Not sure which player/class to use");
               return true;
            }

            pTarget = this.qfcore.getServer().getPlayer(args[1]);
            if (pTarget == null) {
               this.msgCaller(pUser, "Could not locate the player");
               return true;
            }

            this.DoClassSync(pUser, pTarget, arg0);
            return true;
         }
         break;
      case 770410099:
         if (cmdName.equals("qrpgsync")) {
            if (args.length != 1) {
               this.msgCaller(pUser, "Not sure which player to use");
               return true;
            }

            pTarget = this.qfcore.getServer().getPlayer(args[0]);
            if (pTarget == null) {
               this.msgCaller(pUser, "Could not locate the player");
               return true;
            }

            this.DoClassSync(pUser, pTarget, "");
            return true;
         }
      }

      return false;
   }

   public void DoClassSync(Player pUser, Player pTarget, String arg0) {
      if (!arg0.equalsIgnoreCase("clear") && !arg0.equalsIgnoreCase("reset")) {
         if (arg0.equalsIgnoreCase("hero")) {
            this.qfcore.getLogger().info("dcs: hero");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.attained.hero");
         }

         if (arg0.equalsIgnoreCase("king")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.attained.king");
         }

         if (arg0.equalsIgnoreCase("grandmaster")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.attained.grandmaster");
         }

         if (arg0.equalsIgnoreCase("chancellor")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.attained.chancellor");
         }

         this.clearRank(pUser, pTarget, arg0);
         this.setClergyLevels(pUser, pTarget, arg0);
         this.setSoldierLevels(pUser, pTarget, arg0);
         this.setNobilityLevels(pUser, pTarget, arg0);
         this.setTradesmanLevels(pUser, pTarget, arg0);
         this.qfcore.classMgr.setProperPrefix(pTarget);
      } else {
         this.clearRank(pUser, pTarget, (String)null);
      }
   }

   public void clearRank(Player pUser, Player pTarget, String arg0) {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add peasant");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.class.primary.nobility");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.display.nobility");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.class.primary.soldier");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.display.soldier");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.class.primary.clergy");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.display.clergy");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.class.primary.tradesman");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.display.tradesman");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove hero");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove commander");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove kingsguard");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove captain");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove warrior");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove bowman");
      if (pTarget.hasPermission("Qrpg.class.attained.hero")) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add knight");
      } else {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove knight");
      }

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove grandmaster");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove paladin");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove crusader");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove bishop");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove cleric");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove acolyte");
      if (pTarget.hasPermission("Qrpg.class.attained.grandmaster")) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add monk");
      } else {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove monk");
      }

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove king");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove prince");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove grandduke");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove count");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove lord");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove gentleman");
      if (pTarget.hasPermission("Qrpg.class.attained.king")) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add baron");
      } else {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove baron");
      }

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove chancellor");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove treasurer");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove merchant");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove shopkeeper");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove trader");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove peddler");
      if (pTarget.hasPermission("Qrpg.class.attained.chancellor")) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add blacksmith");
      } else {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove blacksmith");
      }

      this.SyncHearts(pTarget);
      this.SyncClassRegions(pTarget);
      this.qfcore.deactivatePlayerAuraNow(pUser, pTarget);
      this.qfcore.staffMgr.clearPrefix(pTarget);
   }

   public void setSoldierLevels(Player pUser, Player pTarget, String arg0) {
      String upMsg;
      label125: {
         boolean isFemale;
         label145: {
            label146: {
               label147: {
                  label114: {
                     label113: {
                        upMsg = null;
                        isFemale = pTarget.hasPermission("Qrpg.profile.gender.female");
                        switch(arg0.hashCode()) {
                        case -1606001559:
                           if (!arg0.equals("kingsguard")) {
                              break label125;
                           }
                           break;
                        case -1498725064:
                           if (!arg0.equals("commander")) {
                              break label125;
                           }
                           break label113;
                        case -1383146672:
                           if (!arg0.equals("bowman")) {
                              break label125;
                           }
                           break label147;
                        case -1126830451:
                           if (!arg0.equals("knight")) {
                              break label125;
                           }
                           break label145;
                        case 3198970:
                           if (!arg0.equals("hero")) {
                              break label125;
                           }
                           break label114;
                        case 552565540:
                           if (!arg0.equals("captain")) {
                              break label125;
                           }

                           if (upMsg == null) {
                              upMsg = ChatColor.GOLD + "As a proven soldier you are rapidly become renowned and have earned your new post as a " + ChatColor.YELLOW + (isFemale ? "Captain" : "Captain") + ChatColor.GOLD + "!";
                           }
                           break;
                        case 1124565314:
                           if (!arg0.equals("warrior")) {
                              break label125;
                           }
                           break label146;
                        default:
                           break label125;
                        }

                        if (upMsg == null) {
                           upMsg = ChatColor.GOLD + "The glorious honor of " + ChatColor.YELLOW + (isFemale ? "Queensguard" : "Kingsguard") + ChatColor.GOLD + " has been bestowed upon you!";
                        }
                     }

                     if (upMsg == null) {
                        upMsg = ChatColor.GOLD + "Gather your troops and conquer the world " + ChatColor.YELLOW + (isFemale ? "Commander" : "Commander") + ChatColor.GOLD + "!";
                     }
                  }

                  if (upMsg == null) {
                     upMsg = ChatColor.GOLD + "Across the entire world, people know your name, the name of a mighty " + ChatColor.YELLOW + (isFemale ? "Heroine" : "Hero") + ChatColor.GOLD + "!";
                  }

                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove knight");
               }

               if (upMsg == null) {
                  upMsg = ChatColor.GOLD + "You pick up a bow and arrow, strenghen your heart, and begin down the soldier's road as a " + ChatColor.YELLOW + (isFemale ? "Archeress" : "Bowman") + ChatColor.GOLD + "!";
               }
            }

            if (upMsg == null) {
               upMsg = ChatColor.GOLD + "Your skin is thickening, your health is growing, you are now a mighty " + ChatColor.YELLOW + (isFemale ? "Warrior" : "Warrior") + ChatColor.GOLD + "!";
            }
         }

         if (upMsg == null) {
            upMsg = ChatColor.GOLD + "Arise brave " + ChatColor.YELLOW + (isFemale ? "Dame" : "Knight") + ChatColor.GOLD + ", you have been knighted by royalty!";
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.primary.soldier");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.display.soldier");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add " + arg0);
      }

      this.SyncHearts(pTarget);
      if (upMsg != null) {
         this.msgCaller(pTarget, upMsg);
      }

   }

   public void setClergyLevels(Player pUser, Player pTarget, String arg0) {
      String upMsg;
      label125: {
         boolean isFemale;
         label145: {
            label146: {
               label147: {
                  label114: {
                     label113: {
                        upMsg = null;
                        isFemale = pTarget.hasPermission("Qrpg.profile.gender.female");
                        switch(arg0.hashCode()) {
                        case -2000426787:
                           if (!arg0.equals("crusader")) {
                              break label125;
                           }
                           break;
                        case -1388811331:
                           if (!arg0.equals("bishop")) {
                              break label125;
                           }

                           if (upMsg == null) {
                              upMsg = ChatColor.GOLD + "You truely have become a healer, advancing steadily on the path as a " + ChatColor.YELLOW + (isFemale ? "Bishop" : "Bishop") + ChatColor.GOLD + "!";
                           }
                           break;
                        case -1357819280:
                           if (!arg0.equals("cleric")) {
                              break label125;
                           }
                           break label146;
                        case -1166321973:
                           if (!arg0.equals("acolyte")) {
                              break label125;
                           }
                           break label147;
                        case -799045725:
                           if (!arg0.equals("paladin")) {
                              break label125;
                           }
                           break label113;
                        case -354160018:
                           if (!arg0.equals("grandmaster")) {
                              break label125;
                           }
                           break label114;
                        case 3357407:
                           if (!arg0.equals("monk")) {
                              break label125;
                           }
                           break label145;
                        default:
                           break label125;
                        }

                        if (upMsg == null) {
                           upMsg = ChatColor.GOLD + "You have taken up the fight against oppression everywhere as you embrace healing as a " + ChatColor.YELLOW + (isFemale ? "Crusader" : "Crusader") + ChatColor.GOLD + "!";
                        }
                     }

                     if (upMsg == null) {
                        upMsg = ChatColor.GOLD + "Evil-doers beware! You are a true healer of light and are now a " + ChatColor.YELLOW + (isFemale ? "Paladin" : "Paladin") + ChatColor.GOLD + "!";
                     }
                  }

                  if (upMsg == null) {
                     upMsg = ChatColor.GOLD + "Your willingness to heal the young and old and the rich and poor alike have made you a world-renowned " + ChatColor.YELLOW + (isFemale ? "Grand Master" : "Grand Master") + ChatColor.GOLD + ", truely worthy of being called a healer!";
                  }

                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove monk");
               }

               if (upMsg == null) {
                  upMsg = ChatColor.GOLD + "You have begun your journey on the healing path and are now an " + ChatColor.YELLOW + (isFemale ? "Acolyte" : "Acolyte") + ChatColor.GOLD + "!";
               }
            }

            if (upMsg == null) {
               upMsg = ChatColor.GOLD + "Your healing skills are improving, you are now a " + ChatColor.YELLOW + (isFemale ? "Cleric" : "Cleric") + ChatColor.GOLD + "!";
            }
         }

         if (upMsg == null) {
            upMsg = ChatColor.GOLD + "You retreat into solitude to further study the healing arts, becoming " + (isFemale ? "an " + ChatColor.YELLOW + "Ascetic" : "a " + ChatColor.YELLOW + "Monk") + ChatColor.GOLD + "!";
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.primary.clergy");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.display.clergy");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add " + arg0);
      }

      if (upMsg != null) {
         this.msgCaller(pTarget, upMsg);
      }

   }

   public void setNobilityLevels(Player pUser, Player pTarget, String arg0) {
      String upMsg;
      label125: {
         boolean isFemale;
         label145: {
            label146: {
               label147: {
                  label114: {
                     label113: {
                        upMsg = null;
                        isFemale = pTarget.hasPermission("Qrpg.profile.gender.female");
                        switch(arg0.hashCode()) {
                        case -1234135881:
                           if (!arg0.equals("grandduke")) {
                              break label125;
                           }
                           break;
                        case -979984055:
                           if (!arg0.equals("prince")) {
                              break label125;
                           }
                           break label113;
                        case 3292055:
                           if (!arg0.equals("king")) {
                              break label125;
                           }
                           break label114;
                        case 3327733:
                           if (!arg0.equals("lord")) {
                              break label125;
                           }
                           break label146;
                        case 93507890:
                           if (!arg0.equals("baron")) {
                              break label125;
                           }
                           break label145;
                        case 94851343:
                           if (!arg0.equals("count")) {
                              break label125;
                           }

                           if (upMsg == null) {
                              upMsg = ChatColor.GOLD + "You are true nobilty and have been granted the esteemed title of " + ChatColor.YELLOW + (isFemale ? "Countess" : "Count") + ChatColor.GOLD + "!";
                           }
                           break;
                        case 710964381:
                           if (!arg0.equals("gentleman")) {
                              break label125;
                           }
                           break label147;
                        default:
                           break label125;
                        }

                        if (upMsg == null) {
                           upMsg = ChatColor.GOLD + "Your city is growing, your people love you, and a day of celebration for the new " + ChatColor.YELLOW + (isFemale ? "Grand Duchess" : "Grand Duke") + ChatColor.GOLD + " is at hand!";
                        }
                     }

                     if (upMsg == null) {
                        upMsg = ChatColor.GOLD + "You are next in line for the thrown and are now a " + ChatColor.YELLOW + (isFemale ? "Princess" : "Prince") + ChatColor.GOLD + "!";
                     }
                  }

                  if (upMsg == null) {
                     upMsg = ChatColor.GOLD + "The people in your city honor you as they crown you the " + ChatColor.YELLOW + (isFemale ? "Queen" : "King") + ChatColor.GOLD + " of the land!";
                  }

                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove baron");
               }

               if (upMsg == null) {
                  upMsg = ChatColor.GOLD + "You have chosen the noble path of a " + ChatColor.YELLOW + (isFemale ? "Maiden" : "Gentleman") + ChatColor.GOLD + "!";
               }
            }

            if (upMsg == null) {
               upMsg = ChatColor.GOLD + "As you become known throughout the city, people delight in calling you M'" + ChatColor.YELLOW + (isFemale ? "Lady" : "Lord") + ChatColor.GOLD + "!";
            }
         }

         if (upMsg == null) {
            upMsg = ChatColor.GOLD + "Your people adore you and show their admiration for their new " + ChatColor.YELLOW + (isFemale ? "Baroness" : "Baron") + ChatColor.GOLD + "!";
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.primary.nobility");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.display.nobility");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add " + arg0);
      }

      if (upMsg != null) {
         this.msgCaller(pTarget, upMsg);
      }

   }

   public void setTradesmanLevels(Player pUser, Player pTarget, String arg0) {
      String upMsg;
      label90: {
         label97: {
            label92: {
               label98: {
                  label80: {
                     label79: {
                        upMsg = null;
                        // value not used: decompiler artifact?
                        // boolean isFemale = pTarget.hasPermission("Qrpg.profile.gender.female");
                        switch(arg0.hashCode()) {
                        case -1619702940:
                           if (!arg0.equals("blacksmith")) {
                              break label90;
                           }
                           break label98;
                        case -865715314:
                           if (!arg0.equals("trader")) {
                              break label90;
                           }
                           break label92;
                        case -691820348:
                           if (!arg0.equals("peddler")) {
                              break label90;
                           }
                           break label97;
                        case -505296440:
                           if (!arg0.equals("merchant")) {
                              break label90;
                           }
                           break;
                        case -461589144:
                           if (!arg0.equals("shopkeeper")) {
                              break label90;
                           }

                           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.tradesman.shop.available");
                           if (upMsg == null) {
                              upMsg = ChatColor.GOLD + "You have traded enough to warrant your own private shop - you are now a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + "!";
                           }
                           break;
                        case -64015453:
                           if (!arg0.equals("treasurer")) {
                              break label90;
                           }
                           break label79;
                        case 2049250455:
                           if (!arg0.equals("chancellor")) {
                              break label90;
                           }
                           break label80;
                        default:
                           break label90;
                        }

                        if (upMsg == null) {
                           upMsg = ChatColor.GOLD + "Trading has become second nature for you as a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + "!";
                        }
                     }

                     if (upMsg == null) {
                        upMsg = ChatColor.GOLD + "Your mastery of trading has earned you great wealth and you are now known as a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + "!";
                     }
                  }

                  if (upMsg == null) {
                     upMsg = ChatColor.GOLD + "You have a commanding knowledge of trading and are known across the land as a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + ", selling quality goods and supplies to everyone!";
                  }

                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group remove blacksmith");
               }

               if (upMsg == null) {
                  upMsg = ChatColor.GOLD + "You have learned the art of metalworking as a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + ", and can now make even more items to trade!";
               }

               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember tmanvil -w world " + pTarget.getName());
            }

            if (upMsg == null) {
               upMsg = ChatColor.GOLD + "Learning how to haggle, you find yourself making good and fair deals as a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + "!";
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember tmenchant -w world " + pTarget.getName());
         }

         if (upMsg == null) {
            upMsg = ChatColor.GOLD + "You pick up some trinkets and sell them on the street, earning a living as a " + ChatColor.YELLOW + arg0 + ChatColor.GOLD + "!";
         }

         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember tlibrary -w world " + pTarget.getName());
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.class.primary.tradesman");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.display.tradesman");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " group add " + arg0);
      }

      if (upMsg != null) {
         this.msgCaller(pTarget, upMsg);
      }

   }

   public void SyncClassRegions(Player pTarget) {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember tmanvil -w world " + pTarget.getName());
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember tmenchant -w world " + pTarget.getName());
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember tlibrary -w world " + pTarget.getName());
   }

   public void SyncHearts(Player pTarget) {
      int hearts = 20;
      if (pTarget.hasPermission("Qrpg.class.bowman")) {
         hearts = 23;
      }

      if (pTarget.hasPermission("Qrpg.class.warrior")) {
         hearts = 25;
      }

      if (pTarget.hasPermission("Qrpg.class.knight")) {
         hearts = 27;
      }

      if (pTarget.hasPermission("Qrpg.class.captain")) {
         hearts = 30;
      }

      if (pTarget.hasPermission("Qrpg.class.kingsguard")) {
         hearts = 33;
      }

      if (pTarget.hasPermission("Qrpg.class.commander")) {
         hearts = 35;
      }

      if (pTarget.hasPermission("Qrpg.class.hero")) {
         hearts = 40;
      }

      AttributeInstance attribute = pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      attribute.setBaseValue((double)hearts);
      pTarget.setHealth((double)hearts);
   }

   public void setProperPrefix(Player pTarget) {
      if (pTarget.hasPermission("Qrpg.profile.prefix.donor")) {
         this.qfcore.staffMgr.clearPrefix(pTarget);
      } else {
         String playerName = pTarget.getName();
         if (pTarget.hasPermission("Qrpg.profile.prefix.rank")) {
            boolean isFemale = pTarget.hasPermission("Qrpg.profile.gender.female");
            if (pTarget.hasPermission("Qrpg.class.primary.nobility")) {
               if (pTarget.hasPermission("Qrpg.class.king")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&8[&4" + (isFemale ? "Queen" : "King") + "&8] &4\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.prince")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&4[&3" + (isFemale ? "Princess" : "Prince") + "&4] &3\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.grandduke")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9[&e" + (isFemale ? "Grand Duchess" : "Grand Duke") + "&9] &e\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.count")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9[&c" + (isFemale ? "Countess" : "Count") + "&9] &c\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.baron")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9[&b" + (isFemale ? "Baroness" : "Baron") + "&9] &b\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.lord")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&b[&5" + (isFemale ? "Lady" : "Lord") + "&b] &5\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.gentleman")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&3[&8" + (isFemale ? "Maiden" : "Gentleman") + "&3] &8\"");
                  return;
               }

               return;
            }

            if (pTarget.hasPermission("Qrpg.class.primary.soldier")) {
               if (pTarget.hasPermission("Qrpg.class.hero")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&8<&4" + (isFemale ? "Heroine" : "Hero") + "&8> &4\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.commander")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&4<&3" + (isFemale ? "Commander" : "Commander") + "&4> &3\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.kingsguard")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9<&e" + (isFemale ? "Queen's Guard" : "King's Guard") + "&9> &e\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.captain")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9<&c" + (isFemale ? "Captain" : "Captain") + "&9> &c\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.knight")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9<&b" + (isFemale ? "Dame" : "Knight") + "&9> &b\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.warrior")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&b<&5" + (isFemale ? "Warrior" : "Warrior") + "&b> &5\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.bowman")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&3<&8" + (isFemale ? "Archeress" : "Bowman") + "&3> &8\"");
                  return;
               }

               return;
            }

            if (pTarget.hasPermission("Qrpg.class.primary.tradesman")) {
               if (pTarget.hasPermission("Qrpg.class.chancellor")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&8{&4" + (isFemale ? "Chancellor" : "Chancellor") + "&8} &4\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.treasurer")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&4{&3" + (isFemale ? "Treasurer" : "Treasurer") + "&4} &3\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.merchant")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9{&e" + (isFemale ? "Merchant" : "Merchant") + "&9} &e\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.shopkeeper")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9{&c" + (isFemale ? "Shopkeeper" : "Shopkeeper") + "&9} &c\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.blacksmith")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9{&b" + (isFemale ? "Blacksmith" : "Blacksmith") + "&9} &b\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.trader")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&b{&5" + (isFemale ? "Trader" : "Trader") + "&b} &5\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.peddler")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&3{&8" + (isFemale ? "Peddler" : "Peddler") + "&3} &8\"");
                  return;
               }

               return;
            }

            if (pTarget.hasPermission("Qrpg.class.primary.clergy")) {
               if (pTarget.hasPermission("Qrpg.class.grandmaster")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&8(&4" + (isFemale ? "Grand Master" : "Grand Master") + "&8) &4\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.paladin")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&4(&3" + (isFemale ? "Paladin" : "Paladin") + "&4) &3\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.crusader")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9(&e" + (isFemale ? "Crusader" : "Crusader") + "&9) &e\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.bishop")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9(&c" + (isFemale ? "Bishop" : "Bishop") + "&9) &c\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.monk")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&9(&b" + (isFemale ? "Ascetic" : "Monk") + "&9) &b\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.cleric")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&b(&5" + (isFemale ? "Cleric" : "Cleric") + "&b) &5\"");
                  return;
               }

               if (pTarget.hasPermission("Qrpg.class.acolyte")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " prefix \"&3(&8" + (isFemale ? "Acolyte" : "Acolyte") + "&3) &8\"");
                  return;
               }

               return;
            }
         }

      }
   }
}
