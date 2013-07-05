package com.simplyian.easydb;

/**
 * EasyDB API.
 */
public class EasyDB {
    private static EasyDBPlugin instance;

    static public void setInstance(EasyDBPlugin plugin) {
        instance = plugin;
    }

    /**
     * Gets the EasyDB database instance.
     *
     * @return
     */
    static public Database getDb() {
        return instance.getDb();
    }
}
