package com.quantiforte.qfcore.health;

import com.quantiforte.qfcore.QfGeneral;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class QrpgHealthMgr extends QfGeneral implements CommandExecutor {
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

      boolean isBagel;
      label159: {
         String cmdName = cmd.getName().toLowerCase();
         isBagel = false;
         switch(cmdName.hashCode()) {
         case 100897:
            if (cmdName.equals("ext")) {
               if (args.length == 0) {
                  if (!isPlayer) {
                     this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
                     return true;
                  }

                  this.extPlayer(pUser, pUser);
                  return true;
               }

               if (args.length == 1) {
                  if (isPlayer && !pUser.hasPermission("Qrpg.extinguish.others")) {
                     this.msgCaller(pUser, ChatColor.RED + "You do not have the ability to extinguish others");
                     return true;
                  }

                  pTarget = this.qfcore.getServer().getPlayer(args[0]);
                  if (pTarget != null) {
                     this.extPlayer(pUser, pTarget);
                  } else {
                     this.msgCaller(pUser, ChatColor.RED + "Could not find player " + ChatColor.WHITE + args[0] + ChatColor.RED + " to extinguish.");
                  }

                  return true;
               }

               this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have permission to use that command");
               return true;
            }
            break;
         case 3138974:
            if (cmdName.equals("feed")) {
               break label159;
            }
            break;
         case 3148894:
            if (cmdName.equals("food")) {
               if (!isPlayer) {
                  this.msgCaller(pUser, "Cannot use this command from console");
                  return true;
               }

               if (!pUser.hasPermission("Qrpg.class.gentleman")) {
                  this.msgCaller(pUser, ChatColor.DARK_RED + "You must be Nobility to use this command");
                  return true;
               }

               this.DoFood(pUser);
               return true;
            }
            break;
         case 3198440:
            if (cmdName.equals("heal")) {
               if (args.length == 0) {
                  if (!isPlayer) {
                     this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
                     return true;
                  }

                  this.healPlayer(pUser, pUser);
                  return true;
               }

               if (args.length == 1) {
                  if (isPlayer && !pUser.hasPermission("Qrpg.heal.others")) {
                     this.msgCaller(pUser, ChatColor.RED + "You do not have the ability to heal others");
                     return true;
                  }

                  pTarget = this.qfcore.getServer().getPlayer(args[0]);
                  if (pTarget != null) {
                     this.healPlayer(pUser, pTarget);
                  } else {
                     this.msgCaller(pUser, ChatColor.RED + "Could not find player " + ChatColor.WHITE + args[0] + ChatColor.RED + " to heal.");
                  }

                  return true;
               }

               this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have permission to use that command");
               return true;
            }
            break;
         case 3242704:
            if (cmdName.equals("itch")) {
               if (args.length == 0) {
                  if (!isPlayer) {
                     this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
                     return true;
                  }

                  this.itchPlayer(pUser, pUser);
                  return true;
               }

               if (args.length == 1) {
                  if (isPlayer && !pUser.hasPermission("Qrpg.itch.others")) {
                     this.msgCaller(pUser, ChatColor.RED + "You do not have the ability to make others itch");
                     return true;
                  }

                  pTarget = this.qfcore.getServer().getPlayer(args[0]);
                  if (pTarget != null) {
                     this.itchPlayer(pUser, pTarget);
                  } else {
                     this.msgCaller(pUser, ChatColor.RED + "Could not find player " + ChatColor.WHITE + args[0] + ChatColor.RED + " to itch.");
                  }

                  return true;
               }

               this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have permission to use that command");
               return true;
            }
            break;
         case 93497007:
            if (cmdName.equals("bagel")) {
               isBagel = true;
               break label159;
            }
         }

         return false;
      }

      if (args.length == 0) {
         if (!isPlayer) {
            this.msgCaller(pUser, "Cannot use this command from console unless a player name is specified");
            return true;
         } else {
            this.feedPlayer(pUser, isBagel ? ChatColor.GOLD + "Nom Nom Nom - " + ChatColor.YELLOW + "Bagels" + ChatColor.GOLD + "!" : null);
            return true;
         }
      } else if (args.length == 1) {
         if (isPlayer && !pUser.hasPermission("Qrpg.feed.others")) {
            this.msgCaller(pUser, ChatColor.RED + "You do not have the ability to feed others");
            return true;
         } else {
            pTarget = this.qfcore.getServer().getPlayer(args[0]);
            if (pTarget != null) {
               this.feedPlayer(pTarget, isBagel ? ChatColor.GOLD + "You indulge in yummy " + ChatColor.YELLOW + "bagels" + ChatColor.GOLD + " given to you by " + playerName + ChatColor.GOLD + " and feel entirely sated." : null);
            } else {
               this.msgCaller(pUser, ChatColor.RED + "Could not find player " + ChatColor.WHITE + args[0] + ChatColor.RED + (isBagel ? " to give bagels to" : " to feed."));
            }

            return true;
         }
      } else {
         this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have permission to use that command");
         return true;
      }
   }

   public void DoFood(Player pPlayer) {
      if (this.qfcore.cooldownMgr.checkCooldown(pPlayer, "food", true) == null) {
         int count = 0;
         Inventory inv = pPlayer.getInventory();
         // artifact
         // ItemStack[] var12;
         int var11 = inv.getContents().length;

         for(int var10 = 0; var10 < var11; ++var10) {
        	// wack decompiler artifact
            // ItemStack var10000 = var12[var10];
            ++count;
            if (count >= 2) {
               break;
            }
         }

         if (count < 2) {
            this.msgCaller(pPlayer, ChatColor.RED + "You need " + ChatColor.WHITE + "2 " + ChatColor.RED + "open inventory spots to use this command");
         } else {
            int numItems = 12 + (int)(Math.random() * 5.0D);
            int numItems2 = 36 + (int)(Math.random() * 6.0D);
            if (pPlayer.hasPermission("Qrpg.class.king")) {
               numItems += 3;
               numItems2 += 4;
            } else if (pPlayer.hasPermission("Qrpg.class.prince")) {
               numItems += 3;
               numItems2 += 4;
            } else if (pPlayer.hasPermission("Qrpg.class.grandduke")) {
               numItems += 3;
               numItems2 += 4;
            } else if (pPlayer.hasPermission("Qrpg.class.count")) {
               numItems += 3;
               numItems2 += 3;
            } else if (pPlayer.hasPermission("Qrpg.class.baron")) {
               numItems += 3;
               numItems2 += 3;
            } else if (pPlayer.hasPermission("Qrpg.class.lord")) {
               numItems += 3;
               numItems2 += 4;
            }

            ItemStack itemFood2 = new ItemStack(Material.COOKED_BEEF, numItems);
            pPlayer.getInventory().addItem(new ItemStack[]{itemFood2});
            ItemStack itemFood = new ItemStack(Material.POTATO, numItems2);
            pPlayer.getInventory().addItem(new ItemStack[]{itemFood});
            this.qfcore.cooldownMgr.addCooldown(pPlayer, "food", 86400L);
            this.msgCaller(pPlayer, ChatColor.GREEN + "Your townsfolk have graciously given you your daily food");
         }
      }
   }

   public void extPlayer(Player pUser, Player pTarget) {
      if (pTarget != null && pTarget.getFireTicks() > 0) {
         if (pUser == null) {
            pTarget.setFireTicks(0);
         } else {
            String cool;
            Long coolTime;
            String dispMsg;
            if (pUser.getName().equalsIgnoreCase(pTarget.getName())) {
               cool = "extinguish";
               coolTime = 10L;
               dispMsg = ChatColor.AQUA + "Your flames have been extinguished";
            } else {
               if (!pUser.hasPermission("Qrpg.extinguish.others")) {
                  this.msgCaller(pUser, ChatColor.DARK_RED + "You do not have the ability to extinguish other players");
                  return;
               }

               cool = "extinguish.other";
               coolTime = 20L;
               dispMsg = ChatColor.AQUA + "Your flames have been extinguished by " + pUser.getDisplayName();
            }

            if (pUser != null && !pUser.hasPermission("QfCore.admin")) {
               if (this.qfcore.cooldownMgr.checkCooldown(pUser, cool, true) != null) {
                  return;
               }

               pTarget.setFireTicks(0);
               this.qfcore.cooldownMgr.addCooldown(pUser, cool, coolTime);
            } else {
               pTarget.setFireTicks(0);
            }

            this.msgCaller(pTarget, dispMsg);
         }

      }
   }

   public void healPlayer(Player pUser, Player pTarget) {
      if (pTarget != null) {
         String healMsg = null;
         boolean healSelf = false;
         Long coolTime = 60L;
         double maxHealth = pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
         if (pUser == null) {
        	;
            pTarget.setHealth(maxHealth);
            pTarget.setFoodLevel(20);
            pTarget.setFireTicks(0);
         } else {
            double healAmt;
            String cool;
            if (pUser.getName().equalsIgnoreCase(pTarget.getName())) {
               healSelf = true;
               cool = "heal";
               coolTime = 60L;
               if (pUser.hasPermission("Qrpg.heal.full")) {
                  healAmt = maxHealth;
                  coolTime = 200L;
               } else {
                  healAmt = 1.0D;
                  if (pUser.hasPermission("Qrpg.heal.1")) {
                     healAmt += (double)(1 + (int)(Math.random() * 3.0D));
                     coolTime = 60L;
                  }

                  if (pUser.hasPermission("Qrpg.heal.2")) {
                     healAmt += (double)(1 + (int)(Math.random() * 4.0D));
                     coolTime = 150L;
                  }
               }
            } else {
               cool = "heal.other";
               coolTime = 60L;
               if (pUser.hasPermission("Qrpg.heal.others.full")) {
                  healAmt = maxHealth;
                  coolTime = 240L;
               } else {
                  healAmt = 1.0D;
                  if (pUser.hasPermission("Qrpg.heal.others.1")) {
                     healAmt += (double)(1 + (int)(Math.random() * 3.0D));
                     coolTime = 60L;
                  }

                  if (pUser.hasPermission("Qrpg.heal.others.2")) {
                     healAmt += (double)((int)(Math.random() * 4.0D));
                     coolTime = 150L;
                  }
               }
            }

            if (pTarget.getHealth() + healAmt >= maxHealth) {
               healAmt = pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
               healMsg = ChatColor.GRAY + "You have been " + ChatColor.DARK_GREEN + "fully healed";
            } else {
               healMsg = ChatColor.GRAY + "You have been healed " + ChatColor.DARK_GREEN + healAmt / 2.0D + ChatColor.GRAY + " hearts";
               healAmt += pTarget.getHealth();
            }

            if (pUser != null && !pUser.hasPermission("QfCore.admin")) {
               if (this.qfcore.cooldownMgr.checkCooldown(pUser, cool, true) != null) {
                  return;
               }

               pTarget.setHealth(healAmt);
               this.qfcore.cooldownMgr.addCooldown(pUser, cool, coolTime);
            } else {
               pTarget.setHealth(healAmt);
            }
         }

         pTarget.playSound(pTarget.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
         if (pUser == null) {
            this.msgCaller(pTarget, ChatColor.GRAY + "You have been fully healed by " + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "XYZ" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "mystical energies" + ChatColor.DARK_PURPLE + ChatColor.MAGIC + "XYZ");
         } else if (pTarget.getName().toLowerCase() == "crossfire1218") {
            this.msgCaller(pTarget, ChatColor.GRAY + "You have been fully healed by " + ChatColor.LIGHT_PURPLE + "Cross " + ChatColor.GRAY + "himself!");
         } else {
            this.msgCaller(pTarget, healMsg + (healSelf ? "" : ChatColor.GRAY + " by " + pUser.getDisplayName()));
         }

      }
   }

   public void feedPlayer(Player pPlayer, String customMsg) {
      if (pPlayer != null) {
         if (!pPlayer.hasPermission("QfCore.admin")) {
            if (this.qfcore.cooldownMgr.checkCooldown(pPlayer, "feed", true) != null) {
               return;
            }

            this.qfcore.cooldownMgr.addCooldown(pPlayer, "feed", 120L);
            pPlayer.setFoodLevel(40);
         } else {
            pPlayer.setFoodLevel(40);
         }

         pPlayer.playSound(pPlayer.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
         if (customMsg != null) {
            this.msgCaller(pPlayer, customMsg);
            pPlayer.playSound(pPlayer.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
         } else {
            this.msgCaller(pPlayer, ChatColor.GRAY + "Your appetite has been sated.");
         }

      }
   }

   public void itchPlayer(Player pUser, Player pTarget) {
      if (this.qfcore.cooldownMgr.checkCooldown(pUser, "itch", false) != null) {
         pTarget.setHealth(pTarget.getHealth() - 1.0D);
      }

      pTarget.playSound(pTarget.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
      this.qfcore.cooldownMgr.addCooldown(pUser, "itch", 15L);
      this.msgCaller(pTarget, ChatColor.GRAY + "You " + ChatColor.DARK_RED + "itch" + ChatColor.GRAY + ", and so you scratch yourself.");
      this.qfcore.msgNearbyPlayers(pTarget, ChatColor.GRAY + "You hear an " + ChatColor.WHITE + "itching " + ChatColor.GRAY + "sound", (PotionEffect)null, Sound.ENTITY_GENERIC_EAT, 15.0D);
   }
}
