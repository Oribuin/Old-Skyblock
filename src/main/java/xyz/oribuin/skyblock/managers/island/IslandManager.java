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
import org.bukkit.Material;
import org.bukkit.World;
import xyz.oribuin.skyblock.Skyblock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class IslandManager {

    public void createIsland(String name, Location location, UUID owner, int islandRange) {
        Island island = new Island(name, location, owner, islandRange);
        World world = island.getCenter().getWorld();
        if (world == null) return;

        world.getBlockAt(location).setType(Material.BEDROCK);
        File file = new File(Skyblock.getInstance().getDataFolder(), "/schematics/island.schematic");

        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = clipboardFormat.getReader(new FileInputStream(file))) {
            Clipboard clipboard = reader.read();

            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(world), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                        .build();
                Operations.complete(operation);
            }
        } catch (IOException | WorldEditException e) {
            e.printStackTrace();
        }

        /*
        world.getWorldBorder().setCenter(island.getCenter());
        world.getWorldBorder().setSize(islandRange);
         */
    }
}
