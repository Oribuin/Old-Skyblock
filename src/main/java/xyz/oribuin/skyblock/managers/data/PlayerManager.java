package xyz.oribuin.skyblock.managers.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;

import java.io.File;
import java.util.Objects;

public class PlayerManager {

    private final Skyblock plugin;
    private PlayerData playerData;
    private final Player player;

    public PlayerManager(Skyblock plugin, Player player) {
        this.plugin = plugin;
        playerData = new PlayerData(plugin, player);
        this.player = player;
    }

    public Player getIslandOwner() {
        FileConfiguration playerConf = playerData.getData();
        return Bukkit.getPlayer(Objects.requireNonNull(playerConf.getString("island.owner")));
    }

}
