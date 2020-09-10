package xyz.oribuin.skyblock.listener

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPortalEnterEvent
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.manager.ConfigManager
import xyz.oribuin.skyblock.manager.MessageManager

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

    /*
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if (event.player.isFlying)
            return

        val loc = event.player.location
        val location = Location(loc.world, loc.x, loc.y - 1, loc.z)

        loc.world?.getBlockAt(location.clone().subtract(1.0, 0.0, 1.0))?.type = Material.BLUE_STAINED_GLASS
        loc.world?.getBlockAt(location.clone().add(1.0, 0.0, 1.0))?.type = Material.RED_STAINED_GLASS
    }

     */


}
