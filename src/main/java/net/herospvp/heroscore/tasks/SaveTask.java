package net.herospvp.heroscore.tasks;

import lombok.AllArgsConstructor;
import net.herospvp.heroscore.HerosCore;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class SaveTask extends BukkitRunnable {
    private HerosCore plugin;

    @Override
    public void run() {
        plugin.getPlayersHandler().saveAll();
    }

}
