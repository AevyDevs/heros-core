package net.herospvp.heroscore.utils;

import net.herospvp.heroscore.HerosCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Dependencies {

    private final HerosCore herosCore;

    public Dependencies(HerosCore herosCore) {
        this.herosCore = herosCore;
    }

    public boolean check(String... dependency) {

        for (String s : dependency) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(s);
            if (plugin == null || !plugin.isEnabled()) {
                herosCore.getLoggerUtils().failedLoadingDependency(s);
                return false;
            }
        }

        return true;
    }

}
