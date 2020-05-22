package xyz.oribuin.skyblock.managers;

import org.bukkit.Bukkit;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.Island;
import xyz.oribuin.skyblock.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.UUID;

public class DataManager extends Manager {

    public DataManager(Skyblock plugin) {
        super(plugin);
    }

    @Override
    public void reload() {
        FileUtils.createFile(plugin, plugin.getName().toLowerCase() + ".db");
        this.createTables();
    }

    public void createTables() {
        this.async(() -> this.plugin.getConnector().connect(connection -> {

            // Create the Island Data in the file.
            String createIsland = "CREATE TABLE IF NOT EXISTS " + this.getTablePrefix() + "island (" +
                    "owner_uuid VARCHAR(32), " +
                    "name VARCHAR(512), " +
                    "locked BOOLEAN, " +
                    "center_x INT, " +
                    "center_z INT, " +
                    "range INT, " +
                    "spawn_x int, " +
                    "spawn_y INT, " +
                    "spawn_z INT " +
                    "primary key(island_id, owner_uuid))";

            try (PreparedStatement statement = connection.prepareStatement(createIsland)) {
                statement.executeUpdate();
            }


        }));
    }

    private void async(Runnable asyncCallback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, asyncCallback);
    }

    private String getTablePrefix() {
        return plugin.getDescription().getName().toLowerCase() + "_";
    }
}
