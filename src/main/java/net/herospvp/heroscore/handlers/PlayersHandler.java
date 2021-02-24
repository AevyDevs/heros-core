package net.herospvp.heroscore.handlers;

import lombok.Getter;
import lombok.SneakyThrows;
import net.herospvp.database.lib.Musician;
import net.herospvp.database.lib.items.Notes;
import net.herospvp.database.lib.items.Papers;
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

    private final HerosCore plugin;

    @Getter
    private final Map<UUID, HPlayer> players;

    private final Notes notes;
    private final Musician musician;

    public PlayersHandler(HerosCore plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
        this.musician = plugin.getMusician();

        this.notes = new Notes("players");

        musician.offer(startup());

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            musician.offer(load(onlinePlayer.getUniqueId(), () -> { }));
        }
    }

    public HPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void remove(UUID uuid) {
        players.remove(uuid);
    }

    public Papers startup() {
        return (connection, instrument) -> {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(
                        notes.createTable(new String[]{"UUID CHAR(36) NOT NULL", "COINS INTEGER UNSIGNED"})
                );
                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                instrument.close(preparedStatement);
            }
        };
    }

    public Papers save(UUID uuid, Runnable done) {
        return (connection, instrument) -> {
            PreparedStatement preparedStatement = null;
            try {
                HPlayer player = getPlayer(uuid);
                if (!player.isEdited()) {
                    return;
                }
                preparedStatement = connection.prepareStatement(
                        notes.update(new String[]{ "COINS" },
                                new Object[]{ player.getCoins() },
                                "UUID",
                                player.getUuid().toString())
                );
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(preparedStatement);
                done.run();
            }
        };
    }

    public Papers load(UUID uuid, Runnable done) {
        return (connection, instrument) -> {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement(
                        notes.selectWhere("COINS", "UUID", uuid.toString())
                );
                resultSet = preparedStatement.executeQuery();

                int coins;
                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement(
                            notes.insert(new String[]{ "UUID, COINS" }, new Object[]{uuid.toString(), 0})
                    );
                    preparedStatement.executeUpdate();
                    coins = 0;
                } else {
                    coins = resultSet.getInt(1);
                }
                players.put(uuid, new HPlayer(uuid, coins, false));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instrument.close(preparedStatement, resultSet);
                done.run();
            }
        };
    }

    @SneakyThrows
    public void saveAll() {
        int i = 0;
        for (HPlayer player : players.values()) {
            musician.offer(save(player.getUuid(), () -> {}));
            i++;
        }

        plugin.getLogger().warning("Saving " + i + " entries.");

        while (!musician.getBlockingQueue().isEmpty()) {
            Thread.sleep(50);
        }
    }

}
