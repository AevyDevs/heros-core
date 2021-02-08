package net.herospvp.heroscore.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GUIWindow {

    public static Map<String, GUIWindow> windows = new HashMap<>();
    private final Inventory inv;
    private final Map<Integer, GUIItem> items;
    private Consumer<InventoryOpenEvent> onOpen;
    private Consumer<InventoryCloseEvent> onClose;
    private boolean registered;

    public GUIWindow(String name, final int rows) {
        this.onOpen = null;
        this.onClose = null;
        name = this.getValidName(name);
        this.inv = Bukkit.createInventory(null, rows * 9, name);
        this.items = new HashMap<>(rows * 9);
        GUIWindow.windows.put(name, this);
        this.registered = true;
    }

    public void setItem(final int slot, final GUIItem item) {
        this.items.put(slot, item);
        this.inv.setItem(slot, item.getBukkitItem());
    }

    public void setItem(final int x, final int y, final GUIItem item) {
        this.setItem(x + y * 9, item);
    }

    public GUIItem getItem(final int slot) {
        return this.items.get(slot);
    }

    public GUIItem getItem(final int x, final int y) {
        return this.getItem(x * 9 + y);
    }

    public void setOpenEvent(final Consumer<InventoryOpenEvent> e) {
        this.onOpen = e;
    }

    void callOpen(final InventoryOpenEvent e) {
        if (this.onOpen != null) {
            this.onOpen.accept(e);
        }
    }

    public void setCloseEvent(final Consumer<InventoryCloseEvent> e) {
        this.onClose = e;
    }

    void callClosed(final InventoryCloseEvent e) {
        if (this.onClose != null) {
            this.onClose.accept(e);
        }
        unregister();
    }

    public Inventory getBukkitInventory() {
        return this.inv;
    }

    public void show(final HumanEntity h) {
        h.openInventory(inv);
    }

    public void unregister() {
        GUIWindow.windows.remove(this.getBukkitInventory().getTitle());
        this.items.clear();
        this.registered = false;
    }

    static GUIWindow getWindow(final String inv) {
        return GUIWindow.windows.get(inv);
    }

    private String getValidName(final String name) {
        if (GUIWindow.windows.containsKey(name)) {
            return this.getValidName(name + ChatColor.RESET);
        }
        return name;
    }

}
