package xyz.oribuin.skyblock.managers;

import org.bukkit.Bukkit;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.Island;
import xyz.oribuin.skyblock.utils.FileUtils;

import java.sql.PreparedStatement;

public class DataManager extends Manager {

    public DataManager(Skyblock plugin) {
        super(plugin);
    }

    @Override
    public void reload() {
        FileUtils.createFile(plugin, plugin.getName().toLowerCase() + ".db");
        this.createTables();
    }

    private void createTables() {
        this.async(() -> this.plugin.getConnector().connect(connection -> {

            // Create the Island Data in the file.
            String createIsland = "CREATE TABLE IF NOT EXISTS " + this.getTablePrefix() + "island (" +
                    "uuid TXT, " +
                    "name TXT, " +
                    "locked BOOLEAN, " +
                    "center_x DOUBLE, " +
                    "center_z DOUBLE, " +
                    "range INT, " +
                    "spawn_x DOUBLE, " +
                    "spawn_y DOUBLE, " +
                    "spawn_z DOUBLE, " +
                    "primary key(uuid))";

            try (PreparedStatement statement = connection.prepareStatement(createIsland)) {
                statement.executeUpdate();
            }

        }));
    }

    public void createIslandData(Island island) {
        this.async(() -> plugin.getConnector().connect(connection -> {
            String createIslandData = "INSERT INTO " + this.getTablePrefix() + "island (uuid, name, locked, center_x, center_z, range, spawn_x, spawn_y, spawn_z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(createIslandData)) {
                statement.setString(1, island.getOwner().toString());
                statement.setString(2, island.getName());
                statement.setBoolean(3, island.isLocked());
                statement.setDouble(4, island.getCenter().getX());
                statement.setDouble(5, island.getCenter().getZ());
                statement.setInt(6, island.getIslandRange());
                statement.setDouble(7, island.getSpawnPoint().getX());
                statement.setDouble(8, island.getSpawnPoint().getY());
                statement.setDouble(9, island.getSpawnPoint().getZ());
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
