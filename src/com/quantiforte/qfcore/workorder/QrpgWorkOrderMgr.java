package com.quantiforte.qfcore.workorder;

import com.quantiforte.qfcore.QfCore;
import com.quantiforte.qfcore.QfMItem;
import com.quantiforte.qfcore.QfManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class QrpgWorkOrderMgr extends QfManager implements CommandExecutor {
   public void doInit(QfCore newCore) {
      this.configFileName = "config_workorders.yml";
      this.mitems = new ArrayList<QfMItem>();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "workorders";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
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
      // String specName = null;
      // String landType = null;
      switch(cmdName.hashCode()) {
      case 3800:
         if (!cmdName.equals("wo")) {
            return false;
         }
         break;
      case 1105232605:
         if (!cmdName.equals("workorder")) {
            return false;
         }
         break;
      default:
         return false;
      }

      if (args.length == 0 && isPlayer) {
         if (pTarget != null) {
            this.dispWorkOrderMenu(pTarget);
         } else {
            this.msgCaller(pUser, ChatColor.RED + "Could not locate the proper player to work with");
         }

         return true;
      } else if (args.length <= 2 && "reload".startsWith(args[0].toLowerCase())) {
         if (pUser.hasPermission("Qrpg.workorder.reload")) {
            this.msgCaller(pUser, ChatColor.GOLD + "Reloading work orders");
            this.reloadConfig();
            this.loadMgr(this.qfcore);
         } else {
            this.msgCaller(pUser, ChatColor.RED + "You do not have permission to use that command");
         }

         return true;
      } else if (args.length <= 2 && "list".startsWith(args[0].toLowerCase())) {
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
            dispStr = this.listItems2((String)null);
            cat = "null";
         }

         this.msgCaller(pUser, dispStr);
         return true;
      } else {
         return false;
      }
   }

   public void dispWorkOrderMenu(Player pUser) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      byte sz;
      if (this.mitems.size() <= 9) {
         sz = 18;
      } else {
         sz = 45;
      }

      Inventory goInv = Bukkit.createInventory((InventoryHolder)null, sz, ChatColor.DARK_GREEN + "Open Work Orders");
      int idx = 0;
      Iterator<QfMItem> var9 = this.mitems.iterator();

      while(var9.hasNext()) {
         QfMItem mitem = (QfMItem)var9.next();
         QrpgWorkOrder wo = (QrpgWorkOrder)mitem;
         goInv.setItem(idx, this.QuickItemNameLore(wo.icon, wo.name, wo.descr));
         String coolTimeLeft = this.qfcore.cooldownMgr.checkCooldown(pUser, wo.coolId, false);
         if (coolTimeLeft == null) {
            if (this.hasAllReqs(pUser, wo)) {
               goInv.setItem(idx + 9, this.QuickItemNameLores(new ItemStack(Material.LIME_WOOL), ChatColor.GREEN + "Work order ready", wo.reqDescr));
            } else {
               goInv.setItem(idx + 9, this.QuickItemNameLores(new ItemStack(Material.WHITE_WOOL), ChatColor.WHITE + "Work order available", wo.reqDescr));
            }
         } else {
            goInv.setItem(idx + 9, this.QuickItemNameLores(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Work order not available", wo.reqDescr));
         }

         ++idx;
         if (sz == 45 && idx == 9) {
            idx = 27;
         }
      }

      pUser.openInventory(goInv);
   }

   public boolean handlesInventoryItem(ItemStack item) {
      return true;
   }

   public void handleInvClick(Player pUser, ItemStack item) {
      if (!pUser.hasPermission("Qrpg.workorder.fulfill")) {
         this.msgCaller(pUser, ChatColor.RED + "You are not currently allowed to fulfill work orders.");
      } else {
         String itemName;
         if (item != null && item.getItemMeta() != null) {
            itemName = item.getItemMeta().getDisplayName();
         } else {
            itemName = "";
         }

         if (itemName == "") {
            this.qfcore.getLogger().info("handleInvClick: no item name");
         } else {
            itemName = ChatColor.stripColor(itemName);
            String altName = ChatColor.stripColor(this.firstLore(item));
            Iterator<QfMItem> var7 = this.mitems.iterator();

            QrpgWorkOrder wo;
            do {
               if (!var7.hasNext()) {
                  return;
               }

               QfMItem mitem = (QfMItem)var7.next();
               wo = (QrpgWorkOrder)mitem;
            } while(!itemName.equalsIgnoreCase(ChatColor.stripColor(wo.name)) && !altName.equalsIgnoreCase(ChatColor.stripColor(wo.name)));

            this.processWorkOrder(pUser, wo);
         }
      }
   }

   public void processWorkOrder(Player pUser, QrpgWorkOrder wo) {
      String coolTimeLeft = this.qfcore.cooldownMgr.checkCooldown(pUser, wo.coolId, false);
      if (coolTimeLeft != null) {
         this.msgCaller(pUser, ChatColor.RED + "This work order will not be available to you for another " + coolTimeLeft);
      } else {
         boolean hasReqMats = true;
         ItemStack[] remItems = new ItemStack[wo.reqMat.length];
         int idx = 0;
         String[] var14;
         int var13 = (var14 = wo.reqMat).length;

         for(int var12 = 0; var12 < var13; ++var12) {
            String strMat = var14[var12];
            String[] qStr = strMat.split(" ");
            int qty = Integer.parseInt(qStr[0]);
            ItemStack item = new ItemStack(Material.matchMaterial(qStr[1]));
            if (!pUser.getInventory().containsAtLeast(item, qty)) {
               hasReqMats = false;
               break;
            }

            item.setAmount(qty);
            remItems[idx++] = item;
         }

         if (hasReqMats) {
            HashMap<Integer, ItemStack> result = pUser.getInventory().removeItem(remItems);
            if (result.isEmpty()) {
               if (this.qfcore.payPlayer(pUser, wo.rewardCash, false)) {
                  this.qfcore.cooldownMgr.addCooldown(pUser, wo.coolId, wo.coolTime);
                  this.msgCaller(pUser, ChatColor.GOLD + "You have completed the work order and received " + ChatColor.GREEN + wo.rewardStr);
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "Error: Could not properly deposit your reward of " + ChatColor.DARK_RED + wo.rewardStr + ChatColor.RED + " for this work order");
               }
            } else {
               this.msgCaller(pUser, ChatColor.RED + "Error: could not properly collect all the items for this work order.");
            }
         } else {
            this.msgCaller(pUser, ChatColor.RED + "You do not have all of the required items in your inventory for the work order: " + wo.name);
         }

      }
   }

   public boolean hasAllReqs(Player pUser, QrpgWorkOrder wo) {
      boolean hasReqMats = true;
      String[] var10;
      int var9 = (var10 = wo.reqMat).length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String strMat = var10[var8];
         String[] qStr = strMat.split(" ");
         int qty = Integer.parseInt(qStr[0]);
         ItemStack item = new ItemStack(Material.matchMaterial(qStr[1]));
         if (!pUser.getInventory().containsAtLeast(item, qty)) {
            hasReqMats = false;
            break;
         }

         item.setAmount(qty);
      }

      return hasReqMats;
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Available work orders:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Available " + cat + ChatColor.GOLD + " work orders:";
   }

   public String listItems2(String cat) {
      if (this.mitems == null) {
         return ChatColor.GRAY + "No work orders.";
      } else {
         boolean found = false;
         String retVal = this.listItemHeader(cat);
         QfMItem mitem;
         Iterator<QfMItem> var6;
         if (cat == null) {
            for(var6 = this.mitems.iterator(); var6.hasNext(); found = true) {
               mitem = (QfMItem)var6.next();
               retVal = retVal + mitem.NameNice() + "\n";
            }

            if (found) {
               retVal = retVal.substring(0, retVal.length() - 2);
            }
         } else {
            var6 = this.mitems.iterator();

            while(var6.hasNext()) {
               mitem = (QfMItem)var6.next();
               if (!found) {
                  retVal = retVal + mitem.CatColor(cat);
                  found = true;
               }

               String itemName = mitem.NameCat(cat);
               if (itemName != null) {
                  retVal = retVal + " " + itemName;
               }
            }
         }

         if (!found) {
            retVal = retVal + "\n" + ChatColor.RED + "NONE.";
         }

         return retVal;
      }
   }

   public void readConfig() {
      this.mitems.clear();
      this.triggerLocs.clear();
      Set<String> keys = this.getConfig().getConfigurationSection("workorder").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " work orders in config_workorders.yml");
      String[] names = (String[])keys.toArray(new String[keys.size()]);
      String[] var11 = names;
      int var10 = names.length;

      for(int var9 = 0; var9 < var10; ++var9) {
         String name = var11[var9];
         QrpgWorkOrder workOrder = new QrpgWorkOrder();
         workOrder.mgr = this;
         workOrder.doInit();
         workOrder.name = name;
         String path = "workorder." + name + ".name";
         if (this.getConfig().contains(path)) {
            workOrder.name = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(path));
         }

         path = "workorder." + name + ".descr";
         if (this.getConfig().contains(path)) {
            workOrder.descr = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(path));
         }

         path = "workorder." + name + ".active";
         if (this.getConfig().contains(path)) {
            workOrder.active = this.getConfig().getString(path);
            workOrder.isActive = workOrder.active.equalsIgnoreCase("all");
         }

         path = "workorder." + name + ".icon";
         if (this.getConfig().contains(path)) {
            workOrder.iconStr = this.getConfig().getString(path);
            Material mat = Material.matchMaterial(workOrder.iconStr);
            if (mat == null) {
            	this.qfcore.getLogger().severe("Work order \"" + name + "\" has incompatible icon name: " + workOrder.iconStr);
            	mat = Material.BARRIER;
            }
            workOrder.icon = new ItemStack(mat);
         }

         path = "workorder." + name + ".reward";
         if (this.getConfig().contains(path)) {
            workOrder.rewardStr = this.getConfig().getString(path);
            workOrder.rewardCash = Double.parseDouble(workOrder.rewardStr.replace("$", "").replace(",", ""));
         }

         path = "workorder." + name + ".coolId";
         if (this.getConfig().contains(path)) {
            workOrder.coolId = this.getConfig().getString(path);
         }

         path = "workorder." + name + ".coolTime";
         if (this.getConfig().contains(path)) {
            workOrder.coolTimeStr = this.getConfig().getString(path);
            workOrder.coolTime = this.timeStr2secs(workOrder.coolTimeStr);
         }

         path = "workorder." + name + ".category";
         if (this.getConfig().contains(path)) {
            workOrder.category = this.getConfig().getString(path);
         }

         path = "workorder." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            workOrder.subcategory = this.getConfig().getString(path);
         }

         path = "workorder." + name + ".reqMat";
         List<String> strList;
         int si;
         String str;
         Iterator<String> var13;
         if (this.getConfig().contains(path)) {
            strList = this.getConfig().getStringList(path);
            workOrder.reqMat = new String[strList.size()];
            si = 0;

            for(var13 = strList.iterator(); var13.hasNext(); workOrder.reqMat[si++] = ChatColor.translateAlternateColorCodes('&', str)) {
               str = (String)var13.next();
            }
         }

         path = "workorder." + name + ".reqDescr";
         if (this.getConfig().contains(path)) {
            strList = this.getConfig().getStringList(path);
            workOrder.reqDescr = new String[strList.size()];
            si = 0;

            for(var13 = strList.iterator(); var13.hasNext(); workOrder.reqDescr[si++] = ChatColor.translateAlternateColorCodes('&', str)) {
               str = (String)var13.next();
            }
         }

         if (workOrder.isActive) {
            this.mitems.add(workOrder);
         }
      }

   }

   public void saveConfig() {
      try {
         this.getConfig().save(this.ourConfigFile);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }
}
