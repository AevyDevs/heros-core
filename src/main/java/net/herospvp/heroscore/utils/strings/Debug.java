package net.herospvp.heroscore.utils.strings;

import net.herospvp.heroscore.HerosCore;
import org.bukkit.Bukkit;

import java.text.MessageFormat;
import java.util.logging.Level;

public class Debug {

    private final HerosCore plugin;
    private static boolean debugMode;

    public Debug(HerosCore plugin) {
        this.plugin = plugin;
        debugMode = plugin.getConfig().getBoolean("debug-mode");
    }

    public void setDebugMode(boolean debug) {
        this.plugin.getConfig().set("debug-mode", debug);
        this.plugin.saveConfig();
        debugMode = debug;
    }

    public static boolean getDebugMode() {
        return debugMode;
    }

    public static void send(String prefix, String message, Object... args) {
        if (!debugMode) return;
        MessageFormat formatter = new MessageFormat("[" + prefix + "] " + message);
        StringBuffer output = new StringBuffer (256);
        Bukkit.getLogger().log(Level.WARNING, formatter.format (args, output, null).toString());
    }

}
