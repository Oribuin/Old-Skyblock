package xyz.oribuin.skyblock.listeners

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPortalEnterEvent
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.managers.ConfigManager
import xyz.oribuin.skyblock.managers.MessageManager

class GeneralListeners(private val plugin: Skyblock) : Listener {
    private val msg = plugin.getManager(MessageManager::class)

    // Cancel portal
    @EventHandler
    fun onPortalEnter(event: EntityPortalEnterEvent) {
        if (event.entity.world == Bukkit.getWorld(ConfigManager.Setting.WORLD.string)) {
            event.entity.portalCooldown = Integer.MAX_VALUE
            if (event.entity is Player) {
                msg.sendMessage(event.entity, "listener-messages.cant-enter-portal")
            }
        }
    }


}