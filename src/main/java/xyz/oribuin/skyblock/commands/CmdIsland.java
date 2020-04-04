package xyz.oribuin.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.Island;
import xyz.oribuin.skyblock.managers.island.IslandManager;
import xyz.oribuin.skyblock.utilities.Color;

public class CmdIsland implements CommandExecutor {

    private Skyblock plugin;

    public CmdIsland(Skyblock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("create") && sender instanceof Player) {
            Player player = (Player) sender;

            Island island = new Island(plugin, 150, 150, player.getUniqueId(), Color.msg(args[1]));
            IslandManager islandManager = new IslandManager(plugin);

            islandManager.createIsland(args[1], player.getUniqueId());
            player.sendMessage(Color.msg("&aCreated island: &b" + island.getName()));
            player.teleport(island.getCenter());
        }
        return true;
    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage(Color.msg("&a/is"));
    }
}
