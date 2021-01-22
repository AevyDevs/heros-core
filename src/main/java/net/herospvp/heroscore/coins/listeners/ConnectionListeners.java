package net.herospvp.heroscore.coins.listeners;

import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListeners implements Listener {

    private HerosCore plugin;

    public ConnectionListeners(HerosCore plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        plugin.getMusician().update(plugin.getPlayersHandler().load(event.getPlayer().getUniqueId(), () -> { }));
        plugin.getMusician().play();
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        HPlayer player = plugin.getPlayersHandler().getPlayer(event.getPlayer().getUniqueId());
        if (!player.isEdited()) return;

        plugin.getMusician().update(plugin.getPlayersHandler().save(event.getPlayer().getUniqueId(), () -> {
           plugin.getPlayersHandler().remove(event.getPlayer().getUniqueId());
        }));
        plugin.getMusician().play();
    }

}
