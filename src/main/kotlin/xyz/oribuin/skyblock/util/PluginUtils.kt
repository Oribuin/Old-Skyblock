package xyz.oribuin.skyblock.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.manager.ConfigManager
import java.text.SimpleDateFormat
import java.util.*

object PluginUtils {
    private val cooldownList = mutableMapOf<UUID, Long>()

    @JvmStatic
    fun debug(string: String) {
        val plugin = Bukkit.getPluginManager().getPlugin("Skyblock") ?: return
        if (plugin.config.getBoolean("debug", true)) {
            plugin.logger.info("DEBUG: $string")
        }
    }

    @JvmStatic
    fun formatTime(long: Long): String {
        return SimpleDateFormat(ConfigManager.Setting.TIME.string).format(Date(long))
    }

    @JvmStatic
    fun createDelay(player: Player, cooldown: Int, secondLeft: Runnable?, warning: Runnable?) {

        if (cooldownList.containsKey(player.uniqueId)) {
            val secondsLeft = (cooldownList[player.uniqueId] ?: return).div(1000).plus(cooldown.toLong()).minus(System.currentTimeMillis().div(1000))

            if (secondsLeft < 0) {
                secondLeft?.run()
                return
            }

        }

        cooldownList[player.uniqueId] = System.currentTimeMillis()
        warning?.run()
    }

    @JvmField
    val prefix = "<g:#f953c6:#b91d73>Skyblock &f»"
}
