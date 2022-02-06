package com.quantiforte.qfcore.movement;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class QfDynHome {
   public String configName;
   public String name;
   public String active;
   public String locInMsg;
   public String descr;
   public Location locIn;
   protected boolean triggerIsDynamic;
   public Player mPlayer;
   public UUID mUuid;
   public QfDynHomeMgr mgr;

   public boolean addLocIn(String str) {
      return false;
   }
}
