package com.simplyian.easydb;

import com.simplyian.easydb.command.DBReloadCommand;
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

        reloadDb();

        getCommand("dbreload").setExecutor(new DBReloadCommand(this));
    }

    /**
     * Loads the database.
     */
    public void reloadDb() {
        String dbUser = getConfig().getString("db.user");
        String dbPass = getConfig().getString("db.pass", "");
        String dbHost = getConfig().getString("db.host");
        int dbPort = getConfig().getInt("db.port");
        String dbName = getConfig().getString("db.name");
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
