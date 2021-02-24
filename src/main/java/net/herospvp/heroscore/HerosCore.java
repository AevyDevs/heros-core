package net.herospvp.heroscore;

import lombok.Getter;
import lombok.SneakyThrows;
import net.herospvp.database.Main;
import net.herospvp.database.lib.Director;
import net.herospvp.database.lib.Musician;
import net.herospvp.database.lib.items.Instrument;
import net.herospvp.heroscore.coins.commands.CoinsAdminCommands;
import net.herospvp.heroscore.coins.commands.CoinsCommands;
import net.herospvp.heroscore.coins.expansions.CoinsExpansion;
import net.herospvp.heroscore.coins.expansions.StatsExpansion;
import net.herospvp.heroscore.coins.listeners.ConnectionListeners;
import net.herospvp.heroscore.handlers.PacketsHandler;
import net.herospvp.heroscore.handlers.PlayersHandler;
import net.herospvp.heroscore.handlers.ThreadsHandler;
import net.herospvp.heroscore.utils.Configuration;
import net.herospvp.heroscore.utils.Dependencies;
import net.herospvp.heroscore.utils.LoggerUtils;
import net.herospvp.heroscore.utils.inventory.GUIListener;
import net.herospvp.heroscore.utils.strings.Debug;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class HerosCore extends JavaPlugin {

    private static HerosCore instance;

    private LoggerUtils loggerUtils;
    private Dependencies dependencies;

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

        //
        // checking dependencies
        //
        loggerUtils = new LoggerUtils(this);

        dependencies = new Dependencies(this);
        boolean val = dependencies.check("database-lib", "ProtocolLib", "PlaceholderAPI");

        if (!val) return;

        //
        // database-lib
        //
        director = getPlugin(Main.class).getDirector();

        Instrument instrument = new Instrument(
                conf.getString("db.ip"), conf.getString("db.port"),
                conf.getString("db.database"), conf.getString("db.user"), conf.getString("db.password"),
                conf.getString("db.url"), conf.getString("db.driver"), null, true,
                conf.getInt("db.max-pool-size")
        );
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
        new CoinsCommands(this, null, "coins", false, null, false);
        new CoinsAdminCommands(this, "herospvp.admin", "coinsadmin", false, null, false);

    }

    @SneakyThrows
    @Override
    public void onDisable() {
        playersHandler.saveAll();
        threadsHandler.exterminate();
    }

}
