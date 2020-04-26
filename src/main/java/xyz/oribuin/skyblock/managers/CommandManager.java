package xyz.oribuin.skyblock.managers;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.IslandManager;
import xyz.oribuin.skyblock.menus.MenuIslandCreation;
import xyz.oribuin.skyblock.utils.StringPlaceholders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager extends Manager implements TabExecutor {

    public CommandManager(Skyblock plugin) {
        super(plugin);

        PluginCommand trailsCommand = this.plugin.getCommand("island");
        if (trailsCommand != null) {
            trailsCommand.setExecutor(this);
            trailsCommand.setTabCompleter(this);
        }
    }

    @Override
    public void reload() {
        // Unused
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

        MessageManager messageManager = this.plugin.getMessageManager();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                onReloadCommand(sender);
            }
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (!(sender instanceof Player)) {
                    messageManager.sendMessage(sender, "player-only");
                    return true;
                }

                if (!sender.hasPermission("skyblock.island.create")) {
                    messageManager.sendMessage(sender, "invalid-permission");
                    return true;
                }

                Player player = (Player) sender;
                new MenuIslandCreation(player).openFor(args[1]);
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
