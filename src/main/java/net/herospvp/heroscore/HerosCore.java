package net.herospvp.heroscore;

import lombok.Getter;
import lombok.SneakyThrows;
import net.herospvp.database.Director;
import net.herospvp.database.Musician;
import net.herospvp.database.items.Instrument;
import net.herospvp.heroscore.coins.commands.CoinsAdminCommand;
import net.herospvp.heroscore.coins.commands.CoinsCommand;
import net.herospvp.heroscore.coins.expansions.CoinsExpansion;
import net.herospvp.heroscore.coins.expansions.StatsExpansion;
import net.herospvp.heroscore.coins.listeners.ConnectionListeners;
import net.herospvp.heroscore.handlers.PacketsHandler;
import net.herospvp.heroscore.handlers.PlayersHandler;
import net.herospvp.heroscore.handlers.ThreadsHandler;
import net.herospvp.heroscore.utils.Configuration;
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
    private ThreadsHandler threadsHandler;
    private Configuration conf;

    @Override
    public void onEnable() {
        instance = this;

        // load default configuration
        saveDefaultConfig();
        conf = new Configuration(this);

        // load database
        this.director = new Director();

        // an instrument can handle a single connection to a single database
        Instrument instrument = new Instrument(
                null, conf.getString("db.ip"), conf.getString("db.port"),
                conf.getString("db.database"), conf.getString("db.user"), conf.getString("db.password"),
                conf.getString("db.url"), conf.getString("db.driver"), null, true,
                conf.getInt("db.max-pool-size")
        );
        instrument.assemble();

        this.director.addInstrument("heros-core", instrument);

        this.musician = new Musician(director, instrument, conf.getBoolean("db.debug"));

        // load handlers
        this.playersHandler = new PlayersHandler(this);
        this.debugHandler = new Debug(this);
        this.threadsHandler = new ThreadsHandler();

        // load listeners
        new GUIListener(this);
        new ConnectionListeners(this);
        new PacketsHandler(this);

        // load PAPI expansions
        new CoinsExpansion(this).register();
        new StatsExpansion(this).register();

        // load commands
        new CoinsCommand(this);
        new CoinsAdminCommand(this);

        // load tasks
        // new SaveTask(this).runTaskTimerAsynchronously(this, 20*60*60, 20*60*60);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        playersHandler.saveAll(true);
        threadsHandler.exterminate();
    }

}
