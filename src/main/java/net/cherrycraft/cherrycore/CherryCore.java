package net.cherrycraft.cherrycore;

import net.cherrycraft.cherrycore.database.MySQL;
import net.cherrycraft.cherrycore.manager.LanguageManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CherryCore extends JavaPlugin {

    static CherryCore instance;
    private final YamlConfiguration conf = new YamlConfiguration();
    Logger logger = Logger.getLogger("ReefCore");
    LanguageManager languageManager = new LanguageManager();

    public static CherryCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        loadDatabase();
        languageManager.loadAllLanguages();
        Loader.registerCommands(this);
        Loader.registerListeners(this);
        Loader.registerTasks(this);
        Loader.loadWorlds(this);
        logger.info("ReefCore has been enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("ReefCore has been disabled.");
    }

    void loadDatabase() {
        String host = getConfig().getString("database.host");
        int port = getConfig().getInt("database.port");
        String database = getConfig().getString("database.database");
        String user = getConfig().getString("database.username");
        String password = getConfig().getString("database.password");


        MySQL.Login(host, database, user, password, port);
        MySQL.createTables();
    }


}