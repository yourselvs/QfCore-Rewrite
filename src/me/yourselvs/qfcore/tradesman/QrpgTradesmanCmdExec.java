package me.yourselvs.qfcore.tradesman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.yourselvs.qfcore.QfGeneral;
import me.yourselvs.qfcore.movement.QfMoveMgr;

public class QrpgTradesmanCmdExec extends QfGeneral implements CommandExecutor, Listener {
   public QfMoveMgr moveMgr;

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      this.qfcore.getLogger().info("cmd handler");
      // decompiler artifact
      // int nextarg = false;
      boolean isPlayer = sender instanceof Player;
      Player pUser;
      Player pPlayer;
      if (isPlayer) {
         pUser = (Player)sender;
         pPlayer = (Player)sender;
      } else {
         pUser = null;
         pPlayer = null;
      }

      String cmdName = cmd.getName().toLowerCase();
      if (!isPlayer) {
         this.msgCaller(pUser, "Cannot use this command from console.");
         return true;
      } else {
         switch(cmdName.hashCode()) {
         case -1663305267:
            if (cmdName.equals("supplies")) {
               this.doSupplies(pUser, args);
               return true;
            }
            break;
         case -1293307255:
            if (cmdName.equals("etable")) {
               this.moveMgr.DynamicPlace(pUser, "etable");
               return true;
            }
            break;
         case -795021822:
            if (cmdName.equals("warcry")) {
               this.doWarcry(pUser, args);
               return true;
            }
            break;
         case 92975308:
            if (cmdName.equals("anvil")) {
               if (args.length == 0) {
                  this.moveMgr.DynamicPlace(pUser, "anvil");
                  return true;
               }

               if (pUser != null && !pUser.hasPermission("Qrpg.anvil.rename")) {
                  this.msgCaller(pUser, ChatColor.GOLD + "You do not have enough skills as a tradesman to " + ChatColor.RED + "rename items " + ChatColor.GOLD + "using the anvil");
               } else {
                  this.anvilRename(pPlayer, args);
               }

               return true;
            }
            break;
         case 110545371:
            if (cmdName.equals("tools")) {
               this.doTools(pUser, args);
               return true;
            }
            break;
         case 468744647:
            if (cmdName.equals("tlibrary")) {
               this.moveMgr.DynamicPlace(pUser, "tlibrary");
               return true;
            }
         }

         return false;
      }
   }

   public void doTools(Player pUser, String[] args) {
      if (this.qfcore.cooldownMgr.checkCooldown(pUser, "supplies", true) == null) {
         int needed = 7;
         if (pUser.hasPermission("Qrpg.tools.2")) {
            needed += 2;
         }

         int count = 0;
         ItemStack[] inv = pUser.getInventory().getStorageContents();
         // decompiler artifacts
         // ItemStack[] var11;
         int var10 = inv.length;

         for(int var9 = 0; var9 < var10 && count < needed; ++var9) {
            if(inv[var9] == null)
               count++;
         }

         if (count < needed) {
            this.msgCaller(pUser, ChatColor.RED + "You need " + ChatColor.WHITE + needed + ChatColor.RED + "open inventory spots to receive your tools");
         } else {
            ItemStack item = new ItemStack(Material.IRON_AXE);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.IRON_PICKAXE);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.IRON_SHOVEL);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.IRON_SWORD);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.IRON_HOE);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.SHEARS);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.FLINT_AND_STEEL);
            pUser.getInventory().addItem(new ItemStack[]{item});
            if (pUser.hasPermission("Qrpg.tools.2")) {
               item = new ItemStack(Material.COAL_BLOCK, 1);
               pUser.getInventory().addItem(new ItemStack[]{item});
               item = new ItemStack(Material.IRON_PICKAXE);
               pUser.getInventory().addItem(new ItemStack[]{item});
            }

            this.qfcore.cooldownMgr.addCooldown(pUser, "supplies", 86400L);
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "You have received your daily tools");
         }
      }
   }

   public void doSupplies(Player pUser, String[] args) {
      if (this.qfcore.cooldownMgr.checkCooldown(pUser, "supplies", true) == null) {
         int needed = 4;
         if (pUser.hasPermission("Qrpg.supplies.2")) {
            ++needed;
         }

         int count = 0;
         Inventory inv = pUser.getInventory();
         // decompiler artifact
         // ItemStack[] var11;
         int var10 = inv.getContents().length;

         for(int var9 = 0; var9 < var10; ++var9) {
            // ItemStack var10000 = var11[var9];
            ++count;
            if (count >= needed) {
               break;
            }
         }

         if (count < needed) {
            this.msgCaller(pUser, ChatColor.RED + "You need " + ChatColor.WHITE + needed + ChatColor.RED + "open inventory spots to receive your supplies");
         } else {
            ItemStack item = new ItemStack(Material.COBBLESTONE, 64);
            pUser.getInventory().addItem(new ItemStack[]{item});
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.STONE, 64);
            pUser.getInventory().addItem(new ItemStack[]{item});
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.OAK_LOG, 64);
            pUser.getInventory().addItem(new ItemStack[]{item});
            item = new ItemStack(Material.BIRCH_LOG, 64);
            pUser.getInventory().addItem(new ItemStack[]{item});
            if (pUser.hasPermission("Qrpg.supplies.2")) {
               item = new ItemStack(Material.GLOWSTONE, 4);
               pUser.getInventory().addItem(new ItemStack[]{item});
            }

            this.qfcore.cooldownMgr.addCooldown(pUser, "supplies", 86400L);
            this.msgCaller(pUser, ChatColor.DARK_GREEN + "You have received your daily allotment of building supplies for you and your townsfolk");
         }
      }
   }

   public void doWarcry(Player pTarget, String[] args) {
      int level = 0;
      if (args.length == 1) {
         String arg0 = args[0];
         if (arg0.equalsIgnoreCase("1")) {
            level = 1;
         } else if (arg0.equalsIgnoreCase("2")) {
            level = 2;
         } else if (arg0.equalsIgnoreCase("3")) {
            level = 3;
         } else if (arg0.equalsIgnoreCase("4")) {
            level = 4;
         }
      }

      String cool = "warcry";
      long coolTime = 120L;
      String remain;
      if ((remain = this.qfcore.cooldownMgr.checkCooldown(pTarget, cool, false)) != null) {
         this.msgCaller(pTarget, ChatColor.DARK_RED + "Your voice is worn out and you cannot " + ChatColor.YELLOW + "warcry " + ChatColor.DARK_RED + "right now.\nTry again in about" + ChatColor.YELLOW + remain);
      } else {
         String dispStr = null;
         double radius = 0.0D;
         String hearStr = "";
         PotionEffect effTarget = null;
         PotionEffect effNear = null;
         Sound soundNear = null;

         if (pTarget.hasPermission("Qrpg.warcry.4") && (level == 0 || level == 4)) {
            dispStr = ChatColor.DARK_RED + "Your yell " + ChatColor.RED + ChatColor.BOLD + "Whon-Naar" + ChatColor.RESET + ChatColor.DARK_RED + " as you engage your enemy";
            radius = 15.0D;
            hearStr = ChatColor.DARK_RED + "You hear " + ChatColor.RED + ChatColor.BOLD + "Whon-Naar" + ChatColor.RESET + ChatColor.DARK_RED + ", the warcry of " + pTarget.getDisplayName();
            soundNear = Sound.ENTITY_ENDER_DRAGON_GROWL;
            effTarget = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 320, 2, true);
            effNear = new PotionEffect(PotionEffectType.CONFUSION, 180, 3, true);
         } else if (pTarget.hasPermission("Qrpg.warcry.3") && (level == 0 || level == 3)) {
            dispStr = ChatColor.DARK_RED + "Your yell " + ChatColor.RED + ChatColor.BOLD + "Tal-shun" + ChatColor.RESET + ChatColor.DARK_RED + " as you engage your enemy";
            radius = 15.0D;
            hearStr = ChatColor.DARK_RED + "You hear " + ChatColor.RED + ChatColor.BOLD + "Tal-shun" + ChatColor.RESET + ChatColor.DARK_RED + ", the warcry of " + pTarget.getDisplayName();
            soundNear = Sound.ENTITY_ENDERMAN_SCREAM;
            effTarget = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 240, 1, true);
            effNear = new PotionEffect(PotionEffectType.SLOW, 100, 2, true);
         } else if (pTarget.hasPermission("Qrpg.warcry.2") && (level == 0 || level == 2)) {
            dispStr = ChatColor.GOLD + "Your yell " + ChatColor.RED + ChatColor.BOLD + "Barr-kawl" + ChatColor.RESET + ChatColor.GOLD + " as you engage your enemy";
            radius = 15.0D;
            hearStr = ChatColor.GOLD + "You hear " + ChatColor.RED + ChatColor.BOLD + "Barr-kawl" + ChatColor.RESET + ChatColor.GOLD + ", the warcry of " + pTarget.getDisplayName();
            soundNear = Sound.ENTITY_PIGLIN_BRUTE_ANGRY;
            effTarget = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1, true);
            effNear = new PotionEffect(PotionEffectType.SLOW, 40, 2, true);
         } else if (pTarget.hasPermission("Qrpg.warcry.1") && (level == 0 || level == 1)) {
            dispStr = ChatColor.GOLD + "Your yell " + ChatColor.RED + ChatColor.BOLD + "Ahr-zahn" + ChatColor.RESET + ChatColor.GOLD + " as you engage your enemy";
            radius = 15.0D;
            hearStr = ChatColor.GOLD + "You hear " + ChatColor.RED + ChatColor.BOLD + "Ahr-zahn" + ChatColor.RESET + ChatColor.GOLD + ", the warcry of " + pTarget.getDisplayName();
            soundNear = Sound.ENTITY_GHAST_SCREAM;
            effTarget = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 1, true);
            effNear = new PotionEffect(PotionEffectType.HUNGER, 200, 1, true);
         }

         if (dispStr == null) {
            this.msgCaller(pTarget, ChatColor.DARK_RED + "Your voice is worn out and you cannot " + ChatColor.YELLOW + "warcry " + ChatColor.DARK_RED + "right now");
         } else {
            this.msgCaller(pTarget, dispStr);
            if (soundNear != null) {
               pTarget.playSound(pTarget.getLocation(), soundNear, 10.0F, 1.0F);
            }

            if (effTarget != null) {
               pTarget.addPotionEffect(effTarget);
            }

            this.qfcore.msgNearbyPlayers(pTarget, hearStr, effNear, soundNear, radius);
            this.qfcore.cooldownMgr.addCooldown(pTarget, cool, coolTime);
         }
      }
   }

   public void anvilRename(Player pPlayer, String[] args) {
      String name = "";

      for(int i = 0; i < args.length; ++i) {
         name = name + args[i] + " ";
      }

      name = name.trim();
      ItemStack item = pPlayer.getItemInUse();
      if (item == null) {
         this.msgCaller(pPlayer, ChatColor.DARK_RED + "You must be holding the item you want to rename to use this ability");
      } else {
         if (!pPlayer.hasPermission("QfCore.admin")) {
            name = ChatColor.stripColor(name);
         } else {
            name = ChatColor.translateAlternateColorCodes('&', name);
         }

         ItemMeta meta = item.getItemMeta();
         if (this.isRenamableItem(item, pPlayer) ) {
            this.msgCaller(pPlayer, ChatColor.RED + "Your tradesman skills can only rename weapons, armor, and tools.");
         } else {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
            if (pPlayer.hasPermission("QfCore.admin")) {
               this.msgCaller(pPlayer, ChatColor.GOLD + "You have used your " + ChatColor.YELLOW + "admin superpowers " + ChatColor.GOLD + "to rename the item.");
            } else {
               this.msgCaller(pPlayer, ChatColor.GOLD + "You have used your tradesman skills to rename the item.");
            }
         }

      }
   }

   private boolean isRenamableItem(ItemStack item, Player player) {
      return this.isValidTool(item) 
         || this.isValidWeapon(item) 
         || this.isValidArmor(item) 
         || player.hasPermission("QfCore.admin");
   }

   private boolean isValidArmor(ItemStack item) {
      return item.getType().toString().contains("_HELMET")
         || item.getType().toString().contains("_CHESTPLATE")
         || item.getType().toString().contains("_LEGGINGS")
         || item.getType().toString().contains("_BOOTS");
   }

   private boolean isValidWeapon(ItemStack item) {
      return item.getType().toString().contains("_SWORD")
         || item.getType() == Material.BOW;
   }

   private boolean isValidTool(ItemStack item) {
      return item.getType().toString().contains("_PICKAXE")
         || item.getType().toString().contains("_SHOVEL")
         || item.getType().toString().contains("_AXE")
         || item.getType().toString().contains("_HOE")
         || item.getType() == Material.FISHING_ROD;
   }
}
