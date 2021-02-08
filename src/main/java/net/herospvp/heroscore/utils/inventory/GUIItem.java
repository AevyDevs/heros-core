package net.herospvp.heroscore.utils.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GUIItem {

    private final Consumer<InventoryClickEvent> invClick;
    private final ItemStack item;

    public GUIItem(ItemStack item, final Consumer<InventoryClickEvent> toRun) {
        this.invClick = toRun;
        this.item = item;
    }

    ItemStack getBukkitItem() {
        return this.item;
    }

    void invClick(final InventoryClickEvent e) {
        this.invClick.accept(e);
    }
}

