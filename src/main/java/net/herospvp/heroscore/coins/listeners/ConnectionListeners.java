package net.herospvp.heroscore.coins.listeners;

import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ConnectionListeners implements Listener {

    private final HerosCore plugin;

    public ConnectionListeners(HerosCore plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (plugin.getPlayersHandler().getPlayers().containsKey(uuid)) return;

        plugin.getMusician().update(plugin.getPlayersHandler().load(event.getPlayer().getUniqueId(), () -> { }));
        plugin.getMusician().play();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerQuitEvent event) {
        HPlayer player = plugin.getPlayersHandler().getPlayer(event.getPlayer().getUniqueId());
        if (!player.isEdited()) return;

        plugin.getMusician().update(plugin.getPlayersHandler().save(event.getPlayer().getUniqueId(), () -> {
           plugin.getPlayersHandler().remove(event.getPlayer().getUniqueId());
        }));
        plugin.getMusician().play();
    }

}
