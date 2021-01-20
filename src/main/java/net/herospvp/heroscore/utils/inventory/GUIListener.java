package net.herospvp.heroscore.utils.inventory;

import net.herospvp.heroscore.HerosCore;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListener implements Listener
{
    public GUIListener(final HerosCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        final GUIWindow window = GUIWindow.getWindow(e.getInventory().getTitle());
        if (window != null) {
            final GUIItem item = window.getItem(e.getSlot());
            if (item != null) {
                item.invClick(e);
            }
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onOpen(final InventoryOpenEvent e) {
        final GUIWindow window = GUIWindow.getWindow(e.getInventory().getTitle());
        if (window != null) {
            window.callOpen(e);
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        final GUIWindow window = GUIWindow.getWindow(e.getInventory().getTitle());
        if (window != null) {
            window.callClosed(e);
        }
    }

    @EventHandler
    public void onInteract(final InventoryInteractEvent e) {
        if (GUIWindow.getWindow(e.getInventory().getTitle()) != null) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }
}
