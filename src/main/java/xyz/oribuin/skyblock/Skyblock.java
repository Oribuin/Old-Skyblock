package xyz.oribuin.skyblock;

import org.bukkit.*;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oribuin.skyblock.generators.ChunkGeneratorWorld;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Skyblock extends JavaPlugin {

    private static Skyblock instance;

    private ChunkGeneratorWorld chunkGenerator = new ChunkGeneratorWorld(this);

    public static Skyblock getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        this.saveDefaultConfig();

        createFile("messages.yml");
        createFolder("islanddata");
        createFolder("playerdata");
        createFolder("schematics");

        createWorld("islands_normal", World.Environment.NORMAL);
        createWorld("islands_nether", World.Environment.NETHER);
    }


    private void createFile(String fileName) {
        File file = new File(this.getDataFolder(), fileName);
        if (!file.exists()) {
            try (InputStream inStream = this.getResource(fileName)) {
                Files.copy(inStream, Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFolder(String folderName) {
        File file = new File(this.getDataFolder(), folderName);
        if (!file.exists())
            file.mkdir();
    }


    private void createWorld(String worldName, World.Environment environment) {
        String getWorldName = worldName.toLowerCase();
        this.getWorld(getWorldName, environment, chunkGenerator);

    }

    private World getWorld(String worldName, World.Environment environment, ChunkGeneratorWorld chunkGeneratorWorld) {
        WorldCreator worldCreator = WorldCreator.name(worldName)
                .type(WorldType.FLAT)
                .environment(environment)
                .generateStructures(false);

        World world = worldCreator.generator(chunkGeneratorWorld).createWorld();
        if (world != null) {
            world.setMonsterSpawnLimit(getConfig().getInt("item-settings.limits.monster"));
            world.setAmbientSpawnLimit(getConfig().getInt("item-settings.limits.ambient"));
            world.setAnimalSpawnLimit(getConfig().getInt("item-settings.limits.animal"));
            world.setWaterAnimalSpawnLimit(getConfig().getInt("item-settings.limits.water"));
        }
        return world;
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return chunkGenerator;
    }
}
