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

        if (args.length > 2) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            HPlayer player = plugin.getPlayersHandler().getPlayer(target.getUniqueId());

            if (player == null) {
                sender.sendMessage("Operazione non riuscita, il player NON ESISTE!");
                return true;
            }

            int value;

            try {
                value = Integer.parseInt(args[2]);
            } catch (Exception e) {
                sender.sendMessage("/ca [set/add/remove] [player] [amount]");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "set": {
                    player.setCoins(value);
                    save(target.getUniqueId(), sender);
                    break;
                }
                case "add": {
                    player.setCoins(value + player.getCoins());
                    save(target.getUniqueId(), sender);
                    break;
                }
                case "remove": {
                    player.setCoins(player.getCoins() - value);
                    save(target.getUniqueId(), sender);
                    break;
                }
                default: {
                    sender.sendMessage("/ca [set/add/remove] [player] [amount]");
                    break;
                }
            }
            return true;
        }

        sender.sendMessage("/ca [set/add/remove] [player] [amount]");
        return true;
    }

    private void save(UUID uuid, CommandSender sender) {
        plugin.getMusician().update(plugin.getPlayersHandler().save(uuid, () -> {
            sender.sendMessage("Coins impostati al nuovo valore!");
        }));
        plugin.getMusician().play();
    }
}
