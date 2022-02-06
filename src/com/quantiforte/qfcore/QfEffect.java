package com.quantiforte.qfcore;

import java.util.Collection;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class QfEffect {
   public String name;
   public int radiusEff;
   public int radiusNear;
   public int radiusFar;
   public int level;
   public int duration;
   public int cooldown;

   public void applyEffectToPlayer(Player pTarget, Double distanceFromPlayer) {
      Collection<PotionEffect> curEffects = pTarget.getActivePotionEffects();
      if (distanceFromPlayer <= (double)this.radiusFar && distanceFromPlayer >= (double)this.radiusNear) {
         PotionEffect pe;
         label289: {
            String var7;
            switch((var7 = this.name).hashCode()) {
            case -1849290568:
               if (var7.equals("darkbuild")) {
                  pe = new PotionEffect(PotionEffectType.NIGHT_VISION, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case -1383205240:
               if (!var7.equals("bounce")) {
                  return;
               }
               break;
            case -1321333613:
               if (var7.equals("flamedamp")) {
                  pe = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case -1221262756:
               if (!var7.equals("health")) {
                  return;
               }
               break label289;
            case -1206104397:
               if (var7.equals("hunger")) {
                  pe = new PotionEffect(PotionEffectType.HUNGER, this.duration * 20, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case -820818432:
               if (var7.equals("nightvision")) {
                  pe = new PotionEffect(PotionEffectType.NIGHT_VISION, this.duration * 20, this.level, true);
                  curEffects.forEach(potionEffect -> {
                	  if (potionEffect.getType().equals(PotionEffectType.NIGHT_VISION)) {
                          pTarget.removePotionEffect(PotionEffectType.NIGHT_VISION);
                	  }
                  });
                  pTarget.addPotionEffect(pe);
               }

               return;
            case -306977811:
               if (var7.equals("levitation")) {
                  pe = new PotionEffect(PotionEffectType.LEVITATION, this.duration * 40, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case -270580530:
               if (!var7.equals("fireproof")) {
               }

               return;
            case 3143256:
               if (var7.equals("fish")) {
                  pe = new PotionEffect(PotionEffectType.NIGHT_VISION, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
                  pe = new PotionEffect(PotionEffectType.WATER_BREATHING, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 3198440:
               if (var7.equals("heal")) {
                  pe = new PotionEffect(PotionEffectType.HEAL, this.duration * 20, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 3273774:
               if (!var7.equals("jump")) {
                  return;
               }
               break;
            case 98357969:
               if (var7.equals("gills")) {
                  pe = new PotionEffect(PotionEffectType.WATER_BREATHING, this.duration * 20, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 108392509:
               if (!var7.equals("regen")) {
                  return;
               }
               break label289;
            case 109532714:
               if (var7.equals("sloth")) {
                  pe = new PotionEffect(PotionEffectType.SLOW, this.duration * 20, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 109578318:
               if (!var7.equals("snack")) {
               }

               return;
            case 109641799:
               if (var7.equals("speed")) {
                  pe = new PotionEffect(PotionEffectType.SPEED, this.duration * 20, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 151619372:
               if (var7.equals("blindness")) {
                  pe = new PotionEffect(PotionEffectType.BLINDNESS, this.duration * 40, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 280523342:
               if (var7.equals("gravity")) {
                  pe = new PotionEffect(PotionEffectType.JUMP, this.duration * 20, -20 * this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 365177999:
               if (var7.equals("underwaterbuild")) {
                  pe = new PotionEffect(PotionEffectType.NIGHT_VISION, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
                  pe = new PotionEffect(PotionEffectType.WATER_BREATHING, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 761310338:
               if (var7.equals("disorientation")) {
                  pe = new PotionEffect(PotionEffectType.CONFUSION, this.duration * 20, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 1439257549:
               if (var7.equals("autofeed")) {
                  pTarget.setFoodLevel(20);
               }

               return;
            case 1439317015:
               if (var7.equals("autoheal")) {
                  pTarget.setHealth(pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
               }

               return;
            case 1729863050:
               if (var7.equals("extinguish") && pTarget.getFireTicks() > 0) {
                  pTarget.setFireTicks(0);
                  double health = pTarget.getHealth() + 1.0D;
                  if (health > 1.0D && health <= pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
                     pTarget.setHealth(health);
                     return;
                  }
               }

               return;
            case 1791316033:
               if (var7.equals("strength")) {
                  pe = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, this.duration * 20, this.level, true);
                  pTarget.addPotionEffect(pe);
               }

               return;
            case 2032888271:
               if (var7.equals("illumination")) {
                  pe = new PotionEffect(PotionEffectType.GLOWING, this.duration * 40, this.level);
                  pTarget.addPotionEffect(pe);
               }

               return;
            default:
               return;
            }

            pe = new PotionEffect(PotionEffectType.JUMP, this.duration * 20, this.level, true);
            pTarget.addPotionEffect(pe);
            return;
         }

         pe = new PotionEffect(PotionEffectType.REGENERATION, this.duration * 20, this.level);
         pTarget.addPotionEffect(pe);
      }

   }
}
