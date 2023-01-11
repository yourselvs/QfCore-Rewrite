package me.yourselvs.qfcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QfAura extends QfMItem {
   public List<QfEffect> effects;

   public void doInit() {
      this.effects = new ArrayList<QfEffect>();
   }

   public boolean addEffectConfig(String strCon) {
      String[] args = strCon.split(" ");
      if (args.length != 7) {
         return false;
      } else {
         QfEffect eff = new QfEffect();
         eff.name = args[0];
         eff.radiusEff = Integer.parseInt(args[1]);
         eff.radiusNear = Integer.parseInt(args[2]);
         eff.radiusFar = Integer.parseInt(args[3]);
         eff.level = Integer.parseInt(args[4]);
         eff.duration = Integer.parseInt(args[5]);
         eff.cooldown = Integer.parseInt(args[6]);
         this.effects.add(eff);
         return true;
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
      case 94742904:
         if (cat.equals("class")) {
            return ChatColor.BLUE;
         }
      }

      return ChatColor.GRAY;
   }

   public void applyAuraToPlayer(Player pTarget, Double distanceFromPlayer) {
      Iterator<QfEffect> var4 = this.effects.iterator();

      while(var4.hasNext()) {
         QfEffect eff = (QfEffect)var4.next();
         eff.applyEffectToPlayer(pTarget, distanceFromPlayer);
      }

   }

   public String NameNice() {
      return this.CatColor(this.subcategory) + this.name;
   }
}
