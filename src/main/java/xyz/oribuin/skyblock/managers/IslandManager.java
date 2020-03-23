package xyz.oribuin.skyblock.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.api.events.IslandCreateEvent;

public class IslandManager {

    private final Skyblock plugin;

    public IslandManager(Skyblock plugin) {
        this.plugin = plugin;
    }

    public void createIsland(Player player) {


        IslandCreateEvent islandCreateEvent = new IslandCreateEvent();

        Bukkit.getPluginManager().callEvent(islandCreateEvent);
    }
}
