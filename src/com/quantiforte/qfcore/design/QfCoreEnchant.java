package com.quantiforte.qfcore.design;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QfCoreEnchant {
   public static int getMaxEnchantLevel(String enName) {
      String enNameLc = enName.toLowerCase();
      switch(enNameLc.hashCode()) {
      case -1571105471:
         if (enNameLc.equals("sharpness")) {
            return 12;
         }
         break;
      case -1206104397:
         if (enNameLc.equals("hunger")) {
            return 4;
         }
         break;
      case -1137264811:
         if (enNameLc.equals("tornado")) {
            return 4;
         }
         break;
      case -798671486:
         if (enNameLc.equals("fire aspect")) {
            return 2;
         }
         break;
      case -752097008:
         if (enNameLc.equals("winter's touch")) {
            return 4;
         }
         break;
      case 43768941:
         if (enNameLc.equals("poor seeing")) {
            return 4;
         }
         break;
      case 109532714:
         if (enNameLc.equals("sloth")) {
            return 4;
         }
         break;
      case 109556736:
         if (enNameLc.equals("smite")) {
            return 12;
         }
         break;
      case 350056506:
         if (enNameLc.equals("looting")) {
            return 4;
         }
         break;
      case 686445258:
         if (enNameLc.equals("lightning")) {
            return 4;
         }
         break;
      case 949868500:
         if (enNameLc.equals("mending")) {
            return 2;
         }
         break;
      case 1235015391:
         if (enNameLc.equals("deep wound")) {
            return 5;
         }
         break;
      case 1603571740:
         if (enNameLc.equals("unbreaking")) {
            return 7;
         }
         break;
      case 1688171794:
         if (enNameLc.equals("frost walker")) {
            return 2;
         }
         break;
      case 1930568367:
         if (enNameLc.equals("bane of arthropods")) {
            return 8;
         }
      }

      return 0;
   }

   public static String intToRoman(int num) {
      switch(num) {
      case 1:
         return "I";
      case 2:
         return "II";
      case 3:
         return "III";
      case 4:
         return "IV";
      case 5:
         return "V";
      case 6:
         return "VI";
      case 7:
         return "VII";
      case 8:
         return "VIII";
      case 9:
         return "IX";
      case 10:
         return "X";
      case 11:
         return "XI";
      case 12:
         return "XII";
      case 13:
         return "XIII";
      case 14:
         return "XIV";
      case 15:
         return "XV";
      case 16:
         return "XVI";
      case 17:
         return "XVII";
      case 18:
         return "XVIII";
      case 19:
         return "XIX";
      case 20:
         return "XX";
      default:
         return "?";
      }
   }

   public static int romanToInt(String num) {
      switch(num.hashCode()) {
      case 73:
         if (num.equals("I")) {
            return 1;
         }
         break;
      case 86:
         if (num.equals("V")) {
            return 5;
         }
         break;
      case 88:
         if (num.equals("X")) {
            return 10;
         }
         break;
      case 2336:
         if (num.equals("II")) {
            return 2;
         }
         break;
      case 2349:
         if (num.equals("IV")) {
            return 4;
         }
         break;
      case 2351:
         if (num.equals("IX")) {
            return 9;
         }
         break;
      case 2739:
         if (num.equals("VI")) {
            return 6;
         }
         break;
      case 2801:
         if (num.equals("XI")) {
            return 11;
         }
         break;
      case 2814:
         if (num.equals("XV")) {
            return 16;
         }
         break;
      case 2816:
         if (num.equals("XX")) {
            return 20;
         }
         break;
      case 72489:
         if (num.equals("III")) {
            return 3;
         }
         break;
      case 84982:
         if (num.equals("VII")) {
            return 7;
         }
         break;
      case 86904:
         if (num.equals("XII")) {
            return 12;
         }
         break;
      case 86917:
         if (num.equals("XIV")) {
            return 14;
         }
         break;
      case 86919:
         if (num.equals("XIX")) {
            return 19;
         }
         break;
      case 87307:
         if (num.equals("XVI")) {
            return 15;
         }
         break;
      case 2634515:
         if (num.equals("VIII")) {
            return 8;
         }
         break;
      case 2694097:
         if (num.equals("XIII")) {
            return 13;
         }
         break;
      case 2706590:
         if (num.equals("XVII")) {
            return 17;
         }
         break;
      case 83904363:
         if (num.equals("XVIII")) {
            return 18;
         }
      }

      return 1;
   }

   public static boolean isLevel(String num) {
      switch(num.hashCode()) {
      case 49:
         if (num.equals("1")) {
            return true;
         }
         break;
      case 50:
         if (num.equals("2")) {
            return true;
         }
         break;
      case 51:
         if (num.equals("3")) {
            return true;
         }
         break;
      case 52:
         if (num.equals("4")) {
            return true;
         }
         break;
      case 53:
         if (num.equals("5")) {
            return true;
         }
         break;
      case 54:
         if (num.equals("6")) {
            return true;
         }
         break;
      case 55:
         if (num.equals("7")) {
            return true;
         }
         break;
      case 56:
         if (num.equals("8")) {
            return true;
         }
         break;
      case 57:
         if (num.equals("9")) {
            return true;
         }
         break;
      case 73:
         if (num.equals("I")) {
            return true;
         }
         break;
      case 86:
         if (num.equals("V")) {
            return true;
         }
         break;
      case 88:
         if (num.equals("X")) {
            return true;
         }
         break;
      case 1567:
         if (num.equals("10")) {
            return true;
         }
         break;
      case 1568:
         if (num.equals("11")) {
            return true;
         }
         break;
      case 1569:
         if (num.equals("12")) {
            return true;
         }
         break;
      case 1570:
         if (num.equals("13")) {
            return true;
         }
         break;
      case 1571:
         if (num.equals("14")) {
            return true;
         }
         break;
      case 1572:
         if (num.equals("15")) {
            return true;
         }
         break;
      case 1573:
         if (num.equals("16")) {
            return true;
         }
         break;
      case 1574:
         if (num.equals("17")) {
            return true;
         }
         break;
      case 1575:
         if (num.equals("18")) {
            return true;
         }
         break;
      case 1576:
         if (num.equals("19")) {
            return true;
         }
         break;
      case 1598:
         if (num.equals("20")) {
            return true;
         }
         break;
      case 2336:
         if (num.equals("II")) {
            return true;
         }
         break;
      case 2349:
         if (num.equals("IV")) {
            return true;
         }
         break;
      case 2351:
         if (num.equals("IX")) {
            return true;
         }
         break;
      case 2739:
         if (num.equals("VI")) {
            return true;
         }
         break;
      case 2801:
         if (num.equals("XI")) {
            return true;
         }
         break;
      case 2814:
         if (num.equals("XV")) {
            return true;
         }
         break;
      case 2816:
         if (num.equals("XX")) {
            return true;
         }
         break;
      case 72489:
         if (num.equals("III")) {
            return true;
         }
         break;
      case 84982:
         if (num.equals("VII")) {
            return true;
         }
         break;
      case 86904:
         if (num.equals("XII")) {
            return true;
         }
         break;
      case 86917:
         if (num.equals("XIV")) {
            return true;
         }
         break;
      case 86919:
         if (num.equals("XIX")) {
            return true;
         }
         break;
      case 87307:
         if (num.equals("XVI")) {
            return true;
         }
         break;
      case 2634515:
         if (num.equals("VIII")) {
            return true;
         }
         break;
      case 2694097:
         if (num.equals("XIII")) {
            return true;
         }
         break;
      case 2706590:
         if (num.equals("XVII")) {
            return true;
         }
         break;
      case 83904363:
         if (num.equals("XVIII")) {
            return true;
         }
      }

      return false;
   }

   public static List<String> getQEnchants(ItemStack item) {
      if (item == null) {
         return null;
      } else {
         List<String> qenchantList = new ArrayList<String>();
         ItemMeta meta = item.getItemMeta();
         List<String> loreList = meta.getLore();
         if (loreList != null && !loreList.isEmpty()) {
            Iterator<String> var5 = loreList.iterator();

            while(var5.hasNext()) {
               String li = (String)var5.next();
               if (isQEnchant(enchantName(li))) {
                  qenchantList.add(ChatColor.stripColor(li));
               }
            }

            if (!qenchantList.isEmpty()) {
               return qenchantList;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static boolean isQEnchant(String encName) {
      switch(encName.hashCode()) {
      case -2122237229:
         if (encName.equals("Hunger")) {
            return true;
         }
         break;
      case -1994151849:
         if (encName.equals("Medusa")) {
            return true;
         }
         break;
      case -1783642864:
         if (encName.equals("Winter's Touch")) {
            return true;
         }
         break;
      case -1721492669:
         if (encName.equals("Vulcan")) {
            return true;
         }
         break;
      case -1604554070:
         if (encName.equals("Lightning")) {
            return true;
         }
         break;
      case -1108039359:
         if (encName.equals("Wildness")) {
            return true;
         }
         break;
      case -1096039713:
         if (encName.equals("Deep Wound")) {
            return true;
         }
         break;
      case -656663899:
         if (encName.equals("SandStorm")) {
            return true;
         }
         break;
      case -61421729:
         if (encName.equals("Slow Breath")) {
            return true;
         }
         break;
      case 25725542:
         if (encName.equals("Hurricaine")) {
            return true;
         }
         break;
      case 68066119:
         if (encName.equals("Forge")) {
            return true;
         }
         break;
      case 79980042:
         if (encName.equals("Sloth")) {
            return true;
         }
         break;
      case 527388469:
         if (encName.equals("Tornado")) {
            return true;
         }
         break;
      case 795504749:
         if (encName.equals("Poor Seeing")) {
            return true;
         }
         break;
      case 1945176622:
         if (encName.equals("Gravity")) {
            return true;
         }
         break;
      case 1965534933:
         if (encName.equals("Anchor")) {
            return true;
         }
         break;
      case 2023382054:
         if (encName.equals("Loyalty")) {
            return true;
         }
      }

      return false;
   }

   public static String enchantName(String encCompoundName) {
      int maxIdx = encCompoundName.lastIndexOf(32);
      if (maxIdx == -1) {
         return encCompoundName;
      } else {
         String pos = encCompoundName.substring(maxIdx).trim();
         String enName;
         if (isLevel(pos)) {
            enName = encCompoundName.substring(0, maxIdx);
         } else {
            enName = encCompoundName;
         }

         enName = ChatColor.stripColor(enName.trim());
         return enName;
      }
   }

   public static int enchantLevel(String encCompoundName) {
      String[] enOne = encCompoundName.split(" ");
      // decompiler artifact?
      // boolean var2 = true;

      try {
         int enLevel = Integer.parseInt(enOne[enOne.length - 1]);
         return enLevel;
      } catch (NumberFormatException var4) {
         return romanToInt(enOne[enOne.length - 1]);
      }
   }
}
