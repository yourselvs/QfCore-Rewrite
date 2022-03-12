package com.quantiforte.qfcore;

import com.quantiforte.qfcore.classes.QrpgClassMgr;
import com.quantiforte.qfcore.design.QfCoreDesignMgr;
import com.quantiforte.qfcore.design.QfCoreEnchant;
import com.quantiforte.qfcore.guilds.QrpgGuildMgr;
import com.quantiforte.qfcore.guilds.QrpgPartyMgr;
import com.quantiforte.qfcore.health.QrpgHealthMgr;
import com.quantiforte.qfcore.info.QrpgInfoMgr;
import com.quantiforte.qfcore.info.QrpgSignupMgr;
import com.quantiforte.qfcore.inventory.QfBuildInvMgr;
import com.quantiforte.qfcore.mobs.QfMobMgr;
import com.quantiforte.qfcore.movement.QfLandHomeMgr;
import com.quantiforte.qfcore.movement.QfMoveMgr;
import com.quantiforte.qfcore.movement.QfSiteMgr;
import com.quantiforte.qfcore.mytown.QrpgMyTownMgr;
import com.quantiforte.qfcore.soldier.QrpgSoldierMgr;
import com.quantiforte.qfcore.staff.QfStaffMgr;
import com.quantiforte.qfcore.tradesman.QrpgTradesmanCmdExec;
import com.quantiforte.qfcore.workorder.QrpgWorkOrderMgr;
import com.quantiforte.utility.QfCooldownMgr;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class QfCore extends JavaPlugin implements Listener {
	public static QfCore instance;
	public static List<String> auras;
	public static List<String> dungeons;
	public static int taskIdAura = -1;
	public static int taskIdAnnounce = -1;
	public static int taskIdTasker = -1;
	public static int taskIdBlockPersist = -1;
	public static int taskIdQAnnc = -1;
	public static int taskIdGuildMotd = -1;
	public static Economy economy;
	public static List<String> portals;
	public QfAuraMgr auraMgr;
	public QfAuraBlockMgr auraBlockMgr;
	public QfAuraPlayerMgr auraPlayerMgr;
	public QrpgHealthMgr healthMgr;
	public QfMoveMgr moveMgr;
	public QfBuildInvMgr builderInvMgr;
	public QrpgInfoMgr infoMgr;
	public QrpgClassMgr classMgr;
	public QrpgSoldierMgr soldierMgr;
	public QfCooldownMgr cooldownMgr;
	public QrpgMyTownMgr myTownMgr;
	public QfStaffMgr staffMgr;
	public QfSiteMgr siteMgr;
	public QfLandHomeMgr landHomeMgr;
	public QrpgWorkOrderMgr workOrderMgr;
	public QfMobMgr mobMgr;
	public QrpgSignupMgr signupMgr;
	public QrpgGuildMgr guildMgr;
	public QrpgPartyMgr partyMgr;
	public QfCoreDesignMgr designMgr;
	private QrpgTradesmanCmdExec ceTradesman;
	protected List<String> qAnnc;
	protected long qAnncInt;

	public void onEnable() {
		this.getLogger().info("QfCore starting up...");
		instance = this;

		this.qAnnc = new ArrayList<String>();
		this.saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(this, this);

		if (this.cooldownMgr == null) {
			this.cooldownMgr = new QfCooldownMgr();
		}

		this.cooldownMgr.loadMgr(this);
		if (this.auraMgr == null) {
			this.auraMgr = new QfAuraMgr();
		}

		this.auraMgr.loadMgr(this);
		if (this.auraPlayerMgr == null) {
			this.auraPlayerMgr = new QfAuraPlayerMgr();
		}

		this.auraPlayerMgr.loadMgr(this);
		if (this.auraBlockMgr == null) {
			this.auraBlockMgr = new QfAuraBlockMgr();
		}

		this.auraBlockMgr.loadMgr(this);
		if (this.siteMgr == null) {
			this.siteMgr = new QfSiteMgr();
		}

		this.siteMgr.loadMgr(this);
		if (this.guildMgr == null) {
			this.guildMgr = new QrpgGuildMgr();
		}

		this.guildMgr.loadMgr(this);
		if (this.partyMgr == null) {
			this.partyMgr = new QrpgPartyMgr();
		}

		this.partyMgr.loadMgr(this);
		if (this.designMgr == null) {
			this.designMgr = new QfCoreDesignMgr();
		}

		this.designMgr.loadMgr(this);
		if (this.landHomeMgr == null) {
			this.landHomeMgr = new QfLandHomeMgr();
		}

		this.landHomeMgr.loadMgr(this);
		if (this.workOrderMgr == null) {
			this.workOrderMgr = new QrpgWorkOrderMgr();
		}

		this.workOrderMgr.loadMgr(this);
		if (this.mobMgr == null) {
			this.mobMgr = new QfMobMgr();
		}

		this.mobMgr.loadMgr(this);
		if (dungeons == null) {
			dungeons = new ArrayList<String>();
		}

		if (portals == null) {
			portals = new ArrayList<String>();
		}

		if (this.ceTradesman == null) {
			this.ceTradesman = new QrpgTradesmanCmdExec();
			this.ceTradesman.doInit(this);
		}

		if (this.healthMgr == null) {
			this.healthMgr = new QrpgHealthMgr();
			this.healthMgr.doInit(this);
		}

		if (this.moveMgr == null) {
			this.moveMgr = new QfMoveMgr();
			this.moveMgr.doInit(this);
		}

		if (this.myTownMgr == null) {
			this.myTownMgr = new QrpgMyTownMgr();
			this.myTownMgr.doInit(this);
		}

		if (this.builderInvMgr == null) {
			this.builderInvMgr = new QfBuildInvMgr();
			this.builderInvMgr.doInit(this);
		}

		if (this.infoMgr == null) {
			this.infoMgr = new QrpgInfoMgr();
			this.infoMgr.doInit(this);
		}

		if (this.signupMgr == null) {
			this.signupMgr = new QrpgSignupMgr();
			this.signupMgr.doInit(this);
		}

		if (this.classMgr == null) {
			this.classMgr = new QrpgClassMgr();
			this.classMgr.doInit(this);
		}

		if (this.soldierMgr == null) {
			this.soldierMgr = new QrpgSoldierMgr();
			this.soldierMgr.doInit(this);
		}

		if (this.staffMgr == null) {
			this.staffMgr = new QfStaffMgr();
			this.staffMgr.doInit(this);
		}

		this.ceTradesman.moveMgr = this.moveMgr;
		this.readConfigAnc();
		this.setupPortals();
		PluginCommand cmd = this.getCommand("bagel");
		cmd.setExecutor(this.healthMgr);
		cmd = this.getCommand("heal");
		cmd.setExecutor(this.healthMgr);
		cmd = this.getCommand("feed");
		cmd.setExecutor(this.healthMgr);
		cmd = this.getCommand("ext");
		cmd.setExecutor(this.healthMgr);
		cmd = this.getCommand("food");
		cmd.setExecutor(this.healthMgr);
		cmd = this.getCommand("itch");
		cmd.setExecutor(this.healthMgr);
		cmd = this.getCommand("site");
		cmd.setExecutor(this.siteMgr);
		cmd = this.getCommand("setsite");
		cmd.setExecutor(this.siteMgr);
		cmd = this.getCommand("delsite");
		cmd.setExecutor(this.siteMgr);
		cmd = this.getCommand("guild");
		cmd.setExecutor(this.guildMgr);
		cmd = this.getCommand("myguild");
		cmd.setExecutor(this.guildMgr);
		cmd = this.getCommand("party");
		cmd.setExecutor(this.partyMgr);
		cmd = this.getCommand("myparty");
		cmd.setExecutor(this.partyMgr);
		cmd = this.getCommand("anvil");
		cmd.setExecutor(this.ceTradesman);
		cmd = this.getCommand("etable");
		cmd.setExecutor(this.ceTradesman);
		cmd = this.getCommand("tlibrary");
		cmd.setExecutor(this.ceTradesman);
		cmd = this.getCommand("tools");
		cmd.setExecutor(this.ceTradesman);
		cmd = this.getCommand("supplies");
		cmd.setExecutor(this.ceTradesman);
		cmd = this.getCommand("warcry");
		cmd.setExecutor(this.ceTradesman);
		cmd = this.getCommand("workorder");
		cmd.setExecutor(this.workOrderMgr);
		cmd = this.getCommand("wo");
		cmd.setExecutor(this.workOrderMgr);
		cmd = this.getCommand("mob");
		cmd.setExecutor(this.mobMgr);
		cmd = this.getCommand("rpg");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("go");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("tour");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("staff");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("arena");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("setrpg");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("market");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("marketplace");
		cmd.setExecutor(this.moveMgr);
		cmd = this.getCommand("aura");
		cmd.setExecutor(this.auraPlayerMgr);
		cmd.setTabCompleter(this.auraPlayerMgr);
		cmd = this.getCommand("burst");
		cmd.setExecutor(this.soldierMgr);
		cmd = this.getCommand("hoist");
		cmd.setExecutor(this.soldierMgr);
		cmd = this.getCommand("qrpgsync");
		cmd.setExecutor(this.classMgr);
		cmd = this.getCommand("rpgclass");
		cmd.setExecutor(this.classMgr);
		cmd = this.getCommand("binv");
		cmd.setExecutor(this.builderInvMgr);
		cmd.setTabCompleter(this.builderInvMgr);
		cmd = this.getCommand("rank");
		cmd.setExecutor(this.infoMgr);
		cmd = this.getCommand("myrank");
		cmd.setExecutor(this.infoMgr);
		cmd = this.getCommand("myprofile");
		cmd.setExecutor(this.infoMgr);
		cmd = this.getCommand("jobs");
		cmd.setExecutor(this.infoMgr);
		cmd = this.getCommand("signup");
		cmd.setExecutor(this.signupMgr);
		cmd = this.getCommand("myland");
		cmd.setExecutor(this.myTownMgr);
		cmd = this.getCommand("mytown");
		cmd.setExecutor(this.myTownMgr);
		cmd = this.getCommand("myshop");
		cmd.setExecutor(this.myTownMgr);
		cmd = this.getCommand("land");
		cmd.setExecutor(this.myTownMgr);
		cmd = this.getCommand("town");
		cmd.setExecutor(this.myTownMgr);
		cmd = this.getCommand("shop");
		cmd.setExecutor(this.myTownMgr);
		cmd = this.getCommand("item");
		cmd.setExecutor(this.staffMgr);
		cmd = this.getCommand("design");
		cmd.setExecutor(this.designMgr);
		cmd = this.getCommand("di");
		cmd.setExecutor(this.designMgr);
		cmd = this.getCommand("prefix");
		cmd.setExecutor(this.staffMgr);
		cmd = this.getCommand("warn");
		cmd.setExecutor(this.staffMgr);
		cmd = this.getCommand("mtool");
		cmd.setExecutor(this.staffMgr);
		cmd = this.getCommand("inv");
		cmd.setExecutor(this.staffMgr);
		taskIdTasker = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				QfCore.this.cooldownMgr.taskCooldownCheck();
			}
		}, 20L, 20L);
		taskIdTasker = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				QfCore.this.taskLocTrigger();
			}
		}, 10L, 10L);
		taskIdTasker = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				QfCore.this.taskMaintenance();
			}
		}, 6000L, 6000L);
		taskIdBlockPersist = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				QfCore.this.taskBlockPersist();
			}
		}, 18000L, 18000L);
		this.setupRecipes();
		this.setupEconomy();

		this.getLogger().info("Qf Tasker is now on.");
	}

	public void setupEconomy() {
		Plugin vault = this.getServer().getPluginManager().getPlugin("Vault");
		if (vault != null) {
			RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager()
					.getRegistration(Economy.class);
			economy = rsp.getProvider();
			this.getLogger().info("Vault found");
		} else {
			this.getLogger().info("Vault NOT FOUND ============================");
		}

	}

	private void taskMaintenance() {
		this.qSave();
	}

	public void qSave() {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
		this.cooldownMgr.genCooldownConfig();
		this.getLogger().info("Saved Q");
	}

	public void setupTabList() {
	}

	public void doTabList() {
	}

	private void setupRecipes() {
		NamespacedKey key;
		ItemStack item = this.createItem(Material.BOW, 0, "Longbow", false);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Longbow");
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DURABILITY, 2);
		item.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		key = new NamespacedKey(this, "longbow");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { " WS", "WGS", " WS" });
		recipe.setIngredient('S', Material.STRING);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('G', Material.GUNPOWDER);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.IRON_SWORD, 0, "Steel Sword", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Steel");
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DURABILITY, 1);
		key = new NamespacedKey(this, "steel-sword");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "CI ", "CI ", " W " });
		recipe.setIngredient('C', Material.COAL);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('W', Material.STICK);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.IRON_SWORD, 0, "Forged Steel Sword", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Forged Steel");
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DURABILITY, 1);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		key = new NamespacedKey(this, "forged-steel-sword");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "CIC", "CIC", " WS" });
		recipe.setIngredient('C', Material.COAL);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('S', Material.SHEARS);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.IRON_SWORD, 0, "Steel Longsword", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Longsword");
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.KNOCKBACK, 1);
		item.addEnchantment(Enchantment.DURABILITY, 2);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		key = new NamespacedKey(this, "steel-longsword");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "CIC", "III", "GWG" });
		recipe.setIngredient('C', Material.COAL);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('G', Material.GUNPOWDER);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.DIAMOND_SWORD, 0, ChatColor.GRAY + "Lapis Quickblade", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		key = new NamespacedKey(this, "lapis-quickblade");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "lDl", "lDl", " WC" });
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('l', Material.LAPIS_LAZULI);
		recipe.setIngredient('C', Material.SHEARS);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.DIAMOND_SWORD, 0, ChatColor.GRAY + "Lapis Longsword", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.KNOCKBACK, 2);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		item.addEnchantment(Enchantment.DURABILITY, 2);
		key = new NamespacedKey(this, "lapis-longsword");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "lDl", "DDD", "GWG" });
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('l', Material.LAPIS_LAZULI);
		recipe.setIngredient('G', Material.GUNPOWDER);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.DIAMOND_SWORD, 0, ChatColor.GRAY + "Gemmed Quickblade", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 1);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		item.addEnchantment(Enchantment.DURABILITY, 1);
		key = new NamespacedKey(this, "gemmed-quickblade");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { " e ", "LDR", " F " });
		recipe.setIngredient('e', Material.EMERALD);
		recipe.setIngredient('L', Material.LAPIS_BLOCK);
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('F', Material.FISHING_ROD);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.DIAMOND_SWORD, 0, ChatColor.GRAY + "Quick Fireblade", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		item.addEnchantment(Enchantment.DURABILITY, 2);
		key = new NamespacedKey(this, "quick-fireblade-1");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { " Df", " DR", " W " });
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('f', Material.FLINT_AND_STEEL);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.DIAMOND_SWORD, 0, ChatColor.GRAY + "Quick Fireblade", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		key = new NamespacedKey(this, "quick-fireblade-2");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "lDf", "lDR", " W " });
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('f', Material.FLINT_AND_STEEL);
		recipe.setIngredient('l', Material.LAPIS_LAZULI);
		this.getServer().addRecipe(recipe);
		item = this.createItem(Material.DIAMOND_SWORD, 0, ChatColor.GRAY + "Quick Fireblade", false);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Common");
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
		item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		item.addEnchantment(Enchantment.DURABILITY, 3);
		key = new NamespacedKey(this, "quick-fireblade-3");
		recipe = new ShapedRecipe(key, item);
		recipe.shape(new String[] { "lDf", "lDR", " WC" });
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('W', Material.STICK);
		recipe.setIngredient('R', Material.REDSTONE_BLOCK);
		recipe.setIngredient('f', Material.FLINT_AND_STEEL);
		recipe.setIngredient('l', Material.LAPIS_LAZULI);
		recipe.setIngredient('C', Material.SHEARS);
		this.getServer().addRecipe(recipe);
	}

	private void setupPortals() {
		portals.clear();
		Set<String> portalKeys = this.getConfig().getConfigurationSection("portal").getKeys(false);
		this.getLogger().info("found " + portalKeys.size() + " portals in config file");
		String[] portalNames = portalKeys.toArray(new String[portalKeys.size()]);
		String[] var6 = portalNames;
		int var5 = portalNames.length;

		for (int var4 = 0; var4 < var5; ++var4) {
			String name = var6[var4];
			portals.add(name);
		}

		this.getLogger().info("setup portals, " + portals.size() + " found");
	}

	public void onDisable() {
		this.getLogger().info("QfCore closing down...");
	}

	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		if (e.getRawSlot() != e.getSlot())
			return;
		
		String invTitle = ChatColor.stripColor(e.getWhoClicked().getOpenInventory().getTitle());
		Player pUser = (Player) e.getWhoClicked();
		ItemStack item;
		if (invTitle.equalsIgnoreCase("Open Work Orders")) {
			item = e.getCurrentItem();
			boolean success = this.workOrderMgr.handleInvClick(pUser, item);
			if (success) {
				e.setCancelled(true);
				pUser.closeInventory();
			}
		}
		else if (invTitle.equalsIgnoreCase("Legends and Kings Destinations")
				|| invTitle.equalsIgnoreCase("Legends and Kings Tour")
				|| invTitle.equalsIgnoreCase("Legends and Kings Staff")
				|| invTitle.equalsIgnoreCase("Soldier Arenas")) { 
			item = e.getCurrentItem();
			if (item != null && item.getItemMeta() != null) {
				String itemName = item.getItemMeta().getDisplayName();

				this.getLogger().info("Inventory Click: <" + itemName + ">");
				this.moveMgr.handleGoClick(pUser, itemName);
				e.setCancelled(true);
			}
		}
		else if (invTitle.equalsIgnoreCase("Default Enchant Menu")
				|| invTitle.equalsIgnoreCase("Weapon Enchant Menu")
				|| invTitle.equalsIgnoreCase("Helmet Enchant Menu")
				|| invTitle.equalsIgnoreCase("Chestplate Enchant Menu")
				|| invTitle.equalsIgnoreCase("Leggings Enchant Menu")
				|| invTitle.equalsIgnoreCase("Boots Enchant Menu")
				|| invTitle.equalsIgnoreCase("Tool Enchant Menu")) {
			item = e.getCurrentItem();
			if (item != null && item.getItemMeta() != null) {
				String itemName = item.getItemMeta().getDisplayName();

				this.getLogger().info("Inventory Click: <" + itemName + ">");
				this.designMgr.emDispatch(pUser, itemName);
				e.setCancelled(true);
			}
		}
				

	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		this.mobMgr.HandleMobSpawn(event);
	}

	public void buckOFive(Player pTarget, double amt) {
		EconomyResponse er;
		if (economy != null) {
			er = economy.depositPlayer(pTarget, amt);
			if (er.transactionSuccess()) {
				this.msgCaller(pTarget, ChatColor.GREEN + "$" + amt);
			} else {
				this.msgCaller(pTarget, ChatColor.RED + "$" + amt + " FAILED");
			}
		} else {
			this.msgCaller(pTarget, ChatColor.RED + "NO ECO");
			this.setupEconomy();
			er = economy.depositPlayer(pTarget, 1.05D);
			if (er.transactionSuccess()) {
				this.msgCaller(pTarget, ChatColor.GREEN + "$" + amt);
			} else {
				this.msgCaller(pTarget, ChatColor.RED + "$" + amt + " FAILED");
			}
		}

	}

	public double balancePlayer(Player pTarget) {
		if (economy != null) {
			return economy.getBalance(pTarget);
		} else {
			this.getLogger().info("================= Error, NO ECO =======================*+*+*+*+*+*+*+*+");
			this.setupEconomy();
			return economy.getBalance(pTarget);
		}
	}

	public boolean payPlayer(Player pUser, double amt, boolean tellThem) {
		OfflinePlayer pTarget = this.getServer().getOfflinePlayer(pUser.getUniqueId());
		if (pTarget == null) {
			return false;
		} else {
			EconomyResponse er;
			if (economy != null) {
				er = economy.depositPlayer(pUser, amt);
				if (er.transactionSuccess()) {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget,
								ChatColor.GOLD + "You have received " + ChatColor.GREEN + "$" + amt);
					}

					return true;
				} else {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget, ChatColor.RED + "Error - could not deposit "
								+ ChatColor.DARK_RED + "$" + amt + ChatColor.RED + " in your account");
					}

					return false;
				}
			} else {
				this.getLogger().info("================= Error, NO ECO =======================*+*+*+*+*+*+*+*+");
				this.setupEconomy();
				er = economy.depositPlayer(pUser, amt);
				if (er.transactionSuccess()) {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget,
								ChatColor.GOLD + "You have received " + ChatColor.GREEN + "$" + amt);
					}

					return true;
				} else {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget, ChatColor.RED + "Error - could not deposit "
								+ ChatColor.DARK_RED + "$" + amt + ChatColor.RED + " in your account");
					}

					return false;
				}
			}
		}
	}

	public boolean unpayPlayer(Player player, double amt, boolean tellThem) {
		OfflinePlayer pTarget = this.getServer().getOfflinePlayer(player.getUniqueId());
		if (pTarget == null) {
			return false;
		} else {
			EconomyResponse er;
			if (economy != null) {
				er = economy.withdrawPlayer(player, amt);
				if (er.transactionSuccess()) {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget,
								ChatColor.GOLD + "You have paid " + ChatColor.GREEN + "$" + amt);
					}

					return true;
				} else {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget, ChatColor.RED + "Error - could not pay " + ChatColor.DARK_RED
								+ "$" + amt + ChatColor.RED + " from your account");
					}

					return false;
				}
			} else {
				this.getLogger().info("================= Error, NO ECO =======================*+*+*+*+*+*+*+*+");
				this.setupEconomy();
				er = economy.withdrawPlayer(player, amt);
				if (er.transactionSuccess()) {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget,
								ChatColor.GOLD + "You have paid " + ChatColor.GREEN + "$" + amt);
					}

					return true;
				} else {
					if (tellThem && pTarget.isOnline()) {
						this.msgCaller((Player) pTarget, ChatColor.RED + "Error - could not pay " + ChatColor.DARK_RED
								+ "$" + amt + ChatColor.RED + " from your account");
					}

					return false;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().split(" ");
		Player pUser = event.getPlayer();
		String playerName = pUser.getName();
		String cmdName = args[0].toLowerCase();
		if (args[0].equalsIgnoreCase("/op")) {
			if (!playerName.equalsIgnoreCase("wizardwil") && !playerName.equalsIgnoreCase("crossfire1218")) {
				event.setCancelled(true);
				this.msgCaller(pUser, ChatColor.RED + "This command is not availble");
				this.getLogger().info("Stopped player " + playerName + " from using /op");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"mail send wizardwil WARN: /op attempt from " + playerName);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"mail send crossfire1218 WARN: /op attempt from " + playerName);
			}

		} else if (!args[0].startsWith("/bukkit") && !args[0].startsWith("/minecraft") && !args[0].startsWith("/?")) {
			if (args[0].startsWith("/setwarp")) {
				if (pUser.getLocation().getWorld().getName().equalsIgnoreCase("Adventure")) {
					event.setCancelled(true);
					this.msgCaller(pUser, ChatColor.RED + "Warps cannot be set in the Adventure world");
					return;
				}

				if (pUser.getLocation().getWorld().getName().equalsIgnoreCase("world")
						&& !pUser.hasPermission("QfCore.admin")) {
					event.setCancelled(true);
					this.msgCaller(pUser, ChatColor.RED + "Only admins can set warps in the Spawn world");
					return;
				}
			}

			if (args[0].startsWith("/sethome") && pUser.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
				if (!playerName.equalsIgnoreCase("wizardwil") && !playerName.equalsIgnoreCase("crossfire1218")) {
					event.setCancelled(true);
					this.msgCaller(pUser, ChatColor.RED + "You are not permitted to set a home in this world");
				}

			} else if (args[0].startsWith("/sethome")
					&& pUser.getLocation().getWorld().getName().equalsIgnoreCase("Adventure")) {
				if (!pUser.hasPermission("QfCore.builder")) {
					event.setCancelled(true);
					this.msgCaller(pUser, ChatColor.RED + "You are not permitted to set a home in the Adventure world");
				}

			} else {
				this.spyCommands(event, playerName, args);
				switch (cmdName.hashCode()) {
				case -2104352421:
					if (!cmdName.equals("/tphere")) {
					}
					break;
				case -816802874:
					if (!cmdName.equals("/tpahere")) {
					}
					break;
				case 48875:
					if (!cmdName.equals("/tp")) {
					}
					break;
				case 48954:
					if (!cmdName.equals("/wb")) {
					}
					break;
				case 1501574:
					if (!cmdName.equals("/fix")) {
					}
					break;
				case 1513306:
					if (!cmdName.equals("/rpg")) {
					}
					break;
				case 1515206:
					if (!cmdName.equals("/top")) {
					}
					break;
				case 1515222:
					if (!cmdName.equals("/tpa")) {
					}
					break;
				case 46421398:
					if (!cmdName.equals("/back")) {
					}
					break;
				case 46613902:
					if (!cmdName.equals("/home")) {
					}
					break;
				case 46679261:
					if (!cmdName.equals("/jump")) {
					}
					break;
				case 46964838:
					if (!cmdName.equals("/thru")) {
					}
					break;
				case 47047479:
					if (!cmdName.equals("/warp")) {
					}
					break;
				case 444225459:
					if (!cmdName.equals("/tpaccept")) {
					}
					break;
				case 1417730806:
					if (!cmdName.equals("/tlibrary")) {
					}
					break;
				case 1438545405:
					if (!cmdName.equals("/anvil")) {
					}
					break;
				case 1440491345:
					if (!cmdName.equals("/craft")) {
					}
					break;
				case 1444282660:
					if (!cmdName.equals("/guild")) {
					}
					break;
				case 1455208620:
					if (!cmdName.equals("/spawn")) {
					}
					break;
				case 1458888899:
					if (!cmdName.equals("/world")) {
					}
					break;
				case 1764660088:
					if (!cmdName.equals("/etable")) {
					}
					break;
				case 1976661291:
					if (!cmdName.equals("/market")) {
					}
					break;
				case 2072831676:
					if (!cmdName.equals("/marketplace")) {
					}
				}

				if (dungeons.size() != 0) {
					playerName = event.getPlayer().getName();
					Iterator<String> var9 = dungeons.iterator();

					while (var9.hasNext()) {
						String s = (String) var9.next();
						String[] key = s.split(" ");
						if (key[1].equalsIgnoreCase(playerName)) {
							args = event.getMessage().split(" ");
							if (args[0].equalsIgnoreCase("/feed")) {
								event.setCancelled(true);
								this.getLogger().info("Stopped player " + playerName + " from using /feed");
								event.getPlayer().sendMessage("No soup for you!");
								return;
							}
						}
					}

				}
			}
		} else {
			event.setCancelled(true);
			this.msgCaller(pUser, ChatColor.RED + "This command is not availble");
			this.getLogger().info("Stopped player " + playerName + " from using " + args[0]);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"mail send wizardwil WARN: /bukkit attempt from " + playerName);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"mail send crossfire1218 WARN: /bukkit attempt from " + playerName);
		}
	}

	public void spyCommands(PlayerCommandPreprocessEvent event, String playerName, String[] args) {
		String spyColor;
		label185: {
			// decompiler artifacts
			// boolean isChat;
			label200: {
				// boolean isWorldEdit;
				label182: {
					// boolean isHackWatch;
					label181: {
						// isHackWatch = false;
						// boolean isGeneral = false;
						// isChat = false;
						// isWorldEdit = false;
						spyColor = ChatColor.DARK_GRAY + playerName + ": ";
						String var11;
						switch ((var11 = args[0].toLowerCase()).hashCode()) {
						case -1943777068:
							if (var11.equals("//replace")) {
								break label182;
							}
							break;
						case -1854473413:
							if (var11.equals("//rotate")) {
								break label182;
							}
							break;
						case -1525559074:
							if (var11.equals("/creative")) {
								break label181;
							}
							break;
						case -770247032:
							if (var11.equals("/farwand")) {
								break label182;
							}
							break;
						case 1554:
							if (var11.equals("/a")) {
								break label200;
							}
							break;
						case 1558:
							if (var11.equals("/e")) {
								break label200;
							}
							break;
						case 1566:
							if (var11.equals("/m")) {
								break label200;
							}
							break;
						case 48319:
							if (var11.equals("/br")) {
								break label182;
							}
							break;
						case 48321:
							if (var11.equals("/bt")) {
								break label200;
							}
							break;
						case 48414:
							if (var11.equals("/et")) {
								break label200;
							}
							break;
						case 48469:
							if (var11.equals("/gm")) {
								if (args.length <= 1 || !args[1].toLowerCase().startsWith("creative")
										&& !args[1].toLowerCase().startsWith("spectator")
										&& !args[1].toLowerCase().startsWith("1")
										&& !args[1].toLowerCase().startsWith("3")) {
									// isGeneral = true;
								} else {
									// isHackWatch = true;
									spyColor = ChatColor.RED + ">>" + ChatColor.YELLOW + ">> " + ChatColor.WHITE
											+ playerName + ": " + ChatColor.AQUA;
								}
								break label185;
							}
							break;
						case 48875:
							if (var11.equals("/tp")) {
								if (args.length > 3) {
									return;
								}

								// isGeneral = true;
								break label185;
							}
							break;
						case 48957:
							if (var11.equals("/we")) {
								break label182;
							}
							break;
						case 1448496:
							if (var11.equals("//br")) {
								break label182;
							}
							break;
						case 1501057:
							if (var11.equals("/exc")) {
								break label200;
							}
							break;
						case 1501668:
							if (var11.equals("/fly")) {
								break label181;
							}
							break;
						case 1502701:
							if (var11.equals("/god")) {
								break label181;
							}
							break;
						case 1508049:
							if (var11.equals("/mat")) {
								break label182;
							}
							break;
						case 1508594:
							if (var11.equals("/msg")) {
								break label200;
							}
							break;
						case 1515206:
							if (var11.equals("/top")) {
								return;
							}
							break;
						case 44919418:
							if (var11.equals("//sel")) {
								break label182;
							}
							break;
						case 44919426:
							if (var11.equals("//set")) {
								break label182;
							}
							break;
						case 46679261:
							if (var11.equals("/jump")) {
								return;
							}
							break;
						case 46749595:
							if (var11.equals("/mask")) {
								break label182;
							}
							break;
						case 46964838:
							if (var11.equals("/thru")) {
								return;
							}
							break;
						case 47047343:
							if (var11.equals("/wand")) {
								break label182;
							}
							break;
						case 178739127:
							if (var11.equals("//farwand")) {
								break label182;
							}
							break;
						case 215008435:
							if (var11.equals("//paste")) {
								break label182;
							}
							break;
						case 216962653:
							if (var11.equals("//regen")) {
								break label182;
							}
							break;
						case 217827552:
							if (var11.equals("//schem")) {
								break label181;
							}
							break;
						case 218327208:
							if (var11.equals("//stack")) {
								break label182;
							}
							break;
						case 221466121:
							if (var11.equals("//walls")) {
								break label182;
							}
							break;
						case 643445101:
							if (var11.equals("//schematic")) {
								break label181;
							}
							break;
						case 1392035157:
							if (var11.equals("//copy")) {
								break label182;
							}
							break;
						case 1392121421:
							if (var11.equals("//flip")) {
								break label182;
							}
							break;
						case 1392319692:
							if (var11.equals("//mask")) {
								break label182;
							}
							break;
						case 1392333233:
							if (var11.equals("//move")) {
								break label182;
							}
							break;
						case 1392570052:
							if (var11.equals("//undo")) {
								break label182;
							}
							break;
						case 1392617440:
							if (var11.equals("//wand")) {
								break label182;
							}
							break;
						case 1456146313:
							if (var11.equals("/tppos")) {
								return;
							}
							break;
						case 1457001588:
							if (var11.equals("/ungod")) {
								break label181;
							}
							break;
						case 2076507258:
							if (var11.equals("//expand")) {
								break label182;
							}
							break;
						case 2076630266:
							if (var11.equals("//extend")) {
								break label182;
							}
						}

						// isGeneral = true;
						break label185;
					}

					// isHackWatch = true;
					spyColor = ChatColor.RED + ">>" + ChatColor.YELLOW + ">> " + ChatColor.WHITE + playerName + ": "
							+ ChatColor.AQUA;
					break label185;
				}

				// isWorldEdit = true;
				return;
			}

			// isChat = true;
			return;
		}

		String perm = "QfCore.spy.commands";
		String spyMsg = event.getMessage();
		spyMsg = spyColor + ChatColor.stripColor(spyMsg);
		Iterator<? extends Player> var13 = Bukkit.getOnlinePlayers().iterator();

		while (var13.hasNext()) {
			Player np = (Player) var13.next();
			if (np.hasPermission(perm)) {
				this.msgCaller(np, spyMsg);
			}
		}

	}

// unused code
//   private void taskAnnounce() {
//   }
//
//   private void taskMsgAura() {
//      Iterator var4 = auras.iterator();
//
//      while(var4.hasNext()) {
//         String aura = (String)var4.next();
//         String[] args = aura.split(" ");
//         Player pTarget = this.getServer().getPlayer(args[0]);
//         if (pTarget != null) {
//            this.processQfAuraConfig(pTarget, args[1]);
//         }
//      }
//
//   }
//
//   private void doQfEnchant(Player p, ItemStack item) {
//      int level;
//      short duration;
//      PotionEffectType potEffType;
//      label43: {
//         List lore = p.getItemInHand().getItemMeta().getLore();
//         level = this.getQfEnchantLevel(lore, 0);
//         String qfEnch = this.getQfEnchantName(lore, 0);
//         duration = 160;
//         this.getLogger().info("qfench:" + qfEnch + ">>");
//         switch(qfEnch.hashCode()) {
//         case -2139379956:
//            if (qfEnch.equals("Blindness")) {
//               potEffType = PotionEffectType.BLINDNESS;
//               break label43;
//            }
//            break;
//         case -2122237229:
//            if (qfEnch.equals("Hunger")) {
//               potEffType = PotionEffectType.HUNGER;
//               break label43;
//            }
//            break;
//         case -2097758737:
//            if (qfEnch.equals("Illumination")) {
//               potEffType = PotionEffectType.GLOWING;
//               break label43;
//            }
//            break;
//         case -2063047254:
//            if (qfEnch.equals("Levitate")) {
//               potEffType = PotionEffectType.LEVITATION;
//               break label43;
//            }
//            break;
//         case -240682846:
//            if (qfEnch.equals("Disorientation")) {
//               potEffType = PotionEffectType.CONFUSION;
//               break label43;
//            }
//            break;
//         case 83108:
//            if (qfEnch.equals("Shy")) {
//               potEffType = PotionEffectType.INVISIBILITY;
//               level = 35;
//               break label43;
//            }
//            break;
//         case 2493369:
//            if (qfEnch.equals("Poke")) {
//               potEffType = PotionEffectType.HARM;
//               break label43;
//            }
//            break;
//         case 79980042:
//            if (qfEnch.equals("Sloth")) {
//               potEffType = PotionEffectType.SLOW;
//               break label43;
//            }
//         }
//
//         potEffType = PotionEffectType.SPEED;
//      }
//
//      p.addPotionEffect(new PotionEffect(potEffType, duration, level, true));
//   }
//
//   private void giveCoolItems(Player p) {
//      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 9);
//      ItemStack item = this.createItem(Material.CHEST, 0, "Ancient Crate", true);
//      this.addEnchant(item, "Crate", 1);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.IRON_SWORD, 0, "Iron Beast", true);
//      item.addEnchantment(Enchantment.DAMAGE_ALL, 4);
//      this.addEnchant(item, "Blindness", 1);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.GOLDEN_SWORD, 0, ChatColor.BLUE + "Gold Beast", true);
//      this.addEnchant(item, "Blindness", 2);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.COOKED_BEEF, 0, "Elk Steak", false);
//      this.addEnchant(item, "Sloth", 2);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.COOKED_BEEF, 0, "Rotten Elk Steak", false);
//      this.addEnchant(item, "Disorientation", 3);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.COOKED_BEEF, 0, ChatColor.GOLD + "Huge Elk Steak", false);
//      this.addEnchant(item, "Shy", 1);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.COOKED_CHICKEN, 0, "Cheap Takeout", false);
//      this.addEnchant(item, "Hunger", 6);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.STICK, 0, ChatColor.RED + "Basic Mage's Rod", true);
//      this.addEnchant(item, "Poke", 1);
//      inv.addItem(new ItemStack[]{item});
//      item = this.createItem(Material.STICK, 0, ChatColor.YELLOW + "Better " + ChatColor.RED + "Mage's Rod", true);
//      this.addEnchant(item, "Poke", 6);
//      inv.addItem(new ItemStack[]{item});
//      p.openInventory(inv);
//   }
//
//   private void addEnchant(ItemStack item, String name, int level) {
//      ItemMeta itemMeta = item.getItemMeta();
//      List lore = itemMeta.getLore();
//      String sLore = "" + ChatColor.RESET + ChatColor.GRAY + name + " " + this.intToRoman(level);
//      if (lore != null && !this.isEmpty(lore)) {
//         if (!this.hasEnchant(lore, sLore)) {
//            lore.add(sLore);
//            this.getLogger().info("added lore." + sLore);
//         }
//      } else {
//         this.getLogger().info("no lore");
//         List newLore = new ArrayList();
//         newLore.add(sLore);
//         itemMeta.setLore(newLore);
//         this.getLogger().info("added newLore." + sLore);
//      }
//
//      item.setItemMeta(itemMeta);
//   }
//
//   private String getQfEnchant(List lore, int idx) {
//      if (lore == null) {
//         return null;
//      } else {
//         Iterator var5 = lore.iterator();
//         if (var5.hasNext()) {
//            String s = (String)var5.next();
//            String[] slist = s.split(" ");
//            this.getLogger().info("QfEnchatment is " + slist[0] + ":" + this.romanToInt(slist[1]));
//            return s;
//         } else {
//            return null;
//         }
//      }
//   }
//
//   private String getQfEnchantName(List lore, int idx) {
//      if (lore == null) {
//         return "<no lore>";
//      } else {
//         String s = (String)lore.get(idx);
//         s = ChatColor.stripColor(s);
//         this.getLogger().info("getQfEnchantName: " + s + ">>");
//         return s.split(" ")[0];
//      }
//   }
//
//   private int getQfEnchantLevel(List lore, int idx) {
//      if (lore == null) {
//         return 1;
//      } else {
//         String s = (String)lore.get(idx);
//         return this.romanToInt(s.split(" ")[1]);
//      }
//   }
//
//   private boolean hasOurEnchant(List llore) {
//      Iterator var3 = llore.iterator();
//
//      while(var3.hasNext()) {
//         String s = (String)var3.next();
//         if (s.startsWith(ChatColor.RESET + "Blindness")) {
//            return true;
//         }
//
//         if (s.startsWith(ChatColor.RESET + "Rotten")) {
//            return true;
//         }
//      }
//
//      return false;
//   }
//
//   private String intToRoman(int num) {
//      switch(num) {
//      case 1:
//         return "I";
//      case 2:
//         return "II";
//      case 3:
//         return "III";
//      case 4:
//         return "IV";
//      case 5:
//         return "V";
//      case 6:
//         return "VI";
//      case 7:
//         return "VII";
//      case 8:
//         return "VIII";
//      case 9:
//         return "IX";
//      case 10:
//         return "X";
//      case 11:
//         return "XI";
//      case 12:
//         return "XII";
//      case 13:
//         return "XIII";
//      case 14:
//         return "XIV";
//      case 15:
//         return "XV";
//      default:
//         return "?";
//      }
//   }
//
//   private int romanToInt(String num) {
//      switch(num.hashCode()) {
//      case 73:
//         if (num.equals("I")) {
//            return 1;
//         }
//         break;
//      case 86:
//         if (num.equals("V")) {
//            return 5;
//         }
//         break;
//      case 88:
//         if (num.equals("X")) {
//            return 10;
//         }
//         break;
//      case 2336:
//         if (num.equals("II")) {
//            return 2;
//         }
//         break;
//      case 2349:
//         if (num.equals("IV")) {
//            return 4;
//         }
//         break;
//      case 2351:
//         if (num.equals("IX")) {
//            return 9;
//         }
//         break;
//      case 2739:
//         if (num.equals("VI")) {
//            return 6;
//         }
//         break;
//      case 2801:
//         if (num.equals("XI")) {
//            return 11;
//         }
//         break;
//      case 2814:
//         if (num.equals("XV")) {
//            return 15;
//         }
//         break;
//      case 72489:
//         if (num.equals("III")) {
//            return 3;
//         }
//         break;
//      case 84982:
//         if (num.equals("VII")) {
//            return 7;
//         }
//         break;
//      case 86904:
//         if (num.equals("XII")) {
//            return 12;
//         }
//         break;
//      case 86917:
//         if (num.equals("XIV")) {
//            return 14;
//         }
//         break;
//      case 2634515:
//         if (num.equals("VIII")) {
//            return 8;
//         }
//         break;
//      case 2694097:
//         if (num.equals("XIII")) {
//            return 13;
//         }
//      }
//
//      return 1;
//   }
//
//   private boolean isEmpty(List slist) {
//      Iterator var3 = slist.iterator();
//
//      String s;
//      do {
//         if (!var3.hasNext()) {
//            return true;
//         }
//
//         s = (String)var3.next();
//      } while(s == "" || s == null);
//
//      return false;
//   }
//
//   private boolean hasEnchant(List llore, String enchant) {
//      Iterator var4 = llore.iterator();
//
//      while(var4.hasNext()) {
//         String s = (String)var4.next();
//         if (s.startsWith(ChatColor.RESET + enchant + " ")) {
//            return true;
//         }
//      }
//
//      return false;
//   }
//
//   private void unlockCrate(Player player) {
//      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 9);
//   }

	private ItemStack createItem(Material mat, int index, String name, boolean addGlow) {
		ItemStack item = new ItemStack(mat);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}

	private void setHearts(Player pTarget, double health) {
		if (health < 5.0D) {
			health = 5.0D;
		}

		AttributeInstance attribute = pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		attribute.setBaseValue((double) health);
		pTarget.setHealth(health);
	}
// unused code
//   private void applyQfEffect(Player pTarget, String cls) {
//      String[] slCls = cls.split(" ");
//      int level;
//      int duration;
//      PotionEffect pe;
//      String var10;
//      switch((var10 = slCls[0]).hashCode()) {
//      case -2094862914:
//         if (!var10.equals("aqualung")) {
//            return;
//         }
//         break;
//      case 3198440:
//         if (var10.equals("heal")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.HEAL, duration * 20, level);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 3273774:
//         if (var10.equals("jump")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.JUMP, duration * 20, level);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 98357969:
//         if (!var10.equals("gills")) {
//            return;
//         }
//         break;
//      case 108392509:
//         if (var10.equals("regen")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.REGENERATION, duration * 20, level);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 109532714:
//         if (var10.equals("sloth")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.SLOW, duration * 20, level);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 109641799:
//         if (var10.equals("speed")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.SPEED, duration * 20, level);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 280523342:
//         if (var10.equals("gravity")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.JUMP, duration * 20, -20);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 761310338:
//         if (var10.equals("disorientation")) {
//            level = Integer.parseInt(slCls[1]);
//            duration = Integer.parseInt(slCls[2]);
//            pe = new PotionEffect(PotionEffectType.CONFUSION, duration * 20, level);
//            pTarget.addPotionEffect(pe);
//         }
//
//         return;
//      case 1439257549:
//         if (var10.equals("autofeed")) {
//            pTarget.setFoodLevel(20);
//         }
//
//         return;
//      case 1439317015:
//         if (var10.equals("autoheal")) {
//            pTarget.setHealth(pTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
//         }
//
//         return;
//      default:
//         return;
//      }
//
//      level = Integer.parseInt(slCls[1]);
//      duration = Integer.parseInt(slCls[2]);
//      pe = new PotionEffect(PotionEffectType.WATER_BREATHING, duration * 20, level);
//      pTarget.addPotionEffect(pe);
//   }

	public void deactivatePlayerAuraNow(Player pUser, Player pTarget) {
		this.auraPlayerMgr.deactivatePlayerAuraNow(pUser, pTarget);
	}

	private void taskBlockPersist() {
		Location loc = new Location(this.getServer().getWorld("world"), -3892.0D, 30.0D, -231.0D);
		Block block = loc.getBlock();
		if (block.isEmpty()) {
			block.setType(Material.ANVIL, false);
		}

		loc = new Location(this.getServer().getWorld("world"), -3889.0D, 30.0D, -231.0D);
		this.getLogger().info("block persist check");
		block = loc.getBlock();
		if (block.isEmpty()) {
			block.setType(Material.ANVIL, false);
		}

	}

	private void taskLocTrigger() {
		List<QfTriggerLoc> tLocs = new ArrayList<QfTriggerLoc>();
		List<QfTriggerLoc> tMgrLocs = this.auraBlockMgr.getTriggerLocs();
		if (tMgrLocs != null) {
			tLocs.addAll(tMgrLocs);
		}

		for(QfTriggerLoc trigger : tLocs) {
			if (trigger.location == null) {
				continue;
			}

			if (trigger.location.getWorld() == null) {
				World world = this.getServer().getWorld(trigger.worldName);
				if (world != null) {
					trigger.location.setWorld(world);
				}
				else continue;
			}

			for(Player player : Bukkit.getOnlinePlayers()) {
				if (player.getWorld().equals(trigger.location.getWorld())) {
					Location locUser = player.getLocation();
					Double dist = trigger.location.distance(locUser);

					boolean withinRadius = dist <= trigger.trigRadius;
					boolean passesSneakCheck = trigger.specialSneak && player.isSneaking();
					boolean canTrigger = !trigger.hasSpecial || passesSneakCheck;
					
					if (withinRadius && canTrigger) {
						trigger.mitem.doTriggerAction(player, dist);
					}
				}
			}
		}

		this.taskLocTriggerPortals();
	}

	private void taskLocTriggerPortals() {
		Iterator<String> var22 = portals.iterator();

		label143: while (true) {
			Location locPortal;
			String[] args;
			String destRef;
			boolean hasSpecial;
			boolean specialSneak;
			boolean hasPermAdd;
			boolean hasPermRemove;
			String[] permsAdd;
			String[] permsRemove;
			Double portalRadius;
			Iterator<String> var24;
			do {
				String val;
				String conPath;
				String portalName;
				do {
					do {
						if (!var22.hasNext()) {
							return;
						}

						portalName = (String) var22.next();
						val = null;
						conPath = "portal." + portalName + ".active";
					} while (!this.getConfig().contains(conPath));

					val = this.getConfig().getString(conPath);
				} while (!val.equalsIgnoreCase("all"));

				conPath = "portal." + portalName + ".outbound";
				val = this.getConfig().getString(conPath);
				args = val.split(" ");
				locPortal = new Location(this.getServer().getWorld(args[0]), Double.parseDouble(args[1]),
						Double.parseDouble(args[3]), Double.parseDouble(args[2]));
				portalRadius = Double.parseDouble(args[4]);
				conPath = "portal." + portalName + ".permadd";
				hasPermAdd = false;
				permsAdd = null;
				if (this.getConfig().contains(conPath)) {
					val = this.getConfig().getString(conPath);
					if (val != null && val != "") {
						hasPermAdd = true;
						permsAdd = val.split(", ");
					}
				}

				conPath = "portal." + portalName + ".permremove";
				hasPermRemove = false;
				permsRemove = null;
				if (this.getConfig().contains(conPath)) {
					val = this.getConfig().getString(conPath);
					if (val != null && val != "") {
						hasPermRemove = true;
						permsRemove = val.split(", ");
					}
				}

				conPath = "portal." + portalName + ".special";
				specialSneak = false;
				hasSpecial = false;
				if (this.getConfig().contains(conPath)) {
					hasSpecial = true;
					specialSneak = this.getConfig().getString(conPath).equalsIgnoreCase("sneak");
				}

				conPath = "portal." + portalName + ".dest";
				destRef = conPath;
				val = this.getConfig().getString(conPath);
				args = val.split(" ");
				int rnum = 0;
				// decompiler artifact
				// String debugStr = "none";
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("random")) {
						destRef = "portal." + portalName + ".destrandom";
						List<String> destList = this.getConfig().getStringList(destRef);
						int rtot = 0;
						rnum = (int) (Math.random() * 100.0D) + 1;
						var24 = destList.iterator();

						while (var24.hasNext()) {
							String destEntry = var24.next();
							String[] destInd = destEntry.split(" ");
							rtot += Integer.parseInt(destInd[0]);
							if (rnum <= rtot) {
								destRef = destInd[1];
								val = this.getConfig().getString(destRef);
								if (val != null) {
									args = val.split(" ");
									(new StringBuilder("R ")).append(rnum).append(" found destination: ")
											.append(destRef).toString();
								} else {
									this.getLogger().info("X99 - random dest [[" + destRef + "]]");
									this.getLogger().info("X99 - val <<" + val + ">>");
									this.getLogger().info("X99 - args ==" + args + "==");
								}
								break;
							}
						}
					} else {
						destRef = args[0];
						val = this.getConfig().getString(destRef);
						args = val.split(" ");
						(new StringBuilder("static ")).append(destRef).toString();
					}
				}
			} while (destRef == null);

			String teleMessage = this.getConfig().getString(destRef + "message");
			Iterator<? extends Player> playerList = Bukkit.getOnlinePlayers().iterator();

			while (true) {
				Location locUser;
				Player np;
				do {
					do {
						do {
							if (!playerList.hasNext()) {
								continue label143;
							}

							np = playerList.next();
						} while (!np.getWorld().equals(locPortal.getWorld()));

						locUser = np.getLocation();
					} while (!(locPortal.distance(locUser) <= portalRadius));
				} while (hasSpecial && (!specialSneak || !np.isSneaking()));

				if (args.length > 5) {
					if (args[5].equalsIgnoreCase("P")) {
						locPortal.setYaw(np.getLocation().getYaw());
					} else {
						locPortal.setYaw(Float.parseFloat(args[5]) - 180.0F);
					}

					if (args[6].equalsIgnoreCase("P")) {
						locPortal.setPitch(np.getLocation().getPitch());
					} else {
						locPortal.setPitch(Float.parseFloat(args[6]));
					}
				}

				np.teleport(locPortal, TeleportCause.PLUGIN);
				String perm;
				int var26;
				int var27;
				String[] var28;
				if (hasPermAdd && permsAdd != null) {
					var28 = permsAdd;
					var27 = permsAdd.length;

					for (var26 = 0; var26 < var27; ++var26) {
						perm = var28[var26];
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + np.getName() + " add " + perm);
						this.getLogger().info("adding perm <" + perm + ">");
						if (perm.equalsIgnoreCase("-essentials.fly")) {
							this.getLogger().info("setting fly false for player " + np.getDisplayName());
							np.setFlying(false);
							np.setAllowFlight(false);
						}
					}
				}

				if (hasPermRemove && permsRemove != null) {
					var28 = permsRemove;
					var27 = permsRemove.length;

					for (var26 = 0; var26 < var27; ++var26) {
						perm = var28[var26];
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + np.getName() + " remove " + perm);
					}
				}

				if (teleMessage != null) {
					np.sendMessage(ChatColor.translateAlternateColorCodes('&', teleMessage));
				}
			}
		}
	}

	public void msgNearbyPlayers(Player pTarget, String msg, PotionEffect eff, Sound sound, double radius) {
		Location locTarget = pTarget.getLocation();
		Iterator<? extends Player> var11 = Bukkit.getOnlinePlayers().iterator();

		while (var11.hasNext()) {
			Player np = var11.next();
			if (!np.equals(pTarget) && np.getWorld().equals(locTarget.getWorld())) {
				Location locUser = np.getLocation();
				Double dist = locTarget.distance(locUser);
				if (dist <= radius) {
					this.msgCaller(np, msg);
					if (eff != null) {
						np.addPotionEffect(eff);
					}

					if (sound != null) {
						np.playSound(np.getLocation(), sound, 10.0F, 1.0F);
					}
				}
			}
		}

	}
// unused code
//   private void processQfAuraConfig(Player pUser, String cls) {
//      List lCls = this.getConfig().getStringList(cls);
//      boolean self = false;
//      Location locUser = pUser.getLocation();
//      double auraDist = 10.0D;
//      Iterator var15 = Bukkit.getOnlinePlayers().iterator();
//
//      label83:
//      while(true) {
//         Player np;
//         do {
//            do {
//               if (!var15.hasNext()) {
//                  return;
//               }
//
//               np = (Player)var15.next();
//            } while(!np.getWorld().equals(pUser.getWorld()));
//         } while(!(np.getLocation().distance(locUser) <= auraDist));
//
//         Iterator var17 = lCls.iterator();
//
//         while(true) {
//            while(true) {
//               if (!var17.hasNext()) {
//                  continue label83;
//               }
//
//               String s = (String)var17.next();
//               String[] slCls = s.split(" ");
//               String var18;
//               switch((var18 = slCls[0]).hashCode()) {
//               case -2094862914:
//                  if (!var18.equals("aqualung")) {
//                     continue;
//                  }
//                  break;
//               case -9888733:
//                  if (var18.equals("className")) {
//                     String var9 = slCls[1];
//                  }
//                  continue;
//               case 3198440:
//                  if (!var18.equals("heal")) {
//                     continue;
//                  }
//                  break;
//               case 3273774:
//                  if (!var18.equals("jump")) {
//                     continue;
//                  }
//                  break;
//               case 3526476:
//                  if (var18.equals("self")) {
//                     self = slCls[1].equalsIgnoreCase("true");
//                  }
//                  continue;
//               case 98357969:
//                  if (!var18.equals("gills")) {
//                     continue;
//                  }
//                  break;
//               case 108392509:
//                  if (!var18.equals("regen")) {
//                     continue;
//                  }
//                  break;
//               case 109532714:
//                  if (!var18.equals("sloth")) {
//                     continue;
//                  }
//                  break;
//               case 109641799:
//                  if (!var18.equals("speed")) {
//                     continue;
//                  }
//                  break;
//               case 280523342:
//                  if (!var18.equals("gravity")) {
//                     continue;
//                  }
//                  break;
//               case 761310338:
//                  if (!var18.equals("disorientation")) {
//                     continue;
//                  }
//                  break;
//               case 1439257549:
//                  if (!var18.equals("autofeed")) {
//                     continue;
//                  }
//                  break;
//               case 1439317015:
//                  if (!var18.equals("autoheal")) {
//                     continue;
//                  }
//                  break;
//               default:
//                  continue;
//               }
//
//               if (np.equals(pUser)) {
//                  if (self) {
//                     this.applyQfEffect(np, s);
//                  }
//               } else {
//                  this.applyQfEffect(np, s);
//               }
//            }
//         }
//      }
//   }

	private void processQfClass(Player pTarget, String cls, Player pUser) {
		String[] slCls = cls.split(" ");
		String var7;
		switch ((var7 = slCls[0]).hashCode()) {
		case -1221256979:
			if (var7.equals("hearts")) {
				double dVal = Double.parseDouble(slCls[1]);
				this.setHearts(pTarget, dVal);
			}
			break;
		case -9888733:
			if (var7.equals("className")) {
				pTarget.sendMessage(ChatColor.GOLD + "Emperor Cross " + ChatColor.GREEN
						+ " bestows upon you the rank of " + ChatColor.YELLOW + slCls[1]);
				if (pUser != null) {
					pUser.sendMessage(ChatColor.GOLD + "Player " + ChatColor.GREEN + pTarget.getName() + ChatColor.GOLD
							+ " has been given the rank of " + ChatColor.YELLOW + slCls[1]);
				} else {
					this.getServer().getConsoleSender()
							.sendMessage(ChatColor.YELLOW + "[QfCore].qfclass " + ChatColor.RESET + "Player "
									+ ChatColor.GREEN + pTarget.getName() + ChatColor.RESET
									+ " has been given the rank of " + ChatColor.GREEN + slCls[1]);
				}
			}
		}

	}

	private void processQfClassConfig(Player pTarget, String cls, Player pUser) {
		List<String> lCls = this.getConfig().getStringList(cls);
		Iterator<String> var6 = lCls.iterator();

		while (var6.hasNext()) {
			String s = (String) var6.next();
			this.processQfClass(pTarget, s, pUser);
		}

	}
// unused code
//   private void DispatchNpcCommands(Player pUser, CommandSender sender, Command cmd, String label, String[] args) {
//      Player pTarget = null;
//      this.msgCaller(pUser, "Going to try");
//      pTarget = pUser;
//      if (args.length == 1) {
//         pTarget = this.getServer().getPlayer(args[0]);
//      }
//
//      if (pTarget == null) {
//         this.msgCaller(pUser, "Could not locate player");
//      } else {
//         this.msgCaller(pUser, "World: " + pTarget.getWorld().getName());
//         Entity zomb = pTarget.getWorld().spawnEntity(pTarget.getLocation(), EntityType.ZOMBIE);
//         this.msgCaller(pUser, "Created NPC");
//      }
//
//   }

	protected void msgCaller(Player pUser, String msg) {
		if (pUser == null) {
			this.getLogger().info(ChatColor.stripColor(msg));
		} else {
			pUser.sendMessage(msg);
		}

	}

	public boolean chatCommand2(CommandSender sender, Command cmd, String label, String[] args) {
		// decompiler artifact
		// Player pTarget = null;
		Player pUser = null;
		if (args.length == 0) {
			return true;
		} else {
			boolean isPlayer = sender instanceof Player;
			String playerName;
			if (isPlayer) {
				pUser = (Player) sender;
				playerName = pUser.getPlayerListName();
			} else {
				playerName = ChatColor.WHITE + "[Console]";
			}

			String perm;
			ChatColor chatColor;
			label84: {
				String cmdName = cmd.getName().toLowerCase();
				switch (cmdName.hashCode()) {
				case 97:
					if (!cmdName.equals("a")) {
						return false;
					}

					perm = "QfCore.chat.admin";
					chatColor = ChatColor.AQUA;
					break label84;
				case 101:
					if (!cmdName.equals("e")) {
						return false;
					}
					break;
				case 103:
					if (!cmdName.equals("g")) {
						return false;
					}

					perm = "Qrpg.chat.guild";
					chatColor = ChatColor.GOLD;
					break label84;
				case 109:
					if (!cmdName.equals("m")) {
						return false;
					}

					perm = "QfCore.chat.moderator";
					chatColor = ChatColor.YELLOW;
					break label84;
				case 112:
					if (!cmdName.equals("p")) {
						return false;
					}

					perm = "Qrpg.chat.party";
					chatColor = ChatColor.GREEN;
					break label84;
				case 3154:
					if (!cmdName.equals("bt")) {
						return false;
					}

					perm = "QfCore.chat.builder";
					chatColor = ChatColor.GREEN;
					break label84;
				case 3247:
					if (!cmdName.equals("et")) {
						return false;
					}

					perm = "QfCore.chat.builder.spanish";
					chatColor = ChatColor.RED;
					break label84;
				case 3309:
					if (!cmdName.equals("gt")) {
						return false;
					}

					perm = "Qrpg.chat.guildtalk";
					chatColor = ChatColor.GOLD;
					break label84;
				case 100880:
					if (!cmdName.equals("exc")) {
						return false;
					}
					break;
				default:
					return false;
				}

				perm = "QfCore.chat.executive";
				chatColor = ChatColor.LIGHT_PURPLE;
			}

			String chatMsg = "";
			int len = args.length;

			for (int i = 0; i < len; ++i) {
				chatMsg = chatMsg + " " + args[i];
			}

			chatMsg = playerName + ChatColor.WHITE + ":" + chatColor + ChatColor.stripColor(chatMsg);
			Iterator<? extends Player> var16 = Bukkit.getOnlinePlayers().iterator();

			while (var16.hasNext()) {
				Player np = (Player) var16.next();
				if (np.hasPermission(perm)) {
					this.msgCaller(np, chatMsg);
				}
			}

			return true;
		}
	}

	public boolean chatCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// decompiler artifact
		// Player pTarget = null;
		Player pUser = null;
		if (args.length == 0) {
			return true;
		} else {
			boolean isPlayer = sender instanceof Player;
			String playerName;
			if (isPlayer) {
				pUser = (Player) sender;
				playerName = pUser.getPlayerListName();
			} else {
				playerName = ChatColor.WHITE + "[Console]";
			}

			String cmdName = cmd.getName().toLowerCase();
			String chatMsg = "";
			int len = args.length;

			for (int i = 0; i < len; ++i) {
				chatMsg = chatMsg + " " + args[i];
			}

			chatMsg = ChatColor.stripColor(chatMsg);
			return this.sendChat(pUser, cmdName, playerName, chatMsg);
		}
	}

	public boolean sendChat(Player pUser, String chatChannel, String senderName, String chatMsg) {
		String perm;
		ChatColor chatColor;
		label90: {
			this.getLogger().info("sendChat (" + chatChannel + "): " + senderName + " -- " + chatMsg);
			switch (chatChannel.hashCode()) {
			case 97:
				if (chatChannel.equals("a")) {
					perm = "QfCore.chat.admin";
					chatColor = ChatColor.AQUA;
					break label90;
				}

				return false;
			case 101:
				if (!chatChannel.equals("e")) {
					return false;
				}
				break;
			case 103:
				if (chatChannel.equals("g")) {
					String guildName = this.guildMgr.getPlayerGuildName(pUser);
					return this.sendGuildChat(pUser, guildName, senderName, chatMsg);
				}

				return false;
			case 109:
				if (chatChannel.equals("m")) {
					perm = "QfCore.chat.moderator";
					chatColor = ChatColor.YELLOW;
					break label90;
				}

				return false;
			case 112:
				if (chatChannel.equals("p")) {
					String partyName = this.partyMgr.getPlayerPartyId(pUser);
					return this.sendPartyChat(pUser, partyName, senderName, chatMsg);
				}

				return false;
			case 3154:
				if (chatChannel.equals("bt")) {
					perm = "QfCore.chat.builder";
					chatColor = ChatColor.GREEN;
					break label90;
				}

				return false;
			case 3247:
				if (chatChannel.equals("et")) {
					perm = "QfCore.chat.builder.spanish";
					chatColor = ChatColor.RED;
					break label90;
				}

				return false;
			case 3695:
				if (chatChannel.equals("tc")) {
					perm = "Qrpg.tour.receive";
					chatColor = ChatColor.LIGHT_PURPLE;
					break label90;
				}

				return false;
			case 100880:
				if (!chatChannel.equals("exc")) {
					return false;
				}
				break;
			default:
				return false;
			}

			perm = "QfCore.chat.executive";
			chatColor = ChatColor.LIGHT_PURPLE;
		}

		String msg = senderName + ChatColor.WHITE + ":" + chatColor + ChatColor.stripColor(chatMsg);
		this.getServer().getConsoleSender().sendMessage(msg);
		Iterator<? extends Player> var12 = Bukkit.getOnlinePlayers().iterator();

		while (var12.hasNext()) {
			Player np = var12.next();
			if (np.hasPermission(perm)) {
				this.msgCaller(np, msg);
			}
		}

		return true;
	}

	public boolean sendGuildChat(Player pUser, String guildName, String senderName, String chatMsg) {
		ChatColor chatColor = ChatColor.GOLD;
		String msg;
		if (pUser == null) {
			if (chatMsg == null) {
				msg = senderName;
			} else {
				msg = this.guildMgr.lkgm + ChatColor.WHITE + " - " + chatColor + ChatColor.stripColor(chatMsg);
			}
		} else {
			String playerRank = this.guildMgr.getPlayerGuildRankAbrv(pUser);
			msg = senderName + playerRank + chatColor + ChatColor.stripColor(chatMsg);
		}

		this.getServer().getConsoleSender().sendMessage(msg);
		Iterator<? extends Player> var10 = Bukkit.getOnlinePlayers().iterator();

		while (var10.hasNext()) {
			Player np = var10.next();
			if (np != null) {
				String playerGuild = this.guildMgr.getPlayerGuildName(np);
				if (playerGuild != null && playerGuild.equalsIgnoreCase(guildName)) {
					this.msgCaller(np, msg);
				}
			}
		}

		return true;
	}

	public boolean sendGuildChatColorSender(String guildName, String senderName, String chatMsg) {
		ChatColor chatColor = ChatColor.GOLD;
		String msg = senderName + ChatColor.WHITE + ":" + chatColor + ChatColor.stripColor(chatMsg);
		this.getServer().getConsoleSender().sendMessage(msg);
		Iterator<? extends Player> var8 = Bukkit.getOnlinePlayers().iterator();

		while (var8.hasNext()) {
			Player np = var8.next();
			if (np != null) {
				String playerGuild = this.guildMgr.getPlayerGuildName(np);
				if (playerGuild != null && playerGuild.equalsIgnoreCase(guildName)) {
					this.msgCaller(np, msg);
				}
			}
		}

		return true;
	}

	public boolean sendPartyChat(Player pUser, String partyId, String senderName, String chatMsg) {
		ChatColor chatColor = ChatColor.GREEN;
		String msg;
		if (pUser == null) {
			if (chatMsg == null) {
				msg = senderName;
			} else {
				msg = this.partyMgr.lkgm + ChatColor.WHITE + " - " + chatColor + ChatColor.stripColor(chatMsg);
			}
		} else {
			String playerRank = this.partyMgr.getPlayerPartyRankAbrv(pUser);
			msg = senderName + playerRank + chatColor + ChatColor.stripColor(chatMsg);
		}

		this.getServer().getConsoleSender().sendMessage(msg);
		Iterator<? extends Player> var10 = Bukkit.getOnlinePlayers().iterator();

		while (var10.hasNext()) {
			Player np = var10.next();
			if (np != null) {
				String playerGuild = this.partyMgr.getPlayerPartyId(np);
				if (playerGuild != null && playerGuild.equalsIgnoreCase(partyId)) {
					this.msgCaller(np, msg);
				}
			}
		}

		return true;
	}

	public boolean sendPartyChatColorSender(String partyId, String senderName, String chatMsg) {
		ChatColor chatColor = ChatColor.GOLD;
		String msg = senderName + ChatColor.WHITE + ":" + chatColor + ChatColor.stripColor(chatMsg);
		this.getServer().getConsoleSender().sendMessage(msg);
		Iterator<? extends Player> var8 = Bukkit.getOnlinePlayers().iterator();

		while (var8.hasNext()) {
			Player np = var8.next();
			if (np != null) {
				String playerParty = this.partyMgr.getPlayerPartyId(np);
				if (playerParty != null && playerParty.equalsIgnoreCase(partyId)) {
					this.msgCaller(np, msg);
				}
			}
		}

		return true;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		Player pTarget = null;
		Player pUser = null;
		// decompiler artifact
		// int nextarg = false;
		boolean isPlayer = sender instanceof Player;
		if (isPlayer) {
			pUser = (Player) sender;
			p = (Player) sender;
		}

		String cmdName = cmd.getName().toLowerCase();
		switch (cmdName.hashCode()) {
		case -648566315:
			if (cmdName.equals("auramgr")) {
				return this.auraMgr.onCommand(sender, cmd, label, args);
			}
			break;
		case 93179743:
			if (cmdName.equals("aurab")) {
				return this.auraBlockMgr.onCommand(sender, cmd, label, args);
			}
			break;
		case 107880814:
			if (cmdName.equals("qsave")) {
				this.qSave();
				return true;
			}
			break;
		case 1368946914:
			if (cmdName.equals("aurabadd")) {
				return this.auraBlockMgr.onCommand(sender, cmd, label, args);
			}
			break;
		case 1368963291:
			if (cmdName.equals("aurabrem")) {
				return this.auraBlockMgr.onCommand(sender, cmd, label, args);
			}
		}

		if (cmdName.equalsIgnoreCase("qfpong")) {
			if (isPlayer) {
				p = (Player) sender;
				p.sendMessage(ChatColor.GOLD + "[QfCore] " + ChatColor.GREEN + "says hello <"
						+ this.getConfig().getString("testmsg") + ">");
			}

			this.getLogger().info("QfCore says hello");
			this.getConfig().set("testmsg", "Hello World");
			return true;
		} else if (!cmdName.equalsIgnoreCase("a") && !cmdName.equalsIgnoreCase("m") && !cmdName.equalsIgnoreCase("e")
				&& !cmdName.equalsIgnoreCase("exc") && !cmdName.equalsIgnoreCase("bt")
				&& !cmdName.equalsIgnoreCase("et") && !cmdName.equalsIgnoreCase("gt") && !cmdName.equalsIgnoreCase("g")
				&& !cmdName.equalsIgnoreCase("p")) {
			if (cmdName.equalsIgnoreCase("qann")) {
				try {
					this.numAnnouce(Integer.parseInt(args[0]));
				}
				catch (Exception e) {
					sender.sendMessage("Must include a number as the first argument");
				}
				return true;
			} else if (cmdName.equalsIgnoreCase("qfreload")) {
				this.reloadConfig();
				this.auraMgr.reloadConfig();
				this.auraBlockMgr.reloadConfig();
				this.guildMgr.reloadConfig();
				if (isPlayer) {
					p = (Player) sender;
					p.sendMessage(ChatColor.GOLD + "[QfCore] " + ChatColor.GREEN + "reloaded config files.");
				}

				this.getLogger().info("Reloaded config files");
				this.setupPortals();
				this.auraMgr.loadMgr(this);
				this.auraBlockMgr.loadMgr(this);
				this.guildMgr.loadMgr(this);
				this.readConfigAnc();
				return true;
			} else if (cmdName.equalsIgnoreCase("qfnpc")) {
				return true;
			} else {
				if (cmdName.equalsIgnoreCase("qftasker")) {
					if (taskIdTasker != -1) {
						this.getServer().getScheduler().cancelTask(taskIdTasker);
						taskIdTasker = -1;
						this.getLogger().info("Qf Tasker is now off.");
					} else {
						taskIdTasker = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
							public void run() {
								QfCore.this.taskLocTrigger();
							}
						}, 10L, 10L);
						this.getLogger().info("Qf Tasker is now on.");
					}
				}

				Location locUser;
				Location locTarget;
				Double dist;
				String dispStr;
				if (cmdName.equalsIgnoreCase("qfnear")) {
					if (isPlayer) {
						p = (Player) sender;
						int radius;
						if (args.length == 1) {
							radius = Integer.parseInt(args[0]);
						} else {
							radius = 150;
						}

						locUser = p.getLocation();
						dispStr = "";
						Iterator<? extends Player> var33 = Bukkit.getOnlinePlayers().iterator();

						while (var33.hasNext()) {
							Player np = var33.next();
							if (np.getWorld().equals(p.getWorld()) && !pUser.equals(np)) {
								locTarget = np.getLocation();
								dist = locTarget.distance(locUser);
								if (dist.intValue() <= radius) {
									dispStr = dispStr + np.getDisplayName() + ChatColor.WHITE + " (" + dist.intValue()
											+ "m " + (locTarget.getZ() < locUser.getZ() ? "N" : "S")
											+ (locTarget.getX() < locUser.getX() ? "W" : "E") + ")\n";
								}
							}
						}

						if (dispStr == "") {
							dispStr = ChatColor.GOLD + "There are no other players within " + ChatColor.WHITE + radius
									+ "m";
						} else {
							dispStr = ChatColor.GOLD + "Nearby players:" + ChatColor.RESET + "\n" + dispStr;
						}

						p.sendMessage(ChatColor.GOLD + dispStr);
					} else {
						this.getLogger().info("Cannot use the qfnear command from console");
					}

					return true;
				} else if (cmdName.equalsIgnoreCase("qfface")) {
					if (isPlayer) {
						p = (Player) sender;
						pTarget = this.getServer().getPlayer(args[0]);
						if (pTarget.getWorld().equals(p.getWorld())) {
							locUser = p.getLocation();
							locTarget = pTarget.getLocation();
							double yaw = (double) locUser.getYaw();
							double pitch = (double) locUser.getPitch();
							double dx = locTarget.getX() - locUser.getX();
							double dy = locTarget.getY() - locUser.getY();
							double dz = locTarget.getZ() - locUser.getZ();
							if (dx != 0.0D) {
								if (dx < 0.0D) {
									yaw = 4.71238898038469D;
								} else {
									yaw = 1.5707963267948966D;
								}

								yaw -= Math.atan(dz / dx);
							} else if (dz < 0.0D) {
								yaw = 3.141592653589793D;
							}

							dist = Math.sqrt(Math.pow(dx, 2.0D) + Math.pow(dz, 2.0D));
							pitch = -Math.atan(dy / dist);
							locTarget = p.getLocation();
							locTarget.setPitch((float) (pitch * 180.0D / 3.1415927410125732D));
							locTarget.setYaw((float) (-yaw * 180.0D / 3.1415927410125732D));
							p.teleport(locTarget);
							p.sendMessage(ChatColor.GOLD + "You are now looking towards " + pTarget.getDisplayName()
									+ ChatColor.WHITE + " (" + dist.intValue() + "m)");
						} else {
							p.sendMessage(ChatColor.GOLD + "Player " + pTarget.getDisplayName() + ChatColor.WHITE
									+ " is not in this world.");
						}
					} else {
						this.getLogger().info("Cannot use the qfface command from console");
					}

					return true;
				} else {
					String className;
					if (cmdName.equalsIgnoreCase("qfdungeon")) {
						if (args.length != 3) {
							p.sendMessage(
									ChatColor.GOLD + "[qfdungeon] " + ChatColor.RESET + " wrong number of arguments");
							return false;
						} else {
							label254: {
								label296: {
									boolean doAdd;
									label252: {
										label251: {
											String var32;
											switch ((var32 = args[0]).hashCode()) {
											case -934610812:
												if (!var32.equals("remove")) {
													break label254;
												}
												break;
											case 43:
												if (!var32.equals("+")) {
													break label254;
												}
												break label251;
											case 45:
												if (!var32.equals("-")) {
													break label254;
												}
												break;
											case 48:
												if (!var32.equals("0")) {
													break label254;
												}
												break label296;
											case 97:
												if (!var32.equals("a")) {
													break label254;
												}
												break label251;
											case 114:
												if (!var32.equals("r")) {
													break label254;
												}
												break;
											case 96417:
												if (!var32.equals("add")) {
													break label254;
												}
												break label251;
											case 3387192:
												if (!var32.equals("none")) {
													break label254;
												}
												break label296;
											default:
												break label254;
											}

											doAdd = false;
											break label252;
										}

										doAdd = true;
									}

									className = args[1];
									if (!this.getConfig().contains(className)) {
										if (isPlayer) {
											pUser.sendMessage(
													ChatColor.GOLD + "qfdungeon " + ChatColor.RED + " no dungeon named "
															+ ChatColor.WHITE + className + ChatColor.RED + " found.");
										} else {
											this.getLogger().info(
													"No config entry found for '" + className + "' in config.yml");
										}

										return true;
									}

									pTarget = this.getServer().getPlayer(args[2]);
									if (pTarget != null) {
										dispStr = className + " " + pTarget.getName();
										if (doAdd) {
											if (dungeons.contains(dispStr)) {
												this.getLogger().info("player already added to dungeon");
												return true;
											}

											dungeons.add(dispStr);
											this.getLogger().info("player added to dungeon");
										} else {
											if (dungeons.contains(dispStr)) {
												dungeons.remove(dispStr);
												this.getLogger().info("player removed from dungeon");
												return true;
											}

											dungeons.add(dispStr);
											this.getLogger().info("player is not in that dungeon");
										}

										this.processQfClassConfig(pTarget, className, p);
									}

									return true;
								}

								dungeons.clear();
								return true;
							}

							dispStr = ChatColor.GOLD + "qfdungeon: " + ChatColor.RED
									+ "Invalid first arg. available options: add/a/+/remove/r/-";
							if (isPlayer) {
								pUser.sendMessage(dispStr);
							} else {
								this.getLogger().info(ChatColor.stripColor(dispStr));
							}

							return false;
						}
					} else if (cmdName.equalsIgnoreCase("qfclass")) {
						if (isPlayer) {
							p = (Player) sender;
							if (args.length != 2) {
								p.sendMessage(
										ChatColor.GOLD + "[qfclass] " + ChatColor.RESET + " wrong number of arguments");
								return false;
							}

							if (args[0] == ".") {
								this.getLogger().info("player .");
								pTarget = (Player) sender;
							} else {
								pTarget = this.getServer().getPlayer(args[0]);
							}
						} else {
							if (args.length != 2) {
								this.getLogger().info("[QfCore].qfclass wrong number of arguments");
								return false;
							}

							p = null;
							pTarget = this.getServer().getPlayer(args[0]);
						}

						className = args[1];
						if (!this.getConfig().contains(className)) {
							this.getLogger().info("No config entry found for '" + className + "' in config.yml");
							return true;
						} else {
							if (pTarget != null) {
								this.processQfClassConfig(pTarget, className, p);
							}

							return true;
						}
					} else if (cmdName.equalsIgnoreCase("qfhearts")) {
						double dVal;
						if (isPlayer) {
							p = (Player) sender;
							if (args.length != 2) {
								p.sendMessage(ChatColor.GOLD + "[qfhearts] " + ChatColor.RESET
										+ " wrong number of arguments");
								return false;
							}

							if (args[0] == ".") {
								this.getLogger().info("player .");
								pTarget = (Player) sender;
							} else {
								pTarget = this.getServer().getPlayer(args[0]);
							}

							dVal = Double.parseDouble(args[1]);
							this.setHearts(pTarget, dVal);
							p.sendMessage(ChatColor.GOLD + "Player " + ChatColor.GREEN + pTarget.getName()
									+ ChatColor.GOLD + " has been given a max health of " + ChatColor.YELLOW + dVal);
						} else {
							if (args.length != 2) {
								this.getLogger().info("[QfCore].qfhearts wrong number of arguments");
								return false;
							}

							p = null;
							pTarget = this.getServer().getPlayer(args[0]);
							dVal = Double.parseDouble(args[1]);
							this.setHearts(pTarget, dVal);
							this.getServer().getConsoleSender()
									.sendMessage(ChatColor.YELLOW + "[QfCore].qfhearts " + ChatColor.RESET + "Player "
											+ ChatColor.GREEN + pTarget.getName() + ChatColor.RESET
											+ " has been given a max health of " + ChatColor.GREEN + dVal);
						}

						pTarget.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 120, 50, true));
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			return this.chatCommand(sender, cmd, label, args);
		}
	}

	public void readConfigAnc() {
		this.qAnnc.clear();
		// decompiler artifact
		// int idx = 0;
		Set<String> keys = this.getConfig().getConfigurationSection("announcement").getKeys(false);
		this.getLogger().info("found " + keys.size() + " qanc in config file");
		String path = "announcement.interval";
		if (this.getConfig().contains(path)) {
			String tmp = this.getConfig().getString(path);
			this.getLogger().info("qancc int: <" + tmp + ">");
			if (tmp == null) {
				tmp = "5";
			}

			this.qAnncInt = Long.parseLong(tmp);
			this.getLogger().info("qancc int: <==" + this.qAnncInt + "==>");
		}

		path = "announcement.messages";
		if (this.getConfig().contains(path)) {
			List<String> strCons = this.getConfig().getStringList(path);

			for (Iterator<String> var7 = strCons.iterator(); var7.hasNext();) {
				String strCon = var7.next();
				this.qAnnc.add(ChatColor.translateAlternateColorCodes('&', strCon));
			}
		}

		if (taskIdQAnnc != -1) {
			Bukkit.getScheduler().cancelTask(taskIdQAnnc);
			this.msgCaller((Player) null, "Canceled taskid " + taskIdQAnnc);
		}

		taskIdQAnnc = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				QfCore.this.taskAnnouce();
			}
		}, 10L, this.qAnncInt * 20L);
		if (taskIdGuildMotd != -1) {
			Bukkit.getScheduler().cancelTask(taskIdGuildMotd);
			this.msgCaller((Player) null, "Canceled taskid " + taskIdGuildMotd);
		}

		taskIdGuildMotd = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				QfCore.this.taskGuildMotd();
			}
		}, 2400L, 36000L);
	}

	public void taskAnnouce() {
		int rnum = (int) (Math.random() * (double) this.qAnnc.size());
		this.numAnnouce(rnum);
	}

	public void taskGuildMotd() {
		this.guildMgr.sendAllMotd();
	}

	public void numAnnouce(int idx) {
		int idxg = idx;
		if (idx >= this.qAnnc.size()) {
			idxg = this.qAnnc.size() - 1;
		}

		if (idxg < 0) {
			idxg = 0;
		}

		this.doAnnouce((String) this.qAnnc.get(idxg));
	}

	public void doAnnouce(String annc) {
		Iterator<? extends Player> var3 = Bukkit.getOnlinePlayers().iterator();

		while (var3.hasNext()) {
			Player np = var3.next();
			this.msgCaller(np, annc);
		}

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void hitWithEnchantedItems(EntityDamageByEntityEvent event) {
		Entity pTargetEty = event.getEntity();
		Player pSource = null;
		if (event.getEntity() instanceof Player) {
			if (event.getDamager() instanceof Player) {
				pSource = (Player) event.getDamager();
			} else if (event.getDamager() instanceof Arrow) {
				Arrow arr = (Arrow) event.getDamager();
				if (arr.getShooter() instanceof Player) {
					pSource = (Player) arr.getShooter();
				}
			}

			if (pSource == null) {
				return;
			}

			Player pTarget = (Player) event.getEntity();
			String srcParty = this.partyMgr.getPlayerPartyId(pSource);
			String tarParty = this.partyMgr.getPlayerPartyId(pTarget);
			if (srcParty == tarParty && !this.partyMgr.canPvp(srcParty)) {
				event.setCancelled(true);
				return;
			}
		}

		if (event.getDamager() instanceof Player) {
			pSource = (Player) event.getDamager();
		}

		if (pSource != null) {
			ItemStack iAttack = pSource.getItemInUse();
			List<String> qenchantList = QfCoreEnchant.getQEnchants(iAttack);
			if (qenchantList != null) {
				this.pvpQEnchantEngine(event, pSource, pTargetEty, iAttack, qenchantList);
			}
		}

	}

	public void pvpQEnchantEngine(EntityDamageByEntityEvent event, Player pSource, Entity pTargetEty, ItemStack iAttack,
			List<String> qenchantList) {
		boolean isPlayer = false;
		Player pTarget = null;
		if (pTargetEty instanceof Player) {
			pTarget = (Player) pTargetEty;
			isPlayer = true;
		}

		Iterator<String> var21 = qenchantList.iterator();

		while (var21.hasNext()) {
			String qi = var21.next();
			String enName = QfCoreEnchant.enchantName(qi);
			int enLevel = QfCoreEnchant.enchantLevel(qi);
			this.getLogger().info("pvpQEnchantEngine: " + qi + " (lvl " + enLevel + ")");
			PotionEffect pe;
			switch (enName.hashCode()) {
			case -2122237229:
				if (enName.equals("Hunger") && isPlayer) {
					int curLvl = pTarget.getFoodLevel();
					if (curLvl > 1) {
						int newLvl = Math.max(curLvl - enLevel, 1);
						pTarget.setFoodLevel(newLvl);
						pSource.playSound(pSource.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
						pTarget.playSound(pTarget.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
						if (this.isCriticalHit(enLevel * 500)) {
							this.msgCaller(pSource, ChatColor.DARK_GREEN + "Critical hit!");
							pe = new PotionEffect(PotionEffectType.HUNGER, enLevel * 20 * 4, enLevel / 2);
							pTarget.addPotionEffect(pe);
						}
					}
				}
				break;
			case -1604554070:
				if (enName.equals("Lightning") && isPlayer) {
					pTarget.getLocation().getWorld().strikeLightningEffect(pTarget.getLocation());
					this.getLogger().info("Normal damage: " + event.getDamage());
					event.setDamage(event.getDamage() * (double) enLevel / 2.0D);
					this.getLogger().info("Lightning total damage: " + event.getDamage() * (double) enLevel / 2.0D);
					this.msgCaller(pTarget, ChatColor.YELLOW + "You have been struck by lightning");
				}
				break;
			case -1096039713:
				if (enName.equals("Deep Wound") && isPlayer) {
					pe = new PotionEffect(PotionEffectType.HARM, enLevel * 6, 1);
					pTarget.addPotionEffect(pe);
					this.msgCaller(pTarget, ChatColor.DARK_RED + "You are bleeding badly!");
				}
				break;
			case 79980042:
				if (enName.equals("Sloth") && isPlayer) {
					pe = new PotionEffect(PotionEffectType.SLOW, enLevel * 30, Math.max(1, enLevel * 3 / 2));
					pTarget.addPotionEffect(pe);
					this.msgCaller(pTarget, ChatColor.DARK_RED + "Your movements seem to have been slowed!");
				}
				break;
			case 527388469:
				if (enName.equals("Tornado") && isPlayer) {
					if (this.isCriticalHit(enLevel * 500)) {
						pe = new PotionEffect(PotionEffectType.LEVITATION, enLevel * 20, Math.max(1, enLevel * 2));
						pTarget.addPotionEffect(pe);
						pe = new PotionEffect(PotionEffectType.CONFUSION, enLevel * 20 * 4, enLevel * 2 / 3);
						pTarget.addPotionEffect(pe);
						pSource.playSound(pSource.getLocation(), Sound.ENTITY_BAT_AMBIENT, 1.0F, 1.0F);
						pTarget.playSound(pTarget.getLocation(), Sound.ENTITY_WOLF_WHINE, 1.0F, 1.0F);
						pTarget.playSound(pTarget.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
						pTarget.playSound(pTarget.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1.0F, 1.0F);
						pTarget.playSound(pTarget.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 1.0F);
					}

					double curLvld = pTarget.getHealth();
					double diffLvld = (double) enLevel;
					if (this.isCriticalHit(enLevel * 500)) {
						this.msgCaller(pSource, ChatColor.DARK_GREEN + "Heavy hit!");
						diffLvld *= 3.0D;
					}

					double newLvld = Math.max(curLvld - diffLvld, 0.0D);
					pTarget.setHealth(newLvld);
				}
				break;
			case 795504749:
				if (enName.equals("Poor Seeing") && isPlayer) {
					pe = new PotionEffect(PotionEffectType.BLINDNESS, enLevel * 20, Math.max(1, enLevel * 2));
					pTarget.addPotionEffect(pe);
					this.msgCaller(pTarget, ChatColor.BLUE + "You have been temporarily blinded!");
				}
			}
		}

	}

	public boolean isCriticalHit(int chance) {
		int rnum = (int) (Math.random() * 10000.0D) + 1;
		return rnum < chance;
	}
}
