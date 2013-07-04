package com.simplyian.easydb;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * Database stuff. MySQL sucks.
 */
public final class Database {
    private final ResultSetHandler SCALAR_HANDLER = new ScalarHandler();

    private final EasyDB plugin;

    private final MysqlDataSource source;

    public Database(EasyDB plugin, String user, String pass, String host, int port, String database) {
        this.plugin = plugin;

        source = new MysqlDataSource();
        source.setUser(user);
        source.setPassword(pass);
        source.setServerName(host);
        source.setPort(port);
        source.setDatabaseName(database);
    }

    /**
     * Attempts to connect to the database to check if the database credentials
     * are valid.
     *
     * @return
     */
    public boolean isValid() {
        try {
            return source.getConnection() != null;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Executes a database update.
     *
     * @param query
     */
    public void update(String query, Object... params) {
        QueryRunner run = new QueryRunner(source);
        try {
            if (params == null) {
                run.update(query);
            } else {
                run.update(query, params);
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not run database query '" + query + "'!", ex);
        }
    }

    /**
     * Executes a database query.
     *
     * @param <T>
     * @param query
     * @param handler
     * @param params
     * @return
     */
    public <T> T query(String query, ResultSetHandler<T> handler, Object... params) {
        QueryRunner run = new QueryRunner(source);
        try {
            if (params != null) {
                return run.query(query, handler, params);
            } else {
                return run.query(query, handler);
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not execute database query '" + query + "'!", ex);
        }
        return null;
    }

    /**
     * Gets a value from the database. The column desired must be the first.
     *
     * @param query
     * @param def The default value to return if the value is null.
     * @param params
     * @return
     */
    public Object get(String query, Object def, Object... params) {
        Object ret = query(query, SCALAR_HANDLER, params);
        if (ret == null) {
            return def;
        }
        return ret;
    }
}
