package xyz.oribuin.skyblock.managers.island;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.utilities.Color;

import java.util.*;

public class Island {

    private Skyblock skyblock;
    private int getX;
    private int getZ;
    private Location center;
    private World world;
    private UUID owner;
    private String name;
    private boolean locked;
    private Location spawnPoint;
    private Multiset<Material> tileEntityCount = HashMultiset.create();
    private Biome biome;

    private EnumMap<SettingsFlag, Boolean> map = new EnumMap<>(SettingsFlag.class);
    private int levelHandicap;
    private WorldBorder worldBorder;

    public Island(Skyblock skyblock, String serial, List<String> islandSettingsKey) {
        this.skyblock = skyblock;
        int x = getX / 2;
        int z = getZ / 2;
        this.world = Bukkit.getWorld("islands_normal");
        this.center = new Location(world, x, 72, z);
    }

    public Island(Skyblock skyblock, int x, int z, UUID owner, String name) {
        this.skyblock = skyblock;
        this.world = Bukkit.getWorld("islands_normal");
        this.center = new Location(world, x, 72, z);
        this.owner = owner;
        this.name = name;
        this.locked = true;
    }

    public Island(Island island) {
        this.skyblock = island.skyblock;
        this.biome = island.biome == null ? null : Biome.valueOf(island.biome.name());
        this.center = island.center != null ? island.center.clone() : null;
        this.getX = island.getX;
        this.getZ = island.getZ;
        this.locked = island.locked;
        this.levelHandicap = island.levelHandicap;
        this.owner = island.owner == null ? null : UUID.fromString(island.owner.toString());
        this.name = island.name;
        this.spawnPoint = island.spawnPoint;
        this.worldBorder = island.worldBorder;
        this.tileEntityCount.addAll(island.tileEntityCount);
        this.world = island.world == null ? null : Bukkit.getWorld(island.world.getUID());
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public int getSize() {
        return getX * getZ;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Player getOwner() {
        return Bukkit.getPlayer(this.owner);
    }

    public void setOwner(Island island, UUID uuid) {
        this.owner = uuid;
    }

    public UUID getOwnerUUID() {
        return this.owner;
    }

    public Island getIsland() {
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = Color.msg(name);
    }

    public int getLevel() {
        return this.levelHandicap;
    }

    public World getWorld() {
        return this.world;
    }

    public Location getSpawnPoint() {
        return this.spawnPoint;
    }

    public void setSpawnPoint(Location location) {
        this.spawnPoint = location;
    }

    public Location getCenter() {
        return this.center;
    }

    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }

    public void setWorldBorder(WorldBorder worldBorder) {
        if (worldBorder != null) {
            worldBorder.setCenter(getIsland().getCenter());
            worldBorder.setSize(getIsland().getSize());
            worldBorder.setDamageAmount(0);
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