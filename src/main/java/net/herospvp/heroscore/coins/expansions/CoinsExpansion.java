package net.herospvp.heroscore.coins.expansions;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
public class CoinsExpansion extends PlaceholderExpansion {

    private HerosCore plugin;

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "coins";
    }

    @Override
    public String getAuthor() {
        return "Niketion";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (identifier.equals("amount")) {
            HPlayer coinsPlayer = plugin.getPlayersHandler().getPlayer(player.getUniqueId());
            return coinsPlayer == null ? "Caricamento..." : String.valueOf(coinsPlayer.getCoins());
        }

        return "?";
    }

}
