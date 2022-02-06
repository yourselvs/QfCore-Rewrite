package com.quantiforte.qfcore.info;

import com.quantiforte.qfcore.QfGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QrpgInfoMgr extends QfGeneral implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      // decompiler artifact
      // String playerName = "";
      boolean isPlayer = sender instanceof Player;
      if (isPlayer) {
         pUser = (Player)sender;
         pTarget = pUser;
         // playerName = pUser.getDisplayName();
      } else {
         pUser = null;
         pTarget = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      switch(cmdName.hashCode()) {
      case -1059150888:
         if (cmdName.equals("myrank")) {
            if (args.length == 0) {
               if (pUser == null) {
                  this.msgCaller(pUser, "Not sure which player to use");
                  return true;
               }

               this.doMyRank(pUser, pUser);
               return true;
            }

            if (args.length != 1 || pUser != null && !pUser.hasPermission("Qrpg.myrank.others")) {
               return false;
            }

            pTarget = this.qfcore.getServer().getPlayer(args[0]);
            if (pTarget == null) {
               this.msgCaller(pUser, ChatColor.RED + "Not sure which player to use");
               return true;
            }

            this.doMyRank(pUser, pTarget);
            return true;
         }
         break;
      case 3267670:
         if (cmdName.equals("jobs")) {
            this.msgCaller(pUser, ChatColor.GOLD + "Please use " + ChatColor.YELLOW + "/workorder " + ChatColor.GOLD + "(or " + ChatColor.YELLOW + "/wo" + ChatColor.GOLD + ") to see the available work orders");
            return true;
         }
         break;
      case 3492908:
         if (cmdName.equals("rank")) {
            if (args.length == 0) {
               this.msgCaller(pUser, ChatColor.DARK_RED + "Please specify the class for which you want rank tier information");
               return true;
            }

            String arg0 = args[0].toLowerCase();
            if ("clergy".startsWith(arg0)) {
               this.RankBenefitsClergy(pUser, pTarget, true);
               return true;
            }

            if ("tradesman".startsWith(arg0)) {
               this.RankBenefitsTradesman(pUser, pTarget, true);
               return true;
            }

            if ("soldier".startsWith(arg0)) {
               this.RankBenefitsSoldier(pUser, pTarget, true);
               return true;
            }

            if ("nobility".startsWith(arg0)) {
               this.RankBenefitsNobility(pUser, pTarget, true);
               return true;
            }

            if (pUser != null && !pUser.hasPermission("Qrpg.rank.others")) {
               this.msgCaller(pUser, ChatColor.DARK_RED + "Not sure which player you want info on");
               return true;
            }

            pTarget = this.qfcore.getServer().getPlayer(arg0);
            if (pTarget == null) {
               this.msgCaller(pUser, ChatColor.RED + "Not sure which player to use");
               return true;
            }

            this.doMyRank(pUser, pTarget);
            return true;
         }
         break;
      case 673186429:
         if (cmdName.equals("myprofile")) {
            this.doMyProfile(pUser, pTarget, args);
            return true;
         }
      }

      return false;
   }

   public void doMyProfile(Player pUser, Player pTarget, String[] args) {
      if (args.length == 0) {
         this.displayProfile(pUser, pTarget);
      } else {
         String playerName = pTarget.getName();
         String arg0 = args[0].toLowerCase();
         if (args.length == 1) {
            if ("rank".startsWith(arg0)) {
               this.doMyRank(pUser, pTarget);
               return;
            }

            this.msgCaller(pUser, ChatColor.RED + "Not sure which part of the profile to work with");
         }

         if (args.length == 2 && "prefix".startsWith(arg0)) {
            if ("female".startsWith(args[1].toLowerCase())) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.gender.male");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.profile.gender.female");
               this.msgCaller(pTarget, ChatColor.BLUE + "Your class rank prefix will be " + ChatColor.AQUA + "female " + ChatColor.BLUE + "whenever a choice is available");
            } else if ("male".startsWith(args[1].toLowerCase())) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.gender.female");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.profile.gender.male");
               this.msgCaller(pTarget, ChatColor.BLUE + "Your class rank prefix will be " + ChatColor.AQUA + "male " + ChatColor.BLUE + "whenever a choice is available");
            } else if ("donor".startsWith(args[1].toLowerCase())) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.rank");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.custom");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.profile.prefix.donor");
               this.msgCaller(pTarget, ChatColor.BLUE + "Your prefix will now show your " + ChatColor.AQUA + "donor level " + ChatColor.BLUE + "or, if you have not donated, your rank");
            } else if ("class".startsWith(args[1].toLowerCase()) || "rank".startsWith(args[1].toLowerCase())) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " add Qrpg.profile.prefix.rank");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.custom");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.donor");
               this.msgCaller(pTarget, ChatColor.BLUE + "Your prefix will now show your " + ChatColor.AQUA + "class rank");
            }

            this.qfcore.classMgr.setProperPrefix(pTarget);
         }

      }
   }

   public void clearPrefixPrefs(Player pTarget, boolean removeGender) {
      String playerName = pTarget.getName();
      if (removeGender) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.gender.female");
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.gender.male");
      }

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.rank");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.donor");
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playerName + " remove Qrpg.profile.prefix.custom");
   }

   public void displayProfile(Player pUser, Player pTarget) {
      this.msgCaller(pUser, ChatColor.BLUE + "Soon you will be able to see your player's " + ChatColor.AQUA + "profile " + ChatColor.BLUE + "information with this command");
      this.msgCaller(pUser, ChatColor.BLUE + "You can also use " + ChatColor.GRAY + "/myprofile prefix (female,male,donor,rank) " + ChatColor.BLUE + "to change your prefix preference");
   }

   public void doMyRank(Player pUser, Player pTarget) {
      boolean showAll = false;
      boolean didShow = false;
      if (pTarget.hasPermission("Qrpg.class.bowman")) {
         this.RankBenefitsSoldier(pUser, pTarget, showAll);
         didShow = true;
      }

      if (pTarget.hasPermission("Qrpg.class.acolyte")) {
         this.RankBenefitsClergy(pUser, pTarget, showAll);
         didShow = true;
      }

      if (pTarget.hasPermission("Qrpg.class.gentleman")) {
         this.RankBenefitsNobility(pUser, pTarget, showAll);
         didShow = true;
      }

      if (pTarget.hasPermission("Qrpg.class.peddler")) {
         this.RankBenefitsTradesman(pUser, pTarget, showAll);
         didShow = true;
      }

      if (!didShow) {
         this.msgCaller(pUser, ChatColor.DARK_RED + "Not currently in a class");
      }

   }

   public void RankBenefitsNobility(Player pUser, Player pTarget, boolean showAll) {
      String cm = ChatColor.GRAY + ", ";
      String dispStr = ChatColor.GOLD + "Rank benefits for " + ChatColor.YELLOW + "Nobility " + ChatColor.GOLD + "for " + pTarget.getDisplayName() + "\n";
      ChatColor tc;
      ChatColor rc;
      if (pTarget.hasPermission("Qrpg.class.gentleman")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Gentleman/Maiden: " + tc + "/food" + cm + tc + "/supplies (Supplies I)\n";
      if (pTarget.hasPermission("Qrpg.class.lord")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Lord/Lady: " + tc + "MyTown (125x125)" + cm + tc + "Supplies II\n";
      if (pTarget.hasPermission("Qrpg.class.baron")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Baroness/Baron: " + tc + "MyTown /warp" + cm + tc + "MyTown Swiftness" + cm + tc + "RPG Travel Pass I" + cm + tc + "/craft\n";
      if (showAll || pTarget.hasPermission("Qrpg.class.primary.nobility")) {
         if (pTarget.hasPermission("Qrpg.class.count")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Countess/Count: " + tc + "MyTown (250x250)" + cm + tc + "MyTown Weather" + cm + tc + "Community Build Chest" + cm + tc + "/ext\n";
         if (pTarget.hasPermission("Qrpg.class.grandduke")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "GrandDuke/GrandDuchess: " + tc + "MyTown Monument (with Benefit I)" + cm + tc + "Give Blessings" + cm + tc + "Community Food Chest" + cm + tc + "Noble Battle I" + cm + tc + "RPG Travel Pass II\n";
         if (pTarget.hasPermission("Qrpg.class.prince")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Prince/Princess: " + tc + "MyTown (500x500)" + cm + tc + "2nd Benefit (for MyTown Monument, *quest)" + cm + tc + "RPG Travel Pass III\n";
         if (pTarget.hasPermission("Qrpg.class.king")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "King/Queen: " + tc + "MyTown (600x600, *quest)" + cm + tc + "Royal Avatar" + cm + tc + "Royal Blessings" + cm + tc + "Benefit II (for MyTown Monument, *quest)" + cm + tc + "Noble Battle II\n" + cm + tc + "RPG Travel Pass IV\n";
      }

      this.msgCaller(pUser, dispStr);
   }

   public void RankBenefitsSoldier(Player pUser, Player pTarget, boolean showAll) {
      String cm = ChatColor.GRAY + ", ";
      String dispStr = ChatColor.GOLD + "Rank benefits for " + ChatColor.YELLOW + "Soldier " + ChatColor.GOLD + "for " + pTarget.getDisplayName() + "\n";
      ChatColor tc;
      ChatColor rc;
      if (pTarget.hasPermission("Qrpg.class.bowman")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Bowman/Archeress: " + tc + "11 1/2 hearts" + cm + tc + "/warcry (Warcry I)" + cm + tc + "/arena\n";
      if (pTarget.hasPermission("Qrpg.class.warrior")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Warrior: " + tc + "MyLand (25x25)" + cm + tc + "12 1/2 hearts\n";
      if (pTarget.hasPermission("Qrpg.class.knight")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Knight/Dame: " + tc + "13 1/2 hearts" + cm + tc + "/burst (Burst I)" + cm + tc + "Warcry II" + cm + tc + "/craft\n";
      if (showAll || pTarget.hasPermission("Qrpg.class.primary.soldier")) {
         if (pTarget.hasPermission("Qrpg.class.captain")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Captain: " + tc + "15 hearts" + cm + tc + "/ext\n";
         if (pTarget.hasPermission("Qrpg.class.kingsguard")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "King's Guard/Queen's Guard: " + tc + "MyLand (50x50)" + cm + tc + "16 1/2 hearts" + cm + tc + "Warcry III\n";
         if (pTarget.hasPermission("Qrpg.class.commander")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Commander: " + tc + "17 1/2 hearts" + cm + tc + "Burst II\n";
         if (pTarget.hasPermission("Qrpg.class.hero")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Heroine/Hero: " + tc + "20 hearts" + cm + tc + "Burst (faster cooldown)" + cm + tc + "Warcry IV" + cm + tc + "MyTown Weather\n";
      }

      this.msgCaller(pUser, dispStr);
   }

   public void RankBenefitsTradesman(Player pUser, Player pTarget, boolean showAll) {
      String cm = ChatColor.GRAY + ", ";
      String dispStr = ChatColor.GOLD + "Rank benefits for " + ChatColor.YELLOW + "Tradesman " + ChatColor.GOLD + "for " + pTarget.getDisplayName() + "\n";
      ChatColor tc;
      ChatColor rc;
      if (pTarget.hasPermission("Qrpg.class.peddler")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Peddler: " + tc + "Enhanced Crafting (/tlibrary)" + cm + tc + "/tools" + cm + tc + "/craft\n";
      if (pTarget.hasPermission("Qrpg.class.trader")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Trader: " + tc + "MyLand (25x25)" + cm + tc + "/etable\n";
      if (pTarget.hasPermission("Qrpg.class.blacksmith")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Blacksmith: " + tc + "/anvil" + cm + tc + "/anvil rename" + cm + tc + "Enhanced Enchant (*available soon)" + cm + tc + "Crafting Secrets I\n";
      if (showAll || pTarget.hasPermission("Qrpg.class.primary.tradesman")) {
         if (pTarget.hasPermission("Qrpg.class.shopkeeper")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "ShopKeeper: " + tc + "MyShop" + cm + tc + "/ext\n";
         if (pTarget.hasPermission("Qrpg.class.merchant")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Merchant: " + tc + "MyLand (50x50)" + cm + tc + "MyShop (upgrade I)\n";
         if (pTarget.hasPermission("Qrpg.class.treasurer")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Treasurer: " + tc + "High Enchant I" + cm + tc + "Crafting Secrets II\n";
         if (pTarget.hasPermission("Qrpg.class.chancellor")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Chancellor: " + tc + "MyShop (upgrade II)" + cm + tc + "High Enchant II" + cm + tc + "MyTown Weather\n";
      }

      this.msgCaller(pUser, dispStr);
   }

   public void RankBenefitsClergy(Player pUser, Player pTarget, boolean showAll) {
      String cm = ChatColor.GRAY + ", ";
      String dispStr = ChatColor.GOLD + "Rank benefits for " + ChatColor.YELLOW + "Clergy " + ChatColor.GOLD + "for " + pTarget.getDisplayName() + "\n";
      ChatColor tc;
      ChatColor rc;
      if (pTarget.hasPermission("Qrpg.class.acolyte")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Acolyte: " + tc + "Swiftness I" + cm + tc + "Bounce I" + cm + tc + "/heal I (self)\n";
      if (pTarget.hasPermission("Qrpg.class.cleric")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Cleric: " + tc + "MyLand (25x25)" + cm + tc + "Gills" + cm + tc + "Flamedamp I" + cm + tc + "/heal II (self)\n";
      if (pTarget.hasPermission("Qrpg.class.monk")) {
         rc = ChatColor.DARK_GREEN;
         tc = ChatColor.GREEN;
      } else {
         rc = ChatColor.DARK_RED;
         tc = ChatColor.RED;
      }

      dispStr = dispStr + rc + "Ascetic/Monk: " + tc + "Nighteye" + cm + tc + "Snack I" + cm + tc + "/heal III (self)" + cm + tc + "/ext (self)" + cm + tc + "/craft\n";
      if (showAll || pTarget.hasPermission("Qrpg.class.primary.clergy")) {
         if (pTarget.hasPermission("Qrpg.class.bishop")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Bishop: " + tc + "Fish" + cm + tc + "Fireproof I" + cm + tc + "/ext (others)\n";
         if (pTarget.hasPermission("Qrpg.class.crusader")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Crusader: " + tc + "MyLand (50x50)" + cm + tc + "Snack II" + cm + tc + "Flamedamp II" + cm + tc + "/heal I (others)\n";
         if (pTarget.hasPermission("Qrpg.class.paladin")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "Paladin: " + tc + "Strength I" + cm + tc + "Swiftness II" + cm + tc + "/heal II (others)\n";
         if (pTarget.hasPermission("Qrpg.class.grandmaster")) {
            rc = ChatColor.DARK_GREEN;
            tc = ChatColor.GREEN;
         } else {
            rc = ChatColor.DARK_RED;
            tc = ChatColor.RED;
         }

         dispStr = dispStr + rc + "GrandMaster: " + tc + "Strength II" + cm + tc + "Health" + cm + tc + "/heal III (others)" + cm + tc + "MyTown Weather\n";
      }

      this.msgCaller(pUser, dispStr);
   }
}
