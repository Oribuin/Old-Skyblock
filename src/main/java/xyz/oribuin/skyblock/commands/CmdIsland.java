package xyz.oribuin.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
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
            new IslandManager().createIsland(args[1], "island", new Location(Bukkit.getWorld("islands_normal"), 0, 72, 0), player.getUniqueId(), 100);
            player.sendMessage(Color.msg("&aCreated island: &b" + args[1]));
            player.teleport(new Location(Bukkit.getWorld("islands_normal"), 0, 72, 0));
            return true;
        }
        return true;

    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage(Color.msg("&a/is create <name>"));
    }
}
