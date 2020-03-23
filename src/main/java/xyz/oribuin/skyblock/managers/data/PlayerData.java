package xyz.oribuin.skyblock.managers.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;

import java.io.File;

public class PlayerData {

    private final Skyblock plugin;
    private final Player player;

    public PlayerData(Skyblock plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }
    public FileConfiguration getData() {
        File playerData = new File(plugin.getDataFolder(), "/playerdata/" + player.getUniqueId() + ".yml");
        FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(playerData);

        if (dataConfig.get("island") == null)
            dataConfig.set("island", "None");

        try {
            if (!playerData.exists())
                playerData.createNewFile();

            dataConfig.save(playerData.getAbsoluteFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataConfig;
    }

    public File getFile() {
        return new File(plugin.getDataFolder(), "/playerdata/" + player.getUniqueId() + ".yml");
    }
}
