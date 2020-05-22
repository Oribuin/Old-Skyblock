package xyz.oribuin.skyblock;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.oribuin.skyblock.database.DatabaseConnector;
import xyz.oribuin.skyblock.database.SQLiteConnector;
import xyz.oribuin.skyblock.managers.*;
import xyz.oribuin.skyblock.utils.FileUtils;

import java.io.File;

public class Skyblock extends JavaPlugin {

    private static Skyblock instance;

    public static Skyblock getInstance() {
        return instance;
    }

    private DatabaseConnector connector;
    private CommandManager commandManager;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private WorldManager worldManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        this.connector = new SQLiteConnector(this);

        // Register managers
        this.commandManager = new CommandManager(this);
        this.configManager = new ConfigManager(this);
        this.dataManager = new DataManager(this);
        this.messageManager = new MessageManager(this);
        this.worldManager = new WorldManager(this);

        this.saveDefaultConfig();
        this.reload();
    }

    private void createFolder(String folderName) {
        File file = new File(this.getDataFolder(), folderName);
        if (!file.exists())
            file.mkdir();
    }

    public void reload() {
        this.commandManager.reload();
        this.configManager.reload();
        this.messageManager.reload();
        this.worldManager.reload();
        this.dataManager.reload();
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }

    public DatabaseConnector getConnector() {
        return connector;
    }
}
