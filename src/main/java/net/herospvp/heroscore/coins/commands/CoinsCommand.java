package net.herospvp.heroscore.coins.commands;

import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import net.herospvp.heroscore.utils.strings.message.Message;
import net.herospvp.heroscore.utils.strings.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand implements CommandExecutor {

    private final HerosCore plugin;

    public CoinsCommand(HerosCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("coins").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            if (sender instanceof ConsoleCommandSender) return true;

            HPlayer hPlayer = plugin.getPlayersHandler().getPlayer(((Player)sender).getUniqueId());
            if (hPlayer == null) {
                Message.sendMessage(sender, MessageType.ERROR, "Coins", "Caricamento...");
                return true;
            }
            Message.sendMessage(sender, MessageType.INFO, "Coins", "Possiedi &e{0} &fcoins", String.valueOf(hPlayer.getCoins()));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {

            if (sender.hasPermission("herospvp.admin")) {
                plugin.getMusician().update(plugin.getPlayersHandler().load(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), () -> {
                    Message.sendMessage(sender, MessageType.INFO, "Coins", "Coins di &e{0}&f: {1}", args[0],
                            plugin.getPlayersHandler().getPlayer(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getCoins()+"");
                }));
                return true;
            }

            Message.sendMessage(sender, MessageType.ERROR, "Coins", "Player non trovato...");
            return true;
        }

        HPlayer hTarget = plugin.getPlayersHandler().getPlayer(target.getUniqueId());

        if (hTarget == null) {
            Message.sendMessage(sender, MessageType.ERROR, "Coins", "Caricamento...");
            return true;
        }

        Message.sendMessage(sender, MessageType.INFO, "Coins", "Possiede &e{0} &fcoins", String.valueOf(hTarget.getCoins()));
        return true;
    }

}
