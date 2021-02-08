package net.herospvp.heroscore.utils;

import net.herospvp.heroscore.utils.strings.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler implements CommandExecutor, TabCompleter {

    private final boolean onlyPlayer;
    private final boolean tabComplete;
    private final List<String> usage;
    private String permission;
    private final JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin, String permission, String command, boolean onlyPlayer, List<String> usage,
                          boolean tabCompleteCustom) {
        this.onlyPlayer = onlyPlayer;
        this.usage = usage;
        if (permission != null) {
            this.permission = permission;
        }
        this.tabComplete = tabCompleteCustom;
        this.plugin = plugin;

        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
    }

    public abstract boolean command(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (onlyPlayer && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "Comando eseguibile solo dai player :(");
            return false;
        }

        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "Permesso negato. ("  + permission + ")");
            return false;
        }

        if (!command(sender, args)) {
            for (String s : usage) {
                sender.sendMessage(StringUtils.c(s));
            }
            return false;
        }

        return true;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public List<String> getDefaultTabList(String[] args) {
        List<String> list = new ArrayList<>();
        if (args[args.length-1].equalsIgnoreCase("")) {
            Bukkit.getOnlinePlayers().forEach((players) -> {
                list.add(players.getName());
            });
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(args[args.length-1])) {
                    list.add(player.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return tabComplete ? tabComplete(args) : getDefaultTabList(args);
    }

}
