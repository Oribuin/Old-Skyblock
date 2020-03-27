package xyz.oribuin.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CmdTest implements CommandExecutor {
    private Skyblock skyblock;
    public CmdTest(Skyblock skyblock) {
        this.skyblock = skyblock;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;


        loadSchematics();
        return true;
    }

    private void loadSchematics() {
        File schematicsFolder = new File(skyblock.getDataFolder(), "schematics");
        if (!schematicsFolder.exists())
            schematicsFolder.mkdir();

        if (skyblock.getResource("/schematics/island.schematic") != null) {
            skyblock.getLogger().info("Default schematic does not exist, saving it");
            skyblock.saveResource("/schematics/island.schematic", true);

        }
    }
}
