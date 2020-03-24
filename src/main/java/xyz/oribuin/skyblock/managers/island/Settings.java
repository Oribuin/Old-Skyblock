package xyz.oribuin.skyblock.managers.island;

import com.sun.org.apache.xpath.internal.operations.Bool;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import javax.print.DocFlavor;
import java.util.*;

public class Settings {

    public static int resetWait;
    public static int resetLimit;
    public static int maxTeamSize;
    public static String worldName;
    public static int monsterSpawnLimit;
    public static int animalSpawnLimit;
    public static int waterAnimalSpawnLimit;

    public static final Map<Island.SettingsFlag, Boolean> defaultWorldSettings = new HashMap<>();
    public static final Map<Island.SettingsFlag, Boolean> defaultSpawnSettings = new HashMap<>();
    public static final Map<Island.SettingsFlag, Boolean> visitorSettings = new HashMap<>();

    public static boolean allowChestDamage;
    public static boolean allowCreeperDamage;
    public static boolean allowCreeperGriefing;
    public static boolean allowEndermanGriefing;
    public static boolean allowPistonPush;
    public static boolean allowTNTDamage;
    public static boolean allowVisitorKeepInvOnDeath;
    public static boolean restrictWither;
    public static ItemStack[] chestItems;
    public static int islandSize;
    public static int abandonedIslandLevel;
    public static double netherSpawnRadius;
    public static boolean resetMoney;
    public static boolean damageOps;
    public static boolean endermanDeathDrops;
    public static boolean invincibleVisitors;
    public static HashSet<EntityDamageEvent.DamageCause> visitorDamagePrevention;

    public static boolean logInRemoveMobs;
    public static boolean islandRemoveMobs;


    public static HashMap<BlockData, Integer> blockValues;

    public static double biomeCost;
    public static Biome defaultBiome;

    public static List<String> resetCommands = new ArrayList<>();

    public static int inviteWait;

    public static int maxWarps;

}
