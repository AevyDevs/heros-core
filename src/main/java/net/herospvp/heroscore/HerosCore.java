package net.herospvp.heroscore;

import lombok.Getter;
import net.herospvp.heroscore.coins.commands.CoinsAdminCommand;
import net.herospvp.heroscore.coins.commands.CoinsCommand;
import net.herospvp.heroscore.coins.expansions.CoinsExpansion;
import net.herospvp.heroscore.coins.listeners.ConnectionListeners;
import net.herospvp.heroscore.tasks.SaveTask;
import net.herospvp.heroscore.database.Director;
import net.herospvp.heroscore.database.Musician;
import net.herospvp.heroscore.database.items.Instrument;
import net.herospvp.heroscore.handlers.PlayersHandler;
import net.herospvp.heroscore.utils.inventory.GUIListener;
import net.herospvp.heroscore.utils.strings.Debug;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class HerosCore extends JavaPlugin {
    private static HerosCore instance;

    private Director director;
    private Musician musician;

    private Debug debugHandler;
    private PlayersHandler playersHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.director = new Director();
        Instrument guitar = new Instrument(getConfig().getString("mysql.ip"), getConfig().getString("mysql.port"),
                getConfig().getString("mysql.user"), getConfig().getString("mysql.password"), getConfig().getString("mysql.database"),
                "?useSSL=false&characterEncoding=utf8", null, true, 1);
        this.director.addInstrument("guitar", guitar);
        this.musician = new Musician(guitar);

        this.playersHandler = new PlayersHandler(this);
        this.debugHandler = new Debug(this);

        new GUIListener(this);
        new ConnectionListeners(this);

        new CoinsExpansion(this).register();

        new CoinsCommand(this);
        new CoinsAdminCommand(this);

        new SaveTask(this).runTaskTimerAsynchronously(this, 20*60*10, 20*60*10);
    }

    @Override
    public void onDisable() {
        playersHandler.saveAll();
    }
}
