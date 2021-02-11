package net.herospvp.heroscore.coins.expansions;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.herospvp.heroscore.HerosCore;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

@AllArgsConstructor
public class StatsExpansion extends PlaceholderExpansion {

    private final HerosCore plugin;

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "stats";
    }

    @Override
    public String getAuthor() {
        return "Sorridi";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String identifier) {

        Player player = offlinePlayer.getPlayer();
        if (player == null) {
            return "?";
        }

        switch (identifier) {
            case "deaths": {
                return String.valueOf(player.getStatistic(Statistic.DEATHS));
            }
            case "player_kills": {
                return String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS));
            }
            case "kd": {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                long deaths = player.getStatistic(Statistic.DEATHS);
                return String.valueOf(decimalFormat.format(
                        (float) player.getStatistic(Statistic.PLAYER_KILLS) / (float) (deaths == 0 ? 1 : deaths))
                );
            }
            case "mob_kills": {
                return String.valueOf(player.getStatistic(Statistic.MOB_KILLS));
            }
            case "global_kills": {
                return String.valueOf(player.getStatistic(Statistic.KILL_ENTITY));
            }
            case "total_leaves": {
                return String.valueOf(player.getStatistic(Statistic.LEAVE_GAME));
            }
        }

        return "?";
    }

}
