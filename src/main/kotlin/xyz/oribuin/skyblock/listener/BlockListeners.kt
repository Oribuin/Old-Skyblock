package xyz.oribuin.skyblock.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.IslandMember
import xyz.oribuin.skyblock.manager.ConfigManager
import xyz.oribuin.skyblock.manager.MessageManager

class BlockListeners(private val plugin: Skyblock) : Listener {
    private val msg = plugin.getManager(MessageManager::class)

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)
        if (event.player.world != Bukkit.getWorld(ConfigManager.Setting.WORLD.string))
            return

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-break-block")
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onBlockPlace(event: BlockPlaceEvent) {
        val member = IslandMember(plugin, event.player.uniqueId)
        if (event.player.world != Bukkit.getWorld(ConfigManager.Setting.WORLD.string))
            return

        if (!member.hasIsland)
            return

        if (!member.onIsland(member.getIsland() ?: return)) {
            msg.sendActionMessage(event.player, "listener-messages.cant-place-block")
            event.isCancelled = true
        }
    }
}