package me.yourselvs.qfcore.mytown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.yourselvs.qfcore.QfGeneral;

public class QrpgMyTownMgr extends QfGeneral implements CommandExecutor {
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

      String arg0;
      String specName;
      String landType;
      String landMsg;
      int i;
      label326: {
         String cmdName = cmd.getName().toLowerCase();
         specName = null;
         landType = null;
         String rgName;
         switch(cmdName.hashCode()) {
         case -1059329641:
            if (!cmdName.equals("myland")) {
               return false;
            }
            break label326;
         case -1059114334:
            if (cmdName.equals("myshop")) {
               if (args.length == 0) {
                  if (!isPlayer) {
                     this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
                     return true;
                  }

                  this.goMyShop(pUser);
                  return true;
               }

               if (args.length == 1) {
                  arg0 = args[0];
                  if ("open".startsWith(arg0)) {
                     this.flagsMyShop(pTarget, "open");
                     return true;
                  }

                  if ("close".startsWith(arg0)) {
                     this.flagsMyShop(pTarget, "close");
                     return true;
                  }

                  return false;
               }

               landMsg = "";

               for(i = 1; i < args.length; ++i) {
                  landMsg = landMsg + args[i] + " ";
               }

               landMsg = landMsg.replace("\"", "").trim();
               arg0 = args[0];
               if ("greeting".startsWith(arg0)) {
                  this.msgMyShop(pTarget, "greeting", landMsg);
                  return true;
               }

               if ("farewell".startsWith(arg0)) {
                  this.msgMyShop(pTarget, "farewell", landMsg);
                  return true;
               }

               return false;
            }

            return false;
         case -1059077570:
            if (!cmdName.equals("mytown")) {
               return false;
            }

            specName = "a town";
            landType = "town";
            break label326;
         case 3314155:
            if (!cmdName.equals("land")) {
               return false;
            }
            break;
         case 3529462:
            if (cmdName.equals("shop")) {
               if (args.length < 2) {
                  this.msgCaller(pUser, ChatColor.RED + "Cannot use this command unless an option and player name are specified");
                  return true;
               }

               if (args.length <= 2 && "home".startsWith(args[0].toLowerCase())) {
                  this.qfcore.landHomeMgr.goLandHome(pUser, args[1], true);
                  return true;
               }

               pTarget = this.qfcore.getServer().getPlayer(args[args.length - 1]);
               if (pTarget == null) {
                  this.msgCaller(pUser, ChatColor.RED + "Could not locate the player to work with");
                  return true;
               }

               rgName = pTarget.getName() + "shop";
               arg0 = args[0];
               if (args.length == 2) {
                  if ("setup".startsWith(arg0)) {
                     pUser.performCommand("region define " + rgName);
                     pUser.performCommand("region setpriority " + rgName + " 51");
                     pUser.performCommand("region flag " + rgName + " -w world entry allow");
                     pUser.performCommand("region flag " + rgName + " -w world use allow");
                     pUser.performCommand("region flag " + rgName + " -w world mob-spawning deny");
                     pUser.performCommand("region flag " + rgName + " -w world leaf-decay deny");
                     pUser.performCommand("region flag " + rgName + " -w world vine-growth deny");
                     pUser.performCommand("region flag " + rgName + " -w world potion-splash deny");
                     pUser.performCommand("region flag " + rgName + " -w world pvp deny");
                     pUser.performCommand("region flag " + rgName + " -w world build");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + rgName + " -w world " + pTarget.getName());
                     this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully defined and set safe flags for region " + ChatColor.RESET + pTarget.getName());
                     return true;
                  }

                  if ("reset".startsWith(arg0)) {
                     if (isPlayer) {
                        pUser.performCommand("region setpriority " + rgName + " -w world 51");
                        pUser.performCommand("region flag " + rgName + " -w world entry allow");
                        pUser.performCommand("region flag " + rgName + " -w world use allow");
                        pUser.performCommand("region flag " + rgName + " -w world mob-spawning deny");
                        pUser.performCommand("region flag " + rgName + " -w world leaf-decay deny");
                        pUser.performCommand("region flag " + rgName + " -w world vine-growth deny");
                        pUser.performCommand("region flag " + rgName + " -w world potion-splash deny");
                        pUser.performCommand("region flag " + rgName + " -w world pvp deny");
                        pUser.performCommand("region flag " + rgName + " -w world build");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + rgName + " -w world " + pTarget.getName());
                        this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully reset the safe flags for region " + ChatColor.RESET + pTarget.getName());
                     } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region setpriority" + rgName + " -w world 51");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world entry allow");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world use allow");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world mob-spawning deny");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world leaf-decay deny");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world vine-growth deny");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world potion-splash deny");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world pvp deny");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w world build");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + rgName + " -w world " + pTarget.getName());
                        this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully reset the safe flags for region " + ChatColor.RESET + pTarget.getName());
                     }

                     return true;
                  }

                  if ("give".startsWith(arg0)) {
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.tradesman.shop.available");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.myshop");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.myshop.given");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.myshop.home");
                     this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully gave " + ChatColor.RESET + pTarget.getDisplayName() + ChatColor.RESET + ChatColor.DARK_GREEN + " their " + ChatColor.GOLD + "marketplace shop");
                     this.msgCaller(pTarget, ChatColor.GREEN + "You now own " + ChatColor.YELLOW + "a shop " + ChatColor.GREEN + "in the marketplace");
                     this.qfcore.landHomeMgr.setShopHome(pUser, pTarget);
                     return true;
                  }

                  if ("take".startsWith(arg0)) {
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.myshop");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.myshop.given");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.myshop.home");
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember " + rgName + " -w world " + pTarget.getName());
                     this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully took " + ChatColor.RESET + pTarget.getDisplayName() + ChatColor.RESET + ChatColor.DARK_GREEN + " their " + ChatColor.GOLD + "marketplace shop");
                     this.msgCaller(pTarget, ChatColor.RED + "You no longer own " + ChatColor.GOLD + "a shop" + ChatColor.RED + "in the marketplace");
                     return true;
                  }

                  return false;
               }
            }

            return false;
         case 3566226:
            if (cmdName.equals("town")) {
               specName = "a town";
               landType = "town";
               break;
            }

            return false;
         default:
            return false;
         }

         if (specName == null) {
            specName = "a tract of land";
            landType = "land";
         }

         if (args.length == 0) {
            if (!isPlayer) {
               this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
               return true;
            }

            this.msgCaller(pUser, ChatColor.BLUE + "Use this command to admin towns/lands for players");
            return true;
         }

         if (args.length <= 2 && "list".startsWith(args[0].toLowerCase())) {
            String cat;
            String dispStr;
            if (args.length == 2) {
               cat = null;
               if ("nobility".startsWith(args[1].toLowerCase())) {
                  cat = "nobility";
               } else if ("tradesman".startsWith(args[1].toLowerCase())) {
                  cat = "tradesman";
               } else if ("clergy".startsWith(args[1].toLowerCase())) {
                  cat = "clergy";
               } else if ("soldier".startsWith(args[1].toLowerCase())) {
                  cat = "soldier";
               }

               dispStr = this.qfcore.landHomeMgr.listItems2(cat);
            } else {
               dispStr = this.qfcore.landHomeMgr.listItems2(null);
               cat = "null";
            }

            this.msgCaller(pUser, dispStr);
            return true;
         }

         if (args.length <= 2 && "home".startsWith(args[0].toLowerCase())) {
            this.qfcore.landHomeMgr.goLandHome(pUser, args[1], false);
            return true;
         }

         pTarget = this.qfcore.getServer().getPlayer(args[args.length - 1]);
         if (pTarget == null) {
            this.msgCaller(pUser, ChatColor.RED + "Could not locate the player to work with");
            return true;
         }

         rgName = pTarget.getName();
         arg0 = args[0];
         if (args.length == 2) {
            if ("setup".startsWith(arg0)) {
               pUser.performCommand("region define " + rgName);
               pUser.performCommand("region setpriority " + rgName + " 51");
               pUser.performCommand("region flag " + rgName + " entry allow");
               pUser.performCommand("region flag " + rgName + " use allow");
               pUser.performCommand("region flag " + rgName + " mob-spawning allow");
               pUser.performCommand("region flag " + rgName + " leaf-decay allow");
               pUser.performCommand("region flag " + rgName + " potion-splash allow");
               pUser.performCommand("region flag " + rgName + " pvp deny");
               pUser.performCommand("region flag " + rgName + " weather-lock");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + rgName + " -w Survival " + rgName);
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully defined and set safe flags for region " + ChatColor.RESET + pTarget.getName());
               return true;
            }

            if ("reset".startsWith(arg0)) {
               if (isPlayer) {
                  pUser.performCommand("region setpriority " + rgName + " 51");
                  pUser.performCommand("region flag " + rgName + " entry allow");
                  pUser.performCommand("region flag " + rgName + " use allow");
                  pUser.performCommand("region flag " + rgName + " mob-spawning allow");
                  pUser.performCommand("region flag " + rgName + " leaf-decay allow");
                  pUser.performCommand("region flag " + rgName + " potion-splash allow");
                  pUser.performCommand("region flag " + rgName + " pvp deny");
                  pUser.performCommand("region flag " + rgName + " weather-lock");
                  pUser.performCommand("region flag " + rgName + " build");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + rgName + " -w Survival " + rgName);
               } else {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region setpriority " + rgName + " 51");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival entry allow");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival use allow");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival mob-spawning allow");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival leaf-decay allow");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival potion-splash allow");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival pvp deny");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival weather-lock");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + rgName + " -w Survival build");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + rgName + " -w Survival " + rgName);
               }

               this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully reset the safe flags for region " + ChatColor.RESET + pTarget.getName());
               return true;
            }

            if ("give".startsWith(arg0)) {
               if (pTarget.hasPermission("Qrpg.class.primary.nobility")) {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.mytown");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.mytown.given");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.mytown.home");
               } else {
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.myland");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.myland.given");
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " add Qrpg.myland.home");

                  this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully set " + ChatColor.GOLD + landType + ChatColor.DARK_GREEN + " home and gave " + ChatColor.RESET + pTarget.getDisplayName() + ChatColor.RESET + ChatColor.DARK_GREEN + " their " + ChatColor.GOLD + landType);
                  this.msgCaller(pTarget, ChatColor.GREEN + "You now own " + ChatColor.YELLOW + specName);
                  this.qfcore.landHomeMgr.setLandHome(pUser, pTarget);
                  return true;
               }
            }

            if ("take".startsWith(arg0)) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.mytown");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.mytown.given");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.mytown.home");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.myland");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.myland.given");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + pTarget.getName() + " remove Qrpg.myland.home");
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully took " + ChatColor.RESET + pTarget.getDisplayName() + ChatColor.RESET + ChatColor.DARK_GREEN + "'s " + ChatColor.GOLD + landType);
               this.msgCaller(pTarget, ChatColor.RED + "You no longer own " + ChatColor.GOLD + specName);
               return true;
            }

            if ("sethome".startsWith(arg0)) {
               this.qfcore.landHomeMgr.setLandHome(pUser, pTarget);
               return true;
            }
         }

         return false;
      }

      if (specName == null) {
         specName = "a tract of land";
         landType = "land";
      }

      if (args.length == 0) {
         if (!isPlayer) {
            this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
            return true;
         } else {
            this.goMyTown(pUser, specName, landType);
            return true;
         }
      } else if (!pUser.hasPermission("Qrpg.mytown.given") && !pUser.hasPermission("Qrpg.myland.given")) {
         this.msgCaller(pUser, ChatColor.RED + "You must have " + ChatColor.GOLD + specName + ChatColor.RED + " to use this command");
         return true;
      } else {
         arg0 = args[0];
         if (args.length == 1) {
            if ("info".startsWith(arg0)) {
               this.infoMyTown(pUser, pUser.getName());
               return true;
            }

            if ("help".startsWith(arg0)) {
               this.helpMyTown(pUser, landType);
               return true;
            }
         }

         if (args.length == 2) {
            if ("add".startsWith(arg0)) {
               this.addMyTown(pUser, args[1], landType);
               return true;
            }

            if ("remove".startsWith(arg0)) {
               this.removeMyTown(pUser, args[1], landType);
               return true;
            }

            if ("help".startsWith(arg0)) {
               this.helpMyTown(pUser, landType);
               return true;
            }

            if ("info".startsWith(arg0)) {
               this.infoMyTown(pUser, arg0);
               return true;
            }

            if ("mobs".startsWith(arg0)) {
               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }

            if ("use".startsWith(arg0)) {
               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }

            if ("visitors".startsWith(arg0)) {
               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }

            if ("splash".startsWith(arg0)) {
               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }

            if ("leaves".startsWith(arg0)) {
               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }

            if ("vines".startsWith(arg0)) {
               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }

            if ("weather".startsWith(arg0)) {
               if (!pUser.hasPermission("Qrpg.land.weather")) {
                  this.msgCaller(pUser, ChatColor.RED + "You do not yet have the ability to control the weather in your " + landType);
                  return true;
               }

               this.flagsMyTown(pUser, arg0, args[1], landType);
               return true;
            }
         }

         landMsg = "";

         for(i = 1; i < args.length; ++i) {
            landMsg = landMsg + args[i] + " ";
         }

         landMsg = landMsg.replace("\"", "").trim();
         if ("greeting".startsWith(arg0)) {
            this.messageMyTown(pUser, arg0, args[1], landType, landMsg);
            return true;
         } else if ("farewell".startsWith(arg0)) {
            this.messageMyTown(pUser, arg0, args[1], landType, landMsg);
            return true;
         } else {
            return false;
         }
      }
   }

   private void goMyShop(Player pUser) {
      if (pUser.hasPermission("Qrpg.myshop.home")) {
         this.qfcore.landHomeMgr.goLandHome(pUser, pUser.getName(), true);
      } else {
         this.msgCaller(pUser, ChatColor.BLUE + "Soon this command will take you to your " + ChatColor.AQUA + "marketplace shop " + ChatColor.BLUE + ".");
      }

   }

   private void goMyTown(Player pUser, String landType, String specType) {
      if ((!pUser.hasPermission("Qrpg.myland.home") || !specType.equalsIgnoreCase("land")) && (!pUser.hasPermission("Qrpg.mytown.home") || !specType.equalsIgnoreCase("town"))) {
         this.msgCaller(pUser, ChatColor.BLUE + "Soon this command will take you to " + ChatColor.AQUA + landType.replace("a ", "the ") + ChatColor.BLUE + " that you own.");
      } else {
         this.qfcore.landHomeMgr.goLandHome(pUser, pUser.getName(), false);
      }

   }

   private void helpMyTown(Player pUser, String specType) {
      this.msgCaller(pUser, ChatColor.BLUE + "Use " + ChatColor.GRAY + "/my" + specType + " to go to your " + specType);
      this.msgCaller(pUser, ChatColor.BLUE + "You can use " + ChatColor.GRAY + "/my" + specType + " (info,add,remove,greeting,farewell,mobs,visitors,vines,leaves) (on, off, message, player)" + ChatColor.BLUE + " to manage your " + specType);
      this.msgCaller(pUser, ChatColor.BLUE + "For example, use " + ChatColor.GRAY + "/my" + specType + " mobs off" + ChatColor.BLUE + " to turn off mob spawning in your " + specType);
   }

   private void infoMyTown(Player pUser, String rgName) {
      pUser.performCommand("region info " + rgName + " -w Survival");
   }

   private void addMyTown(Player pUser, String targetName, String landType) {
      try {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region addmember " + pUser.getName() + " -w Survival " + targetName);
         this.msgCaller(pUser, ChatColor.GREEN + "Successfully added " + ChatColor.GOLD + targetName + ChatColor.GREEN + " to your " + landType);
      } catch (CommandException var5) {
         this.msgCaller(pUser, ChatColor.RED + "Problem: " + ChatColor.DARK_RED + var5.getMessage());
      }

   }

   private void removeMyTown(Player pUser, String targetName, String landType) {
      try {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region removemember " + pUser.getName() + " -w Survival " + targetName);
         this.msgCaller(pUser, ChatColor.GREEN + "Successfully removed " + ChatColor.GOLD + targetName + ChatColor.GREEN + " from your " + landType);
      } catch (CommandException var5) {
         this.msgCaller(pUser, ChatColor.RED + "Problem: " + ChatColor.DARK_RED + var5.getMessage());
      }
   }

   private void messageMyTown(Player pUser, String flag, String targetName, String landType, String msg) {
      String flagl = flag.toLowerCase();
      String flagName;
      if ("greeting".startsWith(flagl)) {
         flagName = "greeting";
      } else {
         if (!"farewell".startsWith(flagl)) {
            this.msgCaller(pUser, ChatColor.RED + "Could not identify which message to set");
            return;
         }

         flagName = "farewell";
      }

      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + " -w Survival " + flagName + " " + msg);
      this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully set your " + ChatColor.GOLD + landType + "'s " + ChatColor.DARK_GREEN + flagName + " message");
   }

   private void msgMyShop(Player pUser, String flagl, String msg) {
      if ("greeting".startsWith(flagl)) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + "shop -w world " + flagl + " " + msg);
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Your shop's greeting has been set to: " + ChatColor.RESET + msg);
      } else if ("farewell".startsWith(flagl)) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + "shop -w world " + flagl + " " + msg);
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Your shop's farewell has been set to: " + ChatColor.RESET + msg);
      }

   }

   private void flagsMyShop(Player pUser, String flag) {
      String flagl = flag.toLowerCase();
      if ("open".startsWith(flagl)) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + "shop -w world entry allow");
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Your shop is now " + ChatColor.GREEN + "open " + ChatColor.DARK_GREEN + "for business!");
      } else if ("close".startsWith(flagl)) {
         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + "shop -w world entry deny");
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Your shop is now " + ChatColor.RED + "closed");
      }
   }

   private void flagsMyTown(Player pUser, String flag, String targetName, String landType) {
      String flagName = null;
      String dispStr = null;
      String flagl = flag.toLowerCase();
      if ("mobs".startsWith(flagl)) {
         flagName = "mob-spawning";
         dispStr = "mobs";
      } else if ("visitors".startsWith(flagl)) {
         flagName = "entry";
         dispStr = "visitors";
      } else if ("use".startsWith(flagl)) {
         flagName = "use";
         dispStr = "use";
      } else if ("splash".startsWith(flagl)) {
         flagName = "potion-spash";
         dispStr = "splash";
      } else if ("leaves".startsWith(flagl)) {
         flagName = "leaf-decay";
         dispStr = "leaves";
      } else if ("vines".startsWith(flagl)) {
         flagName = "vine-growth";
         dispStr = "vines";
      }

      if ("weather".startsWith(flagl)) {
         flagName = "weather-lock";
         dispStr = "weather";
         String flagOpt;
         if (targetName.equalsIgnoreCase("storm")) {
            flagOpt = "downfall";
         } else if (targetName.equalsIgnoreCase("sun")) {
            flagOpt = "clear";
         } else {
            flagOpt = "";
         }

         try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + " -w Survival weather-lock " + flagOpt);
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully set your " + landType + "'s " + ChatColor.GOLD + dispStr + ChatColor.DARK_GREEN + " to " + (flagOpt == "" ? ChatColor.GREEN + "NORMAL" : ChatColor.GOLD + targetName.toUpperCase()));
         } catch (CommandException var11) {
            this.msgCaller(pUser, ChatColor.RED + "Problem: " + ChatColor.DARK_RED + var11.getMessage());
         }

      } else {
         boolean doOn;
         if (targetName.equalsIgnoreCase("on")) {
            doOn = true;
         } else {
            doOn = false;
         }

         if (flagName == null) {
            this.msgCaller(pUser, ChatColor.RED + "Please specify which part of your town you want to work with: mobs, warp, ...");
         } else {
            try {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "region flag " + pUser.getName() + " -w Survival " + flagName + (doOn ? " ALLOW" : " DENY"));
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "Successfully set your " + landType + "'s " + ChatColor.GOLD + dispStr + ChatColor.DARK_GREEN + " to " + (doOn ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF"));
            } catch (CommandException var12) {
               this.msgCaller(pUser, ChatColor.RED + "Problem: " + ChatColor.DARK_RED + var12.getMessage());
            }

         }
      }
   }
}
