package me.yourselvs.qfcore.design;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.yourselvs.qfcore.QfCore;
import me.yourselvs.qfcore.QfMItem;

public class QfCoreDItem extends QfMItem {
   public String itemName;
   public String readyName;
   public int itemType;
   public int itemClass;
   public Material itemMaterial;
   public String nameColor;
   public boolean nameSqg;
   public boolean nameSqgBig;
   public String nameSqgColor;
   public List<String> enchantList;
   public List<String> qenchantList;
   public List<String> loreList;
   public ItemStack iStack;

   public void doInit() {
      this.category = "general";
      this.subcategory = "general";
      this.itemName = "NewItem";
      this.readyName = this.itemName;
      this.itemType = 0;
      this.itemClass = 0;
      this.itemMaterial = Material.DIAMOND_AXE;
      this.nameColor = "" + ChatColor.WHITE;
      this.nameSqg = false;
      this.nameSqgBig = false;
      this.nameSqgColor = "" + ChatColor.YELLOW;
      this.enchantList = new ArrayList<String>();
      this.qenchantList = new ArrayList<String>();
      this.loreList = new ArrayList<String>();
   }

   public String makeReadyName() {
      this.readyName = this.nameColor + this.itemName;
      if (this.nameSqg) {
         String sq1;
         String sq2;
         if (this.nameSqgBig) {
            sq1 = "|X";
            sq2 = "X|";
         } else {
            sq2 = "|";
            sq1 = "|";
         }

         this.readyName = this.nameSqgColor + ChatColor.MAGIC + sq1 + ChatColor.RESET + this.readyName + this.nameSqgColor + ChatColor.MAGIC + sq2;
      }

      if (this.iStack != null) {
         this.mgr.core.getLogger().info("setting name for item " + this.readyName);
         ItemMeta meta = this.iStack.getItemMeta();
         meta.setDisplayName(this.readyName);
         this.iStack.setItemMeta(meta);
      }

      return this.readyName;
   }

   public void updateLore() {
      List<String> newLore = new ArrayList<String>();
      Iterator<String> var6 = this.qenchantList.iterator();

      String lore;
      while(var6.hasNext()) {
         lore = var6.next();
         String enName = this.getFullEnchantName(lore);
         int enLevel = this.getFullEnchantLevel(lore);
         this.mgr.core.getLogger().info("adding qenchant " + enName + "-" + enLevel);
         newLore.add(this.addQEnchantLore(enName, enLevel));
      }

      newLore.add(this.niceItemClass() + " " + this.niceItemType());
      var6 = this.loreList.iterator();

      while(var6.hasNext()) {
         lore = var6.next();
         newLore.add(lore);
      }

      ItemMeta meta = this.iStack.getItemMeta();
      meta.setLore(newLore);
      this.iStack.setItemMeta(meta);
   }

   public String addQEnchantLore(String enName, int enLevel) {
      String retVal = "" + ChatColor.RESET + ChatColor.GRAY + enName + " " + QfCoreEnchant.intToRoman(enLevel);
      return retVal;
   }

   public void addLore(String newLore) {
      this.mgr.core.getLogger().info("adding lore <" + newLore + "> to item");
      this.loreList.add(newLore);
      this.updateLore();
   }

   public String getFullEnchantName(String encCompoundName) {
      int maxIdx = encCompoundName.lastIndexOf(32);
      if (maxIdx == -1) {
         return encCompoundName;
      } else {
         String pos = encCompoundName.substring(maxIdx).trim();
         String enName;
         if (QfCoreEnchant.isLevel(pos)) {
            enName = encCompoundName.substring(0, maxIdx);
            this.mgr.core.getLogger().info("getFullEnchantName: level found at end, " + pos);
         } else {
            enName = encCompoundName;
         }

         this.mgr.core.getLogger().info("getFullEnchantName: <" + encCompoundName + "> -- >" + enName + "<");
         enName = enName.trim();
         this.mgr.core.getLogger().info("getFullEnchantName: <" + encCompoundName + "> -- >" + enName + "<");
         return enName;
      }
   }

   public int getFullEnchantLevel(String encCompoundName) {
      String[] enOne = encCompoundName.split(" ");
      // decompiler artifact?
      // boolean var3 = true;

      try {
         int enLevel = Integer.parseInt(enOne[enOne.length - 1]);
         return enLevel;
      } catch (NumberFormatException var5) {
         return 0;
      }
   }

   public void makeReadyColor() {
      switch(this.itemClass) {
      case 0:
         this.nameColor = "" + ChatColor.GRAY;
         break;
      case 1:
         this.nameColor = "" + ChatColor.YELLOW;
         break;
      case 2:
         this.nameColor = "" + ChatColor.DARK_PURPLE;
         break;
      case 3:
         this.nameColor = "" + ChatColor.GOLD;
         break;
      case 4:
         this.nameColor = "" + ChatColor.DARK_AQUA;
         break;
      case 5:
         this.nameColor = "" + ChatColor.RED;
         break;
      default:
         this.nameColor = "" + ChatColor.WHITE;
      }

   }

   public void makeReadyClass() {
      this.makeReadyColor();
      if (this.iStack != null) {
         ItemMeta meta = this.iStack.getItemMeta();
         meta.setDisplayName(this.name);
         this.iStack.setItemMeta(meta);
      }

   }

   public String msgName() {
      return ChatColor.GREEN + "Item " + this.niceItemClass() + " " + this.niceItemType() + ChatColor.GREEN + " named " + this.readyName + ChatColor.RESET + ChatColor.GREEN + " ready for more work";
   }

   public String niceItemType() {
      String typeName;
      switch(this.itemType) {
      case 0:
         typeName = "Item";
         break;
      case 1:
         typeName = "Weapon";
         break;
      case 2:
         typeName = "Armor";
         break;
      case 3:
         typeName = "Tool";
         break;
      case 4:
         typeName = "Bow";
         break;
      case 5:
         typeName = "Rod";
         break;
      case 6:
         typeName = "Special Item";
         break;
      default:
         typeName = "Thing";
      }

      return typeName;
   }

   public String niceItemClass() {
      String className;
      switch(this.itemClass) {
      case 0:
         className = ChatColor.GRAY + "Common";
         break;
      case 1:
         className = ChatColor.YELLOW + "Uncommon";
         break;
      case 2:
         className = ChatColor.DARK_PURPLE + "Rare";
         break;
      case 3:
         className = ChatColor.GOLD + "Royal";
         break;
      case 4:
         className = ChatColor.DARK_AQUA + "Heroic";
         break;
      case 5:
         className = ChatColor.RED + "Legendary";
         break;
      default:
         className = ChatColor.WHITE + "Unknown";
      }

      return className;
   }

   public String NameNice() {
      String var1;
      switch((var1 = this.category).hashCode()) {
      case -1600582850:
         if (var1.equals("survival")) {
            return ChatColor.RED + this.itemName;
         }
         break;
      case -1422950650:
         if (var1.equals("active")) {
            return ChatColor.YELLOW + this.itemName;
         }
         break;
      case 94094958:
         if (var1.equals("build")) {
            return ChatColor.GREEN + this.itemName;
         }
      }

      return ChatColor.GOLD + this.itemName;
   }

   public String NameNiceWithId() {
      switch(this.itemType) {
      case 0:
         return ChatColor.GRAY + this.name + " - " + this.itemName;
      case 1:
         return ChatColor.GREEN + this.name + " - " + this.itemName;
      case 2:
         return ChatColor.RED + this.name + " - " + this.itemName;
      case 3:
         return ChatColor.YELLOW + this.name + " - " + this.itemName;
      case 4:
         return ChatColor.AQUA + this.name + " - " + this.itemName;
      case 5:
         return ChatColor.LIGHT_PURPLE + this.name + " - " + this.itemName;
      case 6:
         return ChatColor.GOLD + this.name + " - " + this.itemName;
      default:
         return ChatColor.WHITE + this.name + " - " + this.itemName;
      }
   }

   public ChatColor CatColor(String cat) {
      switch(cat.hashCode()) {
      case -1600582850:
         if (cat.equals("survival")) {
            return ChatColor.WHITE;
         }
         break;
      case -1221262756:
         if (cat.equals("health")) {
            return ChatColor.LIGHT_PURPLE;
         }
         break;
      case -80148248:
         if (cat.equals("general")) {
            return ChatColor.GOLD;
         }
         break;
      case 97285:
         if (cat.equals("bad")) {
            return ChatColor.DARK_RED;
         }
         break;
      case 101759:
         if (cat.equals("fun")) {
            return ChatColor.GOLD;
         }
         break;
      case 3029869:
         if (cat.equals("boss")) {
            return ChatColor.DARK_PURPLE;
         }
         break;
      case 3178685:
         if (cat.equals("good")) {
            return ChatColor.DARK_GREEN;
         }
         break;
      case 3556498:
         if (cat.equals("test")) {
            return ChatColor.AQUA;
         }
         break;
      case 94094958:
         if (cat.equals("build")) {
            return ChatColor.DARK_GREEN;
         }
      }

      return ChatColor.GRAY;
   }

   public void removeEnchant(String encName) {
      String targetName = this.getFullEnchantName(encName);
      Iterator<String> var5 = this.enchantList.iterator();

      while(var5.hasNext()) {
         String mem = var5.next();
         String listName = this.getFullEnchantName(mem);
         this.mgr.core.getLogger().info("trying to remove <" + encName + "> on enchant == " + mem + " (" + listName + ")");
         if (listName.equalsIgnoreCase(targetName)) {
            this.enchantList.remove(mem);
            this.mgr.core.getLogger().info("removed enchant " + encName);
            return;
         }
      }

   }

   public void removeQEnchant(String enName) {
      Iterator<String> var4 = this.qenchantList.iterator();

      while(var4.hasNext()) {
         String mem = var4.next();
         String listName = this.getFullEnchantName(mem);
         this.mgr.core.getLogger().info("trying to remove <" + enName + "> on qenchant == " + mem + " (" + listName + ")");
         if (listName.equalsIgnoreCase(enName)) {
            this.qenchantList.remove(mem);
            this.mgr.core.getLogger().info("removed qenchant >" + mem + "<");
            return;
         }
      }

   }

   public int curEnchantLevel(String enName) {
      String listName;
      String mem;
      Iterator<String> var4;
      if (QfCoreEnchant.isQEnchant(enName)) {
         var4 = this.qenchantList.iterator();

         while(var4.hasNext()) {
            mem = var4.next();
            listName = this.getFullEnchantName(mem);
            if (listName.equalsIgnoreCase(enName)) {
               return this.getFullEnchantLevel(mem);
            }
         }
      } else {
         var4 = this.enchantList.iterator();

         while(var4.hasNext()) {
            mem = var4.next();
            listName = this.getFullEnchantName(mem);
            if (listName.equalsIgnoreCase(enName)) {
               return this.getFullEnchantLevel(mem);
            }
         }
      }

      return -1;
   }

   public void autoType() {
      if (!this.itemMaterial.name().equalsIgnoreCase("DIAMOND_HELMET") && !this.itemMaterial.name().equalsIgnoreCase("GOLD_HELMET") && !this.itemMaterial.name().equalsIgnoreCase("IRON_HELMET") && !this.itemMaterial.name().equalsIgnoreCase("LEATHER_HELMET") && !this.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_HELMET") && !this.itemMaterial.name().equalsIgnoreCase("DIAMOND_CHESTPLATE") && !this.itemMaterial.name().equalsIgnoreCase("GOLD_CHESTPLATE") && !this.itemMaterial.name().equalsIgnoreCase("IRON_CHESTPLATE") && !this.itemMaterial.name().equalsIgnoreCase("LEATHER_CHESTPLATE") && !this.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_CHESTPLATE") && !this.itemMaterial.name().equalsIgnoreCase("DIAMOND_LEGGINGS") && !this.itemMaterial.name().equalsIgnoreCase("GOLD_LEGGINGS") && !this.itemMaterial.name().equalsIgnoreCase("IRON_LEGGINGS") && !this.itemMaterial.name().equalsIgnoreCase("LEATHER_LEGGINGS") && !this.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_LEGGINGS") && !this.itemMaterial.name().equalsIgnoreCase("DIAMOND_BOOTS") && !this.itemMaterial.name().equalsIgnoreCase("GOLD_BOOTS") && !this.itemMaterial.name().equalsIgnoreCase("IRON_BOOTS") && !this.itemMaterial.name().equalsIgnoreCase("LEATHER_BOOTS") && !this.itemMaterial.name().equalsIgnoreCase("CHAINMAIL_BOOTS")) {
         if (this.itemMaterial.name().equalsIgnoreCase("WOOD_SWORD") || this.itemMaterial.name().equalsIgnoreCase("STONE_SWORD") || this.itemMaterial.name().equalsIgnoreCase("IRON_SWORD") || this.itemMaterial.name().equalsIgnoreCase("GOLD_SWORD") || this.itemMaterial.name().equalsIgnoreCase("DIAMOND_SWORD")) {
            this.itemType = 1;
         }
      } else {
         this.itemType = 2;
      }

   }

   public Enchantment getEnchant(String encName) {
	  NamespacedKey key = new NamespacedKey(QfCore.instance, encName.toLowerCase());
      Enchantment enc = Enchantment.getByKey(key);
      if (enc != null) {
         return enc;
      } else {
         String eName = encName.toLowerCase();
         if ("unbreaking".startsWith(eName)) {
            return Enchantment.DURABILITY;
         } else if ("protection".startsWith(eName)) {
            return Enchantment.PROTECTION_ENVIRONMENTAL;
         } else if ("fireprotection".startsWith(eName)) {
            return Enchantment.PROTECTION_FIRE;
         } else if ("blastprotection".startsWith(eName)) {
            return Enchantment.PROTECTION_EXPLOSIONS;
         } else if ("projectileprotection".startsWith(eName)) {
            return Enchantment.PROTECTION_PROJECTILE;
         } else if ("thorns".startsWith(eName)) {
            return Enchantment.THORNS;
         } else if ("featherfalling".startsWith(eName)) {
            return Enchantment.PROTECTION_FALL;
         } else if ("frostwalker".startsWith(eName)) {
            return Enchantment.FROST_WALKER;
         } else if ("mending".startsWith(eName)) {
            return Enchantment.MENDING;
         } else if ("depthstrider".startsWith(eName)) {
            return Enchantment.DEPTH_STRIDER;
         } else if ("respiration".startsWith(eName)) {
            return Enchantment.OXYGEN;
         } else if ("sharpness".startsWith(eName)) {
            return Enchantment.DAMAGE_ALL;
         } else if ("smite".startsWith(eName)) {
            return Enchantment.DAMAGE_UNDEAD;
         } else if ("fire aspect".startsWith(eName)) {
            return Enchantment.FIRE_ASPECT;
         } else if ("knockback".startsWith(eName)) {
            return Enchantment.KNOCKBACK;
         } else if ("bane of arthropods".startsWith(eName)) {
            return Enchantment.DAMAGE_ARTHROPODS;
         } else if ("looting".startsWith(eName)) {
            return Enchantment.LOOT_BONUS_MOBS;
         } else if ("mending".startsWith(eName)) {
            return Enchantment.MENDING;
         } else if ("infinity".startsWith(eName)) {
            return Enchantment.ARROW_INFINITE;
         } else if (!"fire".startsWith(eName) && !"flame".startsWith(eName)) {
            if (!"punch".startsWith(eName) && !"longbow".startsWith(eName)) {
               if ("power".startsWith(eName)) {
                  return Enchantment.ARROW_DAMAGE;
               } else if ("efficiency".startsWith(eName)) {
                  return Enchantment.DIG_SPEED;
               } else if ("silktouch".startsWith(eName)) {
                  return Enchantment.SILK_TOUCH;
               } else {
                  return "fortune".startsWith(eName) ? Enchantment.LOOT_BONUS_BLOCKS : null;
               }
            } else {
               return Enchantment.ARROW_KNOCKBACK;
            }
         } else {
            return Enchantment.ARROW_FIRE;
         }
      }
   }

   public String setEnchant(String enName, int level) {
      String enFName = enName.trim() + " " + level;
      if (QfCoreEnchant.isQEnchant(enName)) {
         this.mgr.core.getLogger().info("adding qenchantment <" + enName + "> [" + level + "] to item");
         this.removeQEnchant(enName);
         if (level > 0) {
            this.qenchantList.add(enFName);
         }

         this.updateLore();
      } else {
         this.mgr.core.getLogger().info("adding enchantment <" + enName + "> [" + level + "] to item");
         this.removeEnchant(enName);
         if (level > 0) {
            this.enchantList.add(enFName);
            this.doAddEnchantToItem(enName, level);
         } else {
            this.doRemoveEnchantFromItem(enName);
         }
      }

      return enFName;
   }

   public void addEnchants() {
      Iterator<String> var2 = this.enchantList.iterator();

      while(var2.hasNext()) {
         String mem = var2.next();
         this.doAddEnchantToItem(mem);
      }

   }

   public void doAddEnchantToItem(String enFName) {
      this.doAddEnchantToItem(this.getFullEnchantName(enFName), this.getFullEnchantLevel(enFName));
   }

   public void doAddEnchantToItem(String encName, int level) {
      if (!QfCoreEnchant.isQEnchant(encName)) {
         Enchantment enc = this.getEnchant(encName);
         this.mgr.core.getLogger().info("doAddEnchantToItem <" + encName + "> [" + level + "] to item with enc = " + (encName == null ? "null" : "good"));
         if (enc != null) {
            this.iStack.addUnsafeEnchantment(enc, level);
         }
      }

   }

   public void doRemoveEnchantFromItem(String encName) {
      this.mgr.core.getLogger().info("doRemoveEnchantFromItem <" + encName + "> <" + this.getFullEnchantName(encName) + ">");
      if (!QfCoreEnchant.isQEnchant(encName)) {
         Enchantment enc = this.getEnchant(encName);
         this.mgr.core.getLogger().info("doremoveEnchantFromItem <" + encName + "> from  item with enc = " + (encName == null ? "null" : "good"));
         if (enc != null) {
            this.iStack.removeEnchantment(enc);
         }
      }

   }
}
