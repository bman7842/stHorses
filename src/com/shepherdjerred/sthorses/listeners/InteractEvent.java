
package com.shepherdjerred.sthorses.listeners;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.shepherdjerred.sthorses.Main;


public class InteractEvent implements Listener {

	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {

		// Check if a block was right-clicked
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			Player player = event.getPlayer();

			if (player.hasPermission("stHorses.spawn")) {

				// See if the player has an item in their hand
				if (player.getItemInHand() != null) {

					ItemStack item = player.getItemInHand();

					// Check if the player has a saddle in their hand
					if (item.getType() == Material.SADDLE) {

						// Check if the saddle has metadata
						if (item.getItemMeta() != null) {

							ItemMeta itemMeta = item.getItemMeta();

							// Check if the saddle has lore
							if (itemMeta.getLore() != null) {

								List<String> itemLore = itemMeta.getLore();

								// Check that the lore is ours
								if (itemLore.get(0).contains("Type:") && itemLore.get(1).contains("Color:") && itemLore.get(2).contains("Style:")
										&& itemLore.get(3).contains("Dom:") && itemLore.get(4).contains("MaxDom:") && itemLore.get(5).contains("Jump:")
										&& itemLore.get(6).contains("Name") && itemLore.get(7).contains("Health:") && itemLore.get(8).contains("MaxHealth:")
										&& itemLore.get(9).contains("Age:") && itemLore.get(10).contains("Owner:") && itemLore.get(11).contains("UUID:")) {

									// Remove the saddle
									player.getInventory().removeItem(item);

									// Create a new location for the horse to spawn at
									Location location = new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY() + 1,
											event.getClickedBlock().getZ());

									// Spawn a horse
									Horse horse = event.getClickedBlock().getWorld().spawn(location, Horse.class);

									// Apply variant value
									horse.setVariant(Variant.valueOf(itemLore.get(0).replace("Type: ", "")));

									// Apply color value
									horse.setColor(Color.valueOf(itemLore.get(1).replace("Color: ", "")));

									// Apply style value
									horse.setStyle(Style.valueOf(itemLore.get(2).replace("Style: ", "")));

									// Apply domestication value
									horse.setDomestication(Integer.parseInt(itemLore.get(3).replace("Dom: ", "")));

									// Apply max domestication value
									horse.setMaxDomestication(Integer.parseInt(itemLore.get(4).replace("MaxDom: ", "")));

									// Apply jump strength value
									horse.setJumpStrength(Double.parseDouble(itemLore.get(5).replace("Jump: ", "")));

									// Apply the horses name
									if (itemLore.get(6).replace("Name: ", "") != "null") {
										horse.setCustomName(itemLore.get(6).replace("Name: ", ""));
									}

									// Set current health
									horse.setHealth(Double.parseDouble(itemLore.get(7).replace("Health: ", "")));

									// Apply max heath value
									horse.setMaxHealth(Double.parseDouble(itemLore.get(8).replace("MaxHealth: ", "")));

									// Apply age value
									horse.setAge(Integer.parseInt(itemLore.get(9).replace("Age: ", "")));

									// Set the owner
									horse.setOwner((AnimalTamer) Main.getInstance().getServer().getPlayer(itemLore.get(10).replace("Owner: ", "")));

									horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));

								}

							}

						}
					}
				}

			}

		}

	}

}