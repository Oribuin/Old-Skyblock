package xyz.oribuin.skyblock.managers.island;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.*;
import org.bukkit.block.Biome;
import xyz.oribuin.skyblock.Skyblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Island {

    private Skyblock skyblock;
    private int size;
    private Location center;
    private World world;
    private UUID owner;
    private String name;
    private boolean locked;
    private Location spawnPoint;
    private Multiset<Material> tileEntityCount = HashMultiset.create();
    private Biome biome;
    private static final List<String> islandSettingsKey = new ArrayList<>();

    static {
        islandSettingsKey.clear();
        islandSettingsKey.add("");
    }
    private HashMap<SettingsFlag, Boolean> igs = new HashMap<>();
    private int levelHandicap;
    private WorldBorder worldBorder;

    public Island(Skyblock skyblock, String serial, List<String> islandSettingsKey) {
        this.skyblock = skyblock;

        int x = size / 2;
        int z = size / 2;

        this.world = Bukkit.getWorld("islands_normal");
        this.center = new Location(world, x, 72, z);
    }

    public void setIgsDefaults() {
        for (SettingsFlag flag : SettingsFlag.values()) {
            // Sort out flags
        }
    }
    public enum SettingsFlag {
        ANVIL,
        ARMOR_STAND,
        BEACON,
        BED,
        BREAK_BLOCKS,
        BREEDING,
        BREWING,
        BUCKET,
        BUTTON,
        COLLECT_LAVA,
        COLLECT_WATER,
        CHEST,
        CHORUS_FRUIT,
        CRAFTING,
        CREEPER_PAIN,
        CROP_TRAMPLE,
        DOOR,
        EGGS,
        ENCHANTING,
        ENDER_PEARL,
        FIRE,
        FIRE_EXTINGUISH,
        FIRE_SPREAD,
        GATE,
        HIVES,
        MOB_INVENTORY,
        MOB_RODING,
        HURT_ANIMALS,
        HURT_MONSTERS,
        LEASH,
        LEVER,
        MILKING,
        ANIMAL_SPAWN,
        MONSTER_SPAWN,
        MUSIC,
        PLACE_BLOCKS,
        PORTAL,
        PRESSURE_PLATE,
        SPAWN_EGGS,
        SHEARING,
        TRIDENT_USE,
        VILLAGER_TRADING,
        VISITOR_ITEM_DROP,
        VISITOR_ITEM_PICKUP
    }
}

/*
 * Here is just for me because I have gotten confused many times
 *
 * • You're defining the island methods
 * • So getRange() getMembers() ect
 * • Define size, players, upgrades, flags, everything.
 *
 * • This way you can use the methods across the plugin.
 * Jesus christ get yourself together
 */