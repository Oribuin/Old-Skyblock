package xyz.oribuin.skyblock.managers.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.Manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class IslandManager extends Manager {

    public IslandManager(Skyblock plugin) {
        super(plugin);
    }

    public Island createIsland(String name, String schematicName, Location location, UUID owner, int islandRange) {
        Island island = new Island(name, location, owner, islandRange);

        this.createSchematic(island, schematicName);
        plugin.getDataManager().createIslandData(island);
        return island;
    }

    @Override
    public void reload() {
        // Unused
    }

    private void createSchematic(Island island, String schematicName) {
        World world = island.getCenter().getWorld();
        File file = new File(plugin.getDataFolder(), "/schematics/" + schematicName + ".schematic");

        if (world == null)
            return;

        if (!file.exists())
            throw new IllegalArgumentException("Invalid Schematic: " + schematicName);

        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        if (clipboardFormat == null)
            return;

        try (ClipboardReader reader = clipboardFormat.getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();

            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(world), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(island.getCenter().getX(), island.getCenter().getY(), island.getCenter().getZ()))
                        .build();
                Operations.complete(operation);
            }
        } catch (IOException | WorldEditException e) {
            e.printStackTrace();
        }

    }


}
