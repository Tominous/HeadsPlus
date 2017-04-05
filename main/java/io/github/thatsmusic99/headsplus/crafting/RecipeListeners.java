package io.github.thatsmusic99.headsplus.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import io.github.thatsmusic99.headsplus.HeadsPlus;

public class RecipeListeners {
	
	public static void makeSell(ItemMeta m) {
		if ((HeadsPlus.getInstance().sellable)) {
			
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.GOLD + ChatColor.BOLD + "This head can be sold!");
			lore.add("" + ChatColor.GOLD + "Do /sellhead to sell!");
			m.setLore(lore);
			
		}
	}
}
