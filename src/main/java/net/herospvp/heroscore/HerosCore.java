package net.herospvp.heroscore;

import lombok.Getter;
import net.herospvp.database.Director;
import net.herospvp.database.Musician;
import net.herospvp.database.items.Instrument;
import net.herospvp.heroscore.coins.commands.CoinsAdminCommand;
import net.herospvp.heroscore.coins.commands.CoinsCommand;
import net.herospvp.heroscore.coins.expansions.CoinsExpansion;
import net.herospvp.heroscore.coins.listeners.ConnectionListeners;
import net.herospvp.heroscore.handlers.PlayersHandler;
import net.herospvp.heroscore.handlers.ThreadsHandler;
import net.herospvp.heroscore.tasks.SaveTask;
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
        Instrument guitar = new Instrument(conf.getString("mysql.ip"), conf.getString("mysql.port"),
                conf.getString("mysql.user"), conf.getString("mysql.password"), conf.getString("mysql.database"),
                "?useSSL=false&characterEncoding=utf8", null, true,
                conf.getInt("mysql.max-pool-size"));

        this.director.addInstrument("guitar", guitar);

        this.musician = new Musician(director, guitar, conf.getBoolean("mysql.debug"));

        // load handlers
        this.playersHandler = new PlayersHandler(this);
        this.debugHandler = new Debug(this);
        this.threadsHandler = new ThreadsHandler();

        // load listeners
        new GUIListener(this);
        new ConnectionListeners(this);

        // load PAPI expansions
        new CoinsExpansion(this).register();

        // load commands
        new CoinsCommand(this);
        new CoinsAdminCommand(this);

        // load tasks
        new SaveTask(this).runTaskTimerAsynchronously(this, 20*60*10, 20*60*10);
    }

    @Override
    public void onDisable() {
        playersHandler.saveAll();
        threadsHandler.exterminate();
    }

}
