package xyz.oribuin.skyblock.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.Island;
import xyz.oribuin.skyblock.managers.island.IslandManager;
import xyz.oribuin.skyblock.utils.StringPlaceholders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager extends Manager implements TabExecutor {

    public CommandManager(Skyblock plugin) {
        super(plugin);

        PluginCommand islandCommand = this.plugin.getCommand("island");
        if (islandCommand != null) {
            islandCommand.setExecutor(this);
            islandCommand.setTabCompleter(this);
        }
    }

    @Override
    public void reload() {
        // Unused
    }

    public void onIslandCreateCommand(Player sender, String name) {
        IslandManager islandManager = new IslandManager(plugin);
        Island island = islandManager.createIsland(name, "island", new Location(Bukkit.getWorld(ConfigManager.Setting.WORLD_NAME.getString()), 0, 50, 0), sender.getUniqueId(), 100);

        sender.teleport(island.getSpawnPoint());
    }

    public void onReloadCommand(CommandSender sender) {
        MessageManager messageManager = this.plugin.getMessageManager();
        if (!sender.hasPermission("skyblock.reload")) {
            messageManager.sendMessage(sender, "invalid-permission");
            return;
        }

        this.plugin.reload();
        messageManager.sendMessage(sender, "reload", StringPlaceholders.single("version", this.plugin.getDescription().getVersion()));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("island"))
            return true;

        if (args.length == 1) {

            switch (args[0].toLowerCase()) {
                case "reload":
                    onReloadCommand(sender);
                    break;
                case "create":
                    if (sender instanceof Player)
                        onIslandCreateCommand((Player) sender, "test");
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("trails"))
            return Collections.emptyList();

        List<String> suggestions = new ArrayList<>();

        return suggestions;
    }

}
