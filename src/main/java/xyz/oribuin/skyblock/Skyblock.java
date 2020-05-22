package xyz.oribuin.skyblock;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.oribuin.skyblock.database.DatabaseConnector;
import xyz.oribuin.skyblock.database.SQLiteConnector;
import xyz.oribuin.skyblock.managers.CommandManager;
import xyz.oribuin.skyblock.managers.ConfigManager;
import xyz.oribuin.skyblock.managers.MessageManager;
import xyz.oribuin.skyblock.managers.WorldManager;

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

    @Override
    public void onEnable() {
        instance = this;
        this.commandManager = new CommandManager(this);
        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(this);
        this.worldManager = new WorldManager(this);

        this.saveDefaultConfig();
        this.reload();

        this.connector = new SQLiteConnector(this);

        createFolder("schematics");
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
