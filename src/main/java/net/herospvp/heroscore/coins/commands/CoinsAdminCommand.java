package net.herospvp.heroscore.coins.commands;

import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class CoinsAdminCommand implements CommandExecutor {
    private HerosCore plugin;

    public CoinsAdminCommand(HerosCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("coinsadmin").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("herospvp.admin")) return true;

        if (args.length > 2) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            HPlayer player = plugin.getPlayersHandler().getPlayer(target.getUniqueId());

            switch (args[0].toLowerCase()) {
                case "set": {
                    if (player != null) {
                        player.setCoins(Integer.parseInt(args[2]));
                    }
                    save(target.getUniqueId(), sender);
                    break;
                }
                case "add": {
                    if (player != null) {
                        player.setCoins(Integer.parseInt(args[2])+player.getCoins());
                    }
                    save(target.getUniqueId(), sender);
                    break;
                }
                case "remove": {
                    if (player != null) {
                        player.setCoins(player.getCoins()-Integer.parseInt(args[2]));
                    }
                    save(target.getUniqueId(), sender);
                    break;
                }
            }
            return true;
        }

        sender.sendMessage("/ca [set/add/remove] [player] [amount]");
        return true;
    }

    private void save(UUID uuid, CommandSender sender) {
        plugin.getMusician().updateMirror(plugin.getPlayersHandler().save(uuid, () -> {
            sender.sendMessage("Coins impostati al nuovo valore!");
        }));
        plugin.getMusician().play();
    }
}
