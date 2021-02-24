package net.herospvp.heroscore.coins.commands;

import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import net.herospvp.heroscore.utils.CommandsHandler;
import net.herospvp.heroscore.utils.strings.message.Message;
import net.herospvp.heroscore.utils.strings.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class CoinsAdminCommands extends CommandsHandler {

    private final HerosCore plugin;

    public CoinsAdminCommands(HerosCore instance, String permission, String command, boolean onlyPlayer, List<String> usage, boolean tabCompleteCustom) {
        super(instance, permission, command, onlyPlayer, usage, tabCompleteCustom);
        this.plugin = instance;
    }

    @Override
    public boolean command(CommandSender sender, String[] args) {
        if (args.length != 3) {
            Message.sendMessage(sender, MessageType.ERROR, "Coins", "/ca [set/add/remove] [player] [amount]");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        HPlayer player = plugin.getPlayersHandler().getPlayer(target.getUniqueId());

        if (player == null) {
            Message.sendMessage(sender, MessageType.ERROR, "Coins", "Operazione non riuscita, il player NON ESISTE!");
            return true;
        }

        int value;

        try {
            value = Integer.parseInt(args[2]);
        } catch (Exception ignored) {
            Message.sendMessage(sender, MessageType.ERROR, "Coins", "/ca [set/add/remove] [player] [amount]");
            return true;
        }

        UUID uuid = target.getUniqueId();
        switch (args[0].toLowerCase()) {
            case "set": {
                player.setCoins(value);
                save(uuid, sender);
                break;
            }
            case "add": {
                player.addCoins(value);
                save(uuid, sender);
                break;
            }
            case "remove": {
                player.removeCoins(value);
                save(uuid, sender);
                break;
            }
            default: {
                Message.sendMessage(sender, MessageType.ERROR, "Coins", "/ca [set/add/remove] [player] [amount]");
                break;
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }

    private void save(UUID uuid, CommandSender sender) {
        plugin.getMusician().offer(plugin.getPlayersHandler().save(uuid, () -> {
            Message.sendMessage(sender, MessageType.WARNING, "Coins", "Coins impostati al nuovo valore!");
        }));
    }

}
