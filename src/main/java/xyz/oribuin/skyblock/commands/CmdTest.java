package xyz.oribuin.skyblock.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.Island;
import xyz.oribuin.skyblock.managers.island.IslandManager;
import xyz.oribuin.skyblock.utilities.Color;

public class CmdTest implements CommandExecutor {
    private final Skyblock plugin;
    public CmdTest(final Skyblock skyblock) {
        this.plugin = skyblock;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        Island island = new Island(plugin, 150, 150, player.getUniqueId());
        IslandManager islandManager = new IslandManager(plugin);

        islandManager.createIsland("&bTest", player.getUniqueId());
        player.sendMessage(Color.msg("&aCreated island: " + island.getName()));
        player.teleport(island.getCenter());
        return true;
    }

}
