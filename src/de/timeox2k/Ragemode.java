package de.timeox2k;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.timeox2k.listener.PlayerListener;

public class Ragemode extends JavaPlugin {

	private static Ragemode instance;

	@Override
	public void onEnable() {
		PluginManager pluginManager = getServer().getPluginManager();
		instance = this;

		pluginManager.registerEvents(new PlayerListener(), this);

		getConfig().addDefault("Arena.World", "world");
		getConfig().options().copyDefaults(true);
		saveConfig();
		new BukkitRunnable() {

			@Override
			public void run() {

				for (Entity entity : Bukkit.getWorld(getConfig().getString("Arena.World")).getEntities()) {

					if (entity instanceof Arrow) {
						if (entity.isOnGround()) {
							entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_HUGE, 1);

							for (Player all : Bukkit.getOnlinePlayers()) {
								double distance = all.getLocation().distance(entity.getLocation());
								if (distance < 5D) {

									double damage = 0;

									for (int i = 0; i < distance; i--) {
										damage += 1D;
									}

									all.damage(damage);
									entity.remove();
									break;
								}

							}

							entity.remove();
						}
					} else if (entity instanceof Item) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							if (entity.isOnGround()) {
								break;
							}
							double distance = all.getLocation().distance(entity.getLocation());

							Item entityItem = (Item) entity;

							if (entityItem.getItemStack().getItemMeta() != null
									&& entityItem.getItemStack().getItemMeta().getLore() != null) {

								List<String> lore = entityItem.getItemStack().getItemMeta().getLore();

								for (String loreList : lore) {
									if (lore.contains(all.getName())) {
										entity.remove();
										break;
									}
								}

								if (distance < 2.5) {

									if (all.getHealth() > 0) {
										all.setHealth(0D);
									}

									entity.remove();
									break;
								}

							}
						}
					}
				}
			}
		}.runTaskTimer(getInstance(), 5, 5);
	}

	public static Ragemode getInstance() {
		return instance;
	}

}
