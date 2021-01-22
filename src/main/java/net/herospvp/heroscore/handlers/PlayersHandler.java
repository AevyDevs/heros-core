package net.herospvp.heroscore.handlers;

import lombok.Getter;
import net.herospvp.database.items.Notes;
import net.herospvp.database.items.Papers;
import net.herospvp.heroscore.HerosCore;
import net.herospvp.heroscore.objects.HPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersHandler {

    @Getter private final Map<UUID, HPlayer> players;

    private String table;
    private HerosCore plugin;

    public PlayersHandler(HerosCore plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();

        this.table = "players";

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
            plugin.getMusician().update(load(onlinePlayer.getUniqueId(), () -> { }));
            plugin.getMusician().play();
        }
    }

    public HPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void remove(UUID uuid) {
        players.remove(uuid);
    }

    public Papers save(UUID uuid, Runnable done) {
        return (((connection, instrument) -> {
            PreparedStatement preparedStatement = null;
            try {
                HPlayer player = getPlayer(uuid);
                preparedStatement = connection.prepareStatement(
                        new Notes(table).update(new String[]{"COINS"}, new Object[]{player.getCoins()}, "UUID", player.getUuid().toString())
                );

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(null, preparedStatement, null);
                done.run();
            }
        }));
    }

    public Papers load(UUID uuid, Runnable done) {
        return (((connection, instrument) -> {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                preparedStatement = connection.prepareStatement(
                        new Notes(table).createTable(new String[]{"UUID CHAR(36) NOT NULL", "COINS INTEGER UNSIGNED"})
                );
                preparedStatement.execute();

                preparedStatement = connection.prepareStatement(
                        new Notes(table).selectWhere("COINS", "UUID", uuid.toString())
                );
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement(
                            new Notes(table).insert(new String[]{"UUID, COINS"}, new Object[]{uuid.toString(), 0})
                    );
                    preparedStatement.executeUpdate();
                    players.put(uuid, new HPlayer(uuid, 0, false));
                } else {
                    int coins = resultSet.getInt(1);

                    players.put(uuid, new HPlayer(uuid, coins, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(null, preparedStatement, resultSet);
                done.run();
            }
        }));
    }

    public void saveAll() {
        for (HPlayer player : players.values()) {
            save(player.getUuid(), () -> {});
        }
        plugin.saveConfig();
    }
}
