package de.timeox2k.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	public static ItemStack buildItem(Material material, int count, String displayname) {
		ItemStack itemStack = new ItemStack(material, count);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayname);
		itemStack.setItemMeta(itemMeta);
		
		if(material == Material.BOW) {
			itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
		}
		
		return itemStack;
	}
	
}
