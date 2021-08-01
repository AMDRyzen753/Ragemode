package de.timeox2k.listener;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import de.timeox2k.Ragemode;
import de.timeox2k.utils.ItemBuilder;

public class PlayerListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		ItemBuilder builder = new ItemBuilder();

		player.getInventory().setItem(0, builder.buildItem(Material.IRON_SWORD, 1, "§cButterbrotmesser"));
		player.getInventory().setItem(4, builder.buildItem(Material.BOW, 1, "§cBogen"));
		player.getInventory().setItem(9, builder.buildItem(Material.ARROW, 1, "§cPfeile"));

		player.getInventory().setItem(8, builder.buildItem(Material.IRON_AXE, 1, "§bAxt"));

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR)
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		event.setCancelled(true);
	}

	@EventHandler
	public void on(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		ItemBuilder builder = new ItemBuilder();

		if (event.getItem().getItemStack().getType() == Material.IRON_AXE) {
			event.setCancelled(true);
			event.getItem().remove();
			player.getInventory().setItem(8, builder.buildItem(Material.IRON_AXE, 1, "§bAxt"));
		}
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		if (event.getItem() != null) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager(); 
			
			if(event.getEntity() instanceof Player) {
				Player entity = (Player) event.getEntity();
				
				if(player.getItemInHand().getType() == Material.IRON_SWORD && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cButterbrotmesser")) {
					event.setCancelled(true);
					entity.setHealth(0D);
				}
			}
			
		}
	}
	
	@EventHandler
	public void onExplosion(BlockExplodeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR
				|| player.getItemInHand().getItemMeta() == null)
			return;

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.IRON_AXE
					&& player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§bAxt")) {
				Item axeItem = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.IRON_AXE));
				
				axeItem.getItemStack().getItemMeta().setLore(Arrays.asList("Thrower:" + player.getName()));
				
				axeItem.setVelocity(player.getEyeLocation().getDirection());
				player.getInventory().setItemInHand(new ItemStack(Material.AIR));
			}
		}
	}

}
