package xyz.oribuin.skyblock.utilities;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.utilities.Color;

import java.io.File;

public class MessageUtils {
    private CommandSender sender;
    private Player player;

    public MessageUtils(CommandSender sender) {
        this.sender = sender;
    }

    public MessageUtils(Player player) {
        this.player = player;
    }

    public MessageUtils(CommandSender sender, Player player) {
        this.sender = sender;
        this.player = player;
    }

    public String getMessage(String msgTile) {
        switch (msgTile) {
            case "invalidPermission":
                return Color.msg(getConfig().getString("invalid-permission"));
            case "reload":
                return Color.msg(getConfig().getString("reload").replaceAll("%version%", Skyblock.getInstance().getDescription().getVersion()));
            case "invalidPlayer":
                return Color.msg(getConfig().getString("invalidPlayer").replaceAll("%player%", player.getName()));
            case "hasIsland":
                //return Color.msg(getConfig().getString("hasIsland").replaceAll("%player%", player.getName()));
                return Color.msg("&%player% already has an island.").replaceAll("%player%", player.getName());

        }

        return msgTile;
    }

    public String getUsage(String command) {
        return Color.msg(getConfig().getString("invalid-args").replaceAll("%usage%", Skyblock.getInstance().getCommand(command).getUsage()));
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(new File(Skyblock.getInstance().getDataFolder(), "messages.yml"));
    }
}