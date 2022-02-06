package com.quantiforte.qfcore;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QfGeneral {
   public QfCore qfcore;

   public void doInit(QfCore plugin) {
      this.qfcore = plugin;
   }

   protected void msgCaller(Player pUser, String msg) {
      if (pUser == null) {
         this.qfcore.getServer().getLogger().info(ChatColor.translateAlternateColorCodes('&', msg));
      } else {
         pUser.sendMessage(msg);
      }

   }

   public long timeStr2secs(String timeStr) {
      long secs = 0L;
      String[] time = timeStr.split(" ");
      String[] var8 = time;
      int var7 = time.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         String tim = var8[var6];
         if (tim.endsWith("h")) {
            secs += (long)(Integer.parseInt(tim.replace("h", "")) * 3600);
         } else if (tim.endsWith("m")) {
            secs += (long)(Integer.parseInt(tim.replace("m", "")) * 60);
         } else if (tim.endsWith("s")) {
            secs += (long)Integer.parseInt(tim.replace("s", ""));
         }
      }

      this.qfcore.getLogger().info("timeStr2secs: " + secs + "s from " + timeStr);
      return secs;
   }

   public void setItemName(ItemStack item, String name) {
      ItemMeta itemMeta = item.getItemMeta();
      itemMeta.setDisplayName(name);
      item.setItemMeta(itemMeta);
   }

   public String firstLore(ItemStack item) {
      ItemMeta meta = item.getItemMeta();
      if (meta == null) {
         return null;
      } else {
         List<String> loreList = meta.getLore();
         return loreList == null ? null : (String)loreList.get(0);
      }
   }

   protected ItemStack QuickItem(Material mat, String name, String lore) {
      List<String> loreList = new ArrayList<String>();
      loreList.add(lore);
      ItemStack item = new ItemStack(mat, 1);
      this.setItemName(item, name);
      ItemMeta meta = item.getItemMeta();
      meta.setLore(loreList);
      item.setItemMeta(meta);
      return item;
   }

   protected ItemStack QuickItemNameLore(ItemStack item, String name, String lore) {
      List<String> loreList = new ArrayList<String>();
      loreList.add(lore);
      this.setItemName(item, name);
      ItemMeta meta = item.getItemMeta();
      meta.setLore(loreList);
      item.setItemMeta(meta);
      return item;
   }

   protected ItemStack QuickItemLore(Material mat, String name, String[] lore) {
      List<String> loreList = new ArrayList<String>();
      String[] var10 = lore;
      int var9 = lore.length;

      for(int var8 = 0; var8 < var9; ++var8) {
         String lr = var10[var8];
         loreList.add(lr);
      }

      ItemStack item = new ItemStack(mat, 1);
      this.setItemName(item, name);
      ItemMeta meta = item.getItemMeta();
      meta.setLore(loreList);
      item.setItemMeta(meta);
      return item;
   }

   protected ItemStack QuickItemNameLores(ItemStack item, String name, String[] lore) {
      List<String> loreList = new ArrayList<String>();
      String[] var9 = lore;
      int var8 = lore.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         String lr = var9[var7];
         loreList.add(lr);
      }

      this.setItemName(item, name);
      ItemMeta meta = item.getItemMeta();
      meta.setLore(loreList);
      item.setItemMeta(meta);
      return item;
   }
}
