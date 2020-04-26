package xyz.oribuin.skyblock.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.utils.FileUtils;
import xyz.oribuin.skyblock.utils.StringPlaceholders;

import java.io.File;

public class MessageManager extends Manager {

    private final static String MESSAGE_CONFIG = "messages.yml";

    private FileConfiguration messageConfig;

    public MessageManager(Skyblock plugin) {
        super(plugin);
    }

    @Override
    public void reload() {
        FileUtils.createFile(this.plugin, MESSAGE_CONFIG);
        this.messageConfig = YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder(), MESSAGE_CONFIG));
    }

    public void sendMessage(CommandSender sender, String messageId) {
        this.sendMessage(sender, messageId, StringPlaceholders.empty());
    }

    public void sendMessage(CommandSender sender, String messageId, StringPlaceholders placeholders) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', placeholders.apply(this.messageConfig.getString(messageId))));
    }
}
