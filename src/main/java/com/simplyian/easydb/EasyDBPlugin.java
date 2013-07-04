package com.simplyian.easydb;

import com.simplyian.easydb.command.DBReloadCommand;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * EasyDBPlugin Main class.
 */
public class EasyDBPlugin extends JavaPlugin {
    private Database db;

    @Override
    public void onEnable() {
        // Make sure the config has been made
        saveDefaultConfig();

        EasyDB.setInstance(this);
        reloadDb();

        getCommand("dbreload").setExecutor(new DBReloadCommand(this));

        if (!db.isValid()) {
            getLogger().log(Level.WARNING, "Database credentials are invalid. Please check your credentials and run /dbreload.");
        } else {
            getLogger().log(Level.INFO, "Connected to database at " + db.getSource().getServerName() + ":" + db.getSource().getPort() + " successfully.");
        }
        getLogger().log(Level.INFO, "Plugin loaded.");
    }

    @Override
    public void onDisable() {
        EasyDB.setInstance(null);
        getLogger().log(Level.INFO, "Plugin unloaded.");
    }

    /**
     * Loads the database.
     */
    public void reloadDb() {
        ConfigurationSection s = getConfig().getConfigurationSection("db");
        if (s == null) {
            s = getConfig().createSection("db");
        }

        String dbHost = s.getString("host");
        int dbPort = s.getInt("port");
        String dbUser = s.getString("user");
        String dbPass = s.getString("pass", "");
        String dbName = s.getString("name");
        db = new Database(this, dbUser, dbPass, dbHost, dbPort, dbName);
    }

    /**
     * Gets the database.
     *
     * @return
     */
    public Database getDb() {
        return db;
    }
}
