package xyz.oribuin.skyblock.library

import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Consumer

object PluginUtils {
    fun formatTime(time: Long): String {
        return SimpleDateFormat("HH:mm dd/m/yyy").format(Date(time))
    }

    fun transformName(itemStack: ItemStack): String {
        return StringUtils.capitalize(itemStack.type.name.replace("_", " ").toLowerCase())
    }

    fun isNumber(string: String): Boolean {
        try {
            string.toInt()
        } catch (ex: NumberFormatException) {
            return false
        }
        return true
    }

    @JvmStatic
    fun formatToHex(color: Color?): String {
        if (color == null)
            return "#FFFFFF"

        return "#${String.format("%02x%02x%02x", color.red, color.green, color.blue)}"
    }

    @JvmStatic
    fun createDelay(plugin: Plugin, delay: Int, task: Consumer<BukkitTask>) {
        Bukkit.getScheduler().runTaskLater(plugin, task, (delay * 20).toLong())
    }
}