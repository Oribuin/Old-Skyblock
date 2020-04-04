package xyz.oribuin.skyblock.utilities;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import xyz.oribuin.skyblock.Skyblock;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

    private Skyblock plugin;

    public TabComplete(Skyblock plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList<>();
        List<String> completions = new ArrayList<>();

        if (sender.hasPermission("skyblock.island.create"))
            completions.add("create");
        if (sender.hasPermission("skyblock.admin.reload"))
            completions.add("reload");

        return completions;
    }
}
