package xyz.oribuin.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.menus.MenuIslandCreation;
import xyz.oribuin.skyblock.utilities.Color;

public class CmdIsland implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("create") && sender instanceof Player) {
            Player player = (Player) sender;
            new MenuIslandCreation(player).openFor(args[1]);
            return true;
        }
        return true;

    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage(Color.msg("&a/is create <name>"));
    }
}
