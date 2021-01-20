package net.herospvp.heroscore.utils.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {

    public static void removeItem(Player player, ItemStack item, int amount) {
        PlayerInventory inventory = player.getInventory();

        int i=0;
        List<Integer> slots = new ArrayList<>();

        int tempAmount = amount;
        for (ItemStack content : inventory.getContents()) {
            if (content == null || content.getType() == Material.AIR) {
                i++;
                continue;
            }

            if (content.getType() == item.getType() && content.getDurability() == item.getDurability()) {
                if (tempAmount > content.getAmount()) {
                    slots.add(i);
                } else {
                    content.setAmount(content.getAmount()-tempAmount);
                }
                tempAmount -= content.getAmount();
            }
            i++;

            if (tempAmount <= 0) break;
        }

        for (Integer slot : slots) {
            player.getInventory().getItem(slot).setType(Material.AIR);
        }
    }
}
