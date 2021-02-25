package net.herospvp.heroscore.utils;

import org.bukkit.plugin.Plugin;

import java.util.List;

public class Configuration {

    private final Plugin instance;

    public Configuration(Plugin instance) {
        this.instance = instance;
    }

    public String getString(String string) {
        return instance.getConfig().getString(string);
    }

    public int getInt(String string) {
        return instance.getConfig().getInt(string);
    }

    public double getDouble(String string) {
        return instance.getConfig().getDouble(string);
    }

    public long getLong(String string) {
        return instance.getConfig().getLong(string);
    }

    public List<String> getStringList(String string) {
        return instance.getConfig().getStringList(string);
    }

    public boolean getBoolean(String string) {
        return instance.getConfig().getBoolean(string);
    }

}
