package com.quantiforte.qfcore;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class QfMItem {
   public String name;
   public String active;
   public String self;
   public String category;
   public String subcategory;
   protected boolean triggerIsDynamic;
   protected QfTriggerLoc triggerLoc;
   public Player mPlayer;
   public UUID mUuid;
   public QfManager mgr;

   public void doInit() {
   }

   public ChatColor CatColor(String cat) {
      return ChatColor.GRAY;
   }

   public String NameCat(String cat) {
      return !cat.equalsIgnoreCase(this.category) && !cat.equalsIgnoreCase(this.subcategory) ? null : this.name;
   }

   public String NameNice() {
      return ChatColor.YELLOW + this.name + " " + this.CatColor(this.subcategory) + "(" + this.subcategory + ") - " + ChatColor.GRAY + this.category;
   }

   public void initLocation() {
      if (this.triggerLoc == null) {
         this.triggerLoc = new QfTriggerLoc();
         this.triggerLoc.mitem = this;
      }

      this.triggerLoc.trigLoc = new Location((World)null, 0.0D, 0.0D, 0.0D);
      this.triggerLoc.trigRadius = 10.0D;
   }

   public void setLocation(String sloc) {
      String[] args = sloc.split(" ");
      if (this.triggerLoc == null) {
         this.triggerLoc = new QfTriggerLoc();
         this.triggerLoc.mitem = this;
      }

      this.triggerLoc.trigLoc = this.args2Loc(args);
      this.triggerLoc.worldName = args[0];
      this.mgr.core.getLogger().info("987X: " + args[0]);
      this.triggerIsDynamic = false;
      if (args.length > 4) {
         this.triggerLoc.trigRadius = Double.parseDouble(args[4]);
      }

   }

   public Location args2Loc(String[] args) {
      Location newLoc = new Location(this.mgr.core.getServer().getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[3]), Double.parseDouble(args[2]));
      return newLoc;
   }

   public QfTriggerLoc getTriggerLoc() {
      if (this.triggerIsDynamic) {
         this.recalcTriggerLoc();
      }

      return this.triggerLoc;
   }

   public Location getCurrentTriggerLoc() {
      this.recalcTriggerLoc();
      return this.triggerLoc.trigLoc;
   }

   public void recalcTriggerLoc() {
   }

   public void doTriggerAction(Player pTarget, Double distanceFromPlayer) {
   }
}
