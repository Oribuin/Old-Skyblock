package xyz.oribuin.skyblock;

import dev.esophose.guiframework.GuiFramework;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oribuin.skyblock.generators.ChunkGeneratorWorld;
import xyz.oribuin.skyblock.managers.CommandManager;
import xyz.oribuin.skyblock.managers.ConfigManager;
import xyz.oribuin.skyblock.managers.MessageManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Skyblock extends JavaPlugin {

    private static Skyblock instance;
    private ChunkGeneratorWorld chunkGenerator = new ChunkGeneratorWorld();

    public static Skyblock getInstance() {
        return instance;
    }

    private CommandManager commandManager;
    private ConfigManager configManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        instance = this;
        this.commandManager = new CommandManager(this);
        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(this);

        this.saveDefaultConfig();

        createFolder("schematics");
        createWorld("islands_normal", World.Environment.NORMAL);
        createWorld("islands_nether", World.Environment.NETHER);
    }

    private void createFolder(String folderName) {
        File file = new File(this.getDataFolder(), folderName);
        if (!file.exists())
            file.mkdir();
    }

    private void createWorld(String worldName, World.Environment environment) {
        this.getWorld(worldName.toLowerCase(), environment, chunkGenerator);

    }

    private World getWorld(String worldName, World.Environment environment, ChunkGeneratorWorld chunkGeneratorWorld) {
        WorldCreator worldCreator = WorldCreator.name(worldName)
                .type(WorldType.FLAT)
                .environment(environment)
                .generateStructures(false);

        World world = worldCreator.generator(chunkGeneratorWorld).createWorld();
        if (world != null) {
            world.setMonsterSpawnLimit(100);
            world.setAmbientSpawnLimit(100);
            world.setAnimalSpawnLimit(100);
            world.setWaterAnimalSpawnLimit(100);
        }
        return world;
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return chunkGenerator;
    }

    public void reload() {
        this.commandManager.reload();
        this.configManager.reload();
        this.messageManager.reload();
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

}
