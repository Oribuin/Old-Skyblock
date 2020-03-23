package xyz.oribuin.skyblock.utilities;

import org.bukkit.ChatColor;

public class Chat {
    public static String cl(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
