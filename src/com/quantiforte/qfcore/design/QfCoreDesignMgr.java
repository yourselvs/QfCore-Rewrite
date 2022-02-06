package com.quantiforte.qfcore.design;

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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QfCoreDesignMgr extends QfManager implements CommandExecutor {
   int nextItemId;
   public String lkdm;
   public QfCoreDItem curItem;
   public String currentEmEnchant;
   public Inventory currentEmInv;
   public int currentEmMaxLevel;

   public void doInit(QfCore newCore) {
      this.configFileName = "config_ditems.yml";
      this.mitems = new ArrayList<QfMItem>();
      this.hasLocationTriggers = false;
      this.hasDynLocationTriggers = false;
      this.lkdm = ChatColor.LIGHT_PURPLE + "L" + ChatColor.GRAY + "&" + ChatColor.LIGHT_PURPLE + "K" + ChatColor.AQUA + "Item Design Manager";
      super.doInit(newCore);
   }

   public String cmdPrefix() {
      return "design";
   }

   public String listItemHeader(String cat) {
      return cat == null ? ChatColor.GOLD + "Current items:\n" + ChatColor.YELLOW : ChatColor.GOLD + "Current " + ChatColor.YELLOW + cat + ChatColor.GOLD + " items:";
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player pTarget = null;
      Player pUser = null;
      // unused, decompiler artifact?
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
      switch(cmdName.hashCode()) {
      case -1335246402:
         if (cmdName.equals("design")) {
            this.msgCaller(pUser, ChatColor.RED + "This command no longer available. Use " + ChatColor.YELLOW + "/anvil &5Purple Name " + ChatColor.RED + "for item renaming with color");
            return true;
         }
         break;
      case 3205:
         if (cmdName.equals("di")) {
            String name;
            if (args.length >= 1 && "create".startsWith(args[0])) {
               if (args.length == 2) {
                  name = args[1];
                  this.createItem(pUser, name);
               } else {
                  this.createItem(pUser, (String)null);
               }

               return true;
            }

            if (args.length >= 1 && "list".startsWith(args[0])) {
               if (args.length == 2) {
                  name = args[1];
                  this.listItems(pUser, name);
               } else {
                  this.listItems(pUser, (String)null);
               }

               return true;
            }

            if (args.length == 1 && "info".startsWith(args[0])) {
               this.infoItem(pUser);
               return true;
            }

            if (args.length == 2 && "load".startsWith(args[0].toLowerCase())) {
               this.loadItem(pUser, args[1]);
               return true;
            }

            if (args.length == 2 && "forge".startsWith(args[0].toLowerCase())) {
               this.loadItem(pUser, args[1]);
               this.forgeItem(pUser, this.activeItem(pUser));
               return true;
            }

            if (args.length == 4 && "forge".startsWith(args[0].toLowerCase()) && "give".startsWith(args[2].toLowerCase())) {
               this.forgeGiveItem(pUser, args[1], args[3], false);
               return true;
            }

            if (args.length == 4 && "forge".startsWith(args[0].toLowerCase()) && "stampgive".startsWith(args[2].toLowerCase())) {
               this.forgeGiveItem(pUser, args[1].toLowerCase(), args[3].toLowerCase(), true);
               return true;
            }

            if (args.length >= 2 && "name".startsWith(args[0].toLowerCase())) {
               this.renameItem(pUser, this.activeItem(pUser), args);
               return true;
            }

            if (args.length == 2 && "lore".startsWith(args[0].toLowerCase()) && "clear".startsWith(args[1])) {
               this.removeAllLoreItem(pUser, this.activeItem(pUser));
               return true;
            }

            if (args.length >= 2 && "lore".startsWith(args[0].toLowerCase())) {
               this.loreAddItem(pUser, this.activeItem(pUser), args);
               return true;
            }

            if (args.length == 1 && "enchant".startsWith(args[0].toLowerCase())) {
               this.enchantMenuItem(pUser, this.activeItem(pUser), (String)null);
               return true;
            }

            if (args.length == 2 && "enchant".startsWith(args[0].toLowerCase())) {
               if ("clear".startsWith(args[1].toLowerCase())) {
                  this.removeAllEnchants(pUser, this.activeItem(pUser));
                  return true;
               }

               this.enchantMenuItem(pUser, this.activeItem(pUser), args[1].toLowerCase());
            }

            if (args.length == 1 && ("common".startsWith(args[0]) || "uncommon".startsWith(args[0]) || "rare".startsWith(args[0]) || "royal".startsWith(args[0]) || "heroic".startsWith(args[0]) || "legendary".startsWith(args[0]))) {
               this.classItem(pUser, this.activeItem(pUser), args[0]);
               return true;
            }

            if (args.length == 1 && ("weapon".startsWith(args[0]) || "armor".startsWith(args[0]) || "bow".startsWith(args[0]) || "rod".startsWith(args[0]) || "tool".startsWith(args[0]) || "special".startsWith(args[0]))) {
               this.typeItem(pUser, this.activeItem(pUser), args[0]);
               return true;
            }

            if (args.length == 2 && "give".startsWith(args[0].toLowerCase())) {
               pTarget = this.qfcore.getServer().getPlayer(args[1]);
               if (pTarget == null) {
                  this.msgCaller(pUser, "Could not locate the player " + args[1]);
                  return true;
               }

               if (pTarget.getInventory().firstEmpty() > -1) {
                  ItemStack istack = pUser.getItemInUse();
                  if (istack == null) {
                     this.msgCaller(pUser, ChatColor.RED + "You must be holding the item you want to give");
                     return true;
                  }

                  pTarget.getInventory().addItem(new ItemStack[]{istack.clone()});
                  this.msgCaller(pUser, ChatColor.GOLD + "You have given " + pTarget.getDisplayName() + ChatColor.GOLD + " the item");
               } else {
                  this.msgCaller(pUser, ChatColor.RED + "The player does not have room in their inventory to receive the item.");
               }
            }

            if (args.length == 1 && ("common".startsWith(args[0]) || "uncommon".startsWith(args[0]) || "rare".startsWith(args[0]) || "royal".startsWith(args[0]) || "heroic".startsWith(args[0]) || "legendary".startsWith(args[0]))) {
               this.classItem(pUser, this.activeItem(pUser), args[0]);
               return true;
            }
         }
      }

      return false;
   }

   public void readConfig() {
      this.mitems.clear();
      this.triggerLocs.clear();
      Set<String> keys = this.getConfig().getConfigurationSection("item").getKeys(false);
      this.core.getLogger().info("found " + keys.size() + " item in config_ditems.yml");
      String[] names = keys.toArray(new String[keys.size()]);
      String[] var10 = names;
      int var9 = names.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String name = var10[var8];
         this.core.getLogger().info("adding guild: " + name);
         QfCoreDItem item = new QfCoreDItem();
         item.mgr = this;
         item.doInit();
         item.name = name;
         String path = "item." + name + ".name";
         if (this.getConfig().contains(path)) {
            item.itemName = this.getConfig().getString(path);
         }

         path = "item." + name + ".readyname";
         if (this.getConfig().contains(path)) {
            item.readyName = this.getConfig().getString(path);
         }

         path = "item." + name + ".itemtype";
         if (this.getConfig().contains(path)) {
            item.itemType = Integer.parseInt(this.getConfig().getString(path));
         }

         path = "item." + name + ".itemclass";
         if (this.getConfig().contains(path)) {
            item.itemClass = Integer.parseInt(this.getConfig().getString(path));
         }

         path = "item." + name + ".itemmaterial";
         if (this.getConfig().contains(path)) {
            item.itemMaterial = Material.getMaterial(this.getConfig().getString(path));
         }

         path = "item." + name + ".namecolor";
         if (this.getConfig().contains(path)) {
            item.nameColor = this.getConfig().getString(path);
         }

         path = "item." + name + ".namesqg";
         if (this.getConfig().contains(path)) {
            item.nameSqg = this.getConfig().getString(path).equals("y");
         }

         path = "item." + name + ".namesqgbig";
         if (this.getConfig().contains(path)) {
            item.nameSqgBig = this.getConfig().getString(path).equals("y");
         }

         path = "item." + name + ".namesqgcolor";
         if (this.getConfig().contains(path)) {
            item.nameSqgColor = this.getConfig().getString(path);
         }

         path = "item." + name + ".category";
         if (this.getConfig().contains(path)) {
            item.category = this.getConfig().getString(path);
         }

         path = "item." + name + ".subcategory";
         if (this.getConfig().contains(path)) {
            item.subcategory = this.getConfig().getString(path);
         }

         path = "item." + name + ".enchants";
         List<String> inList;
         String lit;
         Iterator<String> var12;
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               lit = var12.next();
               item.enchantList.add(lit);
            }
         }

         path = "item." + name + ".qenchants";
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               lit = var12.next();
               item.qenchantList.add(lit);
            }
         }

         path = "item." + name + ".lore";
         if (this.getConfig().contains(path)) {
            inList = this.getConfig().getStringList(path);
            var12 = inList.iterator();

            while(var12.hasNext()) {
               lit = var12.next();
               item.loreList.add(lit);
            }
         }

         this.mitems.add(item);
         this.nextItemId = Integer.parseInt(item.name) + 1;
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

   public String niceName(String origName) {
      return origName.replace("'", "").replace(" ", "");
   }

   public String nextItemFileName() {
      int id = this.nextItemId++;
      String nextName = "" + id;
      return nextName;
   }

   public void listItems(Player pUser, String cat) {
      if (this.mitems == null) {
         this.msgCaller(pUser, ChatColor.GRAY + "No Items.");
      }

      boolean found = false;
      String retVal = this.listItemHeader(cat);
      QfMItem mitem;
      Iterator<QfMItem> var8;
      if (cat == null) {
         for(var8 = this.mitems.iterator(); var8.hasNext(); found = true) {
            mitem = var8.next();
            QfCoreDItem di = (QfCoreDItem)mitem;
            retVal = retVal + di.NameNiceWithId() + "\n";
         }
      } else {
         var8 = this.mitems.iterator();

         while(var8.hasNext()) {
            mitem = var8.next();
            if (!found) {
               retVal = retVal + mitem.CatColor(cat);
               found = true;
            }

            String itemName = mitem.NameCat(cat);
            if (itemName != null) {
               retVal = retVal + "\n" + itemName;
            }
         }
      }

      if (!found) {
         retVal = retVal + "\n" + ChatColor.RED + "NONE.";
      }

      this.msgCaller(pUser, retVal);
   }

   public void createItem(Player pUser, String itemName) {
      QfCoreDItem di = new QfCoreDItem();
      di.mgr = this;
      di.doInit();
      di.name = this.nextItemFileName();
      if (itemName != null) {
         di.itemName = itemName;
      }

      di.iStack = pUser.getItemInUse();
      di.itemMaterial = pUser.getItemInUse().getType();
      di.autoType();
      String path = "item." + di.name;
      if (this.getConfig().contains(path)) {
         this.msgCaller(pUser, ChatColor.RED + "Critical Error, an item with that filename already exists");
      } else {
         this.getConfig().createSection(path);
         path = "item." + di.name + ".name";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.itemName);
         path = "item." + di.name + ".readyname";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.readyName);
         path = "item." + di.name + ".itemtype";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.itemType);
         path = "item." + di.name + ".itemclass";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.itemClass);
         path = "item." + di.name + ".itemmaterial";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.itemMaterial.name());
         path = "item." + di.name + ".namecolor";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.nameColor);
         path = "item." + di.name + ".namesqg";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.nameSqg ? "y" : "n");
         path = "item." + di.name + ".namesqgbig";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.nameSqgBig ? "y" : "n");
         path = "item." + di.name + ".namesqgcolor";
         this.getConfig().createSection(path);
         this.getConfig().set(path, di.nameSqgColor);
         path = "item." + di.name + ".enchants";
         this.getConfig().createSection(path);
         path = "item." + di.name + ".qenchants";
         this.getConfig().createSection(path);
         path = "item." + di.name + ".lore";
         this.getConfig().createSection(path);
         this.saveConfig();
         this.mitems.add(di);
         this.curItem = di;
         this.msgCaller(pUser, ChatColor.GREEN + "New item " + ChatColor.GOLD + di.itemName + ChatColor.GREEN + " is ready for work.");
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

   public void infoItem(Player pUser) {
      this.msgCaller(pUser, this.activeItem(pUser).msgName());
   }

   public QfCoreDItem activeItem(Player pUser) {
      return this.curItem;
   }

   public void setActiveItem(Player pUser, QfCoreDItem di) {
      this.curItem = di;
      di.iStack = pUser.getItemInUse();
   }

   public void forgeItem(Player pUser, QfCoreDItem di) {
      ItemStack it = new ItemStack(di.itemMaterial);
      pUser.getInventory().setItemInMainHand(it);
      di.iStack = pUser.getInventory().getItemInMainHand();
      di.addEnchants();
      di.updateLore();
      di.makeReadyClass();
      di.makeReadyName();
   }

   public void forgeGiveItem(Player pUser, String itemIdStr, String playerName, boolean ownerStamp) {
      Player pTarget = this.core.getServer().getPlayer(playerName);
      if (pTarget == null) {
         if (pUser != null) {
            this.msgCaller(pUser, ChatColor.RED + "Could not locate player " + ChatColor.YELLOW + playerName);
         }

      }
   }

   public QfCoreDItem loadItem(Player pUser, String itemId) {
      Iterator<QfMItem> var5 = this.mitems.iterator();

      QfCoreDItem di;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         QfMItem mitem = var5.next();
         di = (QfCoreDItem)mitem;
      } while(!di.name.equalsIgnoreCase(itemId) && !di.itemName.equalsIgnoreCase(itemId));

      this.setActiveItem(pUser, di);
      this.forgeItem(pUser, di);
      return di;
   }

   public void classItem(Player pUser, QfCoreDItem di, String classId) {
      if ("uncommon".startsWith(classId.toLowerCase())) {
         di.itemClass = 1;
      } else if ("rare".startsWith(classId.toLowerCase())) {
         di.itemClass = 2;
      } else if ("royal".startsWith(classId.toLowerCase())) {
         di.itemClass = 3;
      } else if ("heroic".startsWith(classId.toLowerCase())) {
         di.itemClass = 4;
      } else if ("legendary".startsWith(classId.toLowerCase())) {
         di.itemClass = 5;
      } else {
         di.itemClass = 0;
      }

      di.makeReadyClass();
      di.iStack = pUser.getItemInUse();
      di.updateLore();
      // decompiler artifact?
      // String name = di.makeReadyName();
      String path = "item." + di.name + ".itemclass";
      this.getConfig().set(path, di.itemClass);
      path = "item." + di.name + ".namecolor";
      this.getConfig().set(path, di.nameColor);
      path = "item." + di.name + ".readyname";
      this.getConfig().set(path, di.readyName);
      this.saveConfig();
      this.msgCaller(pUser, di.msgName());
   }

   public void typeItem(Player pUser, QfCoreDItem di, String typeId) {
      if ("weapon".startsWith(typeId.toLowerCase())) {
         di.itemType = 1;
      } else if ("armor".startsWith(typeId.toLowerCase())) {
         di.itemType = 2;
      } else if ("tool".startsWith(typeId.toLowerCase())) {
         di.itemType = 3;
      } else if ("bow".startsWith(typeId.toLowerCase())) {
         di.itemType = 4;
      } else if ("rod".startsWith(typeId.toLowerCase())) {
         di.itemType = 5;
      } else if ("special".startsWith(typeId.toLowerCase())) {
         di.itemType = 6;
      } else {
         di.itemType = 0;
      }

      di.makeReadyClass();
      di.iStack = pUser.getItemInUse();
      di.updateLore();
      di.makeReadyName();
      String path = "item." + di.name + ".itemtype";
      this.getConfig().set(path, di.itemType);
      path = "item." + di.name + ".namecolor";
      this.getConfig().set(path, di.nameColor);
      path = "item." + di.name + ".readyname";
      this.getConfig().set(path, di.readyName);
      this.saveConfig();
      this.msgCaller(pUser, di.msgName());
   }

   public void renameItem(Player pUser, QfCoreDItem di, String[] args) {
      String name = "";

      for(int i = 1; i < args.length; ++i) {
         name = name + args[i] + " ";
      }

      name = name.trim();
      di.itemName = ChatColor.translateAlternateColorCodes('&', name);
      di.iStack = pUser.getItemInUse();
      di.updateLore();
      name = di.makeReadyName();
      String path = "item." + di.name + ".name";
      this.getConfig().set(path, di.itemName);
      this.saveConfig();
      this.msgCaller(pUser, di.msgName());
   }

   public void removeAllLoreItem(Player pUser, QfCoreDItem di) {
      Iterator<QfMItem> var8 = this.mitems.iterator();

      while(var8.hasNext()) {
         QfMItem item = var8.next();
         QfCoreDItem ditem = (QfCoreDItem)item;
         if (ditem.equals(di)) {
            Iterator<String> var10 = ditem.loreList.iterator();
            if (var10.hasNext()) {
               // decompiler artifact?
               // String mem = var10.next();
               String path = "item." + ditem.name + ".lore";
               List<String> configList = this.getConfig().getStringList(path);
               configList.clear();
               this.getConfig().set(path, configList);
               this.saveConfig();
               ditem.loreList.clear();
               this.msgCaller(pUser, ChatColor.DARK_GREEN + "All lore has been cleared from the item. Ready for more work.");
               return;
            }
         }
      }

   }

   public void loreAddItem(Player pUser, QfCoreDItem di, String[] args) {
      String newLore = "";

      for(int i = 1; i < args.length; ++i) {
         newLore = newLore + args[i] + " ";
      }

      newLore = newLore.trim();
      newLore = ChatColor.translateAlternateColorCodes('&', newLore);
      di.iStack = pUser.getItemInUse();
      di.addLore(newLore);
      di.makeReadyName();
      String path = "item." + di.name + ".lore";
      this.getConfig().set(path, di.loreList);
      this.saveConfig();
      this.msgCaller(pUser, ChatColor.DARK_GREEN + "Lore added to the item. Ready for more work.");
   }

   public void showDefaultMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Default Enchant Menu");
      this.currentEmMaxLevel = 0;
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.GOLDEN_SWORD, "Weapons", ChatColor.AQUA + "available enchants"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.IRON_SWORD, "Sharpness", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.IRON_AXE, "Smite", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.BLAZE_POWDER, "Fire Aspect", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.RAIL, "Knockback", ChatColor.GREEN + "I - VII"));
      encInv.setItem(var4++, this.QuickItem(Material.SPIDER_EYE, "Bane of Arthropods", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.GOLD_NUGGET, "Looting", ChatColor.GREEN + "I - IV"));
      encInv.setItem(var4++, this.QuickItem(Material.ANVIL, "Mending", ChatColor.GREEN + "I - II"));
      idx = 18;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.POISONOUS_POTATO, "Hunger", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.STRING, "Tornado", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.GLOWSTONE_DUST, "Lightning", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.FERMENTED_SPIDER_EYE, "Poor Seeing", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.SLIME_BLOCK, "Sloth", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.REDSTONE, "Deep Wound", ChatColor.GOLD + "IV"));
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showWeaponMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Weapon Enchant Menu");
      this.currentEmMaxLevel = 0;
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.GOLDEN_SWORD, "Weapons", ChatColor.AQUA + "available enchants"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.IRON_SWORD, "Sharpness", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.IRON_AXE, "Smite", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.BLAZE_POWDER, "Fire Aspect", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.RAIL, "Knockback", ChatColor.GREEN + "I - VII"));
      encInv.setItem(var4++, this.QuickItem(Material.SPIDER_EYE, "Bane of Arthropods", ChatColor.GREEN + "I - VIII"));
      encInv.setItem(var4++, this.QuickItem(Material.GOLD_NUGGET, "Looting", ChatColor.GREEN + "I - IV"));
      encInv.setItem(var4++, this.QuickItem(Material.ANVIL, "Mending", ChatColor.GREEN + "I - II"));
      idx = 18;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.POISONOUS_POTATO, "Hunger", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.STRING, "Tornado", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.GLOWSTONE_DUST, "Lightning", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.FERMENTED_SPIDER_EYE, "Poor Seeing", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.SLIME_BLOCK, "Sloth", ChatColor.GOLD + "IV"));
      encInv.setItem(var4++, this.QuickItem(Material.REDSTONE, "Deep Wound", ChatColor.GOLD + "IV"));
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showSwordMenuItem(Player pUser) {
   }

   public void showArmorHelmetMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Helmet Enchant Menu");
      this.currentEmMaxLevel = 0;
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.LEATHER_HELMET, "Helmets", ChatColor.AQUA + "for helmets"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.ANVIL, "Mending", ChatColor.GREEN + "I, II"));
      encInv.setItem(var4++, this.QuickItem(Material.SHIELD, "Protection", ChatColor.GOLD + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.BLAZE_POWDER, "Fire Protection", ChatColor.WHITE + "I-XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.TNT, "Blast Protection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.ARROW, "Projectile Protection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.CACTUS, "Thorns", ChatColor.WHITE + "I, II, III"));
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showArmorChestplateMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Chestplate Enchant Menu");
      this.currentEmMaxLevel = 0;
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.IRON_CHESTPLATE, "Chestplates", ChatColor.AQUA + "available enchants"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.ANVIL, "Mending", ChatColor.GREEN + "I, II"));
      encInv.setItem(var4++, this.QuickItem(Material.CACTUS, "Thorns", ChatColor.WHITE + "I, II, III"));
      ++var4;
      encInv.setItem(var4++, this.QuickItem(Material.COOKED_BEEF, "Snack Pouch", ChatColor.GOLD + "IV - Counters Hunger"));
      encInv.setItem(var4++, this.QuickItem(Material.ARMOR_STAND, "Anchor", ChatColor.GOLD + "IV - Counters Wind Effects"));
      idx = 18;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.SHIELD, "Protection", ChatColor.GOLD + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.BLAZE_POWDER, "FireProtection", ChatColor.RED + "I-XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.TNT, "BlastProtection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.ARROW, "ProjectileProtection", ChatColor.GREEN + "I - XVI"));
      ++var4;
      encInv.setItem(var4++, this.QuickItem(Material.STICK, "Magic Protection", ChatColor.GOLD + "X - Counters General Magic"));
      // is this a decompiler artifact? need to investigate
      // int idx = true;
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showArmorLeggingsMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Leggings Enchant Menu");
      this.currentEmMaxLevel = 0;
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.LEATHER_LEGGINGS, "Leggings", ChatColor.AQUA + "for leggings"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.ANVIL, "Mending", ChatColor.GREEN + "I, II"));
      encInv.setItem(var4++, this.QuickItem(Material.SHIELD, "Protection", ChatColor.GOLD + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.BLAZE_POWDER, "Fire Protection", ChatColor.WHITE + "I-XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.TNT, "Blast Protection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.ARROW, "Projectile Protection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.CACTUS, "Thorns", ChatColor.WHITE + "I, II, III"));
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showArmorBootsMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Boots Enchant Menu");
      this.currentEmMaxLevel = 0;
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.LEATHER_BOOTS, "Boots", ChatColor.AQUA + "for boots"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.ANVIL, "Mending", ChatColor.GREEN + "I, II"));
      encInv.setItem(var4++, this.QuickItem(Material.SHIELD, "Protection", ChatColor.GOLD + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.BLAZE_POWDER, "Fire Protection", ChatColor.WHITE + "I-XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.TNT, "Blast Protection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.ARROW, "Projectile Protection", ChatColor.WHITE + "I - XVI"));
      encInv.setItem(var4++, this.QuickItem(Material.CACTUS, "Thorns", ChatColor.WHITE + "I, II, III"));
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showToolMenuItem(Player pUser) {
      Inventory encInv;
      this.currentEmInv = encInv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.DARK_GREEN + "Tool Enchant Menu");
      int idx = 0;
      int var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.WOODEN_PICKAXE, "Tools", ChatColor.AQUA + "for tools"));
      idx = 2;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.GRAY + "Common", "Common"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.GRAY_WOOL), ChatColor.YELLOW + "Uncommon", "Uncommon"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.PURPLE_WOOL), ChatColor.DARK_PURPLE + "Rare", "Rare"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.YELLOW_WOOL), ChatColor.GOLD + "Royal", "Royal"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.CYAN_WOOL), ChatColor.DARK_AQUA + "Heroic", "Heroic"));
      encInv.setItem(var4++, this.QuickItemNameLore(new ItemStack(Material.RED_WOOL), ChatColor.RED + "Legendary", "Legendary"));
      idx = 9;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.OBSIDIAN, "Unbreaking", ChatColor.AQUA + "I - X"));
      encInv.setItem(var4++, this.QuickItem(Material.ELYTRA, "Efficiency", ChatColor.GREEN + "I - VI"));
      ++var4;
      encInv.setItem(var4++, this.QuickItem(Material.GLASS, "Silk Touch", ChatColor.GOLD + "I, II"));
      encInv.setItem(var4++, this.QuickItem(Material.GOLD_NUGGET, "Fortune", ChatColor.WHITE + "I, II, III"));
      idx = 18;
      var4 = idx + 1;
      encInv.setItem(idx, this.QuickItem(Material.LAVA, "Magma Touch", ChatColor.WHITE + "III Applies Heat / Auto-Smelting"));
      this.resetEmLevelIndicator(encInv, this.currentEmMaxLevel);
      pUser.openInventory(encInv);
   }

   public void showBowMenuItem(Player pUser) {
   }

   public void showRodMenuItem(Player pUser) {
   }

   public void showSpecialMenuItem(Player pUser) {
   }

   public void enchantMenuItem(Player pUser, QfCoreDItem di, String option) {
      if (pUser == null) {
         this.msgCaller(pUser, "Cannot use this command from console.");
      }

      if (option == null) {
         switch(di.itemType) {
         case 0:
            this.showDefaultMenuItem(pUser);
            break;
         case 1:
            this.showWeaponMenuItem(pUser);
            break;
         case 2:
            if (!di.itemMaterial.name().equalsIgnoreCase("DIAMOND_HELMET") && !di.itemMaterial.name().equalsIgnoreCase("GOLD_HELMET") && !di.itemMaterial.name().equalsIgnoreCase("IRON_HELMET") && !di.itemMaterial.name().equalsIgnoreCase("LEATHER_HELMET") && !di.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_HELMET")) {
               if (di.itemMaterial.name().equalsIgnoreCase("DIAMOND_CHESTPLATE") || di.itemMaterial.name().equalsIgnoreCase("GOLD_CHESTPLATE") || di.itemMaterial.name().equalsIgnoreCase("IRON_CHESTPLATE") || di.itemMaterial.name().equalsIgnoreCase("LEATHER_CHESTPLATE") || di.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_CHESTPLATE")) {
                  this.showArmorChestplateMenuItem(pUser);
                  return;
               }

               if (!di.itemMaterial.name().equalsIgnoreCase("DIAMOND_LEGGINGS ") && !di.itemMaterial.name().equalsIgnoreCase("GOLD_LEGGINGS ") && !di.itemMaterial.name().equalsIgnoreCase("IRON_LEGGINGS ") && !di.itemMaterial.name().equalsIgnoreCase("LEATHER_LEGGINGS ") && !di.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_LEGGINGS ")) {
                  if (!di.itemMaterial.name().equalsIgnoreCase("DIAMOND_BOOTS") && !di.itemMaterial.name().equalsIgnoreCase("GOLD_BOOTS") && !di.itemMaterial.name().equalsIgnoreCase("IRON_BOOTS") && !di.itemMaterial.name().equalsIgnoreCase("LEATHER_BOOTS") && !di.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_BOOTS")) {
                     break;
                  }

                  this.showArmorBootsMenuItem(pUser);
                  return;
               }

               this.showArmorLeggingsMenuItem(pUser);
               return;
            }

            this.showArmorHelmetMenuItem(pUser);
            return;
         case 3:
            this.showToolMenuItem(pUser);
            break;
         case 4:
            this.showBowMenuItem(pUser);
            break;
         case 5:
            this.showRodMenuItem(pUser);
            break;
         case 6:
            this.showDefaultMenuItem(pUser);
         }
      }

   }

   public void emDispatch(Player pUser, String itemName) {
      this.core.getLogger().info("emDispatch <<" + itemName + ">>");
      itemName = ChatColor.stripColor(itemName);
      if (!itemName.equalsIgnoreCase("Common") && !itemName.equalsIgnoreCase("Uncommon") && !itemName.equalsIgnoreCase("Rare") && !itemName.equalsIgnoreCase("Royal") && !itemName.equalsIgnoreCase("Heroic") && !itemName.equalsIgnoreCase("Legendary")) {
         if (!itemName.equalsIgnoreCase("I") && !itemName.equalsIgnoreCase("II") && !itemName.equalsIgnoreCase("III") && !itemName.equalsIgnoreCase("IV") && !itemName.equalsIgnoreCase("V") && !itemName.equalsIgnoreCase("VI") && !itemName.equalsIgnoreCase("VII") && !itemName.equalsIgnoreCase("VIII") && !itemName.equalsIgnoreCase("IX") && !itemName.equalsIgnoreCase("X") && !itemName.equalsIgnoreCase("XI") && !itemName.equalsIgnoreCase("XII") && !itemName.equalsIgnoreCase("XIII") && !itemName.equalsIgnoreCase("XIV") && !itemName.equalsIgnoreCase("XV") && !itemName.equalsIgnoreCase("XVI") && !itemName.equalsIgnoreCase("XVII") && !itemName.equalsIgnoreCase("XVIII") && !itemName.equalsIgnoreCase("XIX") && !itemName.equalsIgnoreCase("XX") && !itemName.equalsIgnoreCase("Unenchant")) {
            this.enchantSelectItem(pUser, this.activeItem(pUser), itemName);
         } else {
            int enLevel;
            if (itemName.equalsIgnoreCase("Unenchant")) {
               enLevel = 0;
            } else {
               enLevel = QfCoreEnchant.romanToInt(itemName);
            }

            this.emSetEnchantLevel(pUser, this.activeItem(pUser), enLevel);
         }
      } else {
         this.classItem(pUser, this.activeItem(pUser), itemName);
      }

   }

   public void setEmLevelIndicator(Inventory encInv, int curLevel) {
      int idx = 35 + curLevel;
      if (curLevel > 0) {
         encInv.setItem(idx, this.QuickItemNameLore(new ItemStack(Material.GOLD_BLOCK), QfCoreEnchant.intToRoman(curLevel), ChatColor.GOLD + "Enchant Level"));
      }

   }

   public void resetEmLevelIndicator(Inventory encInv, int maxLevel) {
      this.core.getLogger().info("resetEmLevelIndicator: " + maxLevel + " <" + encInv + ">");
      int idx = 36;
      idx = idx + 1;
      Material wool = idx - 36 <= maxLevel ? Material.LIME_WOOL : Material.RED_WOOL;
      ItemStack item = new ItemStack(wool);
      encInv.setItem(idx, this.QuickItemNameLore(item, "I", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "II", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "III", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "IV", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "V", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "VI", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "VII", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "VIII", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "IX", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "X", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XI", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XII", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XIII", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XIV", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XV", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XVI", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(item, "XVII", ChatColor.GOLD + "Enchant Level"));
      encInv.setItem(idx++, this.QuickItemNameLore(new ItemStack(Material.GLASS), "Unenchant", ChatColor.RED + "Remove Enchant"));
   }

   public void removeAllEnchants(Player pUser, QfCoreDItem di) {
      di.enchantList.clear();
      String path = "item." + di.name + ".enchants";
      this.getConfig().set(path, di.enchantList);
      this.saveConfig();
      di.iStack = pUser.getItemInUse();
      this.forgeItem(pUser, di);
      this.msgCaller(pUser, ChatColor.DARK_GREEN + "All enchants removed from item. Ready for more work.");
   }

   public void emSetEnchantLevel(Player pUser, QfCoreDItem di, int enLevel) {
      this.core.getLogger().info("working enchant: " + this.currentEmEnchant + " going to set level: " + enLevel);
      if (enLevel > this.currentEmMaxLevel) {
         this.msgCaller(pUser, ChatColor.RED + "Cannot use that enchantment level at this time");
      } else {
         this.enchantSetOnItem(pUser, di, this.currentEmEnchant, enLevel);
         if (enLevel == 0) {
            this.currentEmEnchant = "";
            this.currentEmMaxLevel = 0;
         }

         this.resetEmLevelIndicator(this.currentEmInv, this.currentEmMaxLevel);
         this.setEmLevelIndicator(this.currentEmInv, enLevel);
      }
   }

   public void enchantSelectItem(Player pUser, QfCoreDItem di, String enName) {
      this.currentEmEnchant = enName;
      this.currentEmMaxLevel = QfCoreEnchant.getMaxEnchantLevel(enName);
      this.core.getLogger().info("currentEmMaxLevel for '" + enName + "' is " + this.currentEmMaxLevel);
      this.resetEmLevelIndicator(this.currentEmInv, this.currentEmMaxLevel);
      int level = di.curEnchantLevel(enName);
      this.core.getLogger().info("current Level for '" + enName + "' is " + level);
      if (level > 0) {
         this.setEmLevelIndicator(this.currentEmInv, level);
      }

      this.msgCaller(pUser, ChatColor.DARK_GREEN + "Now working with enchantment " + ChatColor.YELLOW + enName);
   }

   public void enchantSetOnItem(Player pUser, QfCoreDItem di, String enName, int level) {
      this.currentEmEnchant = enName;
      this.currentEmMaxLevel = QfCoreEnchant.getMaxEnchantLevel(enName);
      this.core.getLogger().info("currentEmMaxLevel for " + enName + " is " + this.currentEmMaxLevel);
      di.iStack = pUser.getItemInUse();
      di.setEnchant(enName, level);
      di.makeReadyName();
      this.UpdateEnchantConfigList(di);
      this.UpdateQEnchantConfigList(di);
      if (level == 0) {
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Enchant removed from item. Ready for more work.");
      } else {
         this.msgCaller(pUser, ChatColor.DARK_GREEN + "Enchant added to item. Ready for more work.");
      }

   }

   public void UpdateEnchantConfigList(QfCoreDItem di) {
      String path = "item." + di.name + ".enchants";
      List<String> configList = this.getConfig().getStringList(path);
      configList.clear();
      Iterator<String> var5 = di.enchantList.iterator();

      while(var5.hasNext()) {
         String newLi = var5.next();
         configList.add(newLi);
      }

      this.getConfig().set(path, configList);
      this.saveConfig();
   }

   public void UpdateQEnchantConfigList(QfCoreDItem di) {
      String path = "item." + di.name + ".qenchants";
      List<String> configList = this.getConfig().getStringList(path);
      configList.clear();
      Iterator<String> var5 = di.qenchantList.iterator();

      while(var5.hasNext()) {
         String newLi = var5.next();
         configList.add(newLi);
      }

      this.getConfig().set(path, configList);
      this.saveConfig();
   }
}
